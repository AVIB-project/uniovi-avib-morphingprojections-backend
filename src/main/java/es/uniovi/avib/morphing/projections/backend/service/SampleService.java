package es.uniovi.avib.morphing.projections.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.AbstractElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchScrollHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import es.uniovi.avib.morphing.projections.backend.domain.Hit;
import es.uniovi.avib.morphing.projections.backend.domain.Sample;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class SampleService {
	private ElasticsearchOperations operations;
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@SuppressWarnings("unchecked")
	public List<Sample> findAll(String indexName) {				
		AbstractElasticsearchTemplate template = (AbstractElasticsearchTemplate)operations;
		
    	IndexCoordinates index = IndexCoordinates.of(indexName);

        Query query = NativeQuery.builder()
            .withQuery(q -> q.matchAll(ma -> ma))
            .withReactiveBatchSize(1000)
            .build();
    
        SearchScrollHits<Sample> scroll = template.searchScrollStart(1000, query, Sample.class, index);
        
        List<Sample> samples = new ArrayList<>();
        String scrollId = scroll.getScrollId();
        
        while (scroll.hasSearchHits()) {                      	
        	samples.addAll((List<Sample>)SearchHitSupport.unwrapSearchHits(scroll.getSearchHits()));
        		
        	simpMessagingTemplate.convertAndSend("/topic/samples/hit", new Hit(samples.size(), scroll.getTotalHits()));
        	
        	scrollId = scroll.getScrollId();
        	scroll = template.searchScrollContinue(scrollId, 1000, Sample.class, index);
        }
        
        template.searchScrollClear(scrollId);
        
        log.info("Total document hits {} for the index {}", samples.size(), indexName);  
        
        return samples;
    }	
}
