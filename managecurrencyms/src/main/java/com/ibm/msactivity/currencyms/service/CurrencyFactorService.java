package com.ibm.msactivity.currencyms.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.msactivity.currencyms.model.*;
import com.ibm.msactivity.currencyms.repository.*;

@Service
public class CurrencyFactorService {
	
	@Autowired
	CurrencyRepository conversionRepo;
	
	public CurrencyBean getConversionFactor(Integer id)
	{
		Optional<CurrencyBean> optFactor = conversionRepo.findById(id);
		
		if (optFactor != null)
		{
			return optFactor.get();
		}
		else
			return null;
	}
	
	public CurrencyBean getConversionFactor(String countryCode)
	{
		CurrencyBean optFactor = conversionRepo.findByCountryCode(countryCode);
		
		if (optFactor != null)
		{
			return optFactor;
		}
		else
			return null;
	}
	
	public double getFactor(String countryCode) {
		CurrencyBean conversionValue = conversionRepo.findByCountryCode(countryCode);
		if(null != conversionValue) {
			return conversionValue.getConversionFactor();
		}else {
			return 0;
		} 
	}
	
	public List<CurrencyBean> getAllCurrencyCodes() {
		System.out.println("This method is used to read the all the poducts");
		return conversionRepo.findAll();
	}
	
	
	public List<CurrencyBean> getAllCountries()
	{
		return conversionRepo.findAll();
	}
	

	public CurrencyBeanDTO addConversionFactor(CurrencyBean entity) {
		CurrencyBean convFactor = conversionRepo.saveAndFlush(entity);
		if(null !=convFactor) {
			return createConversionFactorResponseDTO(convFactor);
		} 
		return null;
	}

	public CurrencyBeanDTO updateConversionFactor(CurrencyBean entity) {
		CurrencyBean convFactor = conversionRepo.saveAndFlush(entity);
		if(null !=convFactor) {
			return createConversionFactorResponseDTO(convFactor);
		} 
		return null;
	}

	public void deleteConversionFactor(CurrencyBean entity) {
		CurrencyBean convFactor=conversionRepo.findByCountryCode(entity.getCountryCode());
		conversionRepo.deleteById(convFactor.getId());
	}
	
	
	private CurrencyBeanDTO createConversionFactorResponseDTO( CurrencyBean convFactor) {
		CurrencyBeanDTO cdto = new CurrencyBeanDTO();
		cdto.setCountrycode(convFactor.getCountryCode());
		cdto.setId(convFactor.getId().intValue());
		cdto.setValue(convFactor.getConversionFactor());

		return cdto;
	}
}
