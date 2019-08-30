package com.example.minhaloja;

import com.example.minhaloja.controle.ControladorCliente;
import com.example.minhaloja.controle.ControladorItem;
import com.example.minhaloja.controle.ControladorPedido;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {ControladorCliente.class, ControladorItem.class, ControladorPedido.class})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	
	}

}
