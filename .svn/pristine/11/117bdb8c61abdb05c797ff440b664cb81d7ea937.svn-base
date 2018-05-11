package physics.component;

import controller.SimulationViewController;
import math.MathUtil;
import math.SVector3d;
import physics.ComplexCyclotronListener;
import physics.PhysicsWorld;
import physics.component.field.ElectricField;
import physics.component.field.MagneticField;
import view.dessinable.componentview.ComponentView;

import java.awt.geom.Rectangle2D;

/**
 * Classe qui permet d'accéder aux propriétés avancées du cyclotron
 * @author Sofianne Laurin
 */
public class ComplexCyclotron extends PhysicsWorld {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final double DEFAULT_CYLENER_RADIUS = 1.0, DEFAULT_DISTANCE_BETWEEN_CYLENDERS = 1, DEFAULT_VOLTAGE = 1, DEFAULT_MAGNETIC_FIELD_INTENSITY = 1e-5;

    private ElectricField electricField;
    private MagneticField magneticField1;
    private MagneticField magneticField2;

    private double cylenderRadius = DEFAULT_CYLENER_RADIUS, distanceBetweenCylenders = DEFAULT_DISTANCE_BETWEEN_CYLENDERS, voltage = DEFAULT_VOLTAGE;
    private int directionX = 1;

    /**
     * Constructeur du cyclotron par défault
     * @param particle La particule
     */
    public ComplexCyclotron(Particle particle) {

        electricField = new ElectricField();
        magneticField1 = new MagneticField();
        magneticField2 = new MagneticField();

        magneticField1.setIntensity(DEFAULT_MAGNETIC_FIELD_INTENSITY);
        magneticField2.setIntensity(DEFAULT_MAGNETIC_FIELD_INTENSITY);


        this.add(electricField);
        this.add(magneticField1);
        this.add(magneticField2);

        particle = new Particle(particle);
        particle.setSpeed(new SVector3d());
        particle.setPosition(new SVector3d(cylenderRadius + distanceBetweenCylenders / 2.0, cylenderRadius));

        this.add(particle);
        updateFields();
    }

    /**
     * Permet de mettre à jour la position des champs
     */
    public void updateFields() {
        electricField.setIntensity(calculateElectricField());
        electricField.setSize(new SVector3d(distanceBetweenCylenders, 2.0 * cylenderRadius));
        magneticField1.setSize(new SVector3d(cylenderRadius, 2.0 * cylenderRadius));
        magneticField2.setSize(new SVector3d(cylenderRadius, 2.0 * cylenderRadius));

        electricField.setPosition(new SVector3d(cylenderRadius, 0.0));
        magneticField2.setPosition(new SVector3d(cylenderRadius + distanceBetweenCylenders, 0.0));
    }

    @Override
    /**
     * Lève un événement lorsque le step change
     */
    public void raiseEventStep(double delta_t) {
        super.raiseEventStep(delta_t);
        for (ComplexCyclotronListener cyclotronListener : LISTENERS.getListeners(ComplexCyclotronListener.class)) {
            cyclotronListener.step(delta_t);
        }
    }

    /**
     * Lève un événement lorsque la vitesse est recalculée
     * @param speed La vitesse
     */
    public void raiseEventSpeedCalculated(double speed){
        for (ComplexCyclotronListener cyclotronListener : LISTENERS.getListeners(ComplexCyclotronListener.class)) {
            cyclotronListener.speedCalculated(speed);
        }
    }

    /**
     * Ajout d'un écouteur personnalisé
     * @param listener L'écouteur parsonnalisé
     */
    public void addCyclotronListener(ComplexCyclotronListener listener) {
        LISTENERS.add(ComplexCyclotronListener.class, listener);
    }


    /**
     * Méthode qui retourne l'intensité du champ magnétique
     *
     * @return L'intensité du champ magnétique
     */
    public double calculateElectricField() {
        return -getVoltage() / getDistanceBetweenCylenders();
    }

    /**
     * Méthode qui calcule la fréquance du cyclotron
     * @param particleCharge La charge de la particule
     * @param particleMass La masse de la particule
     * @return La fréquence
     */
    public double calculateFrequency(double particleCharge, double particleMass) {
        return (Math.abs(particleCharge) * getMagneticFieldIntensity()) / (2.0 * Math.PI * particleMass);
    }

