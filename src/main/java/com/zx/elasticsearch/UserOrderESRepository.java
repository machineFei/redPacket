package com.zx.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.zx.bean.UserOrder;
/**
 * 用户集红包排名，es操作类
 * @author lishenbo
 *
 */
public interface UserOrderESRepository extends ElasticsearchRepository<UserOrder, String>{

}
