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

  private final EnhanceContext enhanceContext;

  private final String className;

  private boolean addedMarkerAnnotation;

  private boolean typeQueryBean;

  private boolean typeQueryUser;

  private boolean alreadyEnhanced;

  private List<FieldInfo> fields;

  public ClassInfo(EnhanceContext enhanceContext, String className) {
    this.enhanceContext = enhanceContext;
    this.className = className;
  }

  /**
   * Return the className.
   */
  public String getClassName() {
    return className;
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
    } else if (isAlreadyEnhancedAnnotation(desc)) {
      alreadyEnhanced = true;
    }
  }

  /**
   * Add the type query bean field. We will create a 'property access' method for each field.
   */
  public void addField(int access, String name, String desc, String signature) {

    if (((access & Opcodes.ACC_PUBLIC) != 0)) {
      if (fields == null) {
        fields = new ArrayList<FieldInfo>();
      }
      fields.add(new FieldInfo(this, name, desc, signature));
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
   * Return the fields for a type query bean.
   */
  public List<FieldInfo> getFields() {
    return fields;
  }

  /**
   * Note that a GETFIELD call has been replaced to method call.
   */
  public void addGetFieldIntercept(String owner, String name) {

    if (isLog(2)) {
      log("change getfield " + owner + " name:" + name);
    }
    typeQueryUser = true;
  }

  public boolean isLog(int level) {
    return enhanceContext.isLog(level);
  }

  public void log(String msg) {
    enhanceContext.log(className, msg);
  }
}
