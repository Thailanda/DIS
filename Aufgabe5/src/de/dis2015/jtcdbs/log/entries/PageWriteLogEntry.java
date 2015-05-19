package de.dis2015.jtcdbs.log.entries;

import de.dis2015.jtcdbs.page.Page;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-19
 */
final public class PageWriteLogEntry extends AbstractLogEntry {

    private Page page;
    private int transactionId;

    public PageWriteLogEntry() {
        this.page = new Page();
    }

    public PageWriteLogEntry(Page page, int tx) {
        this.transactionId = tx;
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public int getLSN() {
        return page.getLSN();
    }

    @Override
    public void setLSN(int lsn) {
        page.setLSN(lsn);
    }

    @Override
    public void write(Writer writer) throws IOException {
        writer.write(page.getPageId());

        writer.write(transactionId);

        writer.write(page.getData().length());
        writer.write(page.getData().toCharArray());
    }

    @Override
    public void read(Reader reader) throws IOException {
        page.setPageId(reader.read());

        transactionId = reader.read();

        char[] dataArray = new char[reader.read()];
        reader.read(dataArray);
        page.setData(new String(dataArray));
    }
}
