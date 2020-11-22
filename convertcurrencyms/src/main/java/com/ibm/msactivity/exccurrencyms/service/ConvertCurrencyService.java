package com.ibm.msactivity.exccurrencyms.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ibm.msactivity.exccurrencyms.model.ConvertCurrency;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;



@Service
public class ConvertCurrencyService {

	@Autowired
	ConvertCurrencyFeignClient fiegnClient;

	@Autowired
	DiscoveryClient discoveryClient;

	@Autowired
	LoadBalancerClient lbClient;


	@Autowired
	RestTemplate lbRestTemplate;

	@Bean	  
	@LoadBalanced 
	RestTemplate getRestTemplate() 
	{ 
		return new RestTemplate(); 
	}

	//Calling directly the other MS - tight coupling
	public BigDecimal calcCurrencyConversionV1(ConvertCurrency cc) {

		String url="http://localhost:8080/currencyfactor/{countryCode}";

		RestTemplate template = new RestTemplate();
		HttpEntity<String> reqEntity = new HttpEntity<String>(cc.getCountryCode());
		ResponseEntity<Double> conversionFactor = template.exchange(url, HttpMethod.GET, reqEntity, Double.class,cc.getCountryCode());
		return convertAmount(conversionFactor.getBody(),cc.getAmount());
	}

	//Eureka Discovery Client
	public BigDecimal calcCurrencyConversionV2(ConvertCurrency cc) {

		List<ServiceInstance> instances = discoveryClient.getInstances("managecurrencyms");

		System.out.println("Number of instances: " + instances.size());

		ServiceInstance instance = instances.get(0);				
		String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/currencyfactor/{countryCode}";

		System.out.println("URL is  " + url);

		RestTemplate template = new RestTemplate();
		HttpEntity<String> reqEntity = new HttpEntity<String>(cc.getCountryCode());
		ResponseEntity<Double> conversionFactor = template.exchange(url, HttpMethod.GET, reqEntity, Double.class,cc.getCountryCode());
		return convertAmount(conversionFactor.getBody(),cc.getAmount());
	}

	//Fiegn Client
	public BigDecimal calcCurrencyConversionV3(com.ibm.msactivity.exccurrencyms.model.ConvertCurrency cc) {	
		Double conversionFactor = fiegnClient.calcCurrencyConversion(cc.getCountryCode());		
		return convertAmount(conversionFactor,cc.getAmount());

	}

	//Load Balancer
	public BigDecimal calcCurrencyConversionV4(ConvertCurrency cc) {

		ServiceInstance instance = lbClient.choose("managecurrencyms");			
		String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/currencyfactor/{countryCode}";
		RestTemplate template = new RestTemplate();
		HttpEntity<String> reqEntity = new HttpEntity<String>(cc.getCountryCode());
		ResponseEntity<Double> conversionFactor = template.exchange(url, HttpMethod.GET, reqEntity, Double.class,cc.getCountryCode());
		return convertAmount(conversionFactor.getBody(),cc.getAmount());
	}
	
	//Load Balancer - REST template
	public BigDecimal calcCurrencyConversionV5(ConvertCurrency cc) {

		String url="http://managecurrencyms/currencyfactor/{countryCode}";	
		HttpEntity<String> reqEntity = new HttpEntity<String>(cc.getCountryCode());
		ResponseEntity<Double> conversionFactor = lbRestTemplate.exchange(url, HttpMethod.GET, reqEntity, Double.class,cc.getCountryCode());
		return convertAmount(conversionFactor.getBody(),cc.getAmount());
	}
	//Hystrix Circuit Breaker
	@HystrixCommand(fallbackMethod = "calculateFallback")
	public BigDecimal calcCurrencyConversionV6(ConvertCurrency cc) {	
		Double conversionFactor = fiegnClient.calcCurrencyConversion(cc.getCountryCode());		
		return convertAmount(conversionFactor,cc.getAmount());

	}

	private BigDecimal calculateFallback(ConvertCurrency cc) {

		return BigDecimal.ZERO;
	}

	public BigDecimal convertAmount(Double conversionFactor,BigDecimal amount) {	
		BigDecimal convertedAmount = new BigDecimal ( conversionFactor).multiply(amount);
		System.out.println("convertedAmount:"+convertedAmount);
		return convertedAmount;
	}

}
