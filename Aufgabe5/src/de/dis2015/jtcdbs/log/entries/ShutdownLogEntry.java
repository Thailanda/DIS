package de.dis2015.jtcdbs.log.entries;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-20
 */
final public class ShutdownLogEntry extends AbstractLogEntry {
    @Override
    public void write(Writer writer) throws IOException {
        /* Nothing to do */
    }

    @Override
    public void read(Reader reader) throws IOException {
        /* Nothing to do */
    }
}
