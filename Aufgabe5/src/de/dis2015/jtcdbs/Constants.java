package de.dis2015.jtcdbs;

public class Constants {
	private static final String _persistenceStoragePath = "resources/persistentDataStorage/";
	private static final String _logPath = "resources/logStorage/";
	private static final String _fileExtensionPage = ".ppg"; // ppg ~ PersistentPaGe
	private static final String _fileExtensionLogEntry = ".plog"; // plog ~ PageLog
	private static final String _separator = ";";
	private static final String _beginTransactionMsg = "BOT";
	private static final String _commitMsg = "commit";
	private static final int _commitPage = 999; // used during the logging of a commit of a transaction
	
	public static String getPersistenceStoragePath() {
		return _persistenceStoragePath;
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
	
	public static String getBeginTransactionMessage() {
		return _beginTransactionMsg;
	}
	
	public static String getCommitMessage() {
		return _commitMsg;
	}
	
	public static int getCommitPage() {
		return _commitPage;
	}
}
