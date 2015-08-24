package org.avaje.ebean.typequery.agent;

import java.io.PrintStream;
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

	private final boolean readOnly;

	private final boolean sysoutOnCollect;

	private PrintStream logout;

	private int logLevel;

	/**
	 * Construct a context for enhancement.
	 */
	public EnhanceContext(String agentArgs) {

 		this.ignoreClassHelper = new IgnoreClassHelper(agentArgs);
 		this.agentArgsMap = ArgParser.parse(agentArgs);
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

    this.readOnly = getPropertyBoolean("readonly", false);
		this.sysoutOnCollect = getPropertyBoolean("sysoutoncollect", false);
	}

	/**
	 * Return a value from the agent arguments using its key.
	 */
	public String getProperty(String key){
		return agentArgsMap.get(key.toLowerCase());
	}

	public boolean getPropertyBoolean(String key, boolean dflt){
		String s = getProperty(key);
		if (s == null){
			return dflt;
		} else {
			return s.trim().equalsIgnoreCase("true");
		}
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

	/**
	 * Return true if this should go through the enhancement process but not
	 * actually save the enhanced classes.
	 * <p>
	 * Set this to true to run through the enhancement process without actually
	 * doing the enhancement for debugging etc.
	 * </p>
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

  /**
   * Return true to add some debug sysout via the enhancement.
   */
  public boolean isSysoutOnCollect() {
    return sysoutOnCollect;
  }
}
