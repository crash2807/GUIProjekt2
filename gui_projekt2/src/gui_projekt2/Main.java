package gui_projekt2;

import java.io.File;
import java.io.IOException;

public class Main implements Runnable {
	
	public static void main(String[] args) {
		File file = new File("scores.txt");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		MainFrame mf = new MainFrame();
		mf.start();
	}

	@Override
	public void run() {
		
	}

}