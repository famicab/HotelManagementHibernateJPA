package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

public class MainDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblViajapp;
	private JButton btnVerCatalogoDestinos;
	private JButton btnConsultarReservas;
	private JButton btnGestionDeReservas;
	private JButton btnConsultaHoteles;


	public MainDialog(Frame parent, boolean modal) {
		super(parent, modal);
		start();
	}

	/**
	 * Create the dialog.
	 */
	public void start() {
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			btnVerCatalogoDestinos = new JButton("Ver destinos");
			contentPanel.add(btnVerCatalogoDestinos);
		}
		{
			btnConsultarReservas = new JButton("Ver mis reservas");
			contentPanel.add(btnConsultarReservas);
		}
		{
			btnGestionDeReservas = new JButton("Administrar reservas");
			btnGestionDeReservas.setEnabled(false);
			btnGestionDeReservas.setVisible(false);
			{
				btnConsultaHoteles = new JButton("Información hoteles");
				btnConsultaHoteles.setEnabled(false);
				btnConsultaHoteles.setVisible(false);
				contentPanel.add(btnConsultaHoteles);
			}
			contentPanel.add(btnGestionDeReservas);
		}
		{
			lblViajapp = new JLabel("Viajapp");
			lblViajapp.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
			getContentPane().add(lblViajapp, BorderLayout.NORTH);
		}
	}
	
	//Eventos
	public void irACatalogo(ActionListener actionListener) {
		this.btnVerCatalogoDestinos.addActionListener(actionListener);
	}
	
	public void verMisReservas(ActionListener actionListener) {
		this.btnConsultarReservas.addActionListener(actionListener);
	}
	
	public void gestionarReservas(ActionListener actionListener) {
		this.btnGestionDeReservas.addActionListener(actionListener);
	}
	
	public void consultarHoteles(ActionListener actionListener) {
		this.btnConsultaHoteles.addActionListener(actionListener);
	}
	
	//Getters
	public JButton getButtonVerCatalogo() {
		return this.btnVerCatalogoDestinos;
	}
	
	public JButton getButtonGestionarReservas() {
		return this.btnGestionDeReservas;
	}
	
	public JButton getButtonMisReservas() {
		return this.btnConsultarReservas;
	}
	
	public JButton getButtonVerHoteles() {
		return this.btnConsultaHoteles;
	}

}
