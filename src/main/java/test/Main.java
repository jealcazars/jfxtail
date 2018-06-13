package test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

	private static Logger LOGGER = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS] [%4$-6s] %2$s %5$s%6$s%n");

		Logger rootLog = Logger.getLogger("");
		rootLog.setLevel(Level.FINE);
		rootLog.getHandlers()[0].setLevel(Level.FINE); 

		LOGGER.fine("Logging an INFO-level message");
	}
}