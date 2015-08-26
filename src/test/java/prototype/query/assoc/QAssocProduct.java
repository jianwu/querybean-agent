package prototype.query.assoc;

import org.avaje.ebean.typequery.PJodaDateTime;
import org.avaje.ebean.typequery.PLong;
import org.avaje.ebean.typequery.PMonth;
import org.avaje.ebean.typequery.PString;
import org.avaje.ebean.typequery.PTimestamp;
import org.avaje.ebean.typequery.TQPath;
import org.avaje.ebean.typequery.TypeQueryBean;

@TypeQueryBean
public class QAssocProduct<R> {

  public PLong<R> id;
  public PLong<R> version;
  public PTimestamp<R> whenCreated;
  public PTimestamp<R> whenUpdated;
  public PString<R> sku;
  public PString<R> name;
  public PJodaDateTime<R> jdDateTime;
  public PMonth<R> month;

  public QAssocProduct(String name, R root, int depth) {
    this(name, root, null, depth);
  }
  public QAssocProduct(String name, R root, String prefix, int depth) {
    String path = TQPath.add(prefix, name);
    this.id = new PLong<R>("id", root, path);
    this.version = new PLong<R>("version", root, path);
    this.whenCreated = new PTimestamp<R>("whenCreated", root, path);
    this.whenUpdated = new PTimestamp<R>("whenUpdated", root, path);
    this.sku = new PString<R>("sku", root, path);
    this.name = new PString<R>("name", root, path);
    this.jdDateTime = new PJodaDateTime<R>("jdDateTime", root, path);
    this.month = new PMonth<R>("month", root, path);
  }
}
