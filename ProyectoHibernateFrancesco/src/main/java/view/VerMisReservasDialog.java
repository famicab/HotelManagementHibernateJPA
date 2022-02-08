package view;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileSystemView;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class VerMisReservasDialog extends JDialog {
	private JTable tableMisReservas;
	private JScrollPane scrollPane;
	private JButton btnVerReservas;
	private JButton btnImprimirFactura;
	private JButton btnCancelarBillete;
	private JFileChooser fileChooser;

	public VerMisReservasDialog(MainDialog parent, boolean modal) {
		super(parent, modal);
		start();
	}

	/**
	 * Create the dialog.
	 */
	private void start() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 651, 348);
		scrollPane = new JScrollPane();

		btnVerReservas = new JButton("Ver reservas");

		btnImprimirFactura = new JButton("Imprimir factura");

		btnCancelarBillete = new JButton("CancelarBillete");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(btnCancelarBillete))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addContainerGap(465, Short.MAX_VALUE)
							.addComponent(btnImprimirFactura))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(190)
							.addComponent(btnVerReservas)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(40)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(54)
							.addComponent(btnImprimirFactura)
							.addGap(27)
							.addComponent(btnCancelarBillete)))
					.addGap(18)
					.addComponent(btnVerReservas)
					.addContainerGap(63, Short.MAX_VALUE))
		);

		tableMisReservas = new JTable() {
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
		tableMisReservas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(tableMisReservas);
		getContentPane().setLayout(groupLayout);
		
		fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

	}
	
	public JTable getTable() {
		return this.tableMisReservas;
	}
	
	public void verReservas(ActionListener actionListener) {
		this.btnVerReservas.addActionListener(actionListener);
	}
	
	public void borrarReserva(ActionListener actionListener) {
		this.btnCancelarBillete.addActionListener(actionListener);
	}
	
	public void imprimirBillete(ActionListener actionListener) {
		this.btnImprimirFactura.addActionListener(actionListener);
	}
	
	public JFileChooser getJFileChooser() {
		return this.fileChooser;
	}
	
	public int showFileChooser(){
		return this.fileChooser.showSaveDialog(this);
	}
}
