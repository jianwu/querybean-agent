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

	private DetectQueryBean detectQueryBean;

	private PrintStream logout;

	private int logLevel;

	/**
	 * Construct a context for enhancement.
	 */
	public EnhanceContext(String agentArgs, ClassLoader classLoader, Set<String> initialPackages) {

    this.logout = System.out;

    this.detectQueryBean = Distill.convert(AgentManifestReader.read(classLoader, initialPackages));
    if (detectQueryBean.isEmpty()) {
      System.err.println("---------------------------------------------------------------------------------------------");
      System.err.println("QueryBean Agent: No packages containing query beans - Missing ebean.mf files or is not loadable due to classloader issue");
      System.err.println("QueryBean Agent: Will try type_query_packages agentArgs.");
      System.err.println("---------------------------------------------------------------------------------------------");
    }

    HashMap<String, String> agentArgsMap = ArgParser.parse(agentArgs);
    String[] typeQueryPackages = Distill.parsePackages(agentArgsMap.get("type_query_packages"));
    if (typeQueryPackages.length > 0)
      detectQueryBean = new DetectQueryBean(typeQueryPackages);
    String[] packages = Distill.parsePackages(agentArgsMap.get("packages"));
    if (packages.length > 0) {
      String[] all = Distill.mergePackages(detectQueryBean.getPackages(), packages);
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
        System.err.println("QueryBean Agent: debug argument [" + debugValue + "] is not an int? ignoring.");
      }
    }
    if (logLevel > 1) {
      log(1, "QueryBean Agent: entity bean packages", detectQueryBean.toString());
      log(1, "QueryBean Agent: application packages", Arrays.toString(packages));
    }
  }

  /**
   * Return true if the owner class is a type query bean.
   * <p>
   * If true typically means the caller needs to change GETFIELD calls to instead invoke the generated
   * 'property access' methods.
   * </p>
   */
  public boolean isQueryBean(String owner) {
		return detectQueryBean.isQueryBean(owner);
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
		logout.println("querybean-enhance> " + msg);
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
