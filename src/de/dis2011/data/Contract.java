package de.dis2011.data;

import java.sql.Date;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-07
 */
public class Contract extends Entity {
	private int id = -1;
	private String contractNo = "";
	private Date date = new Date(0);
	private String place = "";
	private Estate estate;
	private Person person;

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Estate getEstate() {
		return estate;
	}

	public void setEstate(Estate estate) {
		this.estate = estate;
	}
	

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
