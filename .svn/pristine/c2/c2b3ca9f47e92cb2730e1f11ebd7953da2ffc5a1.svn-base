package physics.component;

import aaplication.AppUtil;
import math.SVector3d;
import physics.PhysicsWorld;


/**
 * Composant qui crée des particules à chaque interval de temps
 *
 * @author Simon Tran
 */
public class ParticleGenerator extends PhysicComponent {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    /**
     * Le nom du composant en français tel qu'il sera indiqué sur l'interface
     */
    public static final String name = "Générateur de particules";


    /**
     * La période
     * Le nombre de secondes qui s'écoulent avant qu'une particule soit générée
     */
    private double period = 1e-6;

    private double timeRemainingBeforeNextParticleGeneration = 0.0;
    private Particle particle;


    public static final SVector3d DEFAULT_SIZE = AppUtil.COMPONENT_DEFAULT_SIZE.multiply(0.5);
    /**
     * Constructeur par défaut
     */
    public ParticleGenerator() {
        setParticle(Particle.Electron());
        setSize(DEFAULT_SIZE);
    }

    /**
     * Construit un générateur de particules
     *
     * @param particle La particule qui sera copiée
     * @param period La période
     */
    public ParticleGenerator(Particle particle, double period) {
        setParticle(particle);
        setPeriod(period);
    }

    /**
     * Construit un générateur de particules
     *
     * @param particle La particule qui sera copiée
     * @param period   La période
     * @param position La position
     */
    public ParticleGenerator(Particle particle, double period, SVector3d position) {
        this(particle, period);
        setPosition(position);
    }

    /**
     * Génère une particule dans le monde physique
     * @return La particule générée
     */
    public Particle generateParticle() {
        Particle particle = new Particle(getParticle());
        particle.setCreatesField(false);
        particle.setPosition(getPosition().add(getSize().multiply(0.5)));
        return particle;
    }

    /**
     * Permet de mettre à jour le générateur de particules et de générer une particule si le temps est bon
     * @param physicsWorld  Le monde physique
     * @param delta_t   La différence de temps
     * @return  Vrai si une particule a été générée
     */
    public boolean update(PhysicsWorld physicsWorld, double delta_t) {
        if (getPeriod() != 0.0) {
            timeRemainingBeforeNextParticleGeneration -= delta_t;
            if (timeRemainingBeforeNextParticleGeneration <= 0) {
                physicsWorld.add(generateParticle());
                timeRemainingBeforeNextParticleGeneration = getPeriod();
                return true;
            }

        }
        return false;
    }


    /**
     * Permet d'obtenir le champ électrique
     * Le champ est nulle puisque le générateur de particule ne crée pas de champs
     *
     * @param position La position absolue où il faut calculer le champ
     * @param t        Le temps auquel il faut calculer le champ
     * @return Vecteur nulle représentant le champ créé par le composant
     */
    @Override
    public SVector3d getElectricField(SVector3d position, double t) {
        return new SVector3d();
    }

    /**
     * Permet d'obtenir le champ magnétique
     * Le champ est nulle puisque le générateur de particule ne crée pas de champs
     *
     * @param position La position absolue où il faut calculer le champ
     * @param t        Le temps auquel il faut calculer le champ
     * @return Vecteur nulle représentant le champ créé par le composant
     */
    @Override
    public SVector3d getMagneticField(SVector3d position, double t) {
        return new SVector3d();
    }

    /**
     * Permet d'obtenir le nom en français du composant
     * @return Le nom du composant
     */
    @Override
    public String getName() {
        return name;
    }

    /* ------------------Getters & Setters------------------- */

    /**
     * Retourne le period.
     *
     * @return le period
     */
    public double getPeriod() {
        return period;
    }

    /**
     * Définit le period.
     *
     * @param period le nouveau period
     */
    public void setPeriod(double period) {
        this.period = period;
    }

    /**
     * Permet d'obtenir la particule qui sera générée
     * @return La particule
     */
    public Particle getParticle() {
        return particle;
    }

    /**
     * Définit la particule qui sera générée
     * @param particle La particule
     */
    public void setParticle(Particle particle) {
        this.particle = particle;
    }


}
