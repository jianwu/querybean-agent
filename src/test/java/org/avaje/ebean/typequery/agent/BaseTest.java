package org.avaje.ebean.typequery.agent;


import org.avaje.ebean.typequery.agent.offline.MainTransform;

public abstract class BaseTest {

  static String[] transformArgs = {"target/test-classes", "prototype/**","queryBeanPackages=prototype.query;packages=org.example;debug=3;"};

  static {
    MainTransform.main(transformArgs);
  }

}
