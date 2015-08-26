package prototype.query.assoc;

import org.avaje.ebean.typequery.PDouble;
import org.avaje.ebean.typequery.PInteger;
import org.avaje.ebean.typequery.PLong;
import org.avaje.ebean.typequery.PTimestamp;
import org.avaje.ebean.typequery.TQPath;
import org.avaje.ebean.typequery.TypeQueryBean;

@TypeQueryBean
public class QAssocOrderDetail<R> {

  public PLong<R> id;
  public PLong<R> version;
  public PTimestamp<R> whenCreated;
  public PTimestamp<R> whenUpdated;
  public PInteger<R> orderQty;
  public PInteger<R> shipQty;
  public PDouble<R> unitPrice;
  public QAssocProduct<R> product;

//  private String _path;
//  private R _root;
  public QAssocOrderDetail(String name, R root, int depth) {
    this(name, root, null, depth);
  }
  public QAssocOrderDetail(String name, R root, String prefix, int depth) {
//    _path = TQPath.add(prefix, name);
//    _root = root;
//    this.id = new PLong<R>("id", root, path);
//    this.version = new PLong<R>("version", root, path);
//    this.whenCreated = new PTimestamp<R>("whenCreated", root, path);
//    this.whenUpdated = new PTimestamp<R>("whenUpdated", root, path);
//    this.orderQty = new PInteger<R>("orderQty", root, path);
//    this.shipQty = new PInteger<R>("shipQty", root, path);
//    this.unitPrice = new PDouble<R>("unitPrice", root, path);
//    if (--depth > 0) {
//      this.product = new QAssocProduct<R>("product", root, path, depth);
//    }
  }

//  public PDouble<R> _unitPrice() {
//    if (unitPrice == null) {
//      unitPrice = new PDouble<R>("unitPrice", _root, _path);
//    }
//    return unitPrice;
//  }
//
//  public QAssocProduct<R> _product() {
//    if (product == null) {
//      product = new QAssocProduct<R>("product", _root, _path, 1);
//    }
//    return product;
//  }
}
