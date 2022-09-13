package simulator.view;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	// ... 
	Controller _ctrl;
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		// TODO complete this method to build the GUI 
		// ..
		this.getContentPane().add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
		this.getContentPane().add(new StatusBar(_ctrl), BorderLayout.PAGE_END);
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
		this.getContentPane().add(p,BorderLayout.CENTER);
		p.add(new BodiesTable(_ctrl));
		p.add(new Viewer(_ctrl));
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.pack();
		
	}
	// other private/protected methods 
	// ...
}
