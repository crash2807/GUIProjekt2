package gui_projekt2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CardsNumber extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;

	public taskState status;
	enum taskState{
		RUNNING,
		STOPPED
	};
	public Thread thr2;
	
	CardsNumber(){
		setLayout(new BorderLayout());
		initInfoPanel();
		initCenterPanel();
		pack();
		setLocationRelativeTo(null); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		//setSize(320, 240);
		setVisible(true);
		thr2 = new Thread(this);
	}
	
	JPanel infoPanel = new JPanel();
	
	private void initInfoPanel() {
		infoPanel.setLayout(new FlowLayout());
		infoPanel.setBackground(Color.GRAY);
		JLabel info = new JLabel("Game options");
		info.setAlignmentX(CENTER_ALIGNMENT);
		infoPanel.add(info);
		add(infoPanel, BorderLayout.PAGE_START);
	}
	
	JPanel centerPanel = new JPanel();
	static int tmp;
	
	private void initCenterPanel(){
		centerPanel.setLayout(new GridBagLayout());
		centerPanel.setBackground(Color.ORANGE);
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel jl = new JLabel("Select grid size:");
		jl.setAlignmentX(CENTER_ALIGNMENT);
		c.gridx = 0;
		c.gridy = 0;
		centerPanel.add(jl, c);
		
		int size [] = {2,4,6,8,10};
		String sizeString[] = new String[5];
		for(int i=0; i<5; i++){
			sizeString[i] = String.valueOf(size[i]) + " x " + String.valueOf(size[i]);
		}
		
		JComboBox<String> jc = new JComboBox<String>(sizeString);
		c.gridx = 1;
		c.gridy = 0;
		centerPanel.add(jc, c);
		
		JButton jb1 = new JButton("Start");
		jb1.setBackground(Color.YELLOW);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		jb1.addActionListener(p->{
			tmp = size[jc.getSelectedIndex()];
			Game game = new Game(tmp);
			game.start();
			setVisible(false);
			abort();
		});
		centerPanel.add(jb1, c);
		
		JButton jb2 = new JButton("Back");
		jb2.setBackground(Color.YELLOW);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		jb2.addActionListener(b->{
			MainFrame mf = new MainFrame();
			mf.start();
			setVisible(false);
			abort();
		});
		centerPanel.add(jb2, c);
		
		add(centerPanel, BorderLayout.CENTER);
	}
	
	
	
	
	
	public taskState getStatus() {
		return status;
	}
	
	public void start() {
		status = taskState.RUNNING;
		thr2.run();
		System.out.println("Cards_settings: " + getStatus());
	}
	public void abort() {
		status = taskState.STOPPED;
		thr2.interrupt();
		System.out.println("Cards_settings: " + getStatus());
	}
	
	@Override
	public void run() {
		
	}

}
