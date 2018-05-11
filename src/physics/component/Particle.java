package physics.component;

import math.SVector3d;
import physics.Physics;

/**
 * Classe représentant une particule.
 *
 * @author Simon Tran
 */
public class Particle extends PhysicComponent {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static final double DEFAULT_MASS = Physics.MASS_ELECTRON;
    public static final double DEFAULT_CHARGE = -Physics.e;

    public static final double MASS_MIN = 0.0;
    public static final double MASS_MAX = 1.0;
    public static final double CHARGE_MIN = -1.0;
    public static final double CHARGE_MAX = 1.0;

    /**
     * Le nom du composant en français tel qu'il sera indiqué sur l'interface
     */
    public static final String name = "Particule";

    /**
     * Le champ électrique créé par les autres composants du PhysicsWorld à la position de la particule.
     */
    private SVector3d electricField = new SVector3d();

    /**
     * Le champ magnétique créé par les autres composants du PhysicsWorld à la position de la particule.
     */
    private SVector3d magneticField = new SVector3d();


    /**
     * Masse de la particule (Kg)
     */
    private double mass = DEFAULT_MASS;

    /**
     * Charge électrique de la particule en Coulomb (C)
     */
    private double charge = DEFAULT_CHARGE;

    /**
     * Permet de définir si une particule peut bouger
     */
    private boolean moveable = true;

    /**
     * Permet de définir si la particule crée un champ électrique.
     */
    private boolean createsField = true;

    /**
     * La vitesse de la particule
     */
    private SVector3d speed = new SVector3d();

    /**
     * Permet de construire une particule sans information
     */
    public Particle() {
    }

    /**
     * Permet de construire une particule à partir d'une particule existante
     * @param particle  La particule
     */
    public Particle(Particle particle) {
        this.setMass(particle.getMass());
        this.setCharge(particle.getCharge());
        this.setPosition(particle.getPosition());
        this.setSpeed(particle.getSpeed());
        this.setMoveable(particle.isMoveable());
        this.setCreatesField(particle.getCreatesField());
    }


    /**
     * Permet de construire une particule à partir d'une masse et d'une charge.
     * La position de la particule est définie par le point (0, 0)
     *
     * @param mass   La masse de la particule (Kg)
     * @param charge La charge de la particule (C)
     */
    public Particle(double mass, double charge) {
        this.mass = mass;
        this.charge = charge;
    }


    /**
     * Permet de construire une particule à partir d'une masse, d'une charge et d'une position
     *
     * @param mass     La masse de la particule (Kg)
     * @param charge   La charge de la particule (C)
     * @param position La position de la particule
     */
    public Particle(double mass, double charge, SVector3d position) {
        this(mass, charge);
        this.setPosition(position);
    }

    /**
     * Permet de créer une particule comportant les valeurs d'un électron
     *
     * @return l'électron
     */
    public static Particle Electron() {
        return new Particle(Physics.MASS_ELECTRON, -Physics.e);
    }

    /**
     * Permet de créer une particule comportant les valeurs d'un proton
     *
     * @return le proton
     */
    public static Particle Proton() {
        return new Particle(Physics.MASS_PROTON, Physics.e);
    }

    /**
     * Permet de calculer le module du champ électrique à une distance 'r' de la particule.
     *
     * @param r la distance
     * @return La valeur du champ électrique en Coulombs
     */
    public double getElectricField(double r) {
        return Physics.electricField(getCharge(), r);
    }


    /* ------------------Méthodes "Override"------------------- */

    /**
     * Permet de calculer le champ électrique vectoriel à la position absolue 'position' créé par la particule
     *
     * @param position La position absolue où il faut calculer le champ
     * @param t        le temps de la simulation. Pour simplifier les calculs, cette valeur est ignorée puisque les particules qui bougent ne peuvent intéragir entre-elles.
     * @return Un vecteur décrivant le champ électrique à cette position
     */
    @Override
    public SVector3d getElectricField(SVector3d position, double t) {
        if (!getCreatesField() || position.equals(this.getPosition())) {
            return new SVector3d();
        }
        double r = SVector3d.distance(position, this.getPosition());
        SVector3d orientation = position.substract(this.getPosition()).normalize();
        return orientation.multiply(getElectricField(r));
    }

    /**
     * Permet d'obtenir la valeur du champ magnétique créé par la particule. Ce champ est toujours nul puisqu'une Particule ne crée pas de champ magnétique
     *
     * @param position La position absolue où il faut calculer le champ
     * @param t        Le temps auquel il faut calculer le champ
     * @return Un vecteur nul.
     */
    @Override
    public SVector3d getMagneticField(SVector3d position, double t) {
        return new SVector3d();
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
     * Permet de savoir si la particule crée un champ électrique
     * @return Si la particule crée un champ électrique
     */
    public boolean getCreatesField() {
        return createsField;
    }

    /**
     * Permet de définir si la particule crée un champ électrique
     * @param createsField Si la particule crée un champ électrique
     */
    public void setCreatesField(boolean createsField) {
        this.createsField = createsField;
    }

    /**
     * Permet d'obtenir l'accélération de la particule
     *
     * @return l'accélération
     */
    public SVector3d getAcceleration() {
        return Physics.acceleration(getForce(), getMass());
    }


    /**
     * Fixe la valeur de la charge électrique
     *
     * @param charge La nouvelle charge électrique
     */
    public void setCharge(double charge) {
        this.charge = charge;
    }

    /**
     * Retourne la charge électrique
     *
     * @return La charge électrique
     */
    public double getCharge() {
        return charge;
    }

    /**
     * Permet d'obtenir le module de la vitesse
     *
     * @return la vitesse
     */
    public double getSpeedModulus() {
        return getSpeed().modulus();
    }

    /**
     * Permet d'obtenir la vitesse
     *
     * @return la vitesse
     */
    public SVector3d getSpeed() {
        return speed;
    }

    /**
     * Permet de définir la vitesse
     *
     * @param speed La nouvelle vitesse
     */
    public void setSpeed(SVector3d speed) {
        this.speed = speed;
    }


    /**
     * Retourne la force en fonction des champs électromagnétiques à la position de la particule
     *
     * @return la force
     */
    public SVector3d getForce() {
        SVector3d electricForce = Physics.electricForce(getCharge(), getElectricField());
        SVector3d magneticForce = Physics.magneticForce(getCharge(), getSpeed(), getMagneticField());
        return electricForce.add(magneticForce);
    }

    /**
     * Retourne la masse
     *
     * @return la masse
     */
    public double getMass() {
        return mass;
    }

    /**
     * Défini la masse
     *
     * @param mass la nouvelle masse
     */
    public void setMass(double mass) {
        this.mass = mass;
    }

    /**
     * Retourne le champ électrique
     *
     * @return le champ électrique
     */
    public SVector3d getElectricField() {
        return electricField;
    }

    /**
     * Défini le champ électrique
     *
     * @param electricField le champ électrique
     */
    public void setElectricField(SVector3d electricField) {
        this.electricField = electricField;
    }

    /**
     * Retourne le champ magnétique
     *
     * @return le champ magnétique
     */
    public SVector3d getMagneticField() {
        return magneticField;
    }

    /**
     * Défini le champ magnétique
     *
     * @param magneticField le nouveau champ magnétique
     */
    public void setMagneticField(SVector3d magneticField) {
        this.magneticField = magneticField;
    }


    /**
     * Retourne si une particule peut bouger
     *
     * @return si la particule peut bouger
     */
    public boolean isMoveable() {
        return moveable;
    }

    /**
     * Défini si la particule peut bouger
     *
     * @param moveable Si elle peut bouger
     */
    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }


}