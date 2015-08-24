package org.avaje.ebean.typequery.agent;

import org.avaje.ebean.typequery.agent.asm.MethodVisitor;

/**
 * Created by rob on 24/08/15.
 */
public class CallerMethodVisitor extends MethodVisitor {

  public CallerMethodVisitor(int api, MethodVisitor mv) {
    super(api, mv);
  }
}
