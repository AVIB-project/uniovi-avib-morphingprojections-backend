package es.uniovi.avib.morphing.projections.backend.service;

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

import com.google.common.collect.Ordering;
import com.google.common.primitives.Floats;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import es.uniovi.avib.morphing.projections.backend.domain.AttributeName;
import es.uniovi.avib.morphing.projections.backend.domain.AttributeValue;
import es.uniovi.avib.morphing.projections.backend.domain.Hit;

@Slf4j
@AllArgsConstructor
@Service
public class AttributeService {
	private ElasticsearchOperations operations;
	private SimpMessagingTemplate simpMessagingTemplate;
	
	private List<AttributeValue> normalize(List<AttributeValue> attributes) {
		Ordering<AttributeValue> o = new Ordering<AttributeValue>() {
		    @Override
		    public int compare(AttributeValue left, AttributeValue right) {
		        return Floats.compare(left.getValue(), right.getValue());
		    }
		};
		
		AttributeValue maxAttribute = o.max(attributes);
		AttributeValue minAttribute = o.min(attributes);
		
		for (int i = 0; i < attributes.size(); i++) {
			attributes.get(i).setValue(1 - (attributes.get(i).getValue() - minAttribute.getValue()) / (maxAttribute.getValue() - minAttribute.getValue()));
		}
		
		return attributes;
	}
	
	@SuppressWarnings("unchecked")
	public List<AttributeName> findAllAttributeNamesBySample(String indexName, String sampleId) {
		AbstractElasticsearchTemplate template = (AbstractElasticsearchTemplate)operations;
		
    	IndexCoordinates index = IndexCoordinates.of(indexName);
    	
    	String filter = "{\"match\":{\"sample_id\":{\"query\":\"" + sampleId + "\",\"operator\":\"and\"}}}";
    	
    	Query queryFilter = StringQuery.builder(filter).build();
    	    	    	
    	Query query = NativeQuery.builder()
    			.withSourceFilter(new FetchSourceFilter(
    					new String[] {"attribute"}, 
    					null))
    			.withQuery(queryFilter)
    			.build();
    	
    	SearchScrollHits<AttributeName> scroll = template.searchScrollStart(1000, query, AttributeName.class, index);
        
        List<AttributeName> attributeNames = new ArrayList<>();
        String scrollId = scroll.getScrollId();
        
        while (scroll.hasSearchHits()) {
        	attributeNames.addAll((List<AttributeName>)SearchHitSupport.unwrapSearchHits(scroll.getSearchHits()));
        		
        	simpMessagingTemplate.convertAndSend("/topic/attribute/names/hit", new Hit(attributeNames.size(), scroll.getTotalHits()));
        	
        	scrollId = scroll.getScrollId();
        	scroll = template.searchScrollContinue(scrollId, 1000, AttributeName.class, index);
        }
        
        template.searchScrollClear(scrollId);
        
        log.info("Total document hits {} for the index {}", attributeNames.size(), indexName);
        
		return attributeNames;
	}
	
	@SuppressWarnings("unchecked")
	public List<AttributeValue> findAllAttributeValuesByName(String indexName, String attributeName) {
		AbstractElasticsearchTemplate template = (AbstractElasticsearchTemplate)operations;
		
    	IndexCoordinates index = IndexCoordinates.of(indexName);

    	String filter = "{\"match\":{\"attribute\":\""+ attributeName + "\"}}";
    	
    	Query queryFilter = StringQuery.builder(filter).build();
    	
    	Query query = NativeQuery.builder()
    			.withSourceFilter(new FetchSourceFilter(
    					new String[] {"sample_id", "attribute", "value"}, 
    					null))
    			.withQuery(queryFilter)
    			.build();
            	
        SearchScrollHits<AttributeValue> scroll = template.searchScrollStart(1000, query, AttributeValue.class, index);
                
        List<AttributeValue> attributes = new ArrayList<>();
        String scrollId = scroll.getScrollId();
        
        while (scroll.hasSearchHits()) {
        	attributes.addAll((List<AttributeValue>)SearchHitSupport.unwrapSearchHits(scroll.getSearchHits()));
        		
        	simpMessagingTemplate.convertAndSend("/topic/attribute/values/hit", new Hit(attributes.size(), scroll.getTotalHits()));
        	
        	scrollId = scroll.getScrollId();
        	scroll = template.searchScrollContinue(scrollId, 1000, AttributeValue.class, index);
        }
        
        template.searchScrollClear(scrollId);
        
        log.info("Total document hits {} for the index {}", attributes.size(), indexName);  
        
        return normalize(attributes);        
	}	
}
