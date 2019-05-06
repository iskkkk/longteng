# longteng
#多maven分布式模块项目搭建  
#lt-parent   父模块  
#lt-common   不需要依赖其他子模块  
#lt-model    暂时不需要依赖其他子模块  
#lt-mapper   依赖model模块，以及mysql,mybatis ...依赖  
#lt-service  依赖model模块  
#lt-simpl    依赖service，mapper模块  
#lt-web     依赖simpl模板，（这里就不需要引用model,mapper，service,common了，以为已经间接引用了）  

