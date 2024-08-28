package com.ericsson.openapi.portal.mongo;

import com.ericsson.openapi.portal.model.Microservice;
import com.ericsson.openapi.portal.repository.MicroserviceRepository;
import com.github.javafaker.Faker;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class MongoDatabaseSeeder
{

	private final MicroserviceRepository microserviceRepository;

	public MongoDatabaseSeeder(MicroserviceRepository microserviceRepository)
	{
		this.microserviceRepository = microserviceRepository;
	}

	@PostConstruct
	public void seedData()
	{
		microserviceRepository.deleteAll();
		List<Microservice> seedMicroservices = createSeedMicroservices();

		microserviceRepository.saveAll(seedMicroservices);
	}

	private List<Microservice> createSeedMicroservices()
	{
		List<Microservice> microservices = new ArrayList<>();
		Resource resource = new ClassPathResource("data.json");

		try(BufferedReader br = new BufferedReader(new java.io.InputStreamReader(resource.getInputStream())))
		{
			JSONParser jsonParser = new JSONParser();
			JSONArray jsonArray = (JSONArray) jsonParser.parse(br);
			Faker faker = new Faker();

			for(Object o : jsonArray)
			{
				JSONObject jsonObject = (JSONObject) o;
				Microservice microservice = new Microservice();
				microservice.setTitle((String) jsonObject.get("title"));
				microservice.setDescription((String) jsonObject.get("description"));
				microservice.setCategory((String) jsonObject.get("category"));
				microservice.setName(faker.name().fullName());
				microservice.setEmail(faker.internet().emailAddress());
				microservice.setDate((String) jsonObject.get("date"));
				microservice.setVersion((String) jsonObject.get("version"));
				microservice.setDependencies((List<String>) jsonObject.get("dependencies"));
				microservice.setSpecification((String) jsonObject.get("specification"));
				microservices.add(microservice);
			}

			for(Microservice microservice : microservices)
			{
				List<String> dependencies = new ArrayList<>();
				int numberOfDependencies = faker.number().numberBetween(0, 3);
				for(int i = 0; i < numberOfDependencies; i++)
				{
					int randomIndex = faker.number().numberBetween(0, microservices.size());
					while(randomIndex == microservices.indexOf(microservice))
					{
						randomIndex = faker.number().numberBetween(0, microservices.size());
					}
					if(!dependencies.contains(microservices.get(randomIndex).getTitle()))
					{
						dependencies.add(microservices.get(randomIndex).getTitle());
					}
				}
				microservice.setDependencies(dependencies);
			}

		}
		catch(Exception e)
		{
		}

		return microservices;
	}
}
