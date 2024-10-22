package com.example.certs;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CertsApplication {



	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext context = SpringApplication.run(CertsApplication.class, args);
		try (ForkJoinPool pool = new ForkJoinPool()) {
			pool.execute(() -> {
				System.out.println("ClassLoader" + Thread.currentThread().getContextClassLoader());
				SslBundles bundles = context.getBean(SslBundles.class);
				SslBundle sslBundle = bundles.getBundle("test");
				sslBundle.createSslContext();
			});
			pool.awaitTermination(10, TimeUnit.MINUTES);
		}
	}

}
