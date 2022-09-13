package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;
import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class Viewer extends JPanel implements SimulatorObserver {//he cambiado a jpanel porque no salia nada con jcomponent
	// ...
	private int _centerX;
	private int _centerY;
	private double _scale;
	private List<Body> _bodies;
	private boolean _showHelp;

	Viewer(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() { 
		// TODO add border with title
		this.setSize(1200,600);
		this.setPreferredSize(new Dimension(1200,600));
		//this.setBackground(Color.YELLOW);
		_bodies = new ArrayList<>(); 
		_scale = 1.0;
		_showHelp = true;
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.blue, 2), "Viewer",//black
				TitledBorder.LEFT, TitledBorder.TOP));
		addKeyListener(new KeyListener() { 
			// ... 
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
				case '-':
					_scale = _scale * 1.1;
					break;
				case '+':
					_scale = Math.max(1000.0, _scale / 1.1);
					break;
				case '=':
					autoScale();
					break;
				case 'h':
					_showHelp = !_showHelp;
					break;
				default:
				}
				repaint();
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			} 
		});
		addMouseListener(new MouseListener() {
			// ...
			@Override 
			public void mouseEntered(MouseEvent e) { 
				requestFocus(); 
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				requestFocus(); 
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			} 
		});
		this.setVisible(true);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D gr = (Graphics2D) g;
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		// use ’gr’ to draw not ’g’
		
		ImageIcon icon= new ImageIcon(("resources/icons/espacio.png"));
		gr.drawImage(icon.getImage(), 0, 0, this);
		//gr.drawImage(icon.getImage(), 0, 0, 1200, 600, this);
		
		
		// calculate the center
		_centerX = getWidth() / 2; 
		_centerY = getHeight() / 2;
		// TODO draw a cross at center 
		gr.setColor(Color.RED);
		gr.drawLine(_centerX-10, _centerY, _centerX+10, _centerY);
		gr.drawLine(_centerX, _centerY-10, _centerX, _centerY+10);
		
		// TODO draw bodies 
//		gr.setColor(Color.CYAN);
//		for(Body b:_bodies) {
//			int i = _centerX + (int) (b.getPosition().coordinate(0)/_scale);
//			int j = _centerY + (int) (b.getPosition().coordinate(1)/_scale);
//			gr.fillOval(i, j , 5, 5);
//			gr.drawString(b.getId(), i-3, j-6);
//		}
		//prueba imagenes
		
		int k=1;
		gr.setColor(Color.yellow);
		for(Body b:_bodies) {
			int i = _centerX + (int) (b.getPosition().coordinate(0)/_scale);
			int j = _centerY + (int) (b.getPosition().coordinate(1)/_scale);
			String s = "resources/icons/emoji";
			s=s+Integer.toString(k)+".png";
			ImageIcon img= new ImageIcon((s));
			gr.drawImage(img.getImage(), i-7, j-7, 15,15,this);
			//gr.fillOval(i, j , 1, 1);
			gr.drawString(b.getId(), i-6, j-10);
			++k;
		}
		
		
		// TODO draw help if_showHelp is true
		if(_showHelp) {
			gr.setColor(Color.RED);
			gr.drawString("h: toggle help, +:zoom-in, -:zoom-out, =:fit", 10, 30);
			gr.drawString("Scaling ratio: "+ Double.toString(_scale), 10, 43);
		}
	}

	// other private/protected methods 
	// ...
	private void autoScale() {
		double max = 1.0;
		for (Body b : _bodies) {
			Vector p = b.getPosition();
			for (int i = 0; i < p.dim(); i++)
				max = Math.max(max,
								Math.abs(b.getPosition().coordinate(i)));
		}
		double size = Math.max(1.0, Math.min((double) getWidth(),
											(double) getHeight()));
		_scale = max > size ? 4.0 * max / size : 1.0;
	}
	// SimulatorObserver methods // ...

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		// TODO Auto-generated method stub
		//actualizar _bodies
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				_bodies=bodies;
				autoScale();
				repaint();
			}
			
		});
		
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		// TODO Auto-generated method stub
		//actualizar _bodies
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				_bodies=bodies;
				autoScale();
				repaint();
			}
			
		});
		
	
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		//actualizar _bodies
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				_bodies=bodies;
				autoScale();
				repaint();
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
				repaint();
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
		
	}
}