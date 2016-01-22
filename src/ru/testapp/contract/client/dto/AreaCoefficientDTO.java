package ru.testapp.contract.client.dto;

import java.io.Serializable;

/**
 * @author pavlin
 *
 * Area coefficient data transfer object is used for
 * transferring object to remote server
 * 
 */
public class AreaCoefficientDTO implements Serializable {
	private static final long serialVersionUID = 300L;
	private int id;
	private float size;
	private float coefficient;
	
	public AreaCoefficientDTO() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public float getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(float coefficient) {
		this.coefficient = coefficient;
	}
}
