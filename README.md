#### 使用说明
运行环境
    	
    	JDK 1.7以及以上版本
    
    	Maven 3.3.9以及以上版本

工程chapter1
    
    说明nio 和 oio的区别
    
工程chapter2

    maven构建顺序
    1   构建install netty-in-action
    2   构建chapter2  (勿忘)
    3   构建Service 构建 Client
    4   运行maven插件exec:java
    5   4也可以java运行
        Idea中可直接运行，参数在Run>Edit Configurations localhost 9999
    


This Repository contains the source-code for all chapters of the book [Netty in Action](http://manning.com/maurer)
by Norman Maurer and Marvin Allen Wolfthal.

Latest version: https://github.com/normanmaurer/netty-in-action/tree/2.0-SNAPSHOT

Enjoy! Feedback and PR's welcome!


Prerequisites

	JDK 1.7.0u71 or better

	Maven 3.3.9 or better


If you want to build everything at once, from the top directory run

	mvn install


If you want to build only single projects then from the top directory first run

	mvn install -pl utils


This will make the utils jar available to all the projects.
