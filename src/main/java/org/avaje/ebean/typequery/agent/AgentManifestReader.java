package org.avaje.ebean.typequery.agent;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * Reads all the META-INF/ebean-typequery.mf resources with the locations of all the
 * type query beans.
 */
public class AgentManifestReader {

  private final Set<String> packageSet = new HashSet<String>();

  public static Set<String> read() {

    try {
      return new AgentManifestReader().readManifests();
    } catch (IOException e) {
      // log to standard error and return empty
      System.err.println("TypeQuery Agent: error reading META-INF/ebean-typequery.mf manifest resources");
      e.printStackTrace();
      return new HashSet<String>();
    }
  }


  /**
   * Read all the specific manifest files and return the set of packages containing type query beans.
   */
  private Set<String> readManifests() throws IOException {
    Enumeration<URL> resources = getClass().getClassLoader().getResources("META-INF/ebean-typequery.mf");
    while (resources.hasMoreElements()) {
      try {
        Manifest manifest = new Manifest(resources.nextElement().openStream());
        Attributes attributes = manifest.getMainAttributes();
        add(attributes.getValue("packages"));
      } catch (IOException e) {
        System.err.println("Error reading META-INF/ebean-typequery.mf manifest resources");
        e.printStackTrace();
      }
    }
    return packageSet;
  }

  /**
   * Collect each individual package splitting by delimiters.
   */
  private void add(String packages) {
    String[] split = packages.split(",|;| ");
    for (int i = 0; i <split.length; i++) {
      String pkg = split[i].trim();
      if (!pkg.isEmpty()) {
        packageSet.add(pkg);
      }
    }
  }
}
