package com.leaderboard.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * SpringBootRestApiApp
 * @author funlu
 *
 */
@SpringBootApplication(scanBasePackages={"com.leaderboard.springboot"})// same as @Configuration @EnableAutoConfiguration @ComponentScan combined
public class SpringBootRestApiApp {

	public static void main(String[] args) {
		//SpringApplication.run(SpringBootRestApiApp.class, args);
		ApplicationContext applicationContext = 
				SpringApplication.run(SpringBootRestApiApp.class, args);
		
		/*for (String name : applicationContext.getBeanDefinitionNames()) {
			System.out.println(name);
		}*/
	}
}
