package org.avaje.ebean.typequery.agent;

import org.avaje.ebean.typequery.agent.asm.AnnotationVisitor;
import org.avaje.ebean.typequery.agent.asm.ClassVisitor;
import org.avaje.ebean.typequery.agent.asm.ClassWriter;
import org.avaje.ebean.typequery.agent.asm.FieldVisitor;
import org.avaje.ebean.typequery.agent.asm.MethodVisitor;
import org.avaje.ebean.typequery.agent.asm.Opcodes;

/**
 */
public class TypeQueryClassAdapter extends ClassVisitor {

  EnhanceContext enhanceContext;

  ClassLoader loader;

  String className;
  String signature;
  ClassInfo classInfo;

  public TypeQueryClassAdapter(ClassWriter cw, EnhanceContext enhanceContext, ClassLoader loader) {
    super(Opcodes.ASM5, cw);
    this.enhanceContext = enhanceContext;
    this.loader = loader;
  }


  @Override
  public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

    super.visit(version, access, name, signature, superName, interfaces);
    if ((access & Opcodes.ACC_INTERFACE) != 0) {
      throw new NoEnhancementRequiredException("Not enhancing interface");
    }
    this.className = name;
    this.signature = signature;
    this.classInfo = new ClassInfo(name);
  }

  /**
   * Extract and return the associated entity bean class from the signature.
   */
  protected String getDomainClass() {
    int posStart = signature.indexOf('<');
    int posEnd = signature.indexOf(';', posStart+1);
    return signature.substring(posStart+2, posEnd);
  }

  /**
   * Look for TypeQueryBean annotation.
   */
  @Override
  public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
    classInfo.checkTypeQueryAnnotation(desc);
    return super.visitAnnotation(desc, visible);
  }

  @Override
  public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
    if (classInfo.isAlreadyEnhanced()) {
      throw new AlreadyEnhancedException(className);
    }
    if (classInfo.isTypeQueryBean()) {
      // collect type query bean fields
      classInfo.addField(access, name, desc, signature);
    }
    return super.visitField(access, name, desc, signature, value);
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

    if (classInfo.isTypeQueryBean()) {
      if (classInfo.addMarkerAnnotation()) {
        addMarkerAnnotation();
      }
      if (name.equals("<init>") && desc.equals("(I)V")) {
        // type query bean constructor enhancement
        log(1, "replace constructor code");
        return new ConstructorAdapter(className, getDomainClass(), cv);
      }
    } else if (classInfo.isTypeQueryUser()) {
      // look for field use of query bean and swap to use our generated methods
    }

    MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
    return mv;
  }

  @Override
  public void visitEnd() {
    if (classInfo.isTypeQueryBean()) {
      AddTypeQueryBeanMethods.add(cv, classInfo);
    }
    super.visitEnd();
  }

  /**
   * Add the marker annotation so that we don't enhance the type query bean twice.
   */
  private void addMarkerAnnotation() {

    if (isLog(4)) {
      log(4, "enhancing - detection ");
    }
    AnnotationVisitor av = cv.visitAnnotation(ClassInfo.ANNOTATION_ALREADY_ENHANCED_MARKER, true);
    if (av != null) {
      av.visitEnd();
    }
  }

  protected boolean isLog(int level) {
    return enhanceContext.isLog(level);
  }

  protected void log(int level, String msg) {
    if (isLog(level)) {
      enhanceContext.log(className, msg);
    }
  }

  protected void log(String msg) {
    enhanceContext.log(className, msg);
  }
}
