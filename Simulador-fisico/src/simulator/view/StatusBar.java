package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver {
	// ... 
	private JLabel _currTime; // for current time 
	private JLabel _currLaws; // for gravity laws 
	private JLabel _numOfBodies; // for number of bodies
	
	
	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		// TODO complete the code to build the tool bar
		JToolBar barra = new JToolBar();
		this.add(barra, BorderLayout.SOUTH);
		
		JLabel time = new JLabel("Time: ");
		time.setOpaque(true);
		barra.add(time);
		_currTime = new JLabel();
		_currTime.setOpaque(true);
		_currTime.setMaximumSize(new Dimension(70,15));
		_currTime.setPreferredSize(new Dimension(70,15));
		barra.add(_currTime);
		
		JLabel bodies = new JLabel("Bodies: ");
		bodies.setOpaque(true);
		barra.add(bodies);
		_numOfBodies = new JLabel("0.0");
		_numOfBodies.setOpaque(true);
		_numOfBodies.setMaximumSize(new Dimension(70,15));
		_numOfBodies.setPreferredSize(new Dimension(70,15));
		barra.add(_numOfBodies);
		
		JLabel laws = new JLabel("Laws: ");
		laws.setOpaque(true);
		barra.add(laws);
		_currLaws = new JLabel();
		_currLaws.setOpaque(true);
		_currLaws.setMaximumSize(new Dimension(180,15));
		_currLaws.setPreferredSize(new Dimension(180,15));
		barra.add(_currLaws);
		
	}
	// other private/protected methods // ...
	// SimulatorObserver methods // ...

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				_currTime.setText(Double.toString(time));
				_numOfBodies.setText(Integer.toString(bodies.size()));
				_currLaws.setText(gLawsDesc);
			}
			
		});
		
	
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				_currTime.setText(Double.toString(time));
				_numOfBodies.setText(Integer.toString(bodies.size()));
				_currLaws.setText(gLawsDesc);
			}
			
		});
		
		
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				_numOfBodies.setText(Integer.toString(bodies.size()));
			}
			
		});
		
		
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				_currTime.setText(Double.toString(time));
				_numOfBodies.setText(Integer.toString(bodies.size()));
			
			}
			
		});
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				_currLaws.setText(gLawsDesc);
			}
			
		});
		
		
	}
}
