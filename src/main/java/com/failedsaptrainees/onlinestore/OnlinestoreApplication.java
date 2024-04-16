package com.failedsaptrainees.onlinestore;

import com.failedsaptrainees.onlinestore.DTO.Forms.ProductDTO;
import com.failedsaptrainees.onlinestore.Validators.ProductDTOValidator;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import com.failedsaptrainees.onlinestore.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.print.attribute.standard.Destination;

@ComponentScan(basePackages = "com.failedsaptrainees.onlinestore.*")
@SpringBootApplication
public class OnlinestoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlinestoreApplication.class, args);
	}

}

