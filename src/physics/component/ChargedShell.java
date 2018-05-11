package physics.component;

import aaplication.AppUtil;
import math.MathUtil;
import math.SVector3d;
import physics.Physics;

/**
 * Composant représentant une coquille sphérique uniformément chargée
 * Celle-ci crée un champ électrique équivalent au champ généré par une charge ponctuelle unique en son centre
 * Le champ à l'intérieur de la coquille est nul
 *
 * @author Simon Tran
 */
public class ChargedShell extends PhysicComponent {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final double DEFAULT_RADIUS = AppUtil.COMPONENT_DEFAULT_WIDTH / 4;
    public static final double DEFAULT_CHARGE_DENSITY = PPIUC.DEFAULT_CHARGE_DENSITY * 10;

    /**
     * La densité surfacique de charge (C/m^2)
     */
    private double chargeDensity = DEFAULT_CHARGE_DENSITY;


    /**
     * Le nom du composant en français tel qu'il sera indiqué sur l'interface
     */
    public static final String name = "Coquille chargée";


    /**
     * Permet de créer une coquille sphérique
     */
    public ChargedShell() {
        setRadius(DEFAULT_RADIUS);
    }

    /**
     * Permet de calculer le module du champ électrique à une distance 'r' de la coquille.
     *
     * @param r la distance
     * @return La valeur du champ électrique en Coulombs
     */
    public double getElectricField(double r) {
        return Physics.electricField(getTotalCharge(), r);
    }


    /**
     * Permet d'obtenir le champ électrique créé par la coquille
     *
     * @param position La position absolue où il faut calculer le champ
     * @param t        Le temps auquel il faut calculer le champ
     * @return Le champ électrique vectoriel
     */
    @Override
    public SVector3d getElectricField(SVector3d position, double t) {
        double distance = SVector3d.distance(position, getPosition());
        if (distance <= getRadius()) {
            return new SVector3d();
        }
        SVector3d orientation = position.substract(this.getPosition()).normalize();
        return orientation.multiply(getElectricField(distance));
    }

    /**
     * Permet d'obtenir le champ magnétique
     * Celui-ci est nul pour une coquille chargée
     *
     * @param position La position absolue où il faut calculer le champ
     * @param t        Le temps auquel il faut calculer le champ
     * @return Le champ vectoriel
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

    /**
     * Permet d'obtenir la charge totale sur la coquille (C)
     *
     * @return La charge (C)
     */
    public double getTotalCharge() {
        return getChargeDensity() * getArea();
    }

    /**
     * Permet d'obtenir l'aire de la coquille
     *
     * @return L 'aire de la coquille en m^2
     */
    public double getArea() {
        return MathUtil.sphereArea(getRadius());
    }

    /**
     * Retourne la densité de charge (c/m^2)
     *
     * @return la densité de charge
     */
    public double getChargeDensity() {
        return chargeDensity;
    }

    /**
     * Définit la densité de charge (c/m^2)
     *
     * @param chargeDensity la nouvelle densité de charge
     */
    public void setChargeDensity(double chargeDensity) {
        this.chargeDensity = chargeDensity;
    }

    /**
     * Retourne le rayon (m).
     *
     * @return le rayon
     */
    public double getRadius() {
        return getWidth() / 2.0;
    }

    /**
     * Définit le rayon (m).
     *
     * @param radius le nouveau rayon
     */
    public void setRadius(double radius) {

        setSize(new SVector3d(radius * 2, radius * 2));
    }
}
