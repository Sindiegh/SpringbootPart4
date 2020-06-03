package com.SpringBoot.SpringBootPart1;

import com.SpringBoot.SpringBootPart1.Controller.UserController;
import com.SpringBoot.SpringBootPart1.Doa.FakeRepo;
import com.SpringBoot.SpringBootPart1.service.ConsumeWebService;
import com.SpringBoot.SpringBootPart1.service.UserServiceImplementation;
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
import java.util.Collections;

@SpringBootApplication
@EnableCaching
class Application {
	@Bean
	public RestTemplate getRestTemplate()
	{
		return new RestTemplate();
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

		System.out.println("Currency "+currency);
		System.out.println(""+capital);
		System.out.println(""+population);
		return null;
	}

	public static void main(String[] args)throws NullPointerException {


		SpringApplication application = new SpringApplication(Application.class);
		application.setDefaultProperties(Collections.singletonMap("server.port", "8888"));
		//application.run(args);
		try {

			FakeRepo fakeRepo = new FakeRepo();
			System.out.println(fakeRepo.insertUser(1, "Sindi", "K"));
			System.out.println(fakeRepo.findUserById(1));
			System.out.println(fakeRepo.deleteUser( 1));
			UserServiceImplementation userServiceImplementation = new UserServiceImplementation(fakeRepo);
			System.out.println(userServiceImplementation.addUser(54,"Til","Slip"));
			System.out.println(userServiceImplementation.getUserById(54));
			System.out.println(userServiceImplementation.removeUser(54));

			UserController userController = new UserController(userServiceImplementation);
			System.out.println(userController.updateUser(1));
			System.out.println(userController.removeUser(1));
			System.out.println(userController.getUser(1));
			ConsumeWebService consumeWebService = new ConsumeWebService();
			System.out.println(consumeWebService.ListGitRepos());
			System.out.println(consumeWebService.ListCommitsOfOneRepo());
			lookup();
			System.out.println(consumeWebService.getGitV3ApiVersion());

		}
		catch (IndexOutOfBoundsException | NoSuchMethodException | HttpMessageConversionException | MalformedURLException iobe)
		{
			iobe.getMessage();
		}
	}
}
