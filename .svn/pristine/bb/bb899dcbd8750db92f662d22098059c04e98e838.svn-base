package physics;

import java.util.EventListener;

/**
 * Écouteur personnalisé permettant de notifier l'application que le PhysicsWorld a fait un pas
 * @author Simon tran
 */
public interface PhysicsWorldListener extends EventListener{

    /**
     * Un évènement est levé lorsque la simulation fait un pas
     * @param delta_t   La différence de temps
     */
    void step(double delta_t);

    /**
     * Un évènement est levé lorsqu'une nouvelle particule est générée par une générateur de particules
     */
    void particleGenerated();
    

}
