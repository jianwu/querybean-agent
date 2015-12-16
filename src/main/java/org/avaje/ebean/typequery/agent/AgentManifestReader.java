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

  private final Set<String> packageSet = new HashSet<>();

  public static Set<String> read(ClassLoader classLoader, Set<String> initialPackages) {

    try {
      return new AgentManifestReader(initialPackages).readManifests(classLoader);
    } catch (IOException e) {
      // log to standard error and return empty
      System.err.println("QueryBean Agent: error reading META-INF/ebean-typequery.mf manifest resources");
      e.printStackTrace();
      return new HashSet<>();
    }
  }

  /**
   * Construct with some packages defined externally.
   */
  public AgentManifestReader(Set<String> initialPackages) {
    if (initialPackages != null) {
      packageSet.addAll(initialPackages);
    }
  }

  /**
   * Construct with no initial packages (to use with addRaw()).
   */
  public AgentManifestReader() {
  }

  /**
   * Add raw packages: content (used for IDEA plugin etc).
   */
  public void addRaw(String content) {
    add(content.replace("packages:","").trim());
  }

  /**
   * Return the parsed set of packages that type query beans are in.
   */
  public Set<String> getPackages() {
    return packageSet;
  }

  /**
   * Read all the specific manifest files and return the set of packages containing type query beans.
   */
  private Set<String> readManifests(ClassLoader classLoader) throws IOException {
    Enumeration<URL> resources = classLoader.getResources("META-INF/ebean-typequery.mf");
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
