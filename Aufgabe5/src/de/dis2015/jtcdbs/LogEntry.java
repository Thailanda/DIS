package de.dis2015.jtcdbs;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-19
 */
public interface LogEntry {

    /**
     * Returns the LSN associated with this log entry.
     */
    int getLSN();

    /**
     * Sets the LSN associated with this log entry.
     */
    void setLSN(int lsn);

    /**
     * Writes entry details.
     *
     * @param writer Writer to write on.
     * @throws IOException when writing fails.
     */
    void write(Writer writer) throws IOException;

    /**
     * Reads entry details.
     *
     * @param reader Reader to read from.
     * @throws IOException when reading fails.
     */
    void read(Reader reader) throws IOException;
}
