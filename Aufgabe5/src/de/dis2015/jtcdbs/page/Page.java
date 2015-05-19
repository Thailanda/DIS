package de.dis2015.jtcdbs.page;

/**
 * Data Structure to represent a Page in Memeory
 *
 * @author tobias
 */
public class Page {

    /**
     * ID of this page.
     */
    private int id;

    /**
     * LSN number associated with this page.
     */
    private int lsn;

    /**
     * Content data of this page.
     */
    private String data;

    public Page() {
        /* Nothing to do here */
    }

    public Page(int pageId, int lsn, String data) {
        this.id = pageId;
        this.lsn = lsn;
        this.data = data;
    }

    public int getPageId() {
        return id;
    }

    public void setPageId(int id) {
        this.id = id;
    }

    public int getLSN() {
        return lsn;
    }

    public void setLSN(int lsn) {
        this.lsn = lsn;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
