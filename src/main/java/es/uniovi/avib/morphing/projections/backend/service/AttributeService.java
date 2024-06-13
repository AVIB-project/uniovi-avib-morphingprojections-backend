package es.uniovi.avib.morphing.projections.backend.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.AbstractElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchScrollHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import es.uniovi.avib.morphing.projections.backend.domain.AttributeName;
import es.uniovi.avib.morphing.projections.backend.domain.AttributeResponse;
import es.uniovi.avib.morphing.projections.backend.domain.AttributeValue;
import es.uniovi.avib.morphing.projections.backend.domain.Hit;

@Slf4j
@AllArgsConstructor
@Service
public class AttributeService {
	private ElasticsearchOperations operations;
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@SuppressWarnings("unchecked")
	public List<AttributeName> findAllAttributeNames(String indexName, Principal user) {
		AbstractElasticsearchTemplate template = (AbstractElasticsearchTemplate)operations;
		
    	IndexCoordinates index = IndexCoordinates.of(indexName);
    	
    	String filter = "{\"match_all\":{}}";
    	
    	Query queryFilter = StringQuery.builder(filter).build();
    	    	    	
    	Query query = NativeQuery.builder()
    			.withSourceFilter(new FetchSourceFilter(
    					new String[] {"attribute_id"}, 
    					null))
    			.withQuery(queryFilter)
    			.build();
    	
    	SearchScrollHits<AttributeName> scroll = template.searchScrollStart(1000, query, AttributeName.class, index);
        
        List<AttributeName> attributeNames = new ArrayList<>();
        String scrollId = scroll.getScrollId();
        
        while (scroll.hasSearchHits()) {
        	attributeNames.addAll((List<AttributeName>)SearchHitSupport.unwrapSearchHits(scroll.getSearchHits()));
        		
        	simpMessagingTemplate.convertAndSendToUser(user.getName(), "/queue/attribute/names/hit", new Hit(attributeNames.size(), scroll.getTotalHits()));
        	
        	scrollId = scroll.getScrollId();
        	scroll = template.searchScrollContinue(scrollId, 1000, AttributeName.class, index);
        }
        
        template.searchScrollClear(scrollId);
        
        log.info("Total document hits {} for the index {}", attributeNames.size(), indexName);
        
		return attributeNames;
	}
	
	@SuppressWarnings("unchecked")
	public AttributeResponse findAllAttributeValuesByAttributeId(String indexName, String attributeId, String attributeProjection, Principal user) {
		AbstractElasticsearchTemplate template = (AbstractElasticsearchTemplate)operations;
		
    	IndexCoordinates index = IndexCoordinates.of(indexName);

    	AttributeResponse attributeResponse = new AttributeResponse();
    	attributeResponse.setAttributeId(attributeId);
    	attributeResponse.setProjection(attributeProjection);
    	attributeResponse.setValues(new ArrayList<AttributeValue>());
    	
    	String filter = "{\"match\":{\"attribute_id\":\""+ attributeId + "\"}}";
    	
    	Query queryFilter = StringQuery.builder(filter).build();
    	
    	Query query = NativeQuery.builder()
    			.withSourceFilter(new FetchSourceFilter(
    					new String[] {"sample_id", "value"}, 
    					null))
    			.withQuery(queryFilter)
    			.build();
            	
        SearchScrollHits<AttributeValue> scroll = template.searchScrollStart(1000, query, AttributeValue.class, index);
                
        String scrollId = scroll.getScrollId();
        
        while (scroll.hasSearchHits()) {
        	attributeResponse.getValues().addAll((List<AttributeValue>)SearchHitSupport.unwrapSearchHits(scroll.getSearchHits()));
        		
        	simpMessagingTemplate.convertAndSendToUser(user.getName(), "/queue/attribute/values/hit", new Hit(attributeResponse.getValues().size(), scroll.getTotalHits()));
        	
        	scrollId = scroll.getScrollId();
        	scroll = template.searchScrollContinue(scrollId, 1000, AttributeValue.class, index);
        }
        
        template.searchScrollClear(scrollId);
        
        log.info("Total document hits {} for the index {}", attributeResponse.getValues().size(), indexName);  
               
        return attributeResponse;
	}	
}
