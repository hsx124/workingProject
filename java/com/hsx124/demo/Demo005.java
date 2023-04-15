package com.hsx124.demo;

import java.io.File;

public class Demo005 {
	public static void main(String[] args) {
		File file = new File("D:\\workspace\\personal\\test\\久保田大輝iPhone_14_Pro_Max6_7inchsilver256gb_2023032812084942");
		file.renameTo(new File("D:\\workspace\\personal\\test\\佐藤透iPhone_14_Pro_Max6_7inchgold256gb_YYYYMMMMDDD"));

	}
}
