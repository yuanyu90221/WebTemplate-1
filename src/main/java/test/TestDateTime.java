package test;

import java.time.LocalDateTime;

public class TestDateTime {
	public static void main(String[] args) {
		String strDatewithTime = "2015-08-04T10:11:30";
		LocalDateTime aLDT = LocalDateTime.parse(strDatewithTime);
		System.out.println("Date with Time: " + aLDT);
	}
}
