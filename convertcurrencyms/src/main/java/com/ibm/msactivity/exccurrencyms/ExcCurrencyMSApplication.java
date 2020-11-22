package com.ibm.msactivity.exccurrencyms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
//import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients("com.ibm.msactivity.convertcurrencyms.service")
@EnableHystrix
@EnableHystrixDashboard
public class ExcCurrencyMSApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExcCurrencyMSApplication.class, args);
	}

}
