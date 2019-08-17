package com.zx.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.zx.bean.Activity;

/**
 * 活动配置类 es操作
 * @author lishenbo
 *
 */
public interface ActivityESRepository extends ElasticsearchRepository<Activity, String>{

}
