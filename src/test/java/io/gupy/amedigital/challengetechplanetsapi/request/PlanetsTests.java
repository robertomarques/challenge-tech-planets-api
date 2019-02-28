package io.gupy.amedigital.challengetechplanetsapi.request;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanetsTests {
	
		@Test
		@Ignore
		public void testGetAllPlanets() {
			WebTestClient testClient = WebTestClient
					  .bindToServer()
					  .baseUrl("http://localhost:8080/planets/")
					  .build();
					  testClient.get().exchange().expectStatus().is2xxSuccessful().expectBody().equals("TESTE");
		}

	
}
