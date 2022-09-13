package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.NewtonUniversalGravitation;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver {
	// ...
	private Controller _ctrl;
	private volatile Thread _thread;
	
	private JSpinner spinner;
	private JSpinner delaySpinner;
	private JTextField text;
	
	//botones
	private JButton load;
	private JButton law;
	private JButton start;
	private JButton stop;
	private JButton exit;
	
	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		// TODO build the tool bar by adding buttons, etc.
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("resources"));
		JToolBar barra = new JToolBar();
		barra.setPreferredSize(new Dimension(1200,40));
		this.add(barra);

		load = new JButton();
		load.setActionCommand("load");
		load.setToolTipText("Load a file");
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				int ret = fc.showOpenDialog(ControlPanel.this);
				if (ret == JFileChooser.APPROVE_OPTION) {
					InputStream is = null;
					try {
						//is = new  FileInputStream(new File(((String) lstLista.getSelectedItem()));
						is = new  FileInputStream(fc.getSelectedFile());
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(ControlPanel.this,
								"Se produjo un erro, el archivo no existe", "Error",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
					_ctrl.reset();
					_ctrl.loadBodies(is);

				}
			} 
		});
		//load.setIcon(new ImageIcon(this.getClass().getResource("open.png")));
		load.setIcon(new ImageIcon("resources/icons/open.png"));
		barra.add(load);
		barra.addSeparator();


