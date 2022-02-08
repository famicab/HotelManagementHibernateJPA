package com.accesoadatos.hibernate.ProyectoHibernateFrancesco;

import controlador.ControladorLogin;
import database.Database;
import utils.Log;
import view.LoginFrame;
import view.RegisterFrame;


/**
 * En esta clase se encuentra el método main, el cual inicializa todo el programa.
 * El resto de vistas y controladores se inicializan en los controladores.
 * @author Francesco De Amicis
 *
 */
public class App 
{
    public static void main(String[] args )
    {
    	LoginFrame loginFrame = new LoginFrame();
    	loginFrame.setVisible(true);
    	
    	RegisterFrame registerFrame = new RegisterFrame((LoginFrame)null, true);
    	Database database = Database.getInstance();
    	ControladorLogin controladorLogin = new ControladorLogin(loginFrame, registerFrame, database);
    	
    	Log.create(); //creo un fichero de log
    	//Test comment
    	/***
    	 * Este hilo se ejecuta en la finalizacion del programa y cierra nuestra session factory
    	 */
    	Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
    		@Override
    		public void run() {
    			database.close();
    			Log.i("Fin del programa.");
    		}
    	}));
    }
}
