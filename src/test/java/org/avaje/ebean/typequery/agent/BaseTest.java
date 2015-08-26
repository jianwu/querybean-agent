package org.avaje.ebean.typequery.agent;


import org.avaje.ebean.typequery.agent.offline.MainTransform;

public abstract class BaseTest {

  static String[] transformArgs = {"target/test-classes", "prototype/**","packages=org.example;debug=1;"};

  static {
    MainTransform.main(transformArgs);
  }

}
