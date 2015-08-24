package org.avaje.ebean.typequery.agent;


import org.avaje.ebean.typequery.PLong;
import org.junit.Test;
import prototype.query.QAddress;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class EnhanceTest extends BaseTest {

  @Test
  public void test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {


    QAddress qAddress = new QAddress();
    PLong<QAddress> version1 = qAddress.version;
    assertNull(version1);

    Class<?>[] cls= {};
    Method method = qAddress.getClass().getMethod("_version", cls);
    Object result = method.invoke(qAddress, null);
    assertNotNull(result);

    PLong<QAddress> version2 = qAddress.version;
    assertNotNull(version2);
    assertSame(result, version2);

    System.out.println("done");

  }
}
