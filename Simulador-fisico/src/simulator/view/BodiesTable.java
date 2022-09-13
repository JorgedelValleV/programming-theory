package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

public class BodiesTable extends JPanel {
	private BodiesTableModel _modelo;
	private JTable _eventsTable;

	BodiesTable(Controller ctrl) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Bodies",
				TitledBorder.LEFT, TitledBorder.TOP));
		// TODO complete
		

		_modelo = new BodiesTableModel(ctrl);
		_eventsTable = new JTable(_modelo);
		//modo de selecciÃ³n: celdas Ãºnicas
		_eventsTable.setCellSelectionEnabled(false);
		_eventsTable.setPreferredSize(new Dimension(1200,300));
		JScrollPane aux=new JScrollPane(_eventsTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(aux);
		aux.setPreferredSize(new Dimension(1200,300));
		setVisible(true);	
	}
}
