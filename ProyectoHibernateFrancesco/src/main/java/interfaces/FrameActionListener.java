package interfaces;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;

import view.RegisterFrame;

/**
 * Interfaz que permite hacer visible la vista de registro al producirse un evento.
 * @author Francesco De Amicis
 *
 */
public class FrameActionListener implements ActionListener {

	private RegisterFrame registerFrame;
	private JFrame parent;
	
	public FrameActionListener(RegisterFrame registerFrame, JFrame parent) {
		this.registerFrame = registerFrame;
		this.parent = parent;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.registerFrame.setVisible(true);
	}
	
	public void setJDialog(RegisterFrame dialog) {
		this.registerFrame = dialog;
	}

}
