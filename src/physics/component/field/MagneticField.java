package physics.component.field;

import math.SVector3d;

import java.awt.geom.Rectangle2D;

/**
 * Composant représentant un champ magnétique uniforme en deux dimensions de forme rectangulaire
 * Le module du champ est constant à l'intérieur du champ, mais il est égal à zéro à l'extérieur.
 *
 * @author Simon Tran
 */
public class MagneticField extends Field {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static final double DEFAULT_INTENSITY = Field.DEFAULT_INTENSITY * 1e-5;
    public static final SVector3d DEFAULT_FIELD_DIRECTION = new SVector3d(0.0, 0.0, 1.0);
    /**
     * Le nom du composant en français tel qu'il sera indiqué sur l'interface
     */
    public static final String name = "Champ Magnétique Uniforme";

    /**
     * Construit un champ magnétique avec aucune information
     */
    public MagneticField() {
        super();
        this.setFieldDirection(DEFAULT_FIELD_DIRECTION);
        setIntensity(DEFAULT_INTENSITY);
    }
    /* ------------------Méthodes "Override"------------------- */


    /**
     * Permet de construire un champ magnétique
     *
     * @param bounds         Les bornes du champ
     * @param fieldDirection La direction du champ
     * @param intensity      L'intensité du champ
     */
    public MagneticField(Rectangle2D.Double bounds, SVector3d fieldDirection, double intensity) {
        super(bounds, fieldDirection);
        this.setIntensity(intensity);
    }

    /**
     * Permet de construire un champ avec des dimensions et une direction de champ. La position du champ est mise à zéro par défaut.
     *
     * @param size           Les dimensions du champ
     * @param fieldDirection la direction du champ
     */
    public MagneticField(SVector3d size, SVector3d fieldDirection) {
        super(new Rectangle2D.Double(0.0, 0.0, size.getX(), size.getY()), fieldDirection);
    }

    /**
     * Permet de construire un champ avec la taille, la direction et l'intensité du champ
     *
     * @param size           La taille (m)
     * @param fieldDirection La direction du champ
     * @param intensity      L'intensité
     **/
    public MagneticField(SVector3d size, SVector3d fieldDirection, double intensity) {
        super(size, fieldDirection, intensity);
    }

    /* ------------------Méthodes "Override"------------------- */

    /**
     * Permet de calculer le champ électrique vectoriel à la position absolue 'position'
     * Le champ est toujours nul, puisque c'est un champ strictement magnétique
     *
     * @param position La position absolue où il faut calculer le champ
     * @param t        le temps de la simulation. Pour simplifier les calculs, cette valeur est ignorée puisque les particules qui bougent ne peuvent intéragir entre-elles.
     * @return un vecteur nul.
     */
    @Override
    public SVector3d getElectricField(SVector3d position, double t) {
        return new SVector3d();
    }

    /**
     * Permet de calculer le champ magnétique vectoriel à la position absolue 'position'
     *
     * @param position La position absolue où il faut calculer le champ
     * @param t        le temps de la simulation. Cette valeur n'influence pas le champ magnétique, elle est donc ignorée
     * @return Un vecteur décrivant le champ magnétique à cette position
     */
    @Override
    public SVector3d getMagneticField(SVector3d position, double t) {
        if (!getBounds().contains(position.getX(), position.getY())) {
            return new SVector3d();
        }
        return getFieldDirection().multiply(getIntensity());
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
