package org.avaje.ebean.typequery.agent;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

/**
 * Used to hold meta data, arguments and log levels for the enhancement.
 */
public class EnhanceContext {

	private final IgnoreClassHelper ignoreClassHelper;

  private final String[] queryBeanPackages;

	private PrintStream logout;

	private int logLevel;

	/**
	 * Construct a context for enhancement.
	 */
	public EnhanceContext(String agentArgs) {

    this.logout = System.out;
    this.queryBeanPackages = convert(AgentManifestReader.read());
    if (queryBeanPackages.length == 0) {
      System.err.println("---------------------------------------------------------------------------");
      System.err.println("TypeQuery Agent: No packages containing type query beans - this won't work.");
      System.err.println("---------------------------------------------------------------------------");
    }

    HashMap<String, String> agentArgsMap = ArgParser.parse(agentArgs);
    String[] packages = parsePackages(agentArgsMap.get("packages"));
    if (packages.length > 0) {
      String[] all = mergePackages(queryBeanPackages, packages);
      this.ignoreClassHelper = new IgnoreClassHelper(all);
    } else {
      // no explicit packages (so use built in ignores)
      this.ignoreClassHelper = new IgnoreClassHelper(new String[0]);
    }

    String debugValue = agentArgsMap.get("debug");
    if (debugValue != null) {
      try {
        logLevel = Integer.parseInt(debugValue.trim());
      } catch (NumberFormatException e) {
        System.err.println("TypeQuery Agent: debug argument [" + debugValue + "] is not an int? ignoring.");
      }
    }
    if (logLevel > 1) {
      log(1, "TypeQuery Agent: queryBeanPackages", Arrays.toString(queryBeanPackages));
      log(1, "TypeQuery Agent: packages", Arrays.toString(packages));
    }
  }

  /**
   * Merge the packages to include in the enhancement (all other packages will now be ignored).
   */
  private String[] mergePackages(String[] packages1, String[] packages2) {

    String[] all = new String[packages1.length + packages2.length];
    System.arraycopy(packages1, 0, all, 0, packages1.length);
    System.arraycopy(packages2, 0, all, packages1.length, packages2.length);
    return all;
  }

  /**
   * Split using delimiter and convert to slash notation.
   */
  private String[] parsePackages(String packages) {
    if (packages == null || packages.trim().length() == 0) {
      return new String[0];
    }
    String[] commaSplit = packages.split(",");
    String[] processPackages = new String[commaSplit.length];
    for (int i = 0; i < commaSplit.length; i++) {
      processPackages[i] = convertPackage(commaSplit[i]);
    }
    return processPackages;
  }

  /**
   * Convert to an array with slash notation.
   */
  private String[] convert(Set<String> pkg) {
    String[] asArray = pkg.toArray(new String[pkg.size()]);
    for (int i = 0; i < asArray.length; i++) {
      asArray[i] = convertPackage(asArray[i]);
    }
    return asArray;
  }

  /**
   * Concert to slash notation taking into account trailing wildcard.
   */
  private String convertPackage(String pkg) {

    pkg = pkg.replace('.','/');
    if (pkg.endsWith("*")) {
      // wild card, remove the * ... and don't add a "." to the end
      return pkg.substring(0, pkg.length() - 1);

    } else if (pkg.endsWith("/")) {
      return pkg;

    } else {
      // add "/" so we don't pick up another
      // package with a similar starting name
      return pkg + "/";
    }
  }

  /**
   * Return true if the owner class is a type query bean.
   * <p>
   * If true typically means the caller needs to change GETFIELD calls to instead invoke the generated
   * 'property access' methods.
   * </p>
   */
  public boolean isTypeQueryBean(String owner) {
    for (int i = 0; i < queryBeanPackages.length; i++) {
      if (owner.startsWith(queryBeanPackages[i])) {
        return true;
      }
    }
    return false;
  }

	/**
	 * Return true if this class should be ignored. That is JDK classes and
	 * known libraries JDBC drivers etc can be skipped.
	 */
	public boolean isIgnoreClass(String className) {
		return ignoreClassHelper.isIgnoreClass(className);
	}

	/**
	 * Change the logout to something other than system out.
	 */
	public void setLogout(PrintStream logout) {
		this.logout = logout;
	}

	/**
	 * Log some debug output.
	 */
	public void log(int level, String msg, String extra) {
		if (logLevel >= level) {
			logout.println(msg + extra);
		}
	}
	
	public void log(String className, String msg) {
		if (className != null) {
			msg = "cls: " + className + "  msg: " + msg;
		}
		logout.println("typequery-enhance> " + msg);
	}
	
	public boolean isLog(int level){
		return logLevel >= level;
	}

	/**
	 * Log an error.
	 */
	public void log(Throwable e) {
		e.printStackTrace(logout);
	}

	/**
	 * Return the log level.
	 */
	public int getLogLevel() {
		return logLevel;
	}

}
