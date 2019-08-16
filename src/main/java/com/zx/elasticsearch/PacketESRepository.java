package com.zx.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.zx.bean.Packet;

public interface PacketESRepository extends ElasticsearchRepository<Packet, String>{

}
