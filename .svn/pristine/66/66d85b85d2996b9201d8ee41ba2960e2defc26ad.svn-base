package physics.component;


import aaplication.AppUtil;
import math.SVector3d;
import physics.component.field.MagneticField;

/**
 * Classe qui permet de créer un déflecteur
 * @author Sofianne
 *
 */
public class Deflecteur extends MagneticField{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final double DEFAULT_INTENSITY = 1e-5;


	public static final String name = "Déflecteur";

	private SVector3d entrancePosition = new SVector3d();

	/**
	 * Constructeur du déflecteur par défaut
	 */
	public Deflecteur(){
		this.setPosition(new SVector3d(0, 0));
		setSize(AppUtil.COMPONENT_MAX_SIZE);

		setIntensity(DEFAULT_INTENSITY);


//		this.setMagneticFieldIntensity(7.06e-3);
//		initializeMagneticFields();
	}
	/**
	 * Constructeur du déflecteur
	 * @param position Position du point milieu du déflecteur
	 * @param magneticFieldIntensity Intensité du champ magnétique
	 */
	public Deflecteur(SVector3d position, double magneticFieldIntensity) {
		this.setPosition(position);
		setIntensity(magneticFieldIntensity);

	}

	/**
	 * Méthode qui calcule la masse
	 * @param charge La charge de la particule
	 * @param speed La vitesse
	 * @param trajectoryRadius Le rayon de la trajectoire
	 * @return La masse
	 */
	public double calculateMass(double charge, double speed,  double trajectoryRadius) {
		return (trajectoryRadius * getIntensity() * Math.abs(charge)) / speed;

	}



	/**
	 * Méthode qui retourne la position d'entrée dans le déflecteur
	 * @return La position d'entrée
	 */
	public SVector3d getEntrancePosition() {
		return entrancePosition;
	}

	/**
	 * Méthode qui ajuste la position d'entrée
	 * @param entrancePosition la position d'entrée
	 */
	public void setEntrancePosition(SVector3d entrancePosition) {
		this.entrancePosition = entrancePosition;
	}

	/**
	 * Méthode qui retourne le nom du composant
	 * @return Le nom du composant
	 */
	public String getName() {
		
	    return name;
	}
}
