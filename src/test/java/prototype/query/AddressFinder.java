package prototype.query;

public class AddressFinder {


  public QAddress simpleFind() {

    QAddress query = new QAddress();

    query.version.lessThan(3);
//    query.city.like("abc");
//    query.country.code.equalTo("NZ");

    //query._version().lessThan(3);

    return query;
  }
}
