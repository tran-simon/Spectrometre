package physics.component;


import math.SVector3d;
import physics.Physics;

/**
 * Classe qui permet de créer un cyclotron
 *
 * @author Sofianne
 */

public class Cyclotron extends ParticleGenerator {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static final double DEFAULT_FINAL_SPEED = 1e-3 * Physics.c;


    public static final String name = "Cyclotron";


    private double finalSpeed = DEFAULT_FINAL_SPEED;

    private SVector3d direction = new SVector3d(1, 0);


    /**
     * Constructeur
     */
    public Cyclotron() {
        super();
    }

    /**
     * Crée un cyclotron par défault
     *
     * @param size       La taille du cyclotron
     * @param finalSpeed La vitesse à la sortie
     * @param direction  La direction de la particule
     */
    public Cyclotron(SVector3d size, double finalSpeed, SVector3d direction) {
        this();
        setSize(size);
        this.finalSpeed = finalSpeed;
        this.direction = direction;
    }

    @Override
    /**
     * Méthode qui génère une particule
     */
    public Particle generateParticle() {
        Particle particle = new Particle(getParticle());

        SVector3d particlePosition = getPosition().add(getSize().multiply(0.5)); /* On place la particule au centre du cyclotron */

        /* Le vecteur à additionner à la position centrale du cyclotron afin d'obtenir la position de la particule correspondant à la sortie du cyclotron */
        SVector3d localPos = direction.scale(getSize().multiply(0.5)).rotate(-Math.PI / 4.0);
        particle.setPosition(particlePosition.add(localPos));
        particle.setSpeed(getDirection().normalize().multiply(getFinalSpeed()));
        particle.setCreatesField(false);

        return particle;
    }



    @Override
    /**
     * Méthode qui retourne le nom du composant
     */
    public String getName() {
        return name;
    }


    /**
     * Méthode qui retourne la vitesse finale
     *
     * @return La vitesse finale
     */
    public double getFinalSpeed() {
        return finalSpeed;
    }

    /**
     * Méthode qui ajuste la vitesse finale
     *
     * @param finalSpeed La vitesse finale
     */
    public void setFinalSpeed(double finalSpeed) {
        this.finalSpeed = finalSpeed;
    }

    /**
     * Méthode qui retourne la direction de la particule
     *
     * @return La direction de la particule
     */
    public SVector3d getDirection() {
        return direction.normalize();
    }

    /**
     * Méthode qui ajuste la direction de la particule
     *
     * @param direction La direction de la particule
     */
    public void setDirection(SVector3d direction) {
        this.direction = direction.normalize();
    }
}
