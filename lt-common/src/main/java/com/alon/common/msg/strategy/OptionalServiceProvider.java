package com.alon.common.msg.strategy;

import com.alon.common.msg.strategy.enums.OptionServiceEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName OptionalServiceProvider
 * @Description 可选的服务提供者,通版（通用版本）提供服务接口的默认实现,如果业务流水号不为空则获取对应的实际服务实例
 * 如果业务流水号为空或DEFAULT，则取默认实现
 * @Author zoujiulong
 * @Date 2019/10/29 13:54
 * @Version 1.0
 **/
public class OptionalServiceProvider<T,S extends T> implements BeanFactoryAware {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ConfigurableListableBeanFactory beanFactory;

    private Map<String,T> serviceMap;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof DefaultListableBeanFactory) {
            this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
        }
    }

    /**
      * 方法表述: 使用默认实现
      * @Author zoujiulong
      * @Date 14:01 2019/10/29
      * @param
      * @return T
    */
    public T getService() {
        return serviceMap.get(OptionServiceEnum.DEFAULT.getCode());
    }

    /**
      * 方法表述: 根据传入的编号获取服务
      * @Author zoujiulong
      * @Date 14:03 2019/10/29
      * @param       bizNum
      * @return T
    */
    public T getService(String bizNum) {
        if (null != serviceMap.get(bizNum)) {
            return serviceMap.get(bizNum);
        }
        return serviceMap.get(OptionServiceEnum.DEFAULT.getCode());
    }

    @PostConstruct
    public void init(){
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] types = pt.getActualTypeArguments();

        Class<T> interfaceClazz = (Class<T>)types[0];
        Class<S> defaultImplClazz = (Class<S>)types[1];

        logger.info("可选服务初始化，服务接口为{}，默认实现为{}", interfaceClazz.getName(), defaultImplClazz.getName());

        Map<String, T> serviceBeanMap = beanFactory.getBeansOfType(interfaceClazz);
        serviceMap = new HashMap<String , T>(serviceBeanMap.size());


        for (T processor : serviceBeanMap.values()) {
            if (!(processor instanceof OptionServiceSelector)) {
                // 如果实现类没有实现OptionalServiceSelector接口，则无法识别业务流水，报错
                throw new RuntimeException("可选服务必须实现OptionalServiceSelector接口！");
            }

            // 如果已经存在相同业务流水的服务，则抛出错误
            OptionServiceSelector selector = (OptionServiceSelector)processor;
            if (null != serviceMap.get(selector.getBizNum())) {
                throw new RuntimeException("已经存在编号【" + selector.getBizNum() + "】的服务");
            }

            serviceMap.put(selector.getBizNum(), processor);
        }
    }
}
