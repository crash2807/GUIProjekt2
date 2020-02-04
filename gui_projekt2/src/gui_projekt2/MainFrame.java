package gui_projekt2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends JFrame implements Runnable, ActionListener {
	private static final long serialVersionUID = 1L;
	
	public taskState status;
	enum taskState{
		RUNNING,
		STOPPED
	};
	public Thread thr1;
	
	public MainFrame() {
		setLayout(new BorderLayout());
		initInfoPanel();
		initCenterPanel();
		initAuthorsPanel();
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
		thr1 = new Thread(this);
	}
	
	JPanel infoPanel = new JPanel();
	
	private void initInfoPanel() {
		infoPanel.setLayout(new FlowLayout());
		infoPanel.setBackground(Color.ORANGE);
		JLabel jl1 = new JLabel("Memory - The Game");
		jl1.setAlignmentX(CENTER_ALIGNMENT);
		infoPanel.add(jl1);
		add(infoPanel, BorderLayout.PAGE_START);
	}
	
	JPanel centerPanel = new JPanel();
	
	private void initCenterPanel() {
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setBackground(Color.ORANGE);
		JButton jb1 = new JButton("New Game");
		jb1.setBackground(Color.YELLOW);
		jb1.setMaximumSize(new Dimension(250, 40));
		jb1.setAlignmentX(CENTER_ALIGNMENT);
		jb1.addActionListener(n->{
			CardsNumber cn = new CardsNumber();
			cn.start();
			setVisible(false);
			abort();
		});
		centerPanel.add(jb1);
		
		JButton jb2 = new JButton("High Scores");
		jb2.setBackground(Color.YELLOW);
		jb2.setMaximumSize(new Dimension(250, 40));
		jb2.setAlignmentX(CENTER_ALIGNMENT);
		jb2.addActionListener(h->{
			HighscoreView hs = new HighscoreView();
			hs.start();
		});
		centerPanel.add(jb2);
		
		JButton jb3 = new JButton("Exit");
		jb3.setBackground(Color.YELLOW);
		jb3.setMaximumSize(new Dimension(250, 40));
		jb3.setAlignmentX(CENTER_ALIGNMENT);
		jb3.addActionListener(x->{
			abort();
			System.exit(0);
		});
		centerPanel.add(jb3);
		
		add(centerPanel, BorderLayout.CENTER);
	}
	
	JPanel authorsPanel = new JPanel();
	
	private void initAuthorsPanel() {
		authorsPanel.setLayout(new FlowLayout());
		authorsPanel.setBackground(Color.ORANGE);
		JLabel jl2 = new JLabel("Authors: s17228 / s17041 / s14895");
		jl2.setAlignmentX(CENTER_ALIGNMENT);
		authorsPanel.add(jl2);
		add(authorsPanel, BorderLayout.PAGE_END);
	}
	
	
	
	
	
	public taskState getStatus() {
		return status;
	}
	
	public void start() {
		status = taskState.RUNNING;
		thr1.run();
		System.out.println("Main_window: " + getStatus());
	}
	public void abort() {
		status = taskState.STOPPED;
		thr1.interrupt();
		System.out.println("Main_window: " + getStatus());
	}
	
	@Override
	public void run() {
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
	
}

