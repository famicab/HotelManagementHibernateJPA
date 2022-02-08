package interfaces;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/***
 * Interfaz que habilita el uso de expresiones lambda con eventos de tipo Click.
 * Fuente: https://stackoverflow.com/questions/21833537/java-8-lambda-expressions-what-about-multiple-methods-in-nested-class
 * @author Francesco De Amicis
 *
 */
public interface ClickedListener extends MouseListener{
	
	@Override
    public default void mouseEntered(MouseEvent e) {}

    @Override
    public default void mouseExited(MouseEvent e) {}

    @Override
    public default void mousePressed(MouseEvent e) {}

    @Override
    public default void mouseReleased(MouseEvent e) {}

}
