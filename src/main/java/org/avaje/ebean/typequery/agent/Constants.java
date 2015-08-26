package org.avaje.ebean.typequery.agent;

/**
 * Set of most interesting constants used by the agent.
 */
public interface Constants {

  String ANNOTATION_ALREADY_ENHANCED_MARKER = "Lorg/avaje/ebean/typequery/AlreadyEnhancedMarker;";

  /**
   * The TypeQueryBean annotation.
   */
  String ANNOTATION_TYPE_QUERY_BEAN = "Lorg/avaje/ebean/typequery/TypeQueryBean;";

  String TQ_ROOT_BEAN = "org/avaje/ebean/typequery/TQRootBean";

  String TQ_PATH = "org/avaje/ebean/typequery/TQPath";

  String ASSOC_BEAN_BASIC_CONSTRUCTOR_DESC = "(Ljava/lang/String;Ljava/lang/Object;I)V";

  String ASSOC_BEAN_MAIN_CONSTRUCTOR_DESC =  "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;I)V";

  String ASSOC_BEAN_BASIC_SIG = "(Ljava/lang/String;TR;I)V";

  String ASSOC_BEAN_MAIN_SIG = "(Ljava/lang/String;TR;Ljava/lang/String;I)V";

  String FIELD_PATH = "_path";
  String FIELD_ROOT = "_root";

}
