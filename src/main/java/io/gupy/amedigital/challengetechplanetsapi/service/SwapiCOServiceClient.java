package io.gupy.amedigital.challengetechplanetsapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.gupy.amedigital.challengetechplanetsapi.model.Planet;
import io.gupy.amedigital.challengetechplanetsapi.repository.PlanetsRepository;
import reactor.core.publisher.Mono;

@Service
public class SwapiCOServiceClient {

	
	@Value("${url.base.swapi.co}")
	public String urlBaseSwapiCO;
	
	@Autowired
	private PlanetsRepository planetsRepository;
	
	public Mono<String> getAll(String id) {		
		WebClient client = WebClient
				  .builder()
				    .baseUrl(urlBaseSwapiCO.concat("planets/").concat(id))				    
				    .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE) 				    
				  .build();
	 Mono<String> bodyToMono = client.get().retrieve().bodyToMono(String.class);		
	 return bodyToMono;	
	}
	
	
	public void createPlanet(Planet planet) {
		planetsRepository.insert(planet).subscribe();
	}
}
