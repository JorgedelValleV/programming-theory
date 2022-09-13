package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {
	// ... 
	private List<Body> _bodies;
	//esta linea es anadida
	private String[] _colNames = { "Id", "Mass", "Position" , "Velocity", "Acceleration"};

	BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}

	@Override 
	public int getRowCount() { 
		// TODO complete 
		return _bodies.size();
	}

	@Override 
	public int getColumnCount() { 
		// TODO complete 
		return _colNames.length;
	}

	@Override 
	public String getColumnName(int column) { 
		// TODO complete 
		return _colNames[column];
	}

	@Override 
	public Object getValueAt(int rowIndex, int columnIndex) { 
		// TODO complete 
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = _bodies.get(rowIndex).getId();
			break;
		case 1:
			s = _bodies.get(rowIndex).getMass();
			break;
		case 2:
			s = _bodies.get(rowIndex).getPosition();
			break;
		case 3:
			s = _bodies.get(rowIndex).getVelocity();
			break;
		case 4:
			s = _bodies.get(rowIndex).getAcceleration();
			break;
		}
		return s;

	}
	// SimulatorObserver methods // ...

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				_bodies=bodies;
				fireTableStructureChanged(); 
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
				_bodies=bodies;
				fireTableStructureChanged(); 
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
				_bodies=bodies;
				fireTableStructureChanged(); 
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
				_bodies=bodies;
				fireTableStructureChanged(); 
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