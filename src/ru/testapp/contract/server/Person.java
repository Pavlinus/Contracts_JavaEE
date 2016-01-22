package ru.testapp.contract.server;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import ru.testapp.contract.client.dto.PersonDTO;

@Entity
@Table(name="Person")
public class Person {
	private int id;
	private String fio;
	private Date birthDate;
	private int passportSerial;
	private int passportNumber;
	
	public Person() {}
	
	public Person(PersonDTO person) {
		initFieldsByDTO(person);
	}
	
	public void initFieldsByDTO(PersonDTO person) {
		fio = person.getFio();
		birthDate = person.getBirthDate();
		passportSerial = person.getPassportSerial();
		passportNumber = person.getPassportNumber();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="fio", nullable=false)
	public String getFio() {
		return fio;
	}

	public void setFio(String fio) {
		this.fio = fio;
	}

	@Column(name="birth_date", nullable=false)
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Column(name="passport_serial", nullable=false)
	public int getPassportSerial() {
		return passportSerial;
	}

	public void setPassportSerial(int passportSerial) {
		this.passportSerial = passportSerial;
	}

	@Column(name="passport_num", nullable=false)
	public int getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(int passportNumber) {
		this.passportNumber = passportNumber;
	}
}
