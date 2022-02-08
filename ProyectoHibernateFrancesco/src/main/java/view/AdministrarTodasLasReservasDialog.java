package view;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AdministrarTodasLasReservasDialog extends JDialog {
	private JTable tableTodasLasReservas;
	private JTextField textFieldFiltrado;
	private JButton btnVerReservas;
	private JComboBox<Object> comboBoxOrdenar;
	private final String[] orden = { "", "Fecha de reserva", "Precio", "Hotel" };
	private JLabel lblOrdenarPor;
	private JLabel lblFiltrado;
	private GroupLayout groupLayout;
	private JButton btnModificarReserva;

	public AdministrarTodasLasReservasDialog(MainDialog parent, boolean modal) {
		super(parent, modal);

		start();
	}

	/**
	 * Create the dialog.
	 */
	private void start() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 524, 436);
		JScrollPane scrollPane = new JScrollPane();

		btnVerReservas = new JButton("Ver reservas");

		comboBoxOrdenar = new JComboBox<Object>(orden);

		lblOrdenarPor = new JLabel("Ordenar por:");

		textFieldFiltrado = new JTextField();
		textFieldFiltrado.setColumns(10);

		lblFiltrado = new JLabel("Filtrar por Id:");
		
		btnModificarReserva = new JButton("Modificar Reserva");
		groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(27)
					.addComponent(lblOrdenarPor)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBoxOrdenar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblFiltrado)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textFieldFiltrado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(93, Short.MAX_VALUE)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 368, GroupLayout.PREFERRED_SIZE)
					.addGap(63))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(283, Short.MAX_VALUE)
					.addComponent(btnVerReservas)
					.addGap(180))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(235, Short.MAX_VALUE)
					.addComponent(btnModificarReserva)
					.addGap(152))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBoxOrdenar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblOrdenarPor)
						.addComponent(textFieldFiltrado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFiltrado))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnVerReservas)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnModificarReserva)
					.addContainerGap(44, Short.MAX_VALUE))
		);

		tableTodasLasReservas = new JTable() {
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
		tableTodasLasReservas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(tableTodasLasReservas);
		getContentPane().setLayout(groupLayout);

	}
	
	public void verReservas(ActionListener actionListener) {
		this.btnVerReservas.addActionListener(actionListener);
	}
	
	public String getTextoFiltrado() {
		return this.textFieldFiltrado.getText();
	}
	
	public String getOrden() {
		return this.comboBoxOrdenar.getSelectedItem().toString();
	}
	
	public JTable getTable() {
		return this.tableTodasLasReservas;
	}

	public void updateReserva(ActionListener actionListener) {
		this.btnModificarReserva.addActionListener(actionListener);
	}
}
