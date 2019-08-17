package com.zx.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.zx.bean.Prize;
/**
 * 奖品配置类，es操作
 * @author lishenbo
 *
 */
public interface PrizeESRepository extends ElasticsearchRepository<Prize, String>{

}
