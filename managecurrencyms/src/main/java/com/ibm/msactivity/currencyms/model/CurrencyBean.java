package com.ibm.msactivity.currencyms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "converionfactor")
@Table(name = "currency_conversion_factor")
public class CurrencyBean {

	@Id
	@Column(name = "ID", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name="COUNTRY_CODE",length = 25,nullable = false,unique=true)
	private String countryCode;

	@Column(name="CONVERSION_FACTOR",precision = 4,nullable = false)
	private double conversionFactor;

	public CurrencyBean() {

	}

	public CurrencyBean(Integer id, String countryCode, double converionFactor) {
		super();
		this.id = id;
		this.countryCode = countryCode;
		this.conversionFactor = converionFactor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public double getConversionFactor() {
		return conversionFactor;
	}

	public void setConversionFactor(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}
}