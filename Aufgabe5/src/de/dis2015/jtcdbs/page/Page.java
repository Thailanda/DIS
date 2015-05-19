package de.dis2015.jtcdbs.page;

/**
 * Data Structure to represent a Page in Memeory
 * @author tobias
 *
 */
public class Page {
	private int _pageId;
	private int _lsn;
	private String _data;
	
	public Page(int pageId, int lsn, String data) {
		this._pageId = pageId;
		this._lsn = lsn;
		this._data = data;
	}
	
	public int getPageId() {
		return _pageId;
	}
	public void setPageId(int _pageId) {
		this._pageId = _pageId;
	}
	public int getLSN() {
		return _lsn;
	}
	public void setLSN(int _lsn) {
		this._lsn = _lsn;
	}
	public String getData() {
		return _data;
	}
	public void setData(String _data) {
		this._data = _data;
	}
}
