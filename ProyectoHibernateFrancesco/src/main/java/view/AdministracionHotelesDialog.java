package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import interfaces.ClickedListener;

import javax.swing.JTable;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.Font;

public class AdministracionHotelesDialog extends JDialog {

	private final String[] ORDER_BY = {"", "Compañía", "Cantidad habitaciones asc.", "Cantidad habitaciones desc."};
	private final String[] FILTRADO = {"", "Nombre", "Ciudad", "Pais", "Estrellas" };
	private final JPanel contentPanel = new JPanel();
	private JButton btnRefresh;
	private JButton btnAgrupar;
	private JButton btnHotelesRentables;
	private JTable tableDestinos;
	private JScrollPane scrollPane;
	private JLabel lblOrdenar;
	private JComboBox<Object> comboBoxOrderBy;
	private JButton btnHabitacionBarata;
	private JButton btnHabitacionCara;
	private JButton btnVerHabitacionesNoReservadas;

	public AdministracionHotelesDialog(MainDialog parent, boolean modal) {
		super(parent, modal);
		start();
	}

	/**
	 * Create the dialog.
	 */
	public void start() {
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 423);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			btnRefresh = new JButton("Ver todos los hoteles");
		}
		
		scrollPane = new JScrollPane();
		
		lblOrdenar = new JLabel("Ordenar por:");
		
		comboBoxOrderBy = new JComboBox<Object>(ORDER_BY);
		
		JLabel lblAdmonHoteles = new JLabel("Admon. Hoteles");
		lblAdmonHoteles.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		
		btnAgrupar = new JButton("Agrupar habitaciones por compañía");
		
		btnHotelesRentables = new JButton("Ver hoteles más rentables");
		
		btnHabitacionBarata = new JButton("Habitación más barata");
		
		btnHabitacionCara = new JButton("Habitación más cara");
		
		btnVerHabitacionesNoReservadas = new JButton("Habitaciones no reservadas nunca");
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(44)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblAdmonHoteles)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(btnHotelesRentables)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnHabitacionBarata)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnHabitacionCara))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(btnRefresh)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAgrupar)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnVerHabitacionesNoReservadas))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblOrdenar)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxOrderBy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(25))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblAdmonHoteles)
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblOrdenar)
						.addComponent(comboBoxOrderBy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnRefresh)
						.addComponent(btnAgrupar)
						.addComponent(btnVerHabitacionesNoReservadas))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnHotelesRentables)
						.addComponent(btnHabitacionBarata)
						.addComponent(btnHabitacionCara))
					.addContainerGap())
		);
		contentPanel.setLayout(gl_contentPanel);
		
		tableDestinos = new JTable() {
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
		tableDestinos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(tableDestinos);
	}
	


	
	public String getOrden() {
		return this.comboBoxOrderBy.getSelectedItem().toString();
	}
	
	public void actualizarTabla(ActionListener actionListener) {
		btnRefresh.addActionListener(actionListener);
	}
	
	public void agruparHabitacionesPorHotel(ActionListener actionListener) {
		btnAgrupar.addActionListener(actionListener);
	}
	
	public void verHotelesMasRentables(ActionListener actionListener) {
		this.btnHotelesRentables.addActionListener(actionListener);
	}
	
	public void verHabitacionesMasCaras(ActionListener actionListener) {
		this.btnHabitacionCara.addActionListener(actionListener);
	}
	
	public void verHabitacionesMasBaratas(ActionListener actionListener) {
		this.btnHabitacionBarata.addActionListener(actionListener);
	}
	
	public void verHabitacionesNoReservadasNunca(ActionListener actionListener) {
		this.btnVerHabitacionesNoReservadas.addActionListener(actionListener);
	}
	
	public void setModel(TableModel tableModel) {
		this.tableDestinos.setModel(tableModel);
	}
	
	public JTable getTabla() {
		return this.tableDestinos;
	}
}
