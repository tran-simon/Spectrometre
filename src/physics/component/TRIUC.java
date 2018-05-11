package physics.component;

import math.SVector3d;
import physics.Physics;

/**
 * Classe qui contient les informations et les formules d'une tige rectiligne infinie uniformément chargée (TRIUC)
 *
 * @author Sofianne Laurin
 */
public class TRIUC extends PhysicComponent {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    public static final double DEFAULT_CHARGE_DENSITY = 1e-9;
    public static final SVector3d DEFAULT_SIZE = new SVector3d(2.0, 0.0);

	private double chargeDensity = 0.0;
	private boolean infinite = false;

    

	/**
     * L'orientation de la tige
     */
    private SVector3d orientation = new SVector3d();


    public static final String name = "Tige Rectiligne Infinie Uniformément Chargée (TRIUC)";

    public TRIUC(){
    	setChargeDensity(DEFAULT_CHARGE_DENSITY);
    	setOrientation(new SVector3d(1.0, 0.0));
    	setSize(DEFAULT_SIZE);
    }

    /**
     * Construit une tige rectiligne infinie uniformément chargée à partir d'une position d'un point et d'une orientation
     *
     * @param position    la position d'un point
     * @param orientation L'orientation de la tige
     */
    public TRIUC(SVector3d position, SVector3d orientation) {
        setPosition(position);
        setOrientation(orientation);
    }

    /**
     * Construit une tige rectiligne infinie uniformément chargée à partir de la densitée surfacique, d'une position d'un point et d'une orientation
     *
     * @param position      la position d'un point sur la tige
     * @param orientation       L'orientation de la tige
     * @param chargeDensity la densité linéique de charge
     */
    public TRIUC(SVector3d position, SVector3d orientation, double chargeDensity) {
        this(position, orientation);
        this.chargeDensity = chargeDensity;
    }


    /* ------------------Méthodes "Override"------------------- */

    /**
     * Permet de calculer le champ électrique vectoriel à la position absolue 'point' au temps t
     *
     * Formule fournie par Simon Vézina:
     * http://webprofs.cmaisonneuve.qc.ca/svezina/nyb/note_nyb/NYB_XXI_Chap%201.7.pdf
     *
     * @param point La position absolue où il faut calculer le champ
     * @param t        Le temps auquel il faut calculer le champ
     * @return Un vecteur décrivant le champ électrique à cette position
     */
    @Override
    public SVector3d getElectricField(SVector3d point, double t) {
        if (!isInfinite()) {
            /* Si la tige n'est pas infinie, on vérifie si le point est au dessus ou en dessous de la tige */
            SVector3d relativePoint = point.substract(getPosition());

            SVector3d lineVector = getOrientation().multiply(getWidth());

            double dotProduct = relativePoint.dot(lineVector);
            if (!(0 <= dotProduct && dotProduct <= lineVector.dot(lineVector)) ){
                return new SVector3d();
            }
        }

        SVector3d Rt = orientation.cross(point.substract(getPosition())).cross(orientation);
        return Rt.multiply(2.0 * Physics.k * chargeDensity  / Math.pow(Rt.modulus(), 2.0));
    }

    /**
     * Permet de calculer le champ magnétique vectoriel à la position absolue 'position' dans "delta_t" secondes
     * Le champ magnétique créé par une TRIUC est toujours nul. La méthode retourne donc un vecteur nul.
     *
     * @param position La position absolue où il faut calculer le champ
     * @param t        Le temps auquel il faut calculer le champ
     * @return Un vecteur décrivant le champ magnétique à cette position
     */
    @Override
    public SVector3d getMagneticField(SVector3d position, double t) {
        return new SVector3d();
    }





    @Override
    /**
     * Méthode qui retourne le nom du composant
     */
    public String getName() {
        
        return name;
    }
    /* -------------------Getters & Setters------------------- */

    /**
     * Permet d'obtenir la densité linéique de charge (C/m)
     *
     * @return La densité linéique de charge (C/m)
     */
    public double getChargeDensity() {
        return chargeDensity;
    }

    /**
     * Permet de définir la densité linéique de charge (C/m)
     *
     * @param chargeDensity La densité linéique de charge (C/m)
     */
    public void setChargeDensity(double chargeDensity) {
        this.chargeDensity = chargeDensity;
    }

    /**
     * Permet d'obtenir l'orientation de la tige normalisée
     *
     * @return  L'orientation normalisée
     */
    public SVector3d getOrientation() {
        return orientation;
    }

    /**
     * Permet de définir l'orientation de la tige. Celle-ci est automatiquement normalisée
     *
     * @param orientation L'orientation de la tige
     */
    public void setOrientation(SVector3d orientation) {
        this.orientation = orientation.normalize();
    }
    
    

    /**
     * Permet de savoir si la tige doit être considérée comme étant infinie
     *
     * @return Si la tige est infinie
     */
    public boolean isInfinite() {
        return infinite;
    }

    /**
     * Permet de définir si la tige doit être considérée comme étant infinie
     *
     * @param infinite Si la tige est infinie
     */
    public void setInfinite(boolean infinite) {
        this.infinite = infinite;
    }
    

}
