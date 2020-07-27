package com.SpringBoot.SpringBootPart1;


import com.SpringBoot.SpringBootPart1.service.ConsumeWebService;
import com.nameOfYourPackage.wsdl.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.client.RestTemplate;


import java.net.MalformedURLException;
import java.net.URL;
@SpringBootApplication
@EnableCaching (proxyTargetClass = true)

class Application {
	@Bean
	public RestTemplate getRestTemplate()
	{
		return new RestTemplate();
	}



	public static void main(String[] args)throws NullPointerException {


		SpringApplication.run(Application.class, args);
		try {
			ConsumeWebService consumeWebService = new ConsumeWebService();


			System.out.println("\n"+"List of Git repos"+"\n");
			System.out.println(consumeWebService.ListGitRepos());
			System.out.println("\n"+"List of commits of one repo"+"\n");
			System.out.println(consumeWebService.ListCommitsOfOneRepo());

			System.out.println("Git API"+"\n");
			System.out.println(consumeWebService.getGitV3ApiVersion());

		}
		catch (IndexOutOfBoundsException | HttpMessageConversionException iobe)
		{
			iobe.getMessage();
		}
	}
	@Bean
	static CommandLineRunner lookup() throws MalformedURLException {
		URL url = new URL("http://localhost:9090/ws/countries.wsdl");

		CountriesPortService employee_Service = new CountriesPortService(url);
		CountriesPort employeeServiceProxy = employee_Service.getCountriesPortSoap11();

		GetCountryRequest request = new GetCountryRequest();
		request.setName("United Kingdom");

		GetCountryResponse response = employeeServiceProxy.getCountry(request);

		Currency currency = response.getCountry().getCurrency();
		String capital = response.getCountry().getCapital();
		int population = response.getCountry().getPopulation();

		System.out.println("Currency: " + currency);
		System.out.println("Capital City : " + capital);
		System.out.println("Population: " + population);
		return null;
	}
}
