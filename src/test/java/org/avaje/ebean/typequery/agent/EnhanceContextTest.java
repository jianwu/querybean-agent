package org.avaje.ebean.typequery.agent;

import org.junit.Test;

import static org.junit.Assert.*;


public class EnhanceContextTest {

  @Test
  public void testInclude() {

    String args = "queryBeanPackages=org.example.domain.query;packages=org.example";

    EnhanceContext context = new EnhanceContext(args);

    assertTrue(context.isTypeQueryBean("org/example/domain/query/QProduct"));
    assertTrue(context.isTypeQueryBean("org/example/domain/query/assoc/QAssocProduct"));
    assertFalse(context.isTypeQueryBean("org/example/domain/foo/QAssocProduct"));

    assertFalse(context.isIgnoreClass("org/example/domain/query/QProduct"));
    assertFalse(context.isIgnoreClass("org/example/domain/query/assoc/QAssocProduct"));
    assertFalse(context.isIgnoreClass("org/example/finder/MyFinder"));
    assertFalse(context.isIgnoreClass("org/example/MyFinder"));
    assertTrue(context.isIgnoreClass("org/foo/MyFinder"));

  }

}