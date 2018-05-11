package physics.component.field;

import math.SVector3d;

import java.awt.geom.Rectangle2D;

//import physics.Physics;

/**
 * Composant représentant un champ électrique uniforme en deux dimensions de forme rectangulaire
 * Le module du champ est constant à l'intérieur du champ, mais il est égal à zéro à l'extérieur.
 *
 * @author Simon Tran
 */
public class ElectricField extends Field {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static final SVector3d DEFAULT_FIELD_DIRECTION = new SVector3d(1.0, 0.0, 0.0);

    /**
     * Le nom du composant en français tel qu'il sera indiqué sur l'interface
     */
    public static final String name = "Champ Électrique Uniforme";


    /**
     * Construit un champ électrique avec aucune information
     */
    public ElectricField() {
        super();
        this.setFieldDirection(DEFAULT_FIELD_DIRECTION);
    }

    /**
     * Constructeur d'un champ électrique
     *
     * @param bounds         Les bornes du champ
     * @param fieldDirection La direction du champ
     * @param intensity      L'intensité du champ
     */
    public ElectricField(Rectangle2D.Double bounds, SVector3d fieldDirection, double intensity) {
        super(bounds, fieldDirection);
        this.setIntensity(intensity);
    }

    /**
     * Permet de construire un champ avec des dimensions et une direction de champ. La position du champ est mise à zéro par défaut.
     *
     * @param size           Les dimensions du champ
     * @param fieldDirection la direction du champ
     */
    public ElectricField(SVector3d size, SVector3d fieldDirection) {
        super(new Rectangle2D.Double(0.0, 0.0, size.getX(), size.getY()), fieldDirection);
    }


    /**
     * Permet de construire un champ avec la taille, la direction et l'intensité du champ
     *
     * @param size           La taille (m)
     * @param fieldDirection La direction du champ
     * @param intensity      L'intensité
     **/
    public ElectricField(SVector3d size, SVector3d fieldDirection, double intensity) {
        super(size, fieldDirection, intensity);
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
        if (!getBounds().contains(position.getX(), position.getY())) {
            /* Le champ est nul si le point est à l'extérieur du champ */
            return new SVector3d();
        }

        return getFieldDirection().multiply(getIntensity());
    }

    /**
     * Permet de calculer le champ magnétique vectoriel à la position absolue 'position' dans "delta_t" secondes
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


}
