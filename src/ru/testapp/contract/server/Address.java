package ru.testapp.contract.server;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import ru.testapp.contract.client.dto.AddressDTO;

@Entity
@Table(name="Address")
public class Address {
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
	
	public Address() {}
	
	public Address(AddressDTO dto) {
		initFieldsByDTO(dto);
	}
	
	public void initFieldsByDTO(AddressDTO dto) {
		state = dto.getState();
		postIndex = dto.getPostIndex();
		republic = dto.getRepublic();
		region = dto.getRegion();
		locality = dto.getLocality();
		street = dto.getStreet();
		house = dto.getHouse();
		housing = dto.getHousing();
		building = dto.getBuilding();
		flat = dto.getFlat();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="state", nullable=false)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name="post_index")
	public String getPostIndex() {
		return postIndex;
	}

	public void setPostIndex(String postIndex) {
		this.postIndex = postIndex;
	}

	@Column(name="republic", nullable=false)
	public String getRepublic() {
		return republic;
	}

	public void setRepublic(String republic) {
		this.republic = republic;
	}

	@Column(name="region")
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Column(name="locality", nullable=false)
	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	@Column(name="street", nullable=false)
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name="house")
	public int getHouse() {
		return house;
	}

	public void setHouse(int house) {
		this.house = house;
	}

	@Column(name="housing")
	public String getHousing() {
		return housing;
	}

	public void setHousing(String housing) {
		this.housing = housing;
	}

	@Column(name="building")
	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	@Column(name="flat", nullable=false)
	public int getFlat() {
		return flat;
	}

	public void setFlat(int flat) {
		this.flat = flat;
	}
}
