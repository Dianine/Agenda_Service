package ddev.com.agenda;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@EnableCaching
@SpringBootApplication
public class AgendaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgendaApplication.class, args);
	}

}
