package com.ibm.msactivity.exccurrencyms.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="currency-ms", fallback = ConvertCurrencyFallBack.class)
public interface ConvertCurrencyFeignClient {
	
	@RequestMapping(value ="/currencyfactor/{countryCode}")
	public Double calcCurrencyConversion(@PathVariable("countryCode") String countryCode);

}
