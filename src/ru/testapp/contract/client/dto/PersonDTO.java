package ru.testapp.contract.client.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * @author pavlin
 *
 * Person data transfer object is used for
 * transferring object to remote server
 * 
 */
public class PersonDTO implements Serializable {
	private static final long serialVersionUID = 130L;
	private int id;
	private String fio;
	private Date birthDate;
	private int passportSerial;
	private int passportNumber;
	
	public PersonDTO() {
		passportSerial = 0;
		passportNumber = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFio() {
		return fio;
	}

	public void setFio(String fio) {
		this.fio = fio;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public int getPassportSerial() {
		return passportSerial;
	}

	public void setPassportSerial(int passportSerial) {
		this.passportSerial = passportSerial;
	}

	public int getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(int passportNumber) {
		this.passportNumber = passportNumber;
	}
}