    /**
     * Méthode qui retourne l'intensité du champ magnétique
     * @return l'intensité du champ magnétique
     */
    public double getMagneticFieldIntensity() {
        return magneticField1.getIntensity();
    }

    /**
     * Méthode qui ajuste l'intensité des champs magnétiques
     * @param intensity l'intensité des champs magnétiques
     */
    public void setMagneticFieldIntensity(double intensity) {
        magneticField1.setIntensity(intensity);
        magneticField2.setIntensity(intensity);
    }

    /**
     * Méthode qui permet de changer la particule
     * @param particle La particule
     * @param controller Le controlleur de la simulation
     */
    public void changeParticle(Particle particle, SimulationViewController controller) {
        particle = new Particle(particle);
        particle.setSpeed(new SVector3d());
        particle.setPosition(new SVector3d(cylenderRadius + distanceBetweenCylenders / 2.0, cylenderRadius));
        getParticleList().clear();
        controller.addToWorld(ComponentView.generateView(particle));
    }

    @Override
    /**
     * Méthode qui calcule une itération pysique
     */
    public void step(double delta_t, double time) {
        super.step(delta_t, time);
        if (!getParticleList().isEmpty() && !getBounds().contains(getParticleList().get(0).getPosition().toPoint())) {
            raiseEventSpeedCalculated(getParticleList().get(0).getSpeedModulus());

        }
    }

    @Override
    /**
     * Méthode qui retourne le champ électrique
     */
    public SVector3d getElectricField(SVector3d position, double t) {
        Particle particle = getParticleList().get(0);
        if (particle != null) {
            if (magneticField1.getBounds().contains(particle.getPosition().toPoint())) {
                directionX = 1;
            }
            if (magneticField2.getBounds().contains(particle.getPosition().toPoint())) {
                directionX = -1;
            }
        }
        electricField.setFieldDirection(new SVector3d(directionX, 0.0));
        return electricField.getElectricField(position, t);
    }

    @Override
    /**
     * Méthode qui retourne le champ magnétique
     */
    public SVector3d getMagneticField(SVector3d position, double t) {
        return magneticField1.getMagneticField(position, t).add(magneticField2.getMagneticField(position, t));
    }

    @Override
    /**
     * Méthode qui retourne les limites du cyclotron
     */
    public Rectangle2D.Double getBounds() {
        return MathUtil.createRectangle(new SVector3d(), getSize());
    }

    @Override
    /**
     * Méthode qui retourne la taille du cyclotron
     */
    public SVector3d getSize() {
        return new SVector3d(cylenderRadius * 2.0 + distanceBetweenCylenders, cylenderRadius * 2.0);
    }

    /**
     * Méthode qui retourne le rayon des dés
     * @return le rayon des dés
     */
    public double getCylenderRadius() {
        return cylenderRadius;
    }

    /**
     * Méthode qui ajuste le rayon des dés
     * @param cylenderRadius le rayon des dés
     */
    public void setCylenderRadius(double cylenderRadius) {
        this.cylenderRadius = cylenderRadius;
        updateFields();
    }

    /**
     * Méthode qui retourne la distance entre les deux plaques chargées
     * @return la distance entre les deux plaques chargées
     */
    public double getDistanceBetweenCylenders() {
        return distanceBetweenCylenders;
    }

    /**
     * Méthode qui ajuste la distance entre les deux plaques chargées
     * @param distanceBetweenCylenders la distance entre les deux plaques chargées
     */
    public void setDistanceBetweenCylenders(double distanceBetweenCylenders) {
        this.distanceBetweenCylenders = distanceBetweenCylenders;
        updateFields();
    }

    /**
     * Méthode qui retourne l'intensité du champ électrique (le voltage)
     * @return le voltage
     */
    public double getVoltage() {
        return voltage;
    }

    /**
     * Méthode qui ajuste l'intensité du champ électrique (voltage)
     * @param voltage Le voltage
     */
    public void setVoltage(double voltage) {
        this.voltage = voltage;
        updateFields();
    }

}
