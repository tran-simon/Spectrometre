package physics.component;


import math.SVector3d;
import physics.Physics;
import physics.component.field.ElectricField;
import physics.component.field.MagneticField;

import java.awt.geom.Rectangle2D;

/**
 * Classe qui permet de créer un sélecteur de vitesse
 *
 * @author Sofianne
 */
public class SelecteurDeVitesse extends PhysicComponent {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static final SVector3d DEFAULT_SIZE = new SVector3d(ElectricField.DEFAULT_SIZE.getX() * 2, ElectricField.DEFAULT_SIZE.getY() / 8.0);
    public static final double DEFAULT_MAGNETIC_FIELD_INTENSITY = 1e-5;
    public static final double DEFAULT_SELECTION_SPEED = Cyclotron.DEFAULT_FINAL_SPEED;
    public static final double DEFAULT_ELECTRIC_FIELD_INTENSITY = DEFAULT_SELECTION_SPEED * DEFAULT_MAGNETIC_FIELD_INTENSITY;
    public static final double MIN_SELECTION_SPEED = 1e-20;
    public static final double MAX_SELECTION_SPEED = 0.5 * Physics.c;


    private ElectricField electricField = new ElectricField(DEFAULT_SIZE, new SVector3d(0, 1));

    private MagneticField magneticField = new MagneticField(DEFAULT_SIZE, new SVector3d(0, 0, 1), DEFAULT_MAGNETIC_FIELD_INTENSITY);

    public static final String name = "Sélecteur de vitesse";

    private SVector3d entrancePosition = new SVector3d();
    private double entranceSpeed = 0.0;

    /**
     * Constructeur par défaut du sélecteur de vitesse
     */
    public SelecteurDeVitesse() {
    	
        setSize(DEFAULT_SIZE);
        updateFields();
        setSelectionSpeed(DEFAULT_SELECTION_SPEED);

    }


    /**
     * Méthode qui met à jour la position et la taille des champs électriques et magnétiques
     */
    private void updateFields() {
        getElectricField().setBounds(getBounds());
        getMagneticField().setBounds(getBounds());
    }
//
//	/**
//	 * Méthode qui initialise un champ électrique
//	 */
//	private void initializeElectricFields() {
//		Rectangle2D.Double bounds = new Rectangle2D.Double(getPosition().getX(), getPosition().getY(), 2e-4, 1e-4);
//
//		electricField.setBounds(bounds);
//		electricField.setFieldDirection(new SVector3d(directionX, 0.0));
//		electricField.setIntensity(getElectricFieldIntensity());
//
//	}
//
//
//	/**
//	 * Méthode qui initialise un champ magnétique
//	 */
//	private void initializeMagneticFields() {
//		SVector3d position = new SVector3d(getPosition().getX(), getPosition().getY() );
//
//		SVector3d size = new SVector3d(getWidth(), getDistanceBetweenPlates());
//
//		magneticField.setBounds(new Rectangle2D.Double(position.getX(), position.getY(), size.getX(), size.getY()));
//		magneticField.setFieldDirection(new SVector3d(0.0, 0.0, 1.0));
//		magneticField.setIntensity(getMagneticFieldIntensity());
//
//
//
//	}

    /**
     * Méthode qui retourne le champ magnétique
     *
     * @return Le champ magnétique
     */
    public MagneticField getMagneticField() {
        return magneticField;
    }

    /**
     * Méthode qui retourne le module de la vitesse de sélection
     * La vitesse de sélection est égale à E/B
     * où E: Module du champ électrique (N/C)
     * et B: Module du champ magnétique (T)
     *
     * @return Le module de la vitesse de sélection
     */
    public double getSelectionSpeed() {
        return getElectricFieldIntensity() / getMagneticFieldIntensity();
    }

    /**
     * Méthode qui définie la vitesse de sélection
     *
     * @param selectionSpeed
     */
    public void setSelectionSpeed(double selectionSpeed) {
        getElectricField().setIntensity(selectionSpeed * getMagneticField().getIntensity());
    }


    /**
     * Méthode qui retourne le champ électrique
     *
     * @return Le champ électrique
     */
    public ElectricField getElectricField() {
        return electricField;
    }


    /**
     * Méthode qui retourne l'intensité du champ électrique
     * @return L'intensité du champ électrique
     */
    public double getElectricFieldIntensity() {
        return electricField.getIntensity();
    }

    /**
     * Méthode qui retourne l'intensité du champ magnétique
     * @return L'intensité du champ magnétique
     */
    public double getMagneticFieldIntensity() {
        return magneticField.getIntensity();

    }

    /**
     * Méthode qui ajuste l'intensité du champ magnétique
     * @param intensity L'intensité du champ magnétique
     */
    public void setMagneticFieldIntensity(double intensity) {
        getMagneticField().setIntensity(intensity);
    }

    /**
     * Méthode qui ajuste l'intensité du champ électrique
     * @param intensity L'intensité du champ électrique
     */
    public void setElectricFieldIntensity(double intensity) {
        getElectricField().setIntensity(intensity);
    }


    /**
     * Méthode qui retourne le champ électrique
     *
     * @param position Le vecteur de la position
     * @param t        Le temps
     * @return Le champ électrique
     */
    public SVector3d getElectricField(SVector3d position, double t) {
        return this.electricField.getElectricField(position, t);
    }

    /**
     * Méthode qui retourne le champ magnétique
     *
     * @param position Le vecteur de la position
     * @param t        Le temps
     * @return Le champ magnétique
     */
    public SVector3d getMagneticField(SVector3d position, double t) {
        return magneticField.getMagneticField(position, t);
    }


    /**
     * Méthode qui retourne le nom du composant
     *
     * @return Le nom du composant
     */
    public String getName() {
        return name;
    }

    @Override
    /**
     * Méthode qui ajuste les dimensions du sélecteur de vitesse
     */
    public void setBounds(Rectangle2D.Double bounds) {
        super.setBounds(bounds);
        updateFields();
    }


    /**
     * Méthode qui retourne la position d'entrée
     * @return La position d'entrée
     */
    public SVector3d getEntrancePosition() {
        return entrancePosition;
    }

    /**
     * Méthode qui ajuste la position d'entrée
     * @param entrancePosition La position d'entrée
     */
    public void setEntrancePosition(SVector3d entrancePosition) {
        this.entrancePosition = entrancePosition;
    }

    /**
     * Méthode qui retourne la vitesse à l'entrée 
     * @return La vitesse à l'entrée 
     */
    public double getEntranceSpeed() {
        return entranceSpeed;
    }

    /**
     * Méthode qui ajuste la vitesse à l'entrée
     * @param entranceSpeed La vitesse à l'entrée 
     */
    public void setEntranceSpeed(double entranceSpeed) {
        this.entranceSpeed = entranceSpeed;
    }
}
