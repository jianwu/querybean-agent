package org.avaje.ebean.typequery.agent;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Used to hold meta data, arguments and log levels for the enhancement.
 */
public class EnhanceContext {

  private static final Logger logger = Logger.getLogger(EnhanceContext.class.getName());

	private final IgnoreClassHelper ignoreClassHelper;

	private final HashMap<String, String> agentArgsMap;

  private final String[] queryBeanPackages;

	private PrintStream logout;

	private int logLevel;

	/**
	 * Construct a context for enhancement.
	 */
	public EnhanceContext(String agentArgs) {

    this.agentArgsMap = ArgParser.parse(agentArgs);
    String[] queryBeans = parsePackages(agentArgsMap.get("querybeanpackages"));
    String[] packages = parsePackages(agentArgsMap.get("packages"));
    if (packages.length > 0) {
      String[] all = mergePackages(queryBeans, packages);
      this.ignoreClassHelper = new IgnoreClassHelper(all);
    } else {
      // no explicit packages (so use built in ignores)
      this.ignoreClassHelper = new IgnoreClassHelper(new String[0]);
    }

    this.queryBeanPackages = queryBeans;//convertDotToSlash(queryBeans);
    this.logout = System.out;

		String debugValue = agentArgsMap.get("debug");
 		if (debugValue != null) {
			try {
				logLevel = Integer.parseInt(debugValue.trim());
			} catch (NumberFormatException e) {
				String msg = "agent debug argument [" + debugValue+ "] is not an int? ignoring.";
        System.err.println(msg);
				logger.log(Level.WARNING, msg);
			}
		}
    if (logLevel > 1) {
      log(1, "TypeQuery Agent: queryBeanPackages", Arrays.toString(queryBeanPackages));
      log(1, "TypeQuery Agent: packages", Arrays.toString(packages));
    }
	}

  private String[] mergePackages(String[] packages1, String[] packages2) {

    String[] all = new String[packages1.length + packages2.length];
    System.arraycopy(packages1, 0, all, 0, packages1.length);
    System.arraycopy(packages2, 0, all, packages1.length + 0, packages2.length);
    return all;
  }

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

  private String convertPackage(String pkg) {

    pkg = pkg.replace('.','/');
    if (pkg.endsWith("*")) {
      // wild card, remove the * ... and don't add a "." to the end
      return pkg.substring(0, pkg.length() - 1);

    } else if (pkg.endsWith("/")) {
      // already ends in "/"
      return pkg;

    } else {
      // add "." so we don't pick up another
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
		logout.println("transform> " + msg);
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
