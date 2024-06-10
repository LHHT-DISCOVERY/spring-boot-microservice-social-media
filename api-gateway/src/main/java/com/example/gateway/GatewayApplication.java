package com.example.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class GatewayApplication {

	private static final Logger log = LoggerFactory.getLogger(GatewayApplication.class);

	public static void main(String[] args) throws UnknownHostException {
		SpringApplication app = new SpringApplication(new Class[] {GatewayApplication.class});
		app.setBannerMode(Banner.Mode.CONSOLE);
		Environment env = app.run(args).getEnvironment();
		log.info(
				"Access URLs:\n ------------------------------------------------\n\tLocalhost: http://localhost:{} "
						+ "\n \tExternal:  http://{}:{}\n ------------------------------------------------",
				new Object[] {
						env.getProperty("server.port"),
						InetAddress.getLocalHost().getHostAddress(),
						env.getProperty("server.port")
				});
	}

}
