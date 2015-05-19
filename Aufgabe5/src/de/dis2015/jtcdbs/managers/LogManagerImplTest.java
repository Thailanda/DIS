package de.dis2015.jtcdbs.managers;

import de.dis2015.jtcdbs.LogEntry;
import de.dis2015.jtcdbs.LogManager;
import de.dis2015.jtcdbs.log.entries.PageWriteLogEntry;
import de.dis2015.jtcdbs.log.entries.ShutdownLogEntry;
import de.dis2015.jtcdbs.page.Page;
import java.io.StringReader;
import java.io.StringWriter;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-19
 */
public class LogManagerImplTest {

    public static final String PAGE_WRITE_STR = "*/de.dis2015.jtcdbs.log.entries.PageWriteLogEntryԹ\u0017\u001CHallo Welt, wie geht es dir?\n";
    public static final String SHUTDOWN_STR = "\u0015.de.dis2015.jtcdbs.log.entries.ShutdownLogEntry\n";
    private LogManager logManager;

    @Before
    public void setUp() throws Exception {
        logManager = new LogManagerImpl();
    }

    @org.junit.Test
    public void testWriteLogEntry() throws Exception {
        try (StringWriter writer = new StringWriter()) {
            PageWriteLogEntry pageWrite = new PageWriteLogEntry();
            pageWrite.setPage(new Page(1337, 42, "Hallo Welt, wie geht es dir?"));
            pageWrite.setTransactionId(23);
            assertEquals("", writer.toString());
            logManager.writeLogEntry(writer, pageWrite);
            assertEquals(PAGE_WRITE_STR, writer.toString());

            ShutdownLogEntry shutdown = new ShutdownLogEntry();
            shutdown.setLSN(21);
            logManager.writeLogEntry(writer, shutdown);
            assertEquals(PAGE_WRITE_STR + SHUTDOWN_STR, writer.toString());

            writer.flush();
        }
    }

    @org.junit.Test
    public void testReadLogEntry() throws Exception {
        try (StringReader reader = new StringReader(PAGE_WRITE_STR + SHUTDOWN_STR)) {
            LogEntry logEntry;

            reader.reset();

            logEntry = logManager.readLogEntry(reader);
            assertTrue(logEntry instanceof PageWriteLogEntry);

            PageWriteLogEntry pageWrite = (PageWriteLogEntry) logEntry;
            assertEquals(42, pageWrite.getLSN());
            assertEquals(1337, pageWrite.getPage().getPageId());
            assertEquals(23, pageWrite.getTransactionId());
            assertEquals("Hallo Welt, wie geht es dir?", pageWrite.getPage().getData());

            logEntry = logManager.readLogEntry(reader);
            assertTrue(logEntry instanceof ShutdownLogEntry);
        }
    }
}
