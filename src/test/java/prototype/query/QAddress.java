package prototype.query;

import org.avaje.ebean.typequery.PLong;
import org.avaje.ebean.typequery.PString;
import org.avaje.ebean.typequery.PTimestamp;
import org.avaje.ebean.typequery.TQRootBean;
import org.avaje.ebean.typequery.TypeQueryBean;
import prototype.domain.Address;
import prototype.query.assoc.QAssocCountry;

@TypeQueryBean
public class QAddress extends TQRootBean<Address,QAddress> {

  public PLong<QAddress> id;
  public PLong<QAddress> version;
  public PTimestamp<QAddress> whenCreated;
  public PTimestamp<QAddress> whenUpdated;
  public PString<QAddress> line1;
  public PString<QAddress> line2;
  public PString<QAddress> city;
  public QAssocCountry<QAddress> country;

  public QAddress() {
    this(3);
  }
  public QAddress(int maxDepth) {
    super(Address.class);
    setRoot(this);
    this.id = new PLong<QAddress>("id", this);
    this.version = new PLong<QAddress>("version", this);
    this.whenCreated = new PTimestamp<QAddress>("whenCreated", this);
    this.whenUpdated = new PTimestamp<QAddress>("whenUpdated", this);
    this.line1 = new PString<QAddress>("line1", this);
    this.line2 = new PString<QAddress>("line2", this);
    this.city = new PString<QAddress>("city", this);
    //this.country = new QAssocCountry<QAddress>("country", this, maxDepth);
  }

//  public PLong<QAddress> _version() {
//    if (version == null) {
//      version = new PLong<>("version", this);
//    }
//    return version;
//  }
//
//  public QAssocCountry<QAddress> _country() {
//    if (country == null) {
//      country = new QAssocCountry<>("country", this, 1);
//    }
//    return country;
//  }

}
