package com.ibm.msactivity.currencyms.model;


public class CurrencyBeanDTO {

	private int id;
	private String countrycode;
	private String countryName;
	private double value;
	

	public CurrencyBeanDTO(int id, String countrycode, String countryName, double value) {
		super();
		this.id = id;
		this.setCountrycode(countrycode);
		this.setCountryName(countryName);
		this.setValue(value);
	}


	public CurrencyBeanDTO() {
		super();
	}
	
	public CurrencyBeanDTO(String countrycode, double value) {
		super();
		
		this.setCountrycode(countrycode);
		this.setValue(value);
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ConcurrencyFactorDTO [id=" + id + ", countrycode=" + countrycode + ", value=" + value +"]";
	}


	public String getCountrycode() {
		return countrycode;
	}


	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}


	public String getCountryName() {
		return countryName;
	}


	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}


	public double getValue() {
		return value;
	}


	public void setValue(double value) {
		this.value = value;
	}
	

}
