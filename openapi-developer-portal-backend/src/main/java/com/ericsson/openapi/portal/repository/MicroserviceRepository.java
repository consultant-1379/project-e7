package com.ericsson.openapi.portal.repository;

import com.ericsson.openapi.portal.model.Microservice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MicroserviceRepository extends MongoRepository<Microservice, String>
{

	@Query("{'title': ?0}")
	Optional<Microservice> findByTitle(String title);

	List<Microservice> findByCategory(String category);

	List<Microservice> findByName(String name);

	@Query(value = "{ 'dependencies' : { $size: 0 } }")
	List<Microservice> findByDependenciesIsEmpty();

	List<Microservice> findByTitleIgnoreCaseContaining(String name);

	List<Microservice> findByCategoryAndDependenciesIn(String category, String dependency);

}
