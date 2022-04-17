package start;

import java.util.logging.Logger;
import presentation.Controller;
import presentation.View;

/**
 * Clasa main in care se initializeaza aplicatia si controller-ul
 */
public class Start {
	protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

	public static void main(String[] args){

		View view = new View();
		Controller controller = new Controller(view);

	}
	
	

}
