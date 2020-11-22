package com.ibm.msactivity.currencyms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ibm.msactivity.currencyms.model.CurrencyBean;

@Component
public interface CurrencyRepository extends JpaRepository<CurrencyBean, Integer> {
	CurrencyBean findByCountryCode(String countryCode);

}