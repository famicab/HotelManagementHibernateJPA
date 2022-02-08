package view;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;

public class HacerReservasDialog extends JDialog {

	private JButton btnTest;
	private JTextField textFieldDiaIda;
	private JTextField textFieldMesIda;
	private JTextField textFieldAnoIda;
	private JTextField textFieldDiaVuelta;
	private JTextField textFieldMesVuelta;
	private JTextField textFieldAnoVuelta;
	private JButton btnReservar;
	private JLabel lblInfoReserva;
	private JLabel lblTipoDePago;
	private String[] tipoDePago = {"Tarjeta bancaria", "En efectivo"};
	private JComboBox<Object> comboBoxTipoPago;

	public HacerReservasDialog(ReservaHabitacionesDialog parent, boolean modal) {
		super(parent, modal);
		start();
	}

	/**
	 * Create the dialog.
	 */
	private void start() {

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		btnTest = new JButton("New button");

		JLabel lblFechaIda = new JLabel("Fecha de ida (dd-mm-yyyy):");

		textFieldDiaIda = new JTextField();
		textFieldDiaIda.setColumns(10);

		JLabel lblSlash = new JLabel("/");

		textFieldMesIda = new JTextField();
		textFieldMesIda.setColumns(10);

		JLabel labelSlash2 = new JLabel("/");

		textFieldAnoIda = new JTextField();
		textFieldAnoIda.setColumns(10);

		JLabel labelFechaDeVuelta = new JLabel("Fecha de vuelta (dd-mm-yyyy):");

		textFieldDiaVuelta = new JTextField();
		textFieldDiaVuelta.setColumns(10);

		JLabel label = new JLabel("/");

		textFieldMesVuelta = new JTextField();
		textFieldMesVuelta.setColumns(10);

		JLabel label_1 = new JLabel("/");

		textFieldAnoVuelta = new JTextField();
		textFieldAnoVuelta.setColumns(10);

		btnReservar = new JButton("Reservar");
		
		lblInfoReserva = new JLabel("");
		
		comboBoxTipoPago = new JComboBox<Object>(tipoDePago);
		
		lblTipoDePago = new JLabel("Tipo de pago:");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(25)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblInfoReserva)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblFechaIda)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textFieldDiaIda, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblSlash)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textFieldMesIda, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(labelSlash2, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textFieldAnoIda, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(labelFechaDeVuelta, GroupLayout.PREFERRED_SIZE, 211, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
									.addComponent(lblTipoDePago)
									.addGap(20)))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(comboBoxTipoPago, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(textFieldDiaVuelta, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(label, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textFieldMesVuelta, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textFieldAnoVuelta, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(97, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(203, Short.MAX_VALUE)
					.addComponent(btnReservar)
					.addGap(192))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(21)
					.addComponent(lblInfoReserva)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFechaIda)
						.addComponent(textFieldDiaIda, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSlash)
						.addComponent(textFieldMesIda, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelSlash2)
						.addComponent(textFieldAnoIda, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(33)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(labelFechaDeVuelta)
						.addComponent(textFieldAnoVuelta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1)
						.addComponent(textFieldMesVuelta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label)
						.addComponent(textFieldDiaVuelta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBoxTipoPago, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTipoDePago))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnReservar)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);

		this.pack();

	}

	public String getDiaIda() {
		return this.textFieldDiaIda.getText();
	}

	public String getMesIda() {
		return this.textFieldMesIda.getText();
	}

	public String getAnoIda() {
		return this.textFieldAnoIda.getText();
	}

	public String getDiaVuelta() {
		return this.textFieldDiaVuelta.getText();
	}

	public String getMesVuelta() {
		return this.textFieldMesVuelta.getText();
	}

	public String getAnoVuelta() {
		return this.textFieldAnoVuelta.getText();
	}
	
	public void hacerReserva(ActionListener actionListener) {
		this.btnReservar.addActionListener(actionListener);
	}
	
	public void setTextoInfo(String texto) {
		this.lblInfoReserva.setText(texto);
	}
	
	public String getTipoDePago() {
		return this.comboBoxTipoPago.getSelectedItem().toString();
	}
}
