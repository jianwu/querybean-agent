package org.avaje.ebean.typequery.agent;

import org.avaje.ebean.typequery.agent.asm.Opcodes;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by rob on 24/08/15.
 */
public class ClassInfo {

  /**
   * The TypeQueryBean annotation.
   */
  private static final String ANNOTATION_TYPE_QUERY_BEAN = "Lorg/avaje/ebean/typequery/TypeQueryBean;";

  /**
   * The TypeQueryUser annotation.
   */
  private static final String ANNOTATION_TYPE_QUERY_USER = "Lorg/avaje/ebean/typequery/TypeQueryUser;";

  private boolean typeQueryBean;

  private boolean typeQueryUser;

  private final String className;

  private Map<String,String> fields = new LinkedHashMap<String,String>();

  public ClassInfo(String className) {
    this.className = className;
  }

  public boolean isTypeQueryBean() {
    return typeQueryBean;
  }

  public boolean isTypeQueryUser() {
    return typeQueryUser;
  }

  /**
   * Check for the type query bean and type query user annotations.
   */
  public void checkTypeQueryAnnotation(String desc) {
    if (isTypeQueryBeanAnnotation(desc)) {
      typeQueryBean = true;
    } else if (isTypeQueryUserAnnotation(desc)) {
      typeQueryUser = true;
    }
  }

  public void addField(int access, String name, String desc) {

    boolean publicAccess = ((access & Opcodes.ACC_PUBLIC) != 0);
    if (publicAccess) {
      fields.put(name, desc);
    }
  }


  /**
   * Return true if the annotation is the TypeQueryBean annotation.
   */
  private boolean isTypeQueryBeanAnnotation(String desc) {
    return ANNOTATION_TYPE_QUERY_BEAN.equals(desc);
  }

  /**
   * Return true if the annotation is the TypeQueryUser annotation.
   */
  private boolean isTypeQueryUserAnnotation(String desc) {
    return ANNOTATION_TYPE_QUERY_USER.equals(desc);
  }

}
