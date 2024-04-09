package com.failedsaptrainees.onlinestore;

import com.failedsaptrainees.onlinestore.Validators.ProductDTOValidator;
import com.failedsaptrainees.onlinestore.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

@ComponentScan(basePackages = "com.failedsaptrainees.onlinestore.*")
@SpringBootApplication
public class OnlinestoreApplication {

	@Autowired
	private RoleRepository roleRepository;
	public static void main(String[] args) {
		SpringApplication.run(OnlinestoreApplication.class, args);
	}

}
