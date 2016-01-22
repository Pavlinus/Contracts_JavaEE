package ru.testapp.contract.client.dto;

import java.io.Serializable;

/**
 * @author pavlin
 * 
 * Year coefficient data transfer object is used for
 * transferring object to remote server
 * 
 */
public class YearCoefficientDTO implements Serializable {
	private static final long serialVersionUID = 310L;
	private int id;
	private int year;
	private float coefficient;
	
	public YearCoefficientDTO() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public float getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(float coefficient) {
		this.coefficient = coefficient;
	}
}
