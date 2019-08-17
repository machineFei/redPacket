package com.zx.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.zx.bean.PrizeRecord;
/**
 * 用户中奖记录 es操作
 * @author lishenbo
 *
 */
public interface PrizeRecordESRepository extends ElasticsearchRepository<PrizeRecord,String> {

}
