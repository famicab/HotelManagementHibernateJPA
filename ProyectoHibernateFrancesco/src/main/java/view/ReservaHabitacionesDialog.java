package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import interfaces.ClickedListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class ReservaHabitacionesDialog extends JDialog {

	private final String[] orden = {"", "Tipo", "Precio: ascendente", "Precio: descendente"};
	private JPanel contentPanel = new JPanel();
	private JTable tableHabitaciones;
	private JButton btnVerHabitaciones;
	private JComboBox<Object> comboBoxFiltrado;
	private JButton btnAgrupar;
	


	public ReservaHabitacionesDialog(CatalogoDestinosDialog parent, boolean modal) {
		super(parent, modal);
		start();
	}
	/**
	 * Create the dialog.
	 */
	private void start() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 651, 483);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		comboBoxFiltrado  = new JComboBox<Object>(orden);
		
		btnVerHabitaciones = new JButton("Ver habitaciones");
		btnAgrupar = new JButton("Agrupar habitaciones");
		
		JLabel lblOrdenar = new JLabel("Ordenar por:");
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(220)
					.addComponent(btnVerHabitaciones)
					.addContainerGap(250, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap(33, Short.MAX_VALUE)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblOrdenar)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxFiltrado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 395, GroupLayout.PREFERRED_SIZE))
					.addGap(191))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(200)
					.addComponent(btnAgrupar)
					.addContainerGap(236, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblOrdenar)
						.addComponent(comboBoxFiltrado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(21)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnVerHabitaciones)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnAgrupar)
					.addContainerGap(69, Short.MAX_VALUE))
		);
		btnAgrupar.setEnabled(false);
		
		tableHabitaciones = new JTable() {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		           Component component = super.prepareRenderer(renderer, row, column);
		           int rendererWidth = component.getPreferredSize().width;
		           TableColumn tableColumn = getColumnModel().getColumn(column);
		           tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
		           return component;
		        }
			
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		tableHabitaciones.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(tableHabitaciones);
		contentPanel.setLayout(gl_contentPanel);
	}
	
	public String getFiltro() {
		return this.comboBoxFiltrado.getSelectedItem().toString();
	}
	
	public JTable getTable() {
		return this.tableHabitaciones;
	}
	
	public void verHabitaciones(ActionListener actionListener) {
		this.btnVerHabitaciones.addActionListener(actionListener);
	}
	
	public void agruparHabitaciones(ActionListener actionListener) {
		this.btnAgrupar.addActionListener(actionListener);
	}
	
	public JButton getBotonAgrupar() {
		return this.btnAgrupar;
	}
	
	public void irAReservaHabitacion(ClickedListener clickedListener) {
		this.tableHabitaciones.addMouseListener(clickedListener);
	}
	
}
