package org.avaje.ebean.typequery.agent;

import org.avaje.ebean.typequery.agent.asm.ClassVisitor;
import org.avaje.ebean.typequery.agent.asm.Label;
import org.avaje.ebean.typequery.agent.asm.Opcodes;

/**
 * Changes the existing constructor to remove all the field initialisation as these are going to be
 * initialised lazily by calls to our generated methods.
 */
public class TypeQueryAssocMainConstructor extends BaseConstructorAdapter implements Opcodes, Constants {

  private final ClassInfo classInfo;

  private final ClassVisitor cv;

  private final String desc;

  private final String signature;

  /**
   * Construct for a query bean class given its associated entity bean domain class and a class visitor.
   */
  public TypeQueryAssocMainConstructor(ClassInfo classInfo, ClassVisitor cv, String desc, String signature) {
    super();
    this.cv = cv;
    this.classInfo = classInfo;
    this.desc = desc;
    this.signature = signature;
  }

  @Override
  public void visitCode() {

    mv = cv.visitMethod(ACC_PUBLIC, "<init>", desc, signature, null);
    mv.visitCode();
    Label l0 = new Label();
    mv.visitLabel(l0);
    mv.visitLineNumber(27, l0);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
    Label l1 = new Label();
    mv.visitLabel(l1);
    mv.visitLineNumber(28, l1);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitVarInsn(ALOAD, 3);
    mv.visitVarInsn(ALOAD, 1);
    mv.visitMethodInsn(INVOKESTATIC, TQ_PATH, "add", "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", false);
    mv.visitFieldInsn(PUTFIELD, classInfo.getClassName(), FIELD_PATH, "Ljava/lang/String;");
    Label l2 = new Label();
    mv.visitLabel(l2);
    mv.visitLineNumber(29, l2);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitVarInsn(ALOAD, 2);
    mv.visitFieldInsn(PUTFIELD, classInfo.getClassName(), FIELD_ROOT, "Ljava/lang/Object;");
    Label l3 = new Label();
    mv.visitLabel(l3);
    mv.visitLineNumber(40, l3);
    mv.visitInsn(RETURN);
    Label l4 = new Label();
    mv.visitLabel(l4);
    mv.visitLocalVariable("this", "L"+classInfo.getClassName()+";", "L"+classInfo.getClassName()+"<TR;>;", l0, l4, 0);
    mv.visitLocalVariable("name", "Ljava/lang/String;", null, l0, l4, 1);
    mv.visitLocalVariable("root", "Ljava/lang/Object;", "TR;", l0, l4, 2);
    mv.visitLocalVariable("prefix", "Ljava/lang/String;", null, l0, l4, 3);
    mv.visitLocalVariable("depth", "I", null, l0, l4, 4);
    mv.visitMaxs(3, 5);
    mv.visitEnd();
  }

}
