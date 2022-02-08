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

public class CatalogoDestinosDialog extends JDialog {

	private final String[] ORDER_BY = {"", "Nombre", "Ciudad", "Pais", "Estrellas: ascendente", "Estrellas: descendente"};
	private final String[] FILTRADO = {"", "Nombre", "Ciudad", "Pais", "Estrellas" };
	private final JPanel contentPanel = new JPanel();
	private JButton btnRefresh;
	private JTable tableDestinos;
	private JScrollPane scrollPane;
	private JLabel lblFiltrarPor;
	private JLabel lblOrdenar;
	private JComboBox<Object> comboBoxOrderBy;
	private JComboBox<Object> comboBoxFiltrado;
	private JTextField textFieldFiltrado;

	public CatalogoDestinosDialog(MainDialog parent, boolean modal) {
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
			btnRefresh = new JButton("Actualizar tabla");
		}
		
		scrollPane = new JScrollPane();
		
		lblOrdenar = new JLabel("Ordenar por:");
		
		comboBoxOrderBy = new JComboBox<Object>(ORDER_BY);
		
		lblFiltrarPor = new JLabel("Filtrar por: ");
		
		comboBoxFiltrado = new JComboBox<Object>(FILTRADO);
		
		textFieldFiltrado = new JTextField();
		textFieldFiltrado.setColumns(10);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap(110, Short.MAX_VALUE)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE)
							.addGap(80))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(btnRefresh)
							.addGap(212))))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblOrdenar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBoxOrderBy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(43)
					.addComponent(lblFiltrarPor)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBoxFiltrado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textFieldFiltrado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblOrdenar)
						.addComponent(comboBoxOrderBy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFiltrarPor)
						.addComponent(comboBoxFiltrado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldFiltrado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(38)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnRefresh)
					.addContainerGap(135, Short.MAX_VALUE))
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
	
	/*public void llenarTabla(TableModel tableModel) {
		
	}*/
	public String getFiltro() {
		return this.comboBoxFiltrado.getSelectedItem().toString();
	}
	
	public String getOrden() {
		return this.comboBoxOrderBy.getSelectedItem().toString();
	}
	
	public String getFiltroText() {
		return this.textFieldFiltrado.getText();
	}
	
	public void actualizarTabla(ActionListener actionListener) {
		btnRefresh.addActionListener(actionListener);
	}
	
	public void setModel(TableModel tableModel) {
		this.tableDestinos.setModel(tableModel);
	}
	
	public JTable getTabla() {
		return this.tableDestinos;
	}
	
	public void irAConsultaHabitaciones(ClickedListener clickedListener) {
		this.tableDestinos.addMouseListener(clickedListener);
	}
}
