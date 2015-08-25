package prototype.query;

import org.avaje.ebean.typequery.agent.BaseTest;
import org.junit.Test;

import static org.junit.Assert.*;


public class AddressFinderTest extends BaseTest {

  @Test
  public void test() {

    AddressFinder addressFinder = new AddressFinder();
    QAddress qAddress = addressFinder.simpleFind();

    assertNotNull(qAddress.version);
    assertNotNull(qAddress.country);
    assertNotNull(qAddress.city);
  }

}