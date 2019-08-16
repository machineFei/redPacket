package com.zx.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.zx.bean.Activity;

public interface ActivityESRepository extends ElasticsearchRepository<Activity, String>{

}
