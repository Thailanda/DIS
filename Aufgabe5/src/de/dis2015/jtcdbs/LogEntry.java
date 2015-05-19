package de.dis2015.jtcdbs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-19
 */
public interface LogEntry {

    int getLSN();

    void setLSN(int lsn);

    void write(BufferedWriter writer) throws IOException;

    void read(BufferedReader reader) throws IOException;
}
