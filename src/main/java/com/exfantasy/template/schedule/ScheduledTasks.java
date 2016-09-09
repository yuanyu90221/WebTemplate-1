package com.exfantasy.template.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * <pre>
 * Spring Boot 內建排程
 * 
 * 參考:
 * 	<a href="https://spring.io/guides/gs/scheduling-tasks/">Spring Boot 內建排程</a>
 * </pre>
 * 
 * @author tommy.feng
 */
@Component
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        System.out.println("The time is now " + dateFormat.format(new Date()));
    }
}