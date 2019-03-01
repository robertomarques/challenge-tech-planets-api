package io.gupy.amedigital.challengetechplanetsapi;

import java.util.UUID;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.gupy.amedigital.challengetechplanetsapi.model.Planet;
import io.gupy.amedigital.challengetechplanetsapi.repository.PlanetsRepository;
import io.gupy.amedigital.challengetechplanetsapi.service.SwapiCOServiceClient;

@Configuration
@EnableWebFlux
@ComponentScan("io.gupy.amedigital.challengetechplanetsapi")
public class WebFluxConfiguration {

	@Autowired
	private PlanetsRepository planetsRepository;
	
	@Autowired
	private  SwapiCOServiceClient client;
	
	@Bean
	public RouterFunction<ServerResponse> routerFunctionGetAllPlanet() {
		return RouterFunctions.route()
				.GET("/planets", req -> ServerResponse.ok().body(planetsRepository.findAll(), Planet.class))
				.build();
	}
	
	@Bean
	public RouterFunction<ServerResponse> routerFunctionGetAllPlanetStarWars() {
		return RouterFunctions.route()
				.GET("/planets/starwars", req -> ServerResponse.ok().syncBody(client.findAll()))
				.build();
	}
	
	@Bean
	public RouterFunction<ServerResponse> routerFunctionFindByIdPlanet() {
		return RouterFunctions.route()
				.GET("/planets/{id}", req -> ServerResponse.ok().body(planetsRepository.findById(UUID.fromString(req.pathVariable("id"))), Planet.class))
				.build();
	}
	
	@Bean
	public RouterFunction<ServerResponse> routerFunctionFindByNamePlanet() {
		return RouterFunctions.route()
				.GET("/planets", req -> ServerResponse.ok().body(planetsRepository.findByName(req.queryParam("name").get()), Planet.class))
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> routerFunctionCreatePlanet() {
		return RouterFunctions.route()
				.POST("/planets",
						req -> req.bodyToMono(Planet.class).doOnNext(planet -> { planet.setId(UUID.randomUUID());
							planet.setAmountAppearedMovies(client.getAmountAppearedMoviesByNamePlanet(planet.getName()));
						})								
								.doOnSuccess(planet -> planetsRepository.insert(planet).subscribe())
								.then(ServerResponse.created(null).build()))
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> routerFunctionDeletePlanet() {
		return RouterFunctions.route()
				.DELETE("/planets/{id}",
						req -> planetsRepository.deleteById(UUID.fromString(req.pathVariable("id"))).then(ServerResponse.ok().build()))
				.build();
	}


}
