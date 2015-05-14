package de.dis2015.jtcdbs.page;

/**
 * Data Structure to represent a Page in Memeory
 * @author tobias
 *
 */
public class Page {
	private int _pageNo;
	private int _lsn;
	private String _data;
	
	public Page(int pageNo, int lsn, String data) {
		this._pageNo = pageNo;
		this._lsn = lsn;
		this._data = data;
	}
	
	public int getPageNo() {
		return _pageNo;
	}
	public void setPageNo(int _pageNo) {
		this._pageNo = _pageNo;
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
