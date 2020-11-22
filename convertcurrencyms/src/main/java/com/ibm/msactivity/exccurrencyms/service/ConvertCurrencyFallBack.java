package com.ibm.msactivity.exccurrencyms.service;

import org.springframework.stereotype.Component;

@Component
public class ConvertCurrencyFallBack implements ConvertCurrencyFeignClient{

	@Override
	public Double calcCurrencyConversion(String countryCode) {
		System.out.println("CurrencyConversionFallBack:");
		double amount = 0.0;
		return amount;
	}
	
	

}
