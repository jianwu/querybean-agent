package org.avaje.ebean.typequery.agent;

import org.avaje.ebean.typequery.agent.asm.ClassVisitor;
import org.avaje.ebean.typequery.agent.asm.Label;
import org.avaje.ebean.typequery.agent.asm.MethodVisitor;
import org.avaje.ebean.typequery.agent.asm.Opcodes;

/**
 * Field information.
 */
public class FieldInfo implements Opcodes {

  private final ClassInfo classInfo;
  private final String name;
  private final String desc;
  private final String signature;

  public FieldInfo(ClassInfo classInfo, String name, String desc, String signature) {
    this.classInfo = classInfo;
    this.name = name;
    this.desc = desc;
    this.signature = signature;
  }

  public String toString() {
    return "name:" + name + " desc:" + desc + " sig:" + signature;
  }

  /**
   * Add the 'property access method' that callers should use (instead of get field).
   */
  public void writeMethod(ClassVisitor cw) {

    // simple why to determine the property is an associated bean type
    boolean assocBean = desc.contains("/QAssoc");

    if (classInfo.isLog(3)) {
      classInfo.log(" ... add method _" + name + " assocBean:" + assocBean);
    }

    MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "_"+name, "()"+desc, "()"+signature, null);
    mv.visitCode();
    Label l0 = new Label();
    mv.visitLabel(l0);
    mv.visitLineNumber(1, l0);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitFieldInsn(GETFIELD, classInfo.getClassName(), name, desc);
    Label l1 = new Label();
    mv.visitJumpInsn(IFNONNULL, l1);
    Label l2 = new Label();
    mv.visitLabel(l2);
    mv.visitLineNumber(2, l2);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitTypeInsn(NEW, desc);
    mv.visitInsn(DUP);
    mv.visitLdcInsn(name);
    mv.visitVarInsn(ALOAD, 0);

    if (assocBean) {
      mv.visitInsn(ICONST_1);
      mv.visitMethodInsn(INVOKESPECIAL, desc, "<init>", "(Ljava/lang/String;Ljava/lang/Object;I)V", false);
    } else {
      mv.visitMethodInsn(INVOKESPECIAL, desc, "<init>", "(Ljava/lang/String;Ljava/lang/Object;)V", false);
    }

    mv.visitFieldInsn(PUTFIELD, classInfo.getClassName(), name, desc);
    mv.visitLabel(l1);
    mv.visitLineNumber(3, l1);
    mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitFieldInsn(GETFIELD, classInfo.getClassName(), name, desc);
    mv.visitInsn(ARETURN);
    Label l3 = new Label();
    mv.visitLabel(l3);
    mv.visitLocalVariable("this", "L" + classInfo.getClassName() + ";", null, l0, l3, 0);
    if (assocBean) {
      mv.visitMaxs(6, 1);
    } else {
      mv.visitMaxs(5, 1);
    }
    mv.visitEnd();
  }
}
