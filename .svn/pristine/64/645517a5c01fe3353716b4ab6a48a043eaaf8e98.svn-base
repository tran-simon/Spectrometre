package physics;

import physics.component.Particle;
/**
 * Interface des écouteur personnalisés du spectromètre
 * @author Simon
 *
 */
public interface SpectrometreListener extends PhysicsWorldListener{
	/**
	 * Écouteur lorsque la particule est dans le sélecteur de vitesse
	 * @param particle La particule
	 */
    void particleEnteredSelecteur(Particle particle);

    /**
     * Écouteur lorsque la particule est dans le déflecteur
     * @param particle La particule
     */
    void particleEnteredDeflecteur(Particle particle);

    /**
     * Écouteur lorsque la particule est arrêtée
     * @param particle La particule
     */
    void particleStopped(Particle particle);

    /**
     * Écouteur lorsque la masse de la particule est détectée
     * @param diameter Le diamètre de la trajectoire
     * @param detectedMass La masse détectée
     */
    void massDetected(double diameter, double detectedMass);

    /**
     * Écouteur lorsque la position de la particule change
     * @param particle La particule
     */
    void particlePositionChanged(Particle particle);
}
