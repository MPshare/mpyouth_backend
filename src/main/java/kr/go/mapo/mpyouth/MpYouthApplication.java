package kr.go.mapo.mpyouth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@EnableJpaAuditing
public class MpYouthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MpYouthApplication.class, args);
	}

}
