package uno.stan.myblogBack;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCaching
@SpringBootApplication
@EnableTransactionManagement
public class MyblogBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyblogBackApplication.class, args);
	}

}
