package org.avaje.ebean.typequery.agent;

/**
 * Set of most interesting constants used by the agent.
 */
public interface Constants {

  /**
   * Annotation used to mark beans that are already enhanced.
   */
  String ANNOTATION_ALREADY_ENHANCED_MARKER = "Lorg/avaje/ebean/typequery/AlreadyEnhancedMarker;";

  /**
   * The TypeQueryBean annotation.
   */
  String ANNOTATION_TYPE_QUERY_BEAN = "Lorg/avaje/ebean/typequery/TypeQueryBean;";

  /**
   * The TQRootBean object class name.
   */
  String TQ_ROOT_BEAN = "org/avaje/ebean/typequery/TQRootBean";

  /**
   * The TQPath object class name.
   */
  String TQ_PATH = "org/avaje/ebean/typequery/TQPath";

  String ASSOC_BEAN_BASIC_CONSTRUCTOR_DESC = "(Ljava/lang/String;Ljava/lang/Object;I)V";

  String ASSOC_BEAN_MAIN_CONSTRUCTOR_DESC =  "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;I)V";

  String ASSOC_BEAN_BASIC_SIG = "(Ljava/lang/String;TR;I)V";

  String ASSOC_BEAN_MAIN_SIG = "(Ljava/lang/String;TR;Ljava/lang/String;I)V";

  /**
   * The path field added to assoc type query beans.
   */
  String FIELD_PATH = "_path";

  /**
   * The root object field added to assoc type query beans.
   */
  String FIELD_ROOT = "_root";

  /**
   * EbeanServer as constructor argument.
   */
  String WITH_EBEANSERVER_ARGUMENT = "(Lcom/avaje/ebean/EbeanServer;)V";

}
