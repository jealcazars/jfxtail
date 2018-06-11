package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;

public class DaemonFileWriter {
	
	public static void main(String[] args) {
		Thread thread = new Thread() {
			public void run() {
				File file = new File("d://prueba.log");
				if (file.exists()) {
					file.delete();
				}

				int i = 0;
				while (true) {

					try {
						Thread.sleep(2000L);
						PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("d://prueba.log", true)));
						out.println("line " + i + " " + new Date());
						System.out.println("Writing line " + i);
						out.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					i++;
				}
			}
		};

		thread.start();
	}
}
