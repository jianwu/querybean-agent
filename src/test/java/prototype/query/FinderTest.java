package prototype.query;

import org.avaje.ebean.typequery.agent.BaseTest;
import org.junit.Test;

import static org.junit.Assert.*;


public class FinderTest extends BaseTest {

  @Test
  public void test() {

    Finder finder = new Finder();
    QAddress qAddress = finder.simpleFind();

    assertNotNull(qAddress.version);
    assertNotNull(qAddress.country);
    assertNotNull(qAddress.city);
  }


  @Test
  public void testAssocBeanDepth() {

    Finder finder = new Finder();
    QOrder qOrder = finder.orderQuery();

    assertNotNull(qOrder.id);
    assertNotNull(qOrder.details.product);
  }
}