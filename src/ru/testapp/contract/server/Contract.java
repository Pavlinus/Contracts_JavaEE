package ru.testapp.contract.server;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import ru.testapp.contract.client.dto.ContractDTO;

@Entity
@Table(name="Contract")
public class Contract {
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
	
	public Contract() {}
	
	public Contract(ContractDTO dto) {
		initFieldsByDTO(dto);
	}
	
	public void initFieldsByDTO(ContractDTO dto) {
		person_id = dto.getPerson_id();
		address_id = dto.getAddress_id();
		realty_id = dto.getRealty_id();
		insuranceSum = dto.getInsuranceSum();
		dateSince = dto.getDateSince();
		dateTo = dto.getDateTo();
		builtYear = dto.getBuiltYear();
		area = dto.getArea();
		calculationDate = dto.getCalculationDate();
		bonus = dto.getBonus();
		contractNumber = dto.getContractNumber();
		contractDate = dto.getContractDate();
		comment = dto.getComment();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="insurance_sum", nullable=false)
	public int getInsuranceSum() {
		return insuranceSum;
	}

	public void setInsuranceSum(int insuranceSum) {
		this.insuranceSum = insuranceSum;
	}

	@Column(name="date_since", nullable=false)
	public Date getDateSince() {
		return dateSince;
	}

	public void setDateSince(Date dateSince) {
		this.dateSince = dateSince;
	}

	@Column(name="date_to", nullable=false)
	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	@Column(name="built_year", nullable=false)
	public int getBuiltYear() {
		return builtYear;
	}

	public void setBuiltYear(int builtYear) {
		this.builtYear = builtYear;
	}

	@Column(name="area", nullable=false)
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name="calc_date", nullable=false)
	public Date getCalculationDate() {
		return calculationDate;
	}

	public void setCalculationDate(Date calculationDate) {
		this.calculationDate = calculationDate;
	}

	@Column(name="bonus", nullable=false)
	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	@Column(name="contract_num", nullable=false)
	public int getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(int contractNumber) {
		this.contractNumber = contractNumber;
	}

	@Column(name="contract_date", nullable=false)
	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	@Column(name="comment", nullable=true)
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column(name="person_id", nullable=false)
	public int getPerson_id() {
		return person_id;
	}

	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}

	@Column(name="address_id", nullable=false)
	public int getAddress_id() {
		return address_id;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}

	@Column(name="realty_id", nullable=false)
	public int getRealty_id() {
		return realty_id;
	}

	public void setRealty_id(int realty_id) {
		this.realty_id = realty_id;
	}
	
}
