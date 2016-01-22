package ru.testapp.contract.client.dto;

import java.io.Serializable;

/**
 * @author pavlin
 * 
 * Address data transfer object is used for
 * transferring object to remote server
 * 
 */
public class AddressDTO implements Serializable {
	private static final long serialVersionUID = 110L;
	private int id;
	private String state;
	private String postIndex;
	private String republic;
	private String region;
	private String locality;
	private String street;
	private int house;
	private String housing;
	private String building;
	private int flat;
	
	public AddressDTO() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostIndex() {
		return postIndex;
	}

	public void setPostIndex(String postIndex) {
		this.postIndex = postIndex;
	}

	public String getRepublic() {
		return republic;
	}

	public void setRepublic(String republic) {
		this.republic = republic;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getHouse() {
		return house;
	}

	public void setHouse(int house) {
		this.house = house;
	}

	public String getHousing() {
		return housing;
	}

	public void setHousing(String housing) {
		this.housing = housing;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public int getFlat() {
		return flat;
	}

	public void setFlat(int flat) {
		this.flat = flat;
	}
}
