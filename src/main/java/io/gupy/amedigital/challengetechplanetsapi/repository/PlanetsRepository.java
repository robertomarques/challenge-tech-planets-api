package io.gupy.amedigital.challengetechplanetsapi.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import io.gupy.amedigital.challengetechplanetsapi.model.Planet;
import reactor.core.publisher.Mono;

@Repository
public interface PlanetsRepository extends ReactiveCassandraRepository<Planet, UUID> {

	
	Mono<Planet> findByName(String name);
}
