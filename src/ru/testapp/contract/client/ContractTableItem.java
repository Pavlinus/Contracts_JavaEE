package ru.testapp.contract.client;

import java.io.Serializable;
import java.util.Date;

import ru.testapp.contract.client.dto.ContractDTO;
import ru.testapp.contract.client.dto.PersonDTO;

/**
 * @author pavlin
 *
 * Class defines row object displayed at start activity table
 * 
 */
public class ContractTableItem implements Serializable {
	private static final long serialVersionUID = 100L;
	private int id;
	private int contractNumber;
	private Date contractDate;
	private String personName;
	private float bonus;
	private Date since;
	private Date to;

	public ContractTableItem() {}
	
	public ContractTableItem(ContractDTO contract, PersonDTO person) {
		id = contract.getId();
		bonus = Float.parseFloat(contract.getBonus());
		contractDate = contract.getContractDate();
		contractNumber = contract.getContractNumber();
		personName = person.getFio();
		since = contract.getDateSince();
		to = contract.getDateTo();
	}

	public int getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(int contractNumber) {
		this.contractNumber = contractNumber;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public float getBonus() {
		return bonus;
	}

	public void setBonus(float bonus) {
		this.bonus = bonus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getSince() {
		return since;
	}

	public void setSince(Date since) {
		this.since = since;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}
}
