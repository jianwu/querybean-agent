package org.avaje.ebean.typequery.agent;

import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


public class AgentManifestReaderTest {

  @Test
  public void testRead() throws Exception {

    Set<String> packages = AgentManifestReader.read();

    assertThat(packages).contains("prototype.query","one.foo","two.bar","three.baz");

  }
}