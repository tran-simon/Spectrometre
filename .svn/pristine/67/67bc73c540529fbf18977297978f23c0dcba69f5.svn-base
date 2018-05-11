package physics.component;


import math.SVector3d;
import physics.Physics;

/**
 * Classe qui contient les informations et les formules d'une plaque plane infinie uniformément chargée (PPIUC)
 * Afin de simplifier la simulation, la plaque peut ne pas être infinie. Si c'est le cas, le champ sera zéro si la position à laquelle nous désirons calculer le champ ne se trouve pas au dessus ou en dessous de la plaque.
 *
 * @author Simon Tran
 */
public class PPIUC extends PhysicComponent {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static final double DEFAULT_CHARGE_DENSITY = 1e-9;
    public static final SVector3d DEFAULT_NORMAL = new SVector3d(1.0, 0.0);
    public static final SVector3d DEFAULT_SIZE = new SVector3d(0, 1.0);


    /**
     * Le nom du composant en français tel qu'il sera indiqué sur l'interface
     */
    public static final String name = "Plaque Plane Infinie Uniformément Chargée (PPIUC)";

    /**
     * La normale de la plaque. Elle est toujours normalizée (le module est toujours égal à 1.0 ou 0.0 si le vecteur est nul)
     */
    private SVector3d normal = DEFAULT_NORMAL;
    private double chargeDensity = DEFAULT_CHARGE_DENSITY;

    private boolean infinite = false;

    /**
     * Construit une PPIUC sans information
     */
    public PPIUC() {

        setSize(DEFAULT_SIZE);
    }

    /**
     * Construit une plaque plane infinie uniformément chargée à partir d'une position d'un point et de la normale à la surface
     *
     * @param position la position d'un point
     * @param normal   la normale à la surface
     */
    public PPIUC(SVector3d position, SVector3d normal) {
        setPosition(position);
        setNormal(normal);
    }


    /**
     * Construit une plaque plane infinie uniformément chargée à partir de la densitée surfacique, d'une position d'un point et de la normale à la surface
     *
     * @param position      la position d'un point
     * @param normal        la normale à la surface
     * @param chargeDensity la densitée surfacique
     */
    public PPIUC(SVector3d position, SVector3d normal, double chargeDensity) {
        this(position, normal);
        this.chargeDensity = chargeDensity;
    }


    /* ------------------Méthodes "Override"------------------- */

    /**
     * Permet de calculer le champ électrique vectoriel à la position absolue 'position' au temps t
     *
     * @param point La position absolue où il faut calculer le champ
     * @param t     Le temps auquel il faut calculer le champ
     * @return Un vecteur décrivant le champ électrique à cette position
     */
    @Override
    public SVector3d getElectricField(SVector3d point, double t) {
        if (!isInfinite()) {
            /* Si la plaque n'est pas infinie, on vérifie si le point est au dessus ou en dessous de la plaque */
            SVector3d direction = getNormal().cross(new SVector3d(0, 0, -1)).normalize();
            SVector3d relativePoint = point.substract(getPosition());

            SVector3d planeVector = direction.multiply(getHeight());

            double dotProduct = relativePoint.dot(planeVector);
            if (!(0 <= dotProduct && dotProduct <= planeVector.dot(planeVector)) ){
                return new SVector3d();
            }
        }

        /* Permet de déterminer la position par rapport au plan (au-dessus ou en dessous) */
        double positionComparedToPlan = getNormal().dot(point.substract(getPosition()));
        int s = positionComparedToPlan > 0 ? 1 : -1;

        return getNormal().multiply(s * getChargeDensity() / (2 * Physics.ELECTRIC_CONSTANT));
//        return electricFieldPPIUC(position, getPosition(), normal, chargeDensity);
    }

    /**
     * Permet de calculer le champ magnétique vectoriel à la position absolue 'position' dans "delta_t" secondes
     * Le champ magnétique créé par une PPIUC est toujours nul. La méthode retourne donc un vecteur nul.
     *
     * @param position La position absolue où il faut calculer le champ
     * @param t        Le temps auquel il faut calculer le champ
     * @return Un vecteur décrivant le champ magnétique à cette position
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
     * Permet d'obtenir la densité surfacique de charge (C/m^2)
     *
     * @return La densité surfacique de charge (C/m^2)
     */
    public double getChargeDensity() {
        return chargeDensity;
    }

    /**
     * Permet de définir la densité surfacique de charge (C/m^2)
     *
     * @param chargeDensity La densité surfacique de charge (C/m^2)
     */
    public void setChargeDensity(double chargeDensity) {
        this.chargeDensity = chargeDensity;
    }

    /**
     * Permet d'obtenir la normale normalisée.
     *
     * @return La normale normalisée
     */
    public SVector3d getNormal() {
        return normal;
    }

    /**
     * Permet de définir la normale. Celle-ci est automatiquement normalisée
     *
     * @param normal La normale
     */
    public void setNormal(SVector3d normal) {
        this.normal = normal.normalize();
    }


    /**
     * Permet de savoir si la plaque doit être considérée comme étant infinie
     *
     * @return Si la plaque est infinie
     */
    public boolean isInfinite() {
        return infinite;
    }

    /**
     * Permet de définir si la plaque doit être considérée comme étant infinie
     *
     * @param infinite Si la plaque est infinie
     */
    public void setInfinite(boolean infinite) {
        this.infinite = infinite;
    }
}
