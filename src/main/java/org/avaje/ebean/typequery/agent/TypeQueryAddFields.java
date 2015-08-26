package org.avaje.ebean.typequery.agent;

import org.avaje.ebean.typequery.agent.asm.ClassVisitor;
import org.avaje.ebean.typequery.agent.asm.FieldVisitor;
import org.avaje.ebean.typequery.agent.asm.Opcodes;

/**
 * Add the private _path and _root fields for associated type query beans.
 */
public class TypeQueryAddFields implements Opcodes {

  public static void add(ClassVisitor cw) {

    FieldVisitor fv = cw.visitField(ACC_PRIVATE, "_path", "Ljava/lang/String;", null, null);
    fv.visitEnd();

    fv = cw.visitField(ACC_PRIVATE, "_root", "Ljava/lang/Object;", "TR;", null);
    fv.visitEnd();
  }
}
