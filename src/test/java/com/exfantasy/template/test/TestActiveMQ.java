package com.exfantasy.template.test;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

import com.exfantasy.template.Application;
import com.exfantasy.template.jms.Producer;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
	classes = Application.class,
	webEnvironment = WebEnvironment.RANDOM_PORT
)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestActiveMQ {

	@Rule
	public OutputCapture outputCapture = new OutputCapture();
	
	@Autowired
	private Producer producer;
	
	@Test
	public void testSendMsgToQAndCaptureSystemOut() throws InterruptedException {
		producer.send("Test message");
		Thread.sleep(1000L);
		assertThat(outputCapture.toString().contains("Received message: <Test message>")).isTrue();
	}
}
