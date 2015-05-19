package de.dis2015.jtcdbs.log.entries;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-19
 */
final public class PageWriteLogEntry extends AbstractLogEntry {
    private int transactionId;
    private int pageId;
    private String redoData;

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public String getRedoData() {
        return redoData;
    }

    public void setRedoData(String redoData) {
        this.redoData = redoData;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public void write(BufferedWriter writer) throws IOException {
        writer.write(pageId);
        writer.write(transactionId);
        writer.write(redoData.length());
        writer.write(redoData.toCharArray());
    }

    @Override
    public void read(BufferedReader reader) throws IOException {
        pageId = reader.read();
        transactionId = reader.read();
        char[] redoData = new char[reader.read()];
        reader.read(redoData);
        this.redoData = new String(redoData);
    }
}
