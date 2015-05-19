package de.dis2015.jtcdbs;

import de.dis2015.jtcdbs.page.Page;
import java.io.IOException;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-05-19
 */
public interface PageManager {

    /**
     * Writes a page on disc.
     *
     * @param page page to write on the disc.
     * @throws IOException
     */
    void writeOnDisc(Page page) throws IOException;

    /**
     * Reads a page from the disc.
     *
     * @param pageId id of the page to read.
     * @return the read page.
     * @throws IOException
     */
    Page readFromDisc(int pageId) throws IOException;

    /**
     * Builds a file name for a given page ID.
     *
     * @param pageId ID of the page to build file name for.
     * @return the built file name.
     */
    String buildPageFileName(int pageId);
}
