package physics;

import math.SVector3d;
import physics.component.Particle;
import physics.component.ParticleGenerator;
import physics.component.PhysicComponent;

import javax.swing.event.EventListenerList;
import java.util.ArrayList;


/**
 * Classe qui contient les éléments d'un monde et le simule.
 * L'origine du monde (le point 0,0) se trouve au coin en haut à gauche.
 *
 * @author Simon Tran
 */
public class PhysicsWorld extends PhysicComponent {


    /**
     *
     */
    private static final long serialVersionUID = 1L;


    public enum Algorithm {EULER, RK4}

    private ArrayList<Particle> particleList = new ArrayList<>();
    private ArrayList<PhysicComponent> componentList = new ArrayList<>();
    private ArrayList<ParticleGenerator> particleGeneratorsList = new ArrayList<>();


    private Algorithm algorithm = Algorithm.RK4;


    protected final EventListenerList LISTENERS = new EventListenerList();


    /**
     * Constructeur
     */
    public PhysicsWorld() {
    }


    /**
     * Permet d'ajouter un composant au monde
     *
     * @param component Le composant
     */
    public void add(PhysicComponent component) {
        if (component instanceof Particle) {
            particleList.add((Particle) component);
        }
        else if (component instanceof ParticleGenerator) {
            particleGeneratorsList.add((ParticleGenerator) component);
        }
        else {
            componentList.add(component);
        }
    }

    /**
     * Permet de savoir si le monde physique contient le composant
     *
     * @param component le composant
     * @return Vrai si le monde physique contient le composant
     */
    public boolean contains(PhysicComponent component) {
        if (component instanceof Particle) {
            return particleList.contains(component);
        }
        else if (component instanceof ParticleGenerator) {
            return particleGeneratorsList.contains(component);
        }
        else {
            return componentList.contains(component);
        }
    }

    /**
     * Permet de retirer un composant du monde
     *
     * @param component Le composant à retirer
     */
    public void remove(PhysicComponent component) {
        if (component instanceof Particle) {
            particleList.remove(component);
        }
        else if (component instanceof ParticleGenerator) {
            particleGeneratorsList.remove(component);
        }
        else {
            componentList.remove(component);
        }
    }



    /**
     * Permet de calculer l'accélération future d'une particle.
     * La méthode retourne l'accélération que la particle aura au temps t
     *
     * @param particle la particle
     * @param position la position future de la particle
     * @param speed    la vitesse future de la particle
     * @param t        le temps
     * @return L'accélération sous la forme d'un vecteur
     */
    public SVector3d getParticleAcceleration(Particle particle, SVector3d position, SVector3d speed, double t) {
        /* On garde en mémoire l'état initiale de la particle */
        SVector3d particuleElectricField = particle.getElectricField();
        SVector3d particuleMagneticField = particle.getMagneticField();
        SVector3d particulePosition = particle.getPosition();
        SVector3d particuleSpeed = particle.getPosition();

        /* On met à jour la particle pour avoir son accélération au temps t */
        particle.setPosition(position);
        particle.setSpeed(speed);
        updateParticle(particle, t);
        SVector3d acceleration = particle.getAcceleration();

        /* On restore l'état initiale de la particle */
        particle.setElectricField(particuleElectricField);
        particle.setMagneticField(particuleMagneticField);
        particle.setPosition(particulePosition);
        particle.setSpeed(particuleSpeed);
        return acceleration;
    }


    /**
     * Permet de mettre à jour les forces qui s'appliquent à la particle au temps t
     *
     * @param particle la particle
     * @param t        le temps
     */
    public void updateParticle(Particle particle, double t) {
        if (particle.isInteractive()) {
            SVector3d particulePosition = particle.getPosition();
            particle.setElectricField(getElectricField(particulePosition, t));
            particle.setMagneticField(getMagneticField(particulePosition, t));

        }
    }

