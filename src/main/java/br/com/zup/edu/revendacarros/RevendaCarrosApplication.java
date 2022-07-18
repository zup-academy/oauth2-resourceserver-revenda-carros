package br.com.zup.edu.revendacarros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RevendaCarrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(RevendaCarrosApplication.class, args);
	}

}
