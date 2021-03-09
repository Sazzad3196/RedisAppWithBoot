package com.luv2code.RadisAppWithBoot.Logger;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerClass {
	Logger logger = Logger.getLogger("My Logger");
	FileHandler fh;
	
	public Logger getLogger() {
		try {
			fh = new FileHandler("D:/Logger/sazzad");
			logger.addHandler(fh);
			fh.setFormatter(new SimpleFormatter());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return logger;
	}
}
