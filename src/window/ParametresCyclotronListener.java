package window;

import java.util.EventListener;

/**
 * Classe qui permet de générer des écouteurs parsonnalisés pour la fenêtre du cyclotron
 * @author Sofianne
 *
 */
public interface ParametresCyclotronListener extends EventListener{
	/**
	 * Un événement est levé lorsque la particule a quittée le cyclotron
	 * @param speed La vitesse de la particule
	 */
	void speedCalculated(double speed);

	/**
	 * Un événement est levé lorsque la simulation est en cours
	 * @param simulating Si l'application est en simulation
	 */
	void simulating(boolean simulating);
}
