package com.chinasoft.pojo;

import java.util.HashSet;
import java.util.Set;

/**
 * Region entity. @author MyEclipse Persistence Tools
 */

public class Region implements java.io.Serializable {

	// Fields

	private Integer regionId;
	private String province;
	private String city;
	private String county;
	private Set houseBuyRents = new HashSet(0);
	private Set houseBuyRequests = new HashSet(0);
	private Set houseSellEnterprises = new HashSet(0);
	private Set houseSellSecondhands = new HashSet(0);
	private Set houseSellRents = new HashSet(0);

	// Constructors

	/** default constructor */
	public Region() {
	}

	/** minimal constructor */
	public Region(String province, String city, String county) {
		this.province = province;
		this.city = city;
		this.county = county;
	}

	/** full constructor */
	public Region(String province, String city, String county,
			Set houseBuyRents, Set houseBuyRequests, Set houseSellEnterprises,
			Set houseSellSecondhands, Set houseSellRents) {
		this.province = province;
		this.city = city;
		this.county = county;
		this.houseBuyRents = houseBuyRents;
		this.houseBuyRequests = houseBuyRequests;
		this.houseSellEnterprises = houseSellEnterprises;
		this.houseSellSecondhands = houseSellSecondhands;
		this.houseSellRents = houseSellRents;
	}

	// Property accessors

	public Integer getRegionId() {
		return this.regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public Set getHouseBuyRents() {
		return this.houseBuyRents;
	}

	public void setHouseBuyRents(Set houseBuyRents) {
		this.houseBuyRents = houseBuyRents;
	}

	public Set getHouseBuyRequests() {
		return this.houseBuyRequests;
	}

	public void setHouseBuyRequests(Set houseBuyRequests) {
		this.houseBuyRequests = houseBuyRequests;
	}

	public Set getHouseSellEnterprises() {
		return this.houseSellEnterprises;
	}

	public void setHouseSellEnterprises(Set houseSellEnterprises) {
		this.houseSellEnterprises = houseSellEnterprises;
	}

	public Set getHouseSellSecondhands() {
		return this.houseSellSecondhands;
	}

	public void setHouseSellSecondhands(Set houseSellSecondhands) {
		this.houseSellSecondhands = houseSellSecondhands;
	}

	public Set getHouseSellRents() {
		return this.houseSellRents;
	}

	public void setHouseSellRents(Set houseSellRents) {
		this.houseSellRents = houseSellRents;
	}

}