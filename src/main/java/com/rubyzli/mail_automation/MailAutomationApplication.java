package com.rubyzli.mail_automation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MailAutomationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailAutomationApplication.class, args);
	}

}
