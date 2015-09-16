package prototype.query;

import com.avaje.ebean.EbeanServer;
import org.avaje.ebean.typequery.PLong;
import org.avaje.ebean.typequery.PString;
import org.avaje.ebean.typequery.PTimestamp;
import org.avaje.ebean.typequery.TQRootBean;
import org.avaje.ebean.typequery.TypeQueryBean;
import prototype.domain.Product;

@TypeQueryBean
public class QProduct extends TQRootBean<Product,QProduct> {

  public PLong<QProduct> id;
  public PLong<QProduct> version;
  public PTimestamp<QProduct> whenCreated;
  public PTimestamp<QProduct> whenUpdated;
  public PString<QProduct> sku;
  public PString<QProduct> name;

  // minimal code gen
  public QProduct() {
    super(Product.class);
    setRoot(this);
  }

  /**
   * Construct with a given EbeanServer.
   */
  public QProduct(EbeanServer server) {
    super(Product.class, server);
    setRoot(this);
  }

  /**
   * Construct for alias.
   */
  private QProduct(boolean alias) {
    super(alias);
  }

  private static final QProduct _alias = new QProduct(true);

  public static QProduct alias() {
    return _alias;
  }
}
