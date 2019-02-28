package io.gupy.amedigital.challengetechplanetsapi.model;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("planet")
public class Planet {

	
	@PrimaryKey 	
    private UUID id;
	  
	private String name;
	private String climate;
	private String ground;
	private int amountAppearedMovies;
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClimate() {
		return climate;
	}
	public void setClimate(String climate) {
		this.climate = climate;
	}
	public String getGround() {
		return ground;
	}
	public void setGround(String ground) {
		this.ground = ground;
	}
	public int getAmountAppearedMovies() {
		return amountAppearedMovies;
	}
	public void setAmountAppearedMovies(int amountAppearedMovies) {
		this.amountAppearedMovies = amountAppearedMovies;
	}
	
	
}
