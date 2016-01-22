package ru.testapp.contract.server;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Area")
public class AreaCoefficient {
	private int id;
	private float size;
	private float coefficient;
	
	public AreaCoefficient() {}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="size", nullable=false)
	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	@Column(name="coefficient", nullable=false)
	public float getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(float coefficient) {
		this.coefficient = coefficient;
	}
}
