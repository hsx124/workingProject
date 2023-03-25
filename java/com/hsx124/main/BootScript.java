package com.hsx124.main;

import java.io.FileNotFoundException;
import java.io.IOException;

public class BootScript {
	public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
//		System.out.println(Arrays.toString((Object[]) args));
//		String startDate = args[0];
//		String endTime = args[1];
//
//		String startMonth = startDate.split(" ")[0].split("/")[0];
//		String startDay = startDate.split(" ")[0].split("/")[1];
//		String startHour = startDate.split(" ")[1].split(":")[0];
//		String startMin = startDate.split(" ")[1].split(":")[1];
//
//		String endHour = endTime.split(":")[0];
//		String endMin = endTime.split(":")[1];
//		Timer tm = new Timer();
//		TimerTask task = new TimerTask() {
//			public void run() {
//				OrderItem odi = new OrderItem();
//				try {
//					System.out.println("start....");
//					odi.readInfoFromExcel();
//					odi.readTxt();
//					odi.doOrder();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		};
//
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(2, Integer.parseInt(startMonth) - 1);
//		calendar.set(5, Integer.parseInt(startDay));
//		calendar.set(11, Integer.parseInt(startHour));
//		calendar.set(12, Integer.parseInt(startMin));
//		calendar.set(13, 0);
//
//		Date date = calendar.getTime();
//		System.out.println(date);
//		tm.schedule(task, date, 146000L);
//
//		while (true) {
//			if (LocalDateTime.now().getHour() == Integer.parseInt(endHour) &&
//					LocalDateTime.now().getMinute() == Integer.parseInt(endMin)) {
//				System.out.println("end.....");
//
//				System.exit(0);
//			}
//		}
		OrderItem odi = new OrderItem();
		try {
			odi.readInfoFromExcel();
			odi.readTxt();
			odi.doOrder();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}