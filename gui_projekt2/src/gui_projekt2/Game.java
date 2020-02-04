package gui_projekt2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

public class Game extends JFrame implements Runnable, Observer {
	private static final long serialVersionUID = 1L;

	public taskState status;
	enum taskState{
		RUNNING,
		STOPPED
	};
	public Thread thr3;
	
	int a = 0;
	public Game(int a) {
		this.a = a;
		setLayout(new BorderLayout());
		initInfo();
		initLayers(a);
		initLowerPanel();
		pack();
		setLocationRelativeTo(null); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setVisible(true);
		thr3 = new Thread(this);
	}
	
	JPanel infoPanel = new JPanel();
	
	private void initInfo() {
		infoPanel.setLayout(new FlowLayout());
		JLabel jl1 = new JLabel("Game: " + a + " x " + a);
		jl1.setAlignmentX(CENTER_ALIGNMENT);
		infoPanel.add(jl1);
		add(infoPanel, BorderLayout.PAGE_START);
	}
	
	JPanel centerPanel = new JPanel();
	ArrayList<Object> para_a = new ArrayList<>();
	ArrayList<Object> para_b = new ArrayList<>();
	JButton tab[][] = new JButton[CardsNumber.tmp][CardsNumber.tmp];
	
	private void initCenterPanel(int a) {
		centerPanel.setLayout(new GridLayout(a, a, 10, 10));
		
		//para_a
		for(int i = 0; i < (a*a)/2; i++) {
			int rand = (int)(Math.random()*(a*a/2)+1);
			while(para_a.contains(rand)) {
				rand = (int)(Math.random()*(a*a/2)+1);
			}
			para_a.add(rand);
		}
		//para_b
		for(int i = 0; i < (a*a)/2; i++) {
			int rand = (int)(Math.random()*(a*a/2)+1);
			while(para_b.contains(rand)) {
				rand = (int)(Math.random()*(a*a/2)+1);
			}
			para_b.add(rand);
		}
		
		int counter = 0;
		for(int i = 0; i < a; i++) {
			for(int j = 0; j < a; j++) {
				if(counter < para_a.size()) {
					JButton jbkarty = new JButton(para_a.get(counter).toString());
					jbkarty.setPreferredSize(new Dimension(50, 50));
					tab[j][i] = jbkarty; //zmiana z [i][j] na [j][i]
					centerPanel.add(jbkarty);
					counter++;
				} else if(counter >= para_a.size() && counter < (para_a.size()*2)) {
					JButton jbkarty = new JButton(para_b.get(counter-para_b.size()).toString());
					jbkarty.setPreferredSize(new Dimension(50, 50));
					tab[j][i] = jbkarty; //zmiana z [i][j] na [j][i]
					centerPanel.add(jbkarty);
					counter++;
				}
			}
		}
		counter = 0;
		add(centerPanel, BorderLayout.CENTER);
	}
	
	JPanel hiddenPanel = new JPanel();
	JButton tabUkryte[][] = new JButton[CardsNumber.tmp][CardsNumber.tmp];
	int wrt;	//dodana wartość przycisku
	int check1 = 0;
	int check2 = 0;
	int znalezionePary = 0;
	
	private void initHiddenPanel(int a) {
		hiddenPanel.setLayout(new GridLayout(a, a, 10, 10));
		hiddenPanel.setOpaque(false);
		for(int i = 0; i < a; i++) {
			for(int j = 0; j < a; j++) {
				JButton jbkartyUkryte = new JButton();
				jbkartyUkryte.setPreferredSize(new Dimension(50, 50));
				int x = j;
				int y = i;
				jbkartyUkryte.addActionListener(o->{
					wrt = Integer.parseInt(tab[x][y].getText());	//zmiana odczytanego labela dla każdego buttona na int do porównań
					jbkartyUkryte.setVisible(false);
					//algorytm sprawdzający czy 2 liczby są takie same
					if(check1 == 0 && check2 == 0) {
						check1 = wrt;
					} else if(check1 !=0 && check2 == 0){
						check2 = wrt;
						if(check1 != check2) {
							for(int k = 0; k < a; k++) {
								for(int l = 0; l < a; l++) {
									if(Integer.parseInt(tab[l][k].getText()) == check1) {
										tabUkryte[l][k].setVisible(true);
									}
								}
							}
							check1 = 0;
							for(int k = 0; k < a; k++) {
								for(int l = 0; l < a; l++) {
									if(Integer.parseInt(tab[l][k].getText()) == check2) {
										tabUkryte[l][k].setVisible(true);
									}
								}
							}
							check2 = 0;
						} else {
							for(int k = 0; k < a; k++) {
								for(int l = 0; l < a; l++) {
									if(Integer.parseInt(tab[l][k].getText()) == check1) {
										tab[l][k].setText("0");
									}
								}
							}
							check1 = 0;
							for(int k = 0; k < a; k++) {
								for(int l = 0; l < a; l++) {
									if(Integer.parseInt(tab[l][k].getText()) == check1) {
										tab[l][k].setText("0");
									}
								}
							}
							check2 = 0;
							znalezionePary++;
						}
					}
					if(znalezionePary == para_a.size()) {
						czasGry.odliczanie.cancel();
						Highscore highscore = new Highscore(String.valueOf(test_clock), CardsNumber.tmp);
						highscore.start();
					}
				});
				
				tabUkryte[j][i] = jbkartyUkryte;	//zmiana z [i][j] na [j][i]
				hiddenPanel.add(jbkartyUkryte);
				
			}
		}
		
		add(hiddenPanel, BorderLayout.CENTER);
	}
	
	JPanel jp4 = new JPanel();
	Zegar czasGry = new Zegar();
	static JLabel test_clock = new JLabel();
	
	private void initLowerPanel() {
		jp4.setLayout(new BoxLayout(jp4, BoxLayout.Y_AXIS));
		test_clock.setAlignmentX(CENTER_ALIGNMENT);
		czasGry.addObserver(this);
		jp4.add(test_clock);
		JButton jb1 = new JButton("Main Menu");
		jb1.setAlignmentX(CENTER_ALIGNMENT);
		jb1.addActionListener(e->{
			MainFrame mf = new MainFrame();
			mf.start();
			setVisible(false);
			czasGry.odliczanie.cancel();
			abort();
		});
		jp4.add(jb1);
		
		add(jp4, BorderLayout.PAGE_END);
	}
	
	JLayeredPane jlp1 = new JLayeredPane();
	OverlayLayout warstwy = new OverlayLayout(jlp1);
	
	private void initLayers(int a) {
		jlp1.setLayout(warstwy);
		initCenterPanel(a);
		initHiddenPanel(a);
		jlp1.add(hiddenPanel);
		jlp1.add(centerPanel);
		add(jlp1, BorderLayout.CENTER);
	}
	
	
	
	
	
	public taskState getStatus() {
		return status;
	}
	
	public void start() {
		status = taskState.RUNNING;
		thr3.run();
		System.out.println("Game_window: " + getStatus());
		
		
	}
	public void abort() {
		status = taskState.STOPPED;
		thr3.interrupt();
		System.out.println("Game_window: " + getStatus());
	}

	@Override
	public void run() {
		
	}

	@Override
	public void update(Observable o, Object arg) {
		test_clock.setText(String.valueOf(czasGry.minutes) + ":" + String.valueOf(czasGry.displaySeconds));
	}
	
	
	
}


