package gui_projekt2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Highscore extends JFrame implements Runnable, ActionListener {
	private static final long serialVersionUID = 1L;
	
	public taskState status;
	enum taskState{
		RUNNING,
		STOPPED
	};
	public Thread highscore;
	
	String time;
	int rozmiarpola;
	
	public Highscore(String time, int rozmiarpola) {
		this.time = time;
		this.rozmiarpola = rozmiarpola;
		setLayout(new BorderLayout());
		initInfoPanel();
		initCenterPanel();
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		highscore = new Thread(this);
	}
	
	JPanel infoPanel = new JPanel();
	
	private void initInfoPanel() {
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setBackground(Color.ORANGE);
		JLabel jl1 = new JLabel("Congratulations! " + CardsNumber.tmp + "x" + CardsNumber.tmp + " game completed in: " + Game.test_clock.getText());
		jl1.setAlignmentX(CENTER_ALIGNMENT);
		infoPanel.add(jl1);
		JLabel jl2 = new JLabel("Enter Your name below:");
		jl2.setAlignmentX(CENTER_ALIGNMENT);
		infoPanel.add(jl2);
		add(infoPanel, BorderLayout.PAGE_START);
	}
	
	JPanel centerPanel = new JPanel();
	String wynik;
	File file;
	
	private void initCenterPanel() {
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setBackground(Color.ORANGE);
		
		JTextArea jt1 = new JTextArea();
		jt1.setPreferredSize(new Dimension(50, 20));
		jt1.setAlignmentX(CENTER_ALIGNMENT);
		centerPanel.add(jt1);
		
		JButton jb1 = new JButton("OK");
		jb1.setBackground(Color.YELLOW);
		jb1.setPreferredSize(new Dimension(50, 30));
		jb1.setAlignmentX(CENTER_ALIGNMENT);
		jb1.addActionListener(ok->{
			wynik = (" Name: " + jt1.getText() + " Time: " + Game.test_clock.getText() + " Board size: " + CardsNumber.tmp + "x" + CardsNumber.tmp);
			file = new File("scores.txt");
			try(FileOutputStream fos = new FileOutputStream(file)) {
				if(!file.exists()) {
					file.createNewFile();
				}
				byte[] strToByte = wynik.getBytes();
				//fos.getChannel().position(0);
				fos.write(strToByte);
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			setVisible(false);
			abort();
		});
		centerPanel.add(jb1);
		
		add(centerPanel, BorderLayout.CENTER);
	}
	
	public taskState getStatus() {
		return status;
	}
	
	public void start() {
		status = taskState.RUNNING;
		highscore.run();
		System.out.println("Highscore_save_window: " + getStatus());
	}
	public void abort() {
		status = taskState.STOPPED;
		highscore.interrupt();
		System.out.println("Highscore_save_window: " + getStatus());
	}

	@Override
	public void run() {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
	
	
}