    /**
     * Permet de mettre à jour les générateurs de particules
     *
     * @param delta_t La différence de temps
     */
    public void updateParticleGenerators(double delta_t) {
        for (ParticleGenerator generator : getParticleGeneratorsList()) {
            boolean particleGenerated;
            particleGenerated = generator.update(this, delta_t);
            if (particleGenerated) {
                raiseEventParticleGenerated();
            }
        }

    }

    /**
     * Permet de mettre à jour les forces qui s'appliquent aux particules ainsi que la vitesse et la position des particules en fonction d'une différence de temps
     *
     * @param delta_t La différence de temps
     * @param time Le temps présent
     */
    public void step(double delta_t, double time) {
        raiseEventStep(delta_t);
        updateParticleGenerators(delta_t);
        for (Particle particle : particleList) {
            updateParticle(particle, time + delta_t);
            if (particle.isMoveable()) {
                switch (algorithm) {
                    case RK4:
                        stepRungeKutta(particle, delta_t, time);
                        break;
                    case EULER:
                        stepEuler(particle, delta_t);
                        break;
                }
            }
        }
    }

    /**
     * Permet de mettre à jour la vitesse et la position d'une particle en fonction d'un pas de Runge Kutta (RK4)
     *
     * @param particle La particle à modifier
     * @param delta_t  La différence de temps
     * @param time le temps présent
     */
    public void stepRungeKutta(Particle particle, double delta_t, double time) {
        SVector3d x0 = particle.getPosition();
        SVector3d v0 = particle.getSpeed();
        double t0 = time;
        SVector3d a0 = getParticleAcceleration(particle, x0, v0, t0);

        /* Calculs à la première position finale */
        SVector3d xf1 = Physics.position(x0, v0, new SVector3d(), delta_t);
        SVector3d vf1 = Physics.speed(v0, a0, delta_t);
        double tf1 = t0 + delta_t;
        SVector3d af1 = getParticleAcceleration(particle, xf1, vf1, tf1);

        /* Calculs à la position à demi-temps */
        SVector3d xmid = Physics.position(x0, v0, a0, delta_t / 2);
        SVector3d tempA = a0.multiply(0.75).add(af1.multiply(0.25));
        SVector3d vmid = Physics.speed(v0, tempA, delta_t / 2);
        double tmid = t0 + delta_t / 2;
        SVector3d amid = getParticleAcceleration(particle, xmid, vmid, tmid);

        /* Calculs à la deuxième position finale */
        SVector3d xf2 = Physics.position(x0, v0, a0, delta_t);
        SVector3d tempA2 = a0.multiply(0.5).add(af1.multiply(0.5));
        SVector3d vf2 = Physics.speed(v0, tempA2, delta_t);
        double tf2 = t0 + delta_t;
        SVector3d af2 = getParticleAcceleration(particle, xf2, vf2, tf2);

        /* Calcul de la position finale avec pondération de l’accélération  */
        SVector3d tempA3 = a0.multiply((1.0 / 3)).add(amid.multiply(2.0 / 3));
        SVector3d x = Physics.position(x0, v0, tempA3, delta_t);
        SVector3d tempA4 = a0.multiply(1.0 / 6).add(amid.multiply(4.0 / 6)).add(af2.multiply(1.0 / 6));
        SVector3d v = Physics.speed(v0, tempA4, delta_t);

        particle.setPosition(x);
        particle.setSpeed(v);

    }

    /**
     * Permet de mettre a jour la vitesse et la position d'une particle en fonction d'un pas de Euler
     *
     * @param particle La particle à modifier
     * @param delta_t  La différence de temps
     */
    public void stepEuler(Particle particle, double delta_t) {
        SVector3d speed = particle.getSpeed().add(particle.getAcceleration().multiply(delta_t));
        SVector3d position = particle.getPosition().add(particle.getSpeed().multiply(delta_t));
        particle.setPosition(position);
        particle.setSpeed(speed);
    }





    /* Écouteurs */


    /**
     * Permet d'ajouter un écouteur de type PhysicsWorldListener
     *
     * @param listener l'écouteur
     */
    public void addPhysicsWorldListener(PhysicsWorldListener listener) {
        LISTENERS.add(PhysicsWorldListener.class, listener);
    }

