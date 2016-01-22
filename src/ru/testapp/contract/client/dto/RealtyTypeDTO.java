package ru.testapp.contract.client.dto;

import java.io.Serializable;

/**
 * @author pavlin
 * 
 * Realty type data transfer object is used for
 * transferring object to remote server
 * 
 */
public class RealtyTypeDTO implements Serializable {
	private static final long serialVersionUID = 210L;
	private int id;
	private String name;
	private float coefficient;
	
	public RealtyTypeDTO() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(float coefficient) {
		this.coefficient = coefficient;
	} 
}
