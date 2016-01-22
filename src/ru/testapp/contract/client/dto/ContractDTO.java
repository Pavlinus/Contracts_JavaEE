package ru.testapp.contract.client.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pavlin
 *
 * Contract data transfer object is used for
 * transferring object to remote server
 * 
 */
public class ContractDTO implements Serializable {
	private static final long serialVersionUID = 120L;
	private int id;
	private int person_id;
	private int address_id;
	private int realty_id;
	private int insuranceSum;
	private Date dateSince;
	private Date dateTo;
	private int builtYear;
	private String area;
	private Date calculationDate;
	private String bonus;
	private int contractNumber;
	private Date contractDate;
	private String comment;
	
	public ContractDTO() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInsuranceSum() {
		return insuranceSum;
	}

	public void setInsuranceSum(int insuranceSum) {
		this.insuranceSum = insuranceSum;
	}

	public Date getDateSince() {
		return dateSince;
	}

	public void setDateSince(Date dateSince) {
		this.dateSince = dateSince;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public int getBuiltYear() {
		return builtYear;
	}

	public void setBuiltYear(int builtYear) {
		this.builtYear = builtYear;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Date getCalculationDate() {
		return calculationDate;
	}

	public void setCalculationDate(Date calculationDate) {
		this.calculationDate = calculationDate;
	}

	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getPerson_id() {
		return person_id;
	}

	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}

	public int getAddress_id() {
		return address_id;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}

	public int getRealty_id() {
		return realty_id;
	}

	public void setRealty_id(int realty_id) {
		this.realty_id = realty_id;
	}
}
