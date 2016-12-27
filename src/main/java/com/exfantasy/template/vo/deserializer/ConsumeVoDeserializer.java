package com.exfantasy.template.vo.deserializer;

import java.io.IOException;
import java.time.LocalDate;

import com.exfantasy.template.vo.request.ConsumeVo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * <pre>
 * 前端傳輸過來的記帳資料, 特製 deserializer
 * 
 * 參考: 
 * <a href="https://dzone.com/articles/custom-json-deserialization-with-jackson">Custom JSON Deserialization with Jackson</a>
 * </pre>
 * 
 * @author tommy.feng
 *
 */
public class ConsumeVoDeserializer extends JsonDeserializer<ConsumeVo> {

	@Override
	public ConsumeVo deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectCodec oc = jp.getCodec();
	    JsonNode node = oc.readTree(jp);
	    
	    String sConsumeDate = node.get("consumeDate") != null ? node.get("consumeDate").asText() : null;
	    LocalDate consumeDate = null;
	    if (sConsumeDate != null) {
	    	consumeDate = LocalDate.parse(sConsumeDate);
	    }
	    final Integer type = node.get("type") != null ? node.get("type").asInt() : null;
	    final String prodName = node.get("prodName") != null ? node.get("prodName").asText() : null;
	    final Long amount = node.get("amount") != null ? node.get("amount").asLong() : null;
	    final String lotteryNo = node.get("lotteryNo") != null ? node.get("lotteryNo").asText() : null;
		
		return new ConsumeVo(consumeDate, type, prodName, amount, lotteryNo);
	}

}
