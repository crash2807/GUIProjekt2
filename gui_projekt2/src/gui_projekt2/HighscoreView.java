package gui_projekt2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class HighscoreView extends JFrame implements Runnable, ActionListener {
	private static final long serialVersionUID = 1L;
	
	public taskState status;
	enum taskState{
		RUNNING,
		STOPPED
	};
	public Thread highscoreview;
	
	public HighscoreView() {
		setLayout(new BorderLayout());
		initInfoPanel();
		initCenterPanel();
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setSize(400, 300);
		setVisible(true);
		highscoreview = new Thread(this);
	}
	
	
	JPanel infoPanel = new JPanel();
	
	private void initInfoPanel() {
		infoPanel.setLayout(new FlowLayout());
		infoPanel.setBackground(Color.ORANGE);
		JLabel jl1 = new JLabel("High Scores:");
		jl1.setAlignmentX(CENTER_ALIGNMENT);
		infoPanel.add(jl1);
		add(infoPanel, BorderLayout.PAGE_START);
	}
	
	JPanel centerPanel = new JPanel();
	String wynik = "";
	File file = new File("scores.txt");
	
	private void initCenterPanel() {
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setBackground(Color.ORANGE);
		JTextArea jt1 = new JTextArea(10, 30);
		jt1.setAlignmentX(CENTER_ALIGNMENT);
		jt1.setEditable(false);
		try (FileInputStream fis = new FileInputStream(file)) {
			int content;
			while ((content = fis.read()) != -1) {
				wynik = wynik + String.valueOf((char)(content));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		jt1.setText(wynik);
		JScrollPane scroll = new JScrollPane(jt1);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		centerPanel.add(scroll);
		JButton jb1 = new JButton("Close");
		jb1.setBackground(Color.YELLOW);
		jb1.setMaximumSize(new Dimension(100, 30));
		jb1.setAlignmentX(CENTER_ALIGNMENT);
		jb1.addActionListener(c->{
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
		highscoreview.run();
		System.out.println("Highscore_view_window: " + getStatus());
	}
	public void abort() {
		status = taskState.STOPPED;
		highscoreview.interrupt();
		System.out.println("Highscore_view_window: " + getStatus());
	}
	
	@Override
	public void run() {
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
	
}
