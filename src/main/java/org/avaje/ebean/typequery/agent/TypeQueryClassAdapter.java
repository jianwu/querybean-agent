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
    this.classInfo = new ClassInfo(name);
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
    if (classInfo.isTypeQueryBean()) {
      // collect type query bean fields
      classInfo.addField(access, name, desc);
    }
    return super.visitField(access, name, desc, signature, value);
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

    if (classInfo.isTypeQueryBean()) {
      // type query bean constructor enhancement
    } else if (classInfo.isTypeQueryUser()) {

    }

    return super.visitMethod(access, name, desc, signature, exceptions);
  }

  @Override
  public void visitEnd() {
    if (classInfo.isTypeQueryBean()) {

    }
    super.visitEnd();
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
