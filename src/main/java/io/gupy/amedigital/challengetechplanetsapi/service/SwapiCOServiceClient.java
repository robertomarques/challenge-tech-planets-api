package io.gupy.amedigital.challengetechplanetsapi.service;

import java.util.Collections;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class SwapiCOServiceClient {

	
	@Value("${url.base.swapi.co}")
	public String urlBaseSwapiCO;
	
	
	public Mono<JSONObject> getByName(String name) {		
		WebClient client = WebClient
				  .builder()
				    .baseUrl(urlBaseSwapiCO.concat("planets/?search=").concat(name))				    
				    .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE) 				    
				  .build();
	 Mono<JSONObject> bodyToMono = client.get().retrieve().bodyToMono(JSONObject.class);		
	 return bodyToMono;	
	}
	
	public Integer  getAmountAppearedMoviesByNamePlanet(String name) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		String requestURL = urlBaseSwapiCO.concat("planets/?search=").concat(name);		
		ResponseEntity<String> response = restTemplate.exchange(requestURL,HttpMethod.GET,entity, String.class);
		JSONObject json = new JSONObject(response.getBody());
		return json.getJSONArray("results").getJSONObject(0).getJSONArray("films").length();
	}

}
