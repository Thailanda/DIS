package de.dis2015.jtcdbs;

public class Constants {
	private static final String _persistanceStoragePath = "persistentDataStorage/";
	private static final String _logPath = "logStorage/";
	private static final String _fileExtensionPage = ".ppg"; // ppg ~ PersistentPaGe
	private static final String _fileExtensionLogEntry = ".plog"; // plog ~ PageLog
	private static final String _separator = ";";
	
	public static String getPersistancestoragepath() {
		return _persistanceStoragePath;
	}

	public static String getLogPath() {
		return _logPath;
	}

	public static String getFileExtensionPage() {
		return _fileExtensionPage;
	}

	public static String getFileExtensionLogEntry() {
		return _fileExtensionLogEntry;
	}

	public static String getSeparator() {
		return _separator;
	}
}
