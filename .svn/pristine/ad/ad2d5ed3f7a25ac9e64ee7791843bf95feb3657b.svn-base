package controller;

import physics.PhysicsWorld;
import physics.PhysicsWorldListener;
import physics.component.PhysicComponent;
import view.View;

/**
 * Permet de faire le lien entre une vue (View) et un monde physique (PhysicsWorld)
 * Cette classe contient les méthodes qui permettent de mettre à jour  la vue en fonction du monde physique
 * Cela permet de séparer les instructions reliées aux calculs physiques de la simulation à celles reliées à l'affichage de celle-ci
 *
 * @author Simon Tran
 */
public class ViewController implements Runnable {
    public static double DEFAULT_STEP = 1e-8;
    private View view;
    private PhysicsWorld physicsWorld;


    private boolean simulating = false;
    private boolean paused = true;

    public static final long SLEEP_TIME = 5;

    private double step = DEFAULT_STEP;
    private double time = 0.0;

    private Thread thread = null;


    /**
     * Démarre la simulation
     */
    public void start() {
        if (!simulating) {
            thread = new Thread(this);
            thread.start();
        }
        simulating = true;
        unPause();
    }

    /**
     * Permet de redémarrer la simulation
     */
    public void unPause() {
        if (!simulating) {
            start();
        }
        paused = false;
    }

    /**
     * Permet de mettre la simulation sur pause
     */
    public void pause() {
        paused = true;
    }


    /**
     * Contient la boucle infinie de la simulation
     */
    @Override
    public void run() {
        while (simulating) {
            if (!paused) {
                step();
            }
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Permet de mettre à jour la vue en fonction du monde physique
     */
    public void updateView() {
        getView().resize();
    }

    /**
     * Constructeur par défaut
     */
    public ViewController() {

    }

    /**
     * Construit un ViewController avec un monde physique
     *
     * @param physicsWorld le monde physique
     */
    public ViewController(PhysicsWorld physicsWorld) {
        this.setPhysicsWorld(physicsWorld);
    }


    /**
     * Permet de mettre le monde physique sur pause s'il ne l'était pas déjà, ou de le démarrer s'il était sur pause.
     */
    public void togglePause() {
        if (isPaused()) {
            start();
        }
        else {
            pause();
        }
    }

    /**
     * Permet de calculer une itération du monde physique
     *
     * @author Sofianne
     */
    public void step() {
        getPhysicsWorld().step(getStep(), getTime());
        setTime(getTime() + getStep());
    }



    /**
     * Permet de supprimer un composant du monde physique
     *
     * @param component Le composant
     */
    public void removeFromWorld(PhysicComponent component) {
        getPhysicsWorld().remove(component);
    }


    /**
     * Permet de retirer toutes les particules
     */
    public void removeAllParticles() {
        while (!physicsWorld.getParticleList().isEmpty()) {
            removeFromWorld(getPhysicsWorld().getParticleList().get(0));
        }
        updateView();
    }



    /* -------------------Getters & Setters------------------- */

    /**
     * Permet d'obtenir le monde physique
     *
     * @return Le monde physique
     */
    public PhysicsWorld getPhysicsWorld() {
        return physicsWorld;
    }

    /**
     * Permet de définir le monde physique
     * La méthode associe aussi un écouteur afin de mettre à jour la vue lorsque le monde physique se met à jour
     *
     * @param physicsWorld Le nouveau monde physique
     */
    public void setPhysicsWorld(PhysicsWorld physicsWorld) {
        this.physicsWorld = physicsWorld;
        physicsWorld.addPhysicsWorldListener(new PhysicsWorldListener() {
            @Override
            public void step(double delta_t) {
                updateView();
            }

            @Override
            public void particleGenerated() {
                updateView();
            }

        });
    }


    /**
     * Permet d'obtenir la vue
     *
     * @return La vue
     */
    public View getView() {
        return view;
    }

    /**
     * Permet de définir la vue
     *
     * @param view La vue
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Retourne le pas de la simulation
     *
     * @return le pas
     */
    public double getStep() {
        return step;
    }

    /**
     * Défini le pas de la simulation
     *
     * @param step le nouveau pas
     */
    public void setStep(double step) {
        this.step = step;
    }

    /**
     * Permet d'obtenir si on est en cours de simulation
     *
     * @return Vrai si on est en cours de simulation
     */
    public boolean isSimulating() {
        return simulating;
    }

    /**
     * Permet de savoir si la simulation est sur pause
     *
     * @return Vrai si on est en pause
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Retourne le temps de la simulation
     * @return Le temps de la simulation
     */
    public double getTime() {
        return time;
    }

    /**
     * Définit le temps de la simulation
     * @param time Le temps
     */
    public void setTime(double time) {
        this.time = time;
    }
}
