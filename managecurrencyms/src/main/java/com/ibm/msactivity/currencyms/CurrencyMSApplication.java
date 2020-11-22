package com.ibm.msactivity.currencyms;


import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.ibm.msactivity.currencyms.model.*;
import com.ibm.msactivity.currencyms.repository.*;
@EnableFeignClients
//@EnableHystrix
//@EnableHystrixDashboard


@EnableDiscoveryClient
@SpringBootApplication
public class CurrencyMSApplication {

	@Autowired
	CurrencyRepository currencyRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(CurrencyMSApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("  First Step Loading database  ");
			
			ArrayList<String> countryCode = new ArrayList<>(Arrays.asList("USA", "CANADA", "RIYADH", "AUST"));
			//ArrayList<?> countryExc = new ArrayList<>(Arrays.asList(75.5, 51, 17.8, 47.5));
			ArrayList<Double> countryExc= new ArrayList<Double>(Arrays.asList(75.38, 51.56, 17.3, 47.5));
             

				for (int i = 0; i <4; i++) {
					//double countryCurrency = Double.parseDouble((String) countryExc.get(i));
					CurrencyBean product = new CurrencyBean(i+1,countryCode.get(i),countryExc.get(i));
					currencyRepo.save(product);
				}
				System.out.println("Database  loaded ");

		};
	}
}

	
