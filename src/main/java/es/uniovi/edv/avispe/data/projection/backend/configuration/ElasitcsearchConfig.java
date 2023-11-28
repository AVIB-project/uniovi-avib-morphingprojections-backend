package es.uniovi.edv.avispe.data.projection.backend.configuration;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;

import co.elastic.clients.transport.TransportUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
public class ElasitcsearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.host: localhost}")
    String host;

    @Value("${spring.elasticsearch.port: 9200}")
    String port;
    
    @Value("${spring.elasticsearch.pathPrefix: /}")
    String pathPrefix;
        
    @Value("${spring.elasticsearch.username: elastic}")
    String username;
    
    @Value("${spring.elasticsearch.password: password}")
    String password;

    @Value("${spring.elasticsearch.verifyHostnames: false}")
    boolean verifyHostnames;
    
    @Value("${spring.elasticsearch.validateCertificates: false}")
    boolean validateCertificates;
    
    @Value("classpath:${spring.elasticsearch.caFile:''}")
    Resource caFile;
        
	@Override
	public ClientConfiguration clientConfiguration() {
		try {
			return ClientConfiguration.builder()	    				    
						.connectedTo(host + ":" + port)					
		                .usingSsl(getSSLContext(validateCertificates))						               
		                .withPathPrefix(pathPrefix)
		                .withBasicAuth(username, password)
		                .withClientConfigurer(
		                	ElasticsearchClients.ElasticsearchHttpClientConfigurationCallback.from(clientBuilder -> {
		                		if (!verifyHostnames)
		                			clientBuilder.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
	                            	                            
		                		return clientBuilder;
		                }))
		                .build();
	    } catch (CertificateException e) {
	    	throw new RuntimeException(e);
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException(e);
	    } catch (KeyStoreException e) {
	        throw new RuntimeException(e);
	    } catch (KeyManagementException e) {
	        throw new RuntimeException(e);
	    }
	}
	    
    private SSLContext getSSLContext(boolean validateCertificates) throws
	    CertificateException,
	    IOException, NoSuchAlgorithmException,
	    KeyStoreException,
	    KeyManagementException
	{        
    	SSLContext sslContext;
    	
    	if (validateCertificates) {	    	
			sslContext = TransportUtils.sslContextFromHttpCaCrt(caFile.getFile());				   
    	}
		else {
			final TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
			
			sslContext = SSLContexts.custom()
				.loadTrustMaterial(null, acceptingTrustStrategy)
				.build();					
		}
		 
		return sslContext;
	}
}
