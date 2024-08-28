package com.ericsson.openapi.portal.service;

import com.ericsson.openapi.portal.exception.MicroserviceCollectionException;
import com.ericsson.openapi.portal.model.Microservice;
import com.ericsson.openapi.portal.repository.MicroserviceRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MicroserviceService
{

	@Autowired
	private MicroserviceRepository microserviceRepository;

	public List<Microservice> getAllMicroservices()
	{
		List<Microservice> microservices = microserviceRepository.findAll();
		if(!microservices.isEmpty())
		{
			return microservices;
		}
		else
		{
			return new ArrayList<>();
		}
	}

	public void createMicroservice(Microservice microservice)
	throws ConstraintViolationException, MicroserviceCollectionException
	{
		Optional<Microservice> microserviceTitleOptional = microserviceRepository
				.findByTitle(microservice.getTitle());
		if(microserviceTitleOptional.isPresent())
		{
			throw new MicroserviceCollectionException(
					MicroserviceCollectionException.titleAlreadyExists());
		}
		else
		{
			microserviceRepository.save(microservice);
		}

	}

	public void updateMicroservice(
			String id, String title, String description, String category,
			String name, String email, String version, String dependency,
			String specification)
	throws ConstraintViolationException, MicroserviceCollectionException
	{
		Optional<Microservice> microserviceWithId = microserviceRepository.findById(id);
		Optional<Microservice> microserviceWithSameTitle = microserviceRepository
				.findByTitle(title);
		if(microserviceWithId.isPresent())
		{
			if(microserviceWithSameTitle.isPresent()
			   && !microserviceWithSameTitle.get().getId().equals(id))
			{
				throw new MicroserviceCollectionException(
						MicroserviceCollectionException.titleAlreadyExists());
			}
			Microservice microserviceToUpdate = microserviceWithId.get();
			microserviceToUpdate.setTitle(title);
			microserviceToUpdate.setDescription(description);
			microserviceToUpdate.setCategory(category);
			microserviceToUpdate.setName(name);
			microserviceToUpdate.setEmail(email);
			microserviceToUpdate.setVersion(version);
			List<String> dependencyList = Arrays.asList(dependency.split(","));
			microserviceToUpdate.setDependencies(dependencyList);
			microserviceToUpdate.setSpecification(specification);

			microserviceRepository.save(microserviceToUpdate);
		}
		else
		{
			throw new MicroserviceCollectionException(
					MicroserviceCollectionException.notFoundException());
		}
	}

	public void deleteMicroserviceById(String id) throws MicroserviceCollectionException
	{
		Optional<Microservice> microserviceOptional = microserviceRepository.findById(id);
		if(!microserviceOptional.isPresent())
		{
			throw new MicroserviceCollectionException(MicroserviceCollectionException.notFoundException());
		}
		else
		{
			microserviceRepository.deleteById(id);
		}
	}

	public void deleteAllMicroservices() throws MicroserviceCollectionException
	{
		List<Microservice> microservices = microserviceRepository.findAll();
		if(!microservices.isEmpty())
		{
			microserviceRepository.deleteAll();
		}
		else
		{
			throw new MicroserviceCollectionException("No microservices found");
		}
	}

	public Microservice getMicroserviceById(String id) throws MicroserviceCollectionException
	{
		Optional<Microservice> microserviceOptional = microserviceRepository.findById(id);
		if(microserviceOptional.isPresent())
		{
			return microserviceOptional.get();
		}
		else
		{
			throw new MicroserviceCollectionException(MicroserviceCollectionException.notFoundException());
		}
	}

	public List<Microservice> searchMicroservicesByCategory(String category)
	{
		return microserviceRepository.findByCategory(category);
	}

	public List<Microservice> searchMicroservicesByLead(String leadName)
	{
		return microserviceRepository.findByName(leadName);
	}

	public List<Microservice> searchMicroservicesByName(String name)
	{
		return microserviceRepository.findByTitleIgnoreCaseContaining(name);
	}

	public List<Microservice> listMicroservicesInCategoryWithDependencies(String category, String dependency)
	{
		return microserviceRepository.findByCategoryAndDependenciesIn(category, dependency);
	}

	public List<Microservice> listMicroservicesWithNoDependencies()
	{
		return microserviceRepository.findByDependenciesIsEmpty();
	}

}
