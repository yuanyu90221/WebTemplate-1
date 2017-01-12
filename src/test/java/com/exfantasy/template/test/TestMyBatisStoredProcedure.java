package com.exfantasy.template.test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.exfantasy.template.Application;
import com.exfantasy.template.mybatis.custom.CustomConsumeMapper;
import com.exfantasy.template.mybatis.model.Consume;

@RunWith(SpringRunner.class)
@SpringBootTest(
	classes = Application.class,
	webEnvironment = WebEnvironment.RANDOM_PORT
)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMyBatisStoredProcedure {
	@Autowired
	private CustomConsumeMapper consumeMapper;
	
	@Test
	public void test_1_callSP() throws Exception {
		List<Consume> consumes = consumeMapper.findConsumesByUid(1);

		assertThat(consumes.size()).isEqualTo(2);

		// 成功查出來了
		System.out.println("test_1_callSP -> " + consumes);
		
		// FIXME 但值還沒塞入
	}
}
