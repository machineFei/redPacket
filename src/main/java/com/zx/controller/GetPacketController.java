package com.zx.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zx.util.IDFactory;

/**
 * 红包生成接口
 * @author admin
 *
 */
@RestController
public class GetPacketController {
	@GetMapping("/getredbag")
	public String getPacket(){
		String packetId = IDFactory.getUUID();
		//红包id存redis，待校验.先存布隆过滤器，快速过滤伪红包，再存redis
		
		return packetId;
	}

}
