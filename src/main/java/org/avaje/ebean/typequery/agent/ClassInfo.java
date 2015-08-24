package org.avaje.ebean.typequery.agent;

import org.avaje.ebean.typequery.agent.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds meta information for a class.
 */
public class ClassInfo {

  public static final String ANNOTATION_ALREADY_ENHANCED_MARKER = "Lorg/avaje/ebean/typequery/AlreadyEnhancedMarker;";

  /**
   * The TypeQueryBean annotation.
   */
  private static final String ANNOTATION_TYPE_QUERY_BEAN = "Lorg/avaje/ebean/typequery/TypeQueryBean;";

  /**
   * The TypeQueryUser annotation.
   */
  private static final String ANNOTATION_TYPE_QUERY_USER = "Lorg/avaje/ebean/typequery/TypeQueryUser;";

  private boolean addedMarkerAnnotation;

  private boolean typeQueryBean;

  private boolean typeQueryUser;

  private boolean alreadyEnhanced;

  private final String className;

  private List<FieldInfo> fields = new ArrayList<FieldInfo>();

  public ClassInfo(String className) {
    this.className = className;
  }

  /**
   * Return true if the bean is already enhanced.
   */
  public boolean isAlreadyEnhanced() {
    return alreadyEnhanced;
  }

  public boolean addMarkerAnnotation() {
    if (addedMarkerAnnotation) {
      return false;
    }
    addedMarkerAnnotation = true;
    return true;
  }

  /**
   * Return true if the bean is a type query bean.
   */
  public boolean isTypeQueryBean() {
    return typeQueryBean;
  }

  /**
   * Return true if the class is explicitly annotated with TypeQueryUser annotation.
   */
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
    } else if (isAlreadyEnhancedAnnotation(desc)) {
      alreadyEnhanced = true;
    }
  }

  public void addField(int access, String name, String desc, String signature) {

    if (((access & Opcodes.ACC_PUBLIC) != 0)) {
      fields.add(new FieldInfo(className, name, desc, signature));
    }
  }

  /**
   * Return true if the annotation is the TypeQueryBean annotation.
   */
  private boolean isAlreadyEnhancedAnnotation(String desc) {
    return ANNOTATION_ALREADY_ENHANCED_MARKER.equals(desc);
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

  /**
   * Return the fields for a type query bean.
   */
  public List<FieldInfo> getFields() {
    return fields;
  }
}
