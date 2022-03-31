package com.ml.record.repository;

import com.ml.record.model.Record;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RecordRepository extends ElasticsearchRepository<Record, String> {
    
}
