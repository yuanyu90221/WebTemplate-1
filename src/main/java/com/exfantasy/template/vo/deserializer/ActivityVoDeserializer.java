package com.exfantasy.template.vo.deserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exfantasy.template.vo.request.ActivityVo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ActivityVoDeserializer extends JsonDeserializer<ActivityVo> {
	
	private static final Logger logger = LoggerFactory.getLogger(ActivityVoDeserializer.class);
	
	private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public ActivityVo deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectCodec oc = jp.getCodec();
	    JsonNode node = oc.readTree(jp);
	    
	    final String title = node.get("title") != null ? node.get("title").asText() : null;
	    final String description = node.get("description") != null ? node.get("description").asText() : null;
	    String sStartDatetime = node.get("startDatetime") != null ? node.get("startDatetime").asText() : null;
	    Date startDatetime = null;
	    if (sStartDatetime != null) {
			try {
				startDatetime = dateTimeFormat.parse(sStartDatetime);
			} catch (ParseException e) {
				logger.error("Got invalid startDateTime format, content: <{}>", sStartDatetime);
			}
	    }
	    final BigDecimal latitude = node.get("latitude") != null ? new BigDecimal(node.get("latitude").asText()) : null;
	    final BigDecimal longitude = node.get("longitude") != null ? new BigDecimal(node.get("longitude").asText()) : null;
	    final int attendeeNum = node.get("attendeeNum") != null ? node.get("attendeeNum").asInt() : null;
		
		return new ActivityVo(title, description, startDatetime, latitude, longitude, attendeeNum);
	}
}
