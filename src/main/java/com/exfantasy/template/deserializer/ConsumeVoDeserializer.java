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
 * https://dzone.com/articles/custom-json-deserialization-with-jackson
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
	    Integer type = node.get("type").asInt();
	    String prodName = node.get("prodName").asText();
	    Long amount = node.get("amount").asLong();
	    String lotteryNo = node.get("lotteryNo").asText();
	    Boolean got = node.get("got").asBoolean();
	    Long prize = node.get("prize").asLong();
	    
        LocalDate localDate = LocalDate.parse(consumeDate);
		
		return new ConsumeVo(localDate, type, prodName, amount, lotteryNo, prize, got);
	}

}
