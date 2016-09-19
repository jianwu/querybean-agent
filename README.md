# querybean-agent
Java agent used to enhance "query beans" and their callers.

Refer to documentation at http://ebean-orm.github.io/docs/query/typesafe

# Node for Play Framework: 
 When use querybean-agent in Playframework, because of the way play load class using different class loader for different library jars and
 application classes, when this agent tried to load manifest file: /META-INF/ebean-typequery.mf. it will failed. 
 So the fix in this repository will try to also look at the agent argument to find the typequery package: for example: 
 **"debug=5;packages=models,controllers;type_query_packages=models.query"** 
 The play framework may also use "sbt-play-enhancer" to convert the public fields into getter methods. That will also interfere with this
 agent. In that case, make sure to disable sbt-play-enhancer by add following lines in the build.sbt file: 
 **playEnhancerEnabled := false**
