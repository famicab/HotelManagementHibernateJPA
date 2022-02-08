package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import interfaces.FrameActionListener;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldUsuarioLogin;
	private JPasswordField passwordFieldContrasenaLogin;
	private JMenuBar menuBar;
	private JMenu mnAcercaDe;
	private JMenuItem mntmAyuda;
	private JLabel lblBienvenido;
	private JLabel lblUsuario;
	private JLabel lblContrasena;
	private JButton btnLogin;
	private JButton btnIrARegistro;
	private GroupLayout gl_contentPane;


	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 411, 411);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnAcercaDe = new JMenu("Acerca de");
		menuBar.add(mnAcercaDe);
		
		mntmAyuda = new JMenuItem("Ayuda");
		mnAcercaDe.add(mntmAyuda);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		lblBienvenido = new JLabel("¡Bienvenido!");
		lblBienvenido.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		
		lblUsuario = new JLabel("Usuario");
		
		lblContrasena = new JLabel("Contraseña");
		
		textFieldUsuarioLogin = new JTextField();
		textFieldUsuarioLogin.setColumns(10);
		
		passwordFieldContrasenaLogin = new JPasswordField();
		
		btnLogin = new JButton("Login");
		
		btnIrARegistro = new JButton("Registro");
		gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(116)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(lblUsuario)
							.addGap(170))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblBienvenido)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(textFieldUsuarioLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
											.addComponent(passwordFieldContrasenaLogin, Alignment.TRAILING)
											.addComponent(lblContrasena, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)))))
							.addGap(117))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(btnLogin)
							.addGap(152))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(btnIrARegistro)
							.addGap(143)))
					.addGap(0, 0, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblBienvenido)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblUsuario)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textFieldUsuarioLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblContrasena, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(passwordFieldContrasenaLogin, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnLogin)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnIrARegistro)
					.addContainerGap(60, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void setCorreoUsuario(String correo) {
		this.textFieldUsuarioLogin.setText(correo);
	}
	//Getters
	public String getCorreoUsuario() {
		return textFieldUsuarioLogin.getText();
	}
	
	public String getContrasena() {
		return String.valueOf(passwordFieldContrasenaLogin.getPassword());
	}
	
	//Eventos
	public void logIn(ActionListener actionlistener) {
		btnLogin.addActionListener(actionlistener);
	}
	
	public void irARegistro(FrameActionListener actionListener) {
		btnIrARegistro.addActionListener(actionListener);
	}
	
	//Resetear los campos
	public void reset(boolean b) {
        if(b) {
        	textFieldUsuarioLogin.setText("");
        	passwordFieldContrasenaLogin.setText("");
        }
    }
}