    /**
     * Permet de lever un évènement lorsque la simulation fait un pas
     *
     * @param delta_t La différence de temps
     */
    public void raiseEventStep(double delta_t) {
        for (PhysicsWorldListener listener : LISTENERS.getListeners(PhysicsWorldListener.class)) {
            listener.step(delta_t);
        }
    }

    /**
     * Permet de lever un évènement lorsqu'une particule est générée
     */
    public void raiseEventParticleGenerated() {
        for (PhysicsWorldListener listener : LISTENERS.getListeners(PhysicsWorldListener.class)) {
            listener.particleGenerated();
        }
    }





    /* ------------------Méthodes "Override"------------------- */



    /**
     * Permet de calculer le champ électrique vectoriel à la position absolue 'position' au temps t
     *
     * @param position La position absolue où il faut calculer le champ
     * @param t        Le temps auquel il faut calculer le champ
     * @return Un vecteur décrivant le champ électrique à cette position
     */
    @Override
    public SVector3d getElectricField(SVector3d position, double t) {
        SVector3d electricField = new SVector3d();
        for (Particle particle : particleList) {
            if (particle.isInteractive() && particle.getCreatesField()) {
                electricField = electricField.add(particle.getElectricField(position, t));
            }
        }
        for (PhysicComponent component : componentList) {
            if (component.isInteractive()) {
                electricField = electricField.add(component.getElectricField(position, t));
            }
        }
        return electricField;
    }


    /**
     * Permet de calculer le champ magnétique vectoriel à la position absolue 'position' au temps t
     *
     * @param position La position absolue où il faut calculer le champ
     * @param t        Le temps auquel il faut calculer le champ
     * @return Un vecteur décrivant le champ magnétique à cette position
     */
    @Override
    public SVector3d getMagneticField(SVector3d position, double t) {
        SVector3d magneticField = new SVector3d();
        for (PhysicComponent component : componentList) {

            if (component.isInteractive()) {
                magneticField = magneticField.add(component.getMagneticField(position, t));
            }
        }
        return magneticField;
    }


    /**
     * Permet d'obtenir le nom du composant
     *
     * @return Le nom
     */
    @Override
    public String getName() {
        return name;
    }

    /* -------------------Getters & Setters------------------- */



    /**
     * Retourne l'algorithme qui est utilisé pour la simulation
     *
     * @return l'algorithme
     */
    public Algorithm getAlgorithm() {
        return algorithm;
    }

    /**
     * Défini l'algorithme de la simulation
     *
     * @param algorithm l'algorithme
     */
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }


    /**
     * Permet d'obtenir la liste des particules
     *
     * @return la liste des particules
     */
    public ArrayList<Particle> getParticleList() {
        return particleList;
    }

    /**
     * Permet de définir une nouvelle liste de particules
     *
     * @param particleList la nouvelle liste de particules
     */
    public void setParticleList(ArrayList<Particle> particleList) {
        this.particleList = particleList;
    }

    /**
     * Permet d'obtenir la liste de composants
     *
     * @return La liste de compoants
     */
    public ArrayList<PhysicComponent> getComponentList() {
        return componentList;
    }

    /**
     * Permet de définir la liste des composants
     *
     * @param componentList La liste des composants
     */
    public void setComponentList(ArrayList<PhysicComponent> componentList) {
        this.componentList = componentList;
    }

    /**
     * Permet d'obtenir la liste des générateurs de particules
     *
     * @return La liste des générateurs de particules
     */
    public ArrayList<ParticleGenerator> getParticleGeneratorsList() {
        return particleGeneratorsList;
    }

    /**
     * Permet de définir la liste des générateurs de particules
     *
     * @param particleGeneratorsList La liste des générateurs de particules
     */
    public void setParticleGeneratorsList(ArrayList<ParticleGenerator> particleGeneratorsList) {
        this.particleGeneratorsList = particleGeneratorsList;
    }



}
