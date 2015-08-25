package org.avaje.ebean.typequery.agent;

import org.avaje.ebean.typequery.agent.asm.AnnotationVisitor;
import org.avaje.ebean.typequery.agent.asm.ClassVisitor;
import org.avaje.ebean.typequery.agent.asm.ClassWriter;
import org.avaje.ebean.typequery.agent.asm.FieldVisitor;
import org.avaje.ebean.typequery.agent.asm.MethodVisitor;
import org.avaje.ebean.typequery.agent.asm.Opcodes;

/**
 * Reads/visits the class and performs the appropriate enhancement if necessary.
 */
public class TypeQueryClassAdapter extends ClassVisitor {

  private final EnhanceContext enhanceContext;

  private String className;
  private String signature;
  private ClassInfo classInfo;

  public TypeQueryClassAdapter(ClassWriter cw, EnhanceContext enhanceContext) {
    super(Opcodes.ASM5, cw);
    this.enhanceContext = enhanceContext;
  }


  @Override
  public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

    super.visit(version, access, name, signature, superName, interfaces);
    if ((access & Opcodes.ACC_INTERFACE) != 0) {
      throw new NoEnhancementRequiredException("Not enhancing interface");
    }
    this.className = name;
    this.signature = signature;
    this.classInfo = new ClassInfo(enhanceContext, name);
  }

  /**
   * Extract and return the associated entity bean class from the signature.
   */
  protected String getDomainClass() {
    int posStart = signature.indexOf('<');
    int posEnd = signature.indexOf(';', posStart+1);
    return signature.substring(posStart + 2, posEnd);
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
      if (name.equals("<init>")) {
        if (isLog(3)) {
          log("replace constructor code <init> "+desc);
        }
        // type query bean constructor enhancement
        return new ConstructorAdapter(classInfo, getDomainClass(), cv, desc, signature);
      }
      if (isLog(5)) {
        log("leaving method as is - " + name + " " + desc + " " + signature);
      }
      return super.visitMethod(access, name, desc, signature, exceptions);
    }

    if (isLog(8)) {
      log("... checking method " + name + " " + desc);
    }
    MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
    return new MethodAdapter(mv, enhanceContext, classInfo);
  }

  @Override
  public void visitEnd() {
    if (classInfo.isTypeQueryBean()) {
      AddTypeQueryBeanMethods.add(cv, classInfo);
    } else if (classInfo.isTypeQueryUser() && isLog(1)) {
      classInfo.log("enhanced - getfield calls replaced");
    }
    super.visitEnd();
  }

  /**
   * Add the marker annotation so that we don't enhance the type query bean twice.
   */
  private void addMarkerAnnotation() {

    if (isLog(4)) {
      log("... adding marker annotation");
    }
    AnnotationVisitor av = cv.visitAnnotation(ClassInfo.ANNOTATION_ALREADY_ENHANCED_MARKER, true);
    if (av != null) {
      av.visitEnd();
    }
  }

  protected boolean isLog(int level) {
    return enhanceContext.isLog(level);
  }

  protected void log(String msg) {
    enhanceContext.log(className, msg);
  }
}
