package prototype.query.assoc;

import org.avaje.ebean.typequery.PLong;
import org.avaje.ebean.typequery.PString;
import org.avaje.ebean.typequery.PTimestamp;
import org.avaje.ebean.typequery.TQPath;
import org.avaje.ebean.typequery.TypeQueryBean;

@TypeQueryBean
public class QAssocAddress<R> {

  public PLong<R> id;
  public PLong<R> version;
  public PTimestamp<R> whenCreated;
  public PTimestamp<R> whenUpdated;
  public PString<R> line1;
  public PString<R> line2;
  public PString<R> city;
  public QAssocCountry<R> country;

//  public QAssocAddress(String name, R root, int depth) {
//    this(name, root, null, depth);
//  }
//  //String _path;
//  //R _root;
//  public QAssocAddress(String name, R root, String prefix, int depth) {
//    String path = TQPath.add(prefix, name);
//    this.id = new PLong<R>("id", root, path);
//    this.version = new PLong<R>("version", root, path);
//    this.whenCreated = new PTimestamp<R>("whenCreated", root, path);
//    this.whenUpdated = new PTimestamp<R>("whenUpdated", root, path);
//    this.line1 = new PString<R>("line1", root, path);
//    this.line2 = new PString<R>("line2", root, path);
//    this.city = new PString<R>("city", root, path);
//    if (--depth > 0) {
//      this.country = new QAssocCountry<R>("country", root, path);
//    }
//  }

//  public QAssocCountry<R> _foo() {
//    if (country == null) {
//      country = new QAssocCountry<R>("country", _root, _path);
//    }
//    return country;
//  }
}
