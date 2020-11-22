package com.ibm.msactivity.exccurrencyms.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.msactivity.exccurrencyms.model.ConvertCurrency;
import com.ibm.msactivity.exccurrencyms.service.ConvertCurrencyFeignClient;
import com.ibm.msactivity.exccurrencyms.service.ConvertCurrencyService;

@RestController
public class ConvertCurrencyController {
	
	@Autowired
	ConvertCurrencyService service;
	
	@Autowired
	ConvertCurrencyFeignClient fiegnClient;
	
	@RequestMapping(path = "/default")
	public String welcomeMessage() {
		return "Welcome to currency exchange conversion";
	}
	
	// FeignClient
		@GetMapping("/currencyexc/{id}")
		public String convertCurrencyObj(@PathVariable(value = "id") String id) {
		Double conversionFactor = fiegnClient.calcCurrencyConversion(id);		
		System.out.println("--currency value-  "+conversionFactor);
		
		return String.valueOf(conversionFactor);
	}
	
	
	
	//Directly calling other MS 
	@RequestMapping(value="/convertCurrency/v1" , method=RequestMethod.POST)
	public BigDecimal convertCurrency(@RequestBody ConvertCurrency curCon) {
		
		return service.calcCurrencyConversionV2(curCon);
		
	}
	
	//Directly calling other MS 
		@RequestMapping(value="/convertCurrency/v2" , method=RequestMethod.POST)
		public BigDecimal convertCurrencyV2(@RequestBody ConvertCurrency curCon) {
			
			return service.calcCurrencyConversionV2(curCon);
			
		}
		
	//FeignClient
	@RequestMapping(value="/convertCurrency/v3" , method=RequestMethod.POST)
	public BigDecimal convertCurrencyV3(@RequestBody ConvertCurrency curCon) {
		return service.calcCurrencyConversionV3(curCon);
	}
	
	//Load balancer with Ribbon
	@RequestMapping(value="/convertCurrency/v4" , method=RequestMethod.POST)
	public BigDecimal convertCurrencyV4(@RequestBody ConvertCurrency curCon) {
		
		return service.calcCurrencyConversionV4(curCon);
		
	}
	
	//Load balancer -Ribbon - REST Template
	@RequestMapping(value="/convertCurrency/v5" , method=RequestMethod.POST)
	public BigDecimal convertCurrencyV5(@RequestBody ConvertCurrency curCon) {
		
		return service.calcCurrencyConversionV5(curCon);
		
	}
	
	//Hystrix Circuit Breaker
	@RequestMapping(value="/convertCurrency/v6" , method=RequestMethod.POST)
	public BigDecimal convertCurrencyV6(@RequestBody ConvertCurrency curCon) {
		
		return service.calcCurrencyConversionV6(curCon);
		
	}
	

}