//		String[] names = { "Newton's law of universal gravitation (nlug)", "Falling to center gravity (ftcg)",
//				"No gravity (ng)" };
//		JComboBox<String> list = new JComboBox<String>(names);
//		list.setSelectedIndex(0);
//		list.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent event) {
//				List<JSONObject> j=_ctrl.getGravityLawsFactory().getInfo(); 
//				int n = (Integer)list.getSelectedIndex();
//				_ctrl.setGravityLaws(j.get(n));
//				//puede que sea
//				//JOptionPane.showMessageDialog(ControlPanel.this,"Select Gravity Laws to be used",
//				//								"Gravity Laws Selection",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,
//				//								null,null,null)
//			} 
//		});
//		list.setMaximumSize(new Dimension(150, 60));
//		barra.add(list);
//		barra.addSeparator();
		
		
		law = new JButton();
		law.setActionCommand("law");
		law.setToolTipText("Select Gravity Laws to be used");
		law.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				List<JSONObject> j=_ctrl.getGravityLawsFactory().getInfo(); 
				String[] opciones = new String[3];
				for(int i=0;i<opciones.length;++i) {
					opciones[i]=(String)j.get(i).get("desc");
				}
				//String[] o = { "Newton's law of universal gravitation (nlug)", "Falling to center gravity (ftcg)",
				//"No gravity (ng)" };
				String res = (String) JOptionPane.showInputDialog(
					ControlPanel.this,
					"Select gravity laws to be used", "Gravity Laws Selection",
					JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
				
				for(int i =0;i<3;++i) {
					if(res.equals(opciones[i])) {
						_ctrl.setGravityLaws(j.get(i));
					}
				}
			} 
		});
		//law.setIcon(new ImageIcon(this.getClass().getResource("physics.png")));
		law.setIcon(new ImageIcon("resources/icons/physics.png"));
		barra.add(law);
		barra.addSeparator();
		
		
		
		start = new JButton();
		start.setActionCommand("run");
		start.setToolTipText("run the simulator");
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				
				setEnableComponents(false);
				try {
					Double dt=Double.parseDouble(text.getText());
					_ctrl.setDeltaTime(dt);
				}catch(Exception exc) {
					JOptionPane.showMessageDialog(ControlPanel.this,exc.getMessage(),"WARNING",JOptionPane.ERROR_MESSAGE);
					text.setText("10000.0");
				} 
				_thread=new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						run_sim((Integer)spinner.getValue(),(Integer) delaySpinner.getValue());
						setEnableComponents(true);
						_thread=null;
					}
				
				});
				_thread.start();
				
			}});
		//start.setIcon(new ImageIcon(this.getClass().getResource("run.png")));
		start.setIcon(new ImageIcon("resources/icons/run.png"));
		barra.add(start);
		//barra.addSeparator();
		
		
		stop = new JButton();
		stop.setActionCommand("stop");
		stop.setToolTipText("stop the simulator");
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				if(_thread!=null) {
					_thread.interrupt();
					setEnableComponents(true);
				}
			
			} 
		});
		//stop.setIcon(new ImageIcon(this.getClass().getResource("stop.png")));
		stop.setIcon(new ImageIcon("resources/icons/stop.png"));
		barra.add(stop);
		barra.addSeparator();
		
		JLabel delay = new JLabel(" Delay");
		delay.setOpaque(true);
		barra.add(delay);
		barra.addSeparator();
		
		delaySpinner = new JSpinner(new SpinnerNumberModel(1, 0, 1000, 1));
		delaySpinner.setMaximumSize(new Dimension(50,30));
		barra.add(delaySpinner);
		barra.addSeparator();
		
		
		JLabel steps = new JLabel(" Steps");
		steps.setOpaque(true);
		barra.add(steps);
		barra.addSeparator();
		
		spinner = new JSpinner(new SpinnerNumberModel(10000, 0, 50000, 100));
		spinner.setMaximumSize(new Dimension(70,30));
		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
			}
		});
		barra.add(spinner);
		barra.addSeparator();
		
		JLabel deltaTime = new JLabel(" Delta-Time");
		deltaTime.setOpaque(true);
		barra.add(deltaTime);
		barra.addSeparator();
		
		text = new JTextField("2500");
		text.setMaximumSize(new Dimension(50, 30));
		text.setEditable(true);
		barra.add(text);
		barra.addSeparator();
		
		JLabel vacio = new JLabel("");
		vacio.setOpaque(false);
		vacio.setMaximumSize(new Dimension(660,30));
		barra.add(vacio);
		barra.addSeparator();
		
		exit = new JButton();//se inicializa despues
		exit.setActionCommand("exit");
		exit.setToolTipText("exit the simulator");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				ImageIcon icon= new ImageIcon(("resources/icons/arturo.png"));
				Image aux=icon.getImage();
				Image scaled = aux.getScaledInstance(75, 150, Image.SCALE_SMOOTH);//icon.getIconWidth(), icon.getIconHeight()
				ImageIcon scaledIcon=new ImageIcon(scaled);

				int n = JOptionPane.showOptionDialog(ControlPanel.this,
						"Are sure you want to quit?", "Quit",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, scaledIcon, null, null);//new ImageIcon(this.getClass().getResource("arturo.png"))
				if (n == 0) {
					System.exit(0);
				}
			}
		});
		//exit.setIcon(new ImageIcon(this.getClass().getResource("exit.png")));
		exit.setIcon(new ImageIcon("resources/icons/exit.png"));
		barra.add(exit);
		barra.addSeparator();
		
		this.setVisible(true);

	}

	// other private/protected methods
	// ...
	private void run_sim(int n,int delay){
		while (n > 0 && !Thread.currentThread().isInterrupted()) {
			try {
				_ctrl.run(1);
			} catch (Exception e) {
				// TODO show the error in a dialog box
				// TODO enable all buttons
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						JOptionPane.showMessageDialog(ControlPanel.this,e.getMessage(),"WARNING",JOptionPane.ERROR_MESSAGE);
					}
					
				});
				
				setEnableComponents(true);
				_thread=null;
				return;
			}
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				return;
			}
			--n;
		}
		
	}
	private void setEnableComponents(boolean b) {
		load.setEnabled(b);
		law.setEnabled(b); 
		exit.setEnabled(b);
		start.setEnabled(b);
		spinner.setEnabled(b);
		text.setEnabled(b);
		delaySpinner.setEnabled(b);
	}
	// SimulatorObserver methods
	// ...

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		// TODO Auto-generated method stub
		// TODO enable all buttons
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				text.setText(Double.toString(dt));
			}
			
		});
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		// TODO Auto-generated method stub
		// TODO enable all buttons
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				text.setText(Double.toString(dt));
			}
			
		});
		
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		// TODO enable all buttons
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				text.setText(Double.toString(dt));
			}
			
		});
		
	}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		// TODO Auto-generated method stub
		
	}
}
