package com.zx.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.zx.bean.PrizeRecord;

public interface PrizeRecordESRepository extends ElasticsearchRepository<PrizeRecord,String> {

}
