package com.ibm.msactivity.currencyms.controller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.msactivity.currencyms.model.*;
import com.ibm.msactivity.currencyms.service.CurrencyFactorService;

import antlr.collections.List;

@RestController
public class ConversionFactorController {

//	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CurrencyFactorService factorService;

	@RequestMapping(path = "/currencyfactor")
	public String welcomeMessage() {
		return "Welcome to currency conversion";
	}
	
//	@RequestMapping("/currency-factor/findAll")
//	public List<ConversionFactor> allCountriesList() {
//			
//		return factorService.getAllCountries();
//	}
	
	@RequestMapping(path = "/currencyfactor/{countryCode}")
	public double getConversionFactor(@PathVariable String countryCode) {
		if(countryCode !=null) {
//		logger.info("getConversionFactor : "+ countryCode);
		return factorService.getFactor(countryCode);
		}
		return 0;
	}

	@GetMapping(path = "/getAllCodes", produces = "application/json")
	public ResponseEntity<?> getAllProducts() {
		System.out.println(" ProductController :: getAll Products --------------");
		return new ResponseEntity<>(factorService.getAllCurrencyCodes(), HttpStatus.OK);
	}
	@RequestMapping(value = "/currencyfactor/add", method = RequestMethod.POST)
	public String addConversionFactor(@RequestBody CurrencyBean convFactor) {
		System.out.println(" in add ");
		ResponseEntity<?> responseEntity = null;
		try {
			CurrencyBeanDTO value = factorService.addConversionFactor(convFactor);
			if (value != null) {
				responseEntity = new ResponseEntity<CurrencyBeanDTO>(value, HttpStatus.CREATED);
			} else {
				responseEntity = new ResponseEntity<String>("Exception occured while adding a conversation factor",
						HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception exception) {
			responseEntity = new ResponseEntity<String>("{ Exception occured while adding a conversation factor :" + exception.getMessage() + "}",
					HttpStatus.CONFLICT);
			return "Exceptoin in adding Converion Factor : "+responseEntity.getBody();
		}
		return "Convertion Factor added Successfully";
	}
	
	@RequestMapping(value = "/currencyfactor/update", method = RequestMethod.POST)
	public String updateConversionFactor(@RequestBody CurrencyBean convFactor) {
		ResponseEntity<?> responseEntity = null;
		try {
			CurrencyBeanDTO value = factorService.updateConversionFactor(convFactor);
			if (value != null) {
				responseEntity = new ResponseEntity<CurrencyBeanDTO>(value, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<String>("Exception occured while updating a conversation factor",
						HttpStatus.CONFLICT);
			}

		} catch (Exception exception) {
			responseEntity = new ResponseEntity<String>("{ Exception occured while updating a conversation factor :" + exception.getMessage() + "}",
					HttpStatus.CONFLICT);
			return "Exceptoin in updating Converion Factor : "+responseEntity.getBody();
		}

		return "Convertion Factor updated Successfully";
	}
	
	@RequestMapping(value = "/currencyfactor/delete", method = RequestMethod.POST)
	public String deleteConversionFactor(@RequestBody CurrencyBean convFactor) {
		ResponseEntity<?> responseEntity = null;
		try {
			System.out.println(" in delete"+convFactor.getCountryCode());
//			ConversionFactor convFactor = factorService.getConversionFactor(countryCode);
				factorService.deleteConversionFactor(convFactor);
				responseEntity = new ResponseEntity<CurrencyBean>(HttpStatus.OK);

		} catch (Exception exception) {
			responseEntity = new ResponseEntity<String>("{ Exception occured while deleting a conversation factor :" + exception.getMessage() + "}",
					HttpStatus.CONFLICT);
			return "Exceptoin in deleting Converion Factor : "+responseEntity.getBody();
		}

		return "Convertion Factor deleted Successfully";
	}
	
}