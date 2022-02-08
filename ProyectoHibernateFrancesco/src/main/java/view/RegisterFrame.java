package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class RegisterFrame extends JDialog {

	private JPanel contentPane;
	private JTextField textFieldRegistroEmail;
	private JPasswordField passwordFieldRegistroContrasena;
	private JPasswordField passwordFieldRegistroConfirmaContrasena;
	private JLabel lblCorreo;
	private JLabel lblRegistro;
	private JLabel lblContrasena;
	private JLabel lblConfirmaContrasena;
	private JButton btnRegistrar;
	private GroupLayout gl_contentPane;


	public RegisterFrame(Frame parent, boolean modal) {
		super(parent, modal);
		start();
	}

	public void start() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 451, 388);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		lblRegistro = new JLabel("Registro");
		lblRegistro.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		
		lblCorreo = new JLabel("Correo electrónico");
		
		lblContrasena = new JLabel("Contraseña");
		
		lblConfirmaContrasena = new JLabel("Confirma contraseña");
		
		textFieldRegistroEmail = new JTextField();
		textFieldRegistroEmail.setColumns(10);
		
		passwordFieldRegistroContrasena = new JPasswordField();
		
		passwordFieldRegistroConfirmaContrasena = new JPasswordField();
		passwordFieldRegistroConfirmaContrasena.setText("");
		
		btnRegistrar = new JButton("Registrar");
		gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblCorreo))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(170)
							.addComponent(lblRegistro))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblContrasena))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblConfirmaContrasena)))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldRegistroEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(passwordFieldRegistroConfirmaContrasena, Alignment.LEADING)
							.addComponent(passwordFieldRegistroContrasena, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)))
					.addGap(63))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(152)
					.addComponent(btnRegistrar)
					.addContainerGap(193, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblRegistro)
					.addGap(33)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCorreo)
						.addComponent(textFieldRegistroEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblContrasena)
						.addComponent(passwordFieldRegistroContrasena, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(27)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblConfirmaContrasena)
						.addComponent(passwordFieldRegistroConfirmaContrasena, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(48)
					.addComponent(btnRegistrar)
					.addContainerGap(51, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	//Getters
	public String getTextFieldRegistroEmail() {
		return textFieldRegistroEmail.getText();
	}
	
	public String getPasswordFieldRegistroContrasena() {
		return String.valueOf(passwordFieldRegistroContrasena.getPassword());
	}
	
	public String getPasswordFieldRegistroConfirmaContrasena() {
		return String.valueOf(passwordFieldRegistroConfirmaContrasena.getPassword());
	}
	
	//Eventos
	public void registrar(ActionListener actionListener) {
		btnRegistrar.addActionListener(actionListener);
	}
	
	//Reset
		public void reset(boolean b) {
	        if(b) {
	        	textFieldRegistroEmail.setText("");
	        	passwordFieldRegistroContrasena.setText("");
	        	passwordFieldRegistroConfirmaContrasena.setText("");
	        }
	    }
}
