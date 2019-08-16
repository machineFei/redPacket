package com.zx.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.zx.bean.Prize;

public interface PrizeESRepository extends ElasticsearchRepository<Prize, String>{

}
