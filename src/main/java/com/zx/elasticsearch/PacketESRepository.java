package com.zx.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.zx.bean.Packet;
/**
 * 红包记录类 es操作
 * @author lishenbo
 *
 */
public interface PacketESRepository extends ElasticsearchRepository<Packet, String>{

}
