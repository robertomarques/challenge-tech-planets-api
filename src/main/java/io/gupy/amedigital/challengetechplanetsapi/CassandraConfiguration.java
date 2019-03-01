package io.gupy.amedigital.challengetechplanetsapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

@Configuration
@EnableReactiveCassandraRepositories
public class CassandraConfiguration extends AbstractReactiveCassandraConfiguration {

	    @Value("${cassandra.port}") 
	    private int port;
	    @Value("${cassandra.keyspace}") 
	    private String keyspace;
			   
	    
		@Override
		public String[] getEntityBasePackages() {
			return new String[] {
		            "io.gupy.amedigital.challengetechplanetsapi.repository"
		        };
		}
		@Override
		protected String getKeyspaceName() {
			return keyspace;
		}

		@Override
		protected int getPort() {
			return port;
		}
		@Override
		public SchemaAction getSchemaAction() {		
			return SchemaAction.CREATE_IF_NOT_EXISTS;
		}
		@Override
		protected boolean getMetricsEnabled() {
			return false;
		}
	    
		
		
	    
	   
}
