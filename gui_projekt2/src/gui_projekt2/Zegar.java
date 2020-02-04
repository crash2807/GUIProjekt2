package gui_projekt2;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

class Zegar extends Observable {
	int seconds = 0;
	int minutes = 0;
	int displaySeconds = 0;
	
	public Timer zegar = new Timer();
	public TimerTask odliczanie = new TimerTask() {
		public void run() {
			seconds++;
			setChanged();
			notifyObservers();
			minutes = seconds / 60;
			displaySeconds = seconds % 60;
		};
	};
	
	Zegar() {
		zegar.schedule(odliczanie, 0, 1000);
	}
}	