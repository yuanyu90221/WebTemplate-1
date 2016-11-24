package com.exfantasy.template.deserializer;

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
 * 參考: https://dzone.com/articles/custom-json-deserialization-with-jackson
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
	    
	    String consumeDate = node.get("consumeDate").asText();
	    final LocalDate localDate = LocalDate.parse(consumeDate);
	    final Integer type = node.get("type").asInt();
	    final String prodName = node.get("prodName").asText();
	    final Long amount = node.get("amount").asLong();
	    final String lotteryNo = node.get("lotteryNo").asText();
	    final Boolean got = node.get("got").asBoolean();
	    final Long prize = node.get("prize").asLong();
		
		return new ConsumeVo(localDate, type, prodName, amount, lotteryNo, prize, got);
	}

}
