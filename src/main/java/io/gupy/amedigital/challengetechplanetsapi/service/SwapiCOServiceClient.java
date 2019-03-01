package io.gupy.amedigital.challengetechplanetsapi.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger logger = LoggerFactory.getLogger(SwapiCOServiceClient.class);
	
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
		Integer ammount = 0;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		String requestURL = urlBaseSwapiCO.concat("planets/?search=").concat(name);		
		ResponseEntity<String> response = restTemplate.exchange(requestURL,HttpMethod.GET,entity, String.class);
		JSONObject json;
		try {
			json = new JSONObject(response.getBody());
			JSONArray jsonArray = json.getJSONArray("results").getJSONObject(0).getJSONArray("films");
			ammount = jsonArray.length();
		} catch (Exception e) {		
			logger.error("ERRO AO EFETUAR PARSE JSON:",e);
		}
		return ammount;
	}
	
	public List<Object> findAll() {
		String url = urlBaseSwapiCO.concat("planets");
		List<Object> planets = findAll(url);		
		logger.info("Planets size: "+planets.size());
		return planets;
	}
	
	public List<Object> findAll(String url) {	
		List<Object> planets = new ArrayList<Object>();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);			
		ResponseEntity<String> response = restTemplate.exchange(url,HttpMethod.GET,entity, String.class);
		JSONObject json;	
		try {
			json = new JSONObject(response.getBody());			
			planets.addAll(json.getJSONArray(("results")).toList());
		    			
			if(!json.isNull("next")) {
				planets.addAll(findAll(json.getString("next")));
			}
			  
			
		} catch (Exception e) {
			logger.error("ERRO AO EFETUAR PARSE JSON:",e);
		}
		return planets;	
	
	}

}
