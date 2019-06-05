# longteng
多maven模块项目搭建  
lt-parent   父模块  
lt-common   不需要依赖其他子模块  
lt-model    暂时不需要依赖其他子模块  
lt-mapper   依赖model模块，以及mysql,mybatis ...依赖  
lt-service  依赖mapper模块  
lt-simpl    依赖service模块，整合elasticsearch，使用jest连接es集群，并实现mysql数据表数据同步到es中
整合redis    
lt-boot-rebbit 依赖simpl模块整合amqp
lt-shiro 依赖lt-boot-rebbit模块整合shiro 
lt-web     依赖rebbit模板，（这里就不需要引用model,mapper，service,common了，因为已经间接引用了）  

实现elasticsearch,amqp,redis缓存，websocket,微信扫码支付和登陆，获取用户信息，公众号支付等功能
