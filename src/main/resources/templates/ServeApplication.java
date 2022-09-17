package ${package_name};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"org.trips.*", "${package_name}.*"})
public class ServeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServeApplication.class, args);
	}

}
