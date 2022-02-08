package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Date;

import database.Database;
import interfaces.FrameActionListener;
import modelo.Usuario;
import utils.Notificador;
import view.CatalogoDestinosDialog;
import view.LoginFrame;
import view.MainDialog;
import view.RegisterFrame;

/***
 * Clase que controla las vistas del Login y Registro.
 * @author Francesco De Amicis
 *
 */
public class ControladorLogin {

	private LoginFrame loginFrame;
	private final RegisterFrame registerFrame;
	private Database database;

	public ControladorLogin(LoginFrame loginFrame, RegisterFrame registerFrame, Database database) {
		this.database = database;
		this.loginFrame = loginFrame;
		this.registerFrame = registerFrame;

		/***
		 * Evento que hace visible el frame de registro.
		 */
		this.loginFrame.irARegistro(new FrameActionListener(registerFrame, loginFrame));

		/***
		 * Evento que abre el Main Dialog si los credenciales son correctos.
		 */
		this.loginFrame.logIn(e -> {
			String correoUsuario = loginFrame.getCorreoUsuario();
			String contrasena = loginFrame.getContrasena();

			if (correoUsuario.contentEquals("") || contrasena.contentEquals("")) {
				Notificador.notificaError("Ambos campos son obligatorios");
			} else {

				Usuario usuario = database.selectUsuarioByEmailAndContrasena(correoUsuario, contrasena);

				if (usuario != null) {
					Notificador.notificaExito("¡Hola " + usuario.getEmail() + "!");

					MainDialog mainDialog = new MainDialog((LoginFrame)null, true);
					CatalogoDestinosDialog librosDialog = new CatalogoDestinosDialog((MainDialog)null, true);
					ControladorHotelesYMain controladorHotelesYMain = new ControladorHotelesYMain(mainDialog, librosDialog, usuario, database);
					
					loginFrame.dispatchEvent(new WindowEvent(loginFrame, WindowEvent.WINDOW_CLOSING));
					mainDialog.setVisible(true);
										
				} else {
					Notificador.notificaError("Credenciales incorrectos");
				}
			}
		});

		/***
		 * Evento que controla el registro del usuario en la bd.
		 */
		this.registerFrame.registrar(e -> {
			
			String correoUsuario = registerFrame.getTextFieldRegistroEmail().trim();
			String contrasena = registerFrame.getPasswordFieldRegistroContrasena().trim();
			String confirmaContrasena = registerFrame.getPasswordFieldRegistroConfirmaContrasena().trim();

			// Valido el formulario
			if (correoUsuario.contentEquals("") || contrasena.contentEquals("")
					|| confirmaContrasena.contentEquals("")) {
				Notificador.notificaError("Todos los campos son obligatorios");
			} else if (!contrasena.contentEquals(confirmaContrasena)) {
				Notificador.notificaError("Las contraseñas no coinciden");
			} else {
				// Crear usuario
				Usuario usuario = new Usuario(new Date(), correoUsuario, contrasena);

				// Almacenarlo en la BD
				if (database.insertUsuario(usuario)) {
					Notificador.notificaExito("Usuario registrado con éxito");
					this.registerFrame.dispatchEvent(new WindowEvent(this.registerFrame, WindowEvent.WINDOW_CLOSING));
					loginFrame.setCorreoUsuario(usuario.getEmail());
				} else {
					Notificador.notificaError("Este usuario ya existe");
				}
				this.registerFrame.reset(true);
			}

		});
		
		

	}

}
