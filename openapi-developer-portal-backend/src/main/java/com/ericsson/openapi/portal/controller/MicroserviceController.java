package com.ericsson.openapi.portal.controller;

import com.ericsson.openapi.portal.exception.MicroserviceCollectionException;
import com.ericsson.openapi.portal.model.Microservice;
import com.ericsson.openapi.portal.service.MicroserviceService;
import com.github.javafaker.Faker;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@OpenAPIDefinition(
		info = @Info(
				title = "Microservices Developer Portal",
				description = "The Microservices Developer Portal is a sophisticated and intuitive platform designed to empower development teams in managing the complexities of microservices architecture. As organizations increasingly adopt microservices to build scalable, modular, and efficient applications, the need for a centralized solution to oversee, document, and visualize the intricate web of microservices and their interdependencies becomes paramount.\n\nWith a focus on enhancing collaboration, fostering seamless communication, and ensuring efficient development processes, the portal provides an extensive array of features. From documenting individual microservice details, categorizing services, and tracking historical versions, to identifying dependencies, facilitating search, and offering dependency visualization, the Microservices Developer Portal streamlines the entire microservice lifecycle.\n\nThe portal's versioned OpenAPI specifications provide a standardized way to define, document, and share each microservice's interface. This ensures clear communication and alignment among cross-functional teams, accelerates development, and minimizes potential errors due to misunderstandings. Through detailed endpoint descriptions, data models, and error responses, developers can confidently interact with microservices while adhering to best practices.\n\nThe comprehensive search facility enables teams to swiftly locate microservices based on names, categories, and even discover dependent microservices, along with their respective lead engineer contact details. Visualizing microservice dependencies using interactive visualizations, powered by D3.js, offers a graphical representation of relationships, fostering deeper insights into the impact of changes and aiding decision-making when planning modifications.\n\nBuilt on Java with Spring Boot, the portal ensures reliability, scalability, and flexibility. Utilizing Docker and Docker Compose for packaging and deployment simplifies deployment across environments, reducing configuration discrepancies and ensuring consistent behavior.\n\nIn addition to technical excellence, quality assurance is upheld through integration with SonarQube, assuring top-notch code quality and security. Automated tests, including a minimum of 80% test coverage, guarantee the stability and correctness of the portal's functionality.\n\nThe Microservices Developer Portal, residing within a Git repository and utilizing Gerrit for code reviews, represents a collaborative effort towards excellence. Optional integration with Jenkins automates the build process, further enhancing the development pipeline's efficiency.\n\nWhether you're a microservices engineer, an architect, or a project manager, the Microservices Developer Portal empowers you to navigate the intricate world of microservices architecture with confidence, enabling you to deliver robust and resilient applications in the ever-evolving landscape of modern software development.",
				version = "v1"
		),
		tags = {
				@Tag(name = "Microservices", description = "APIs related to managing microservices"),
				@Tag(name = "Search", description = "APIs related to searching microservices"),
				@Tag(name = "List", description = "APIs related to listing microservices"),
				@Tag(name = "Utility", description = "Utility APIs"),
		},
		servers = {
				@Server(
						url = "http://localhost:8080",
						description = "Backend Server"
				),
				@Server(
						url = "https://localhost:3000",
						description = "Frontend Server"
				),
				@Server(
						url = "https://localhost:9000",
						description = "SonarQube Server"
				)
		}
)
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MicroserviceController
{

	@Autowired
	private MicroserviceService microserviceService;

	//region GET
	@GetMapping("/utility/health")
	@Operation(summary = "Check health of the application")
	@Tag(name = "Utility")
	public <T> ResponseEntity<T> checkHealth() {
		// You can replace String with the desired type for the ResponseEntity body
		T responseBody = (T) "Congratulations!! Open API Application is up and running..";
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
	}
	@GetMapping("/get/source")
	@Operation(summary = "Get all microservices")
	@Tag(name = "Microservices")
	public <T> ResponseEntity<List<T>> getAllMicroservices() {
		List<T> microservices = (List<T>) microserviceService.getAllMicroservices(); // Assuming microserviceService is defined elsewhere
		HttpStatus status = !microservices.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(microservices, status);
	}

	@GetMapping("/get/{id}")
	@Operation(summary = "Get microservice by id")
	@Tag(name = "Microservices")
	public <T> ResponseEntity<T> getMicroserviceById(@PathVariable("id") String id) {
		try {
			Microservice microservice = microserviceService.getMicroserviceById(id); // Assuming microserviceService is defined elsewhere
			return (ResponseEntity<T>) new ResponseEntity<>(microservice, HttpStatus.OK);
		} catch (MicroserviceCollectionException e) {
			return (ResponseEntity<T>) new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/search/category/{category}")
	@Operation(summary = "Search microservices by category")
	@Tag(name = "Search")
	public <T> ResponseEntity<List<T>> searchMicroservicesByCategory(@PathVariable("category") String category) {
		List<T> microservices = (List<T>) microserviceService.searchMicroservicesByCategory(category); // Assuming microserviceService is defined elsewhere
		HttpStatus status = !microservices.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(microservices, status);
	}

	@GetMapping("/search/lead/{leadName}")
	@Operation(summary = "Search microservices by lead")
	@Tag(name = "Search")
	public <T> ResponseEntity<List<T>> searchMicroservicesByLead(@PathVariable("leadName") String leadName) {
		List<T> microservices = (List<T>) microserviceService.searchMicroservicesByLead(leadName); // Assuming microserviceService is defined elsewhere
		HttpStatus status = !microservices.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(microservices, status);
	}

	@GetMapping("/search/name/{name}")
	@Operation(summary = "Search microservices by name")
	@Tag(name = "Search")
	public <T> ResponseEntity<List<T>> searchMicroservicesByName(@PathVariable("name") String name) {
		List<T> microservices = (List<T>) microserviceService.searchMicroservicesByName(name); // Assuming microserviceService is defined elsewhere
		HttpStatus status = !microservices.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(microservices, status);
	}

	@GetMapping("/list/category+dependencies/{category}/{dependency}")
	@Operation(summary = "List microservices by category and dependencies")
	@Tag(name = "List")
	public <T> ResponseEntity<List<T>> listMicroservicesInCategoryWithDependencies(
			@PathVariable("category") String category,
			@PathVariable("dependency") String dependency) {
		List<T> microservices = (List<T>) microserviceService.listMicroservicesInCategoryWithDependencies(category, dependency); // Assuming microserviceService is defined elsewhere
		HttpStatus status = !microservices.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(microservices, status);
	}

	@GetMapping("/list/dependencies/none")
	@Operation(summary = "List microservices with no dependencies")
	@Tag(name = "List")
	public <T> ResponseEntity<List<T>> listMicroservicesWithNoDependencies() {
		List<T> microservices = (List<T>) microserviceService.listMicroservicesWithNoDependencies(); // Assuming microserviceService is defined elsewhere
		HttpStatus status = !microservices.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(microservices, status);
	}
	//endregion

	//region POST
	@Operation(summary = "Create a new microservice")
	@Tag(name = "Microservices")

	@PostMapping("/create/new/{title}/{description}/{category}/{name" +
	             "}/{email" +
	             "}/{version}/{dependencies}/{specification}")
	public ResponseEntity<String> createMicroservice(
			@PathVariable("title") String title,
			@PathVariable("description") String description,
			@PathVariable("category") String category,
			@PathVariable("name") String name,
			@PathVariable("email") String email,
			@PathVariable("version") String version,
			@PathVariable("dependencies") String dependency,
			@PathVariable("specification") String specification)
	{
		List<String> dependencies = Arrays.asList(dependency.split(","));
		Date date = new Date();
		specification = "https://" + specification;
		try
		{
			Microservice microservice = new Microservice(
					URLDecoder.decode(title, StandardCharsets.UTF_8),
					URLDecoder.decode(description, StandardCharsets.UTF_8),
					URLDecoder.decode(category, StandardCharsets.UTF_8),
					URLDecoder.decode(name, StandardCharsets.UTF_8),
					URLDecoder.decode(email, StandardCharsets.UTF_8),
					date,
					URLDecoder.decode(version, StandardCharsets.UTF_8),
					dependencies,
					specification);

			microserviceService.createMicroservice(microservice);
			return new ResponseEntity<>("MICROSERVICE CREATED SUCCESSFULLY", HttpStatus.CREATED);
		}
		catch(MicroserviceCollectionException e)
		{
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	// create random microservice
	@PostMapping("/create/random")
	@Operation(summary = "Create a new microservice")
	@Tag(name = "Microservices")
	public ResponseEntity<String> createMicroservice() throws MicroserviceCollectionException
	{
		List<Microservice> microservices = microserviceService.getAllMicroservices();

		String[] categories = {"Analytics", "Application", "Automation", "Communication", "Connectivity", "Database",
				"Management", "Marketplace", "Monitoring", "Security", "Subscription"};

		Faker faker = new Faker();

		Microservice microservice = new Microservice();
		microservice.setTitle(faker.hacker().abbreviation() + " " + faker.gameOfThrones().character() + " API");
		microservice.setDescription(faker.gameOfThrones().quote() + " " + faker.lorem().paragraph());
		microservice.setCategory(categories[faker.number().numberBetween(0, categories.length - 1)]);
		microservice.setName(faker.name().fullName());
		microservice.setEmail(faker.internet().emailAddress());
		microservice.setVersion("3.0." + faker.number().numberBetween(0, 3));

		List<String> dependencies = new ArrayList<>();

		int numberOfDependencies = faker.number().numberBetween(0, 3);

		for(int i = 0; i < numberOfDependencies; i++)
		{
			int randomIndex = faker.number().numberBetween(0, microservices.size());
			while(randomIndex == microservices.indexOf(microservice))
			{
				randomIndex = faker.number().numberBetween(0, microservices.size());
			}
			if(microservices.isEmpty())
			{
				break;
			}
			else if(!dependencies.contains(microservices.get(randomIndex).getTitle()))
			{
				dependencies.add(microservices.get(randomIndex).getTitle());
			}
		}
		microservice.setDependencies(dependencies);
		microservice.setSpecification("https://editor.swagger.io/");
		microservice.setDate();
		microserviceService.createMicroservice(microservice);
		return new ResponseEntity<>("MICROSERVICE CREATED SUCCESSFULLY", HttpStatus.OK);
	}

	//endregion

	//region PUT
	@PutMapping("/update/{id}/{title}/{description}/{category}/{name}/{email}/{version}/{dependencies}/{specification}")
	@Operation(summary = "Update a microservice")
	@Tag(name = "Microservices")
	public ResponseEntity<String> updateMicroservice(
			@PathVariable("id") String id,
			@PathVariable("title") String title,
			@PathVariable("description") String description,
			@PathVariable("category") String category,
			@PathVariable("name") String name,
			@PathVariable("email") String email,
			@PathVariable("version") String version,
			@PathVariable("dependencies") String dependency,
			@PathVariable("specification") String specification)
	{
		try
		{
			microserviceService.updateMicroservice(id, title, description, category, name, email, version, dependency,
			                                       specification);
			return new ResponseEntity<>("UPDATE REQUEST FOR MICROSERVICE WITH ID SUCCESSFUL", HttpStatus.OK);
		}
		catch(MicroserviceCollectionException e)
		{
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	//endregion

	//region DELETE
	@DeleteMapping("/delete/id/{id}")
	@Operation(summary = "Delete a microservice by id")
	@Tag(name = "Microservices")
	public ResponseEntity<String> deleteMicroserviceById(@PathVariable("id") String id)
	{
		try
		{
			microserviceService.deleteMicroserviceById(id);
			return new ResponseEntity<>("DELETE REQUEST FOR MICROSERVICE WITH ID SUCCESSFUL", HttpStatus.OK);
		}
		catch(MicroserviceCollectionException e)
		{
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/name/{name}")
	@Operation(summary = "Delete a microservice by name")
	@Tag(name = "Microservices")
	public ResponseEntity<String> deleteMicroserviceByName(@PathVariable("name") String id)
	{
		try
		{
			microserviceService.deleteMicroserviceById(id);
			return new ResponseEntity<>("Successfully deleted Microservice with id " + id, HttpStatus.OK);
		}
		catch(MicroserviceCollectionException e)
		{
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/all")
	@Operation(summary = "Delete all microservices")
	@Tag(name = "Microservices")
	public ResponseEntity<String> deleteAllMicroservices()
	{
		try
		{
			microserviceService.deleteAllMicroservices();
			return new ResponseEntity<>("ALL MICROSERVICES DELETED", HttpStatus.OK);
		}
		catch(MicroserviceCollectionException e)
		{
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	//endregion
}
