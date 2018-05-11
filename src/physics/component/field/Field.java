package physics.component.field;

import aaplication.AppUtil;
import math.MathUtil;
import math.SVector3d;
import physics.component.PhysicComponent;

import java.awt.geom.Rectangle2D;

/**
 * Classe abstraite représentant un champ quelconque
 *
 * @author Simon Tran
 */
public abstract class Field extends PhysicComponent {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static final double DEFAULT_MAX_INTENSITY = 1e1;
    public static final double DEFAULT_MIN_INTENSITY = 0.0;
    public static final double DEFAULT_INTENSITY = 1.0;
    public static final SVector3d DEFAULT_FIELD_DIRECTION = new SVector3d(1.0, 0.0, 0.0);
    public static final SVector3d DEFAULT_SIZE  = new SVector3d(AppUtil.COMPONENT_DEFAULT_WIDTH, AppUtil.COMPONENT_DEFAULT_HEIGHT);

    public enum FieldType {ELECTRIC, MAGNETIC, BOTH}



    /**
     * La direction du champ
     * Celle-ci est toujours normalisée
     */
    private SVector3d fieldDirection = DEFAULT_FIELD_DIRECTION;
    private double intensity = DEFAULT_INTENSITY;

    /**
     * Construit un champ avec aucune information
     */
    public Field() {
        setSize(DEFAULT_SIZE);
    }

    /**
     * Permet de contruire un champ avec les bornes et la direction du champ
     *
     * @param bounds         les bornes du champ
     * @param fieldDirection La direction du champ
     */
    public Field(Rectangle2D.Double bounds, SVector3d fieldDirection) {
        this.setBounds(bounds);
        this.setFieldDirection(fieldDirection);
    }

    /**
     * Permet de construire un champ avec la taille, la direction et l'intensité du champ
     * @param size La taille (m)
     * @param fieldDirection La direction du champ
     * @param intensity L'intensité
     */
    public Field(SVector3d size, SVector3d fieldDirection, double intensity) {
        this(MathUtil.createRectangle(new SVector3d(), size), fieldDirection);
        setIntensity(intensity);
    }



    /**
     * Permet de savoir le type d'un champ
     *
     * @param field Le champ
     * @return Le type (Magnétique ou électrique)
     */
    public static FieldType getType(Field field) {
        if (field instanceof MagneticField) {
            return FieldType.MAGNETIC;
        }
        else if (field instanceof ElectricField) {
            return FieldType.ELECTRIC;
        }

        return null;
    }


    /**
     * Permet de savoir le type d'un champ
     *
     * @return Le type (Magnétique ou électrique)
     */
    public FieldType getType() {
        return Field.getType(this);
    }


    /* ------------------Méthodes "Override"------------------- */




    /* -------------------Getters & Setters------------------- */




    /**
     * Permet d'obtenir la direction du champ normalisée
     *
     * @return La direction du champ
     */
    public SVector3d getFieldDirection() {
        return fieldDirection;
    }

    /**
     * Permet de définir la direction du champ. Cette direction est automatiquement normalisée
     *
     * @param fieldDirection La nouvelle direction
     */
    public void setFieldDirection(SVector3d fieldDirection) {
        this.fieldDirection = fieldDirection.normalize();
    }


    /**
     * Permet d'obtenir l'intensité du champs
     *
     * @return L'intensité
     */
    public double getIntensity() {
        return intensity;
    }

    /**
     * Permet de définir l'intensité du champ
     *
     * @param intensity La nouvelle intensité
     */
    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    /**
     * Permet d'obtenir la hauteur du champ en m
     *
     * @return La hauteur du champ en m
     */
    public double getHeight() {
        return getSize().getY();
    }

    /**
     * Permet de définir la hauteur du champ en m
     *
     * @param height La hauteur du champ en m
     */
    public void setHeight(double height) {
        setSize(new SVector3d(getWidth(), height));
    }

    /**
     * Permet d'obtenir la largeur du champ en m
     *
     * @return La largeur du champ en m
     */
    public double getWidth() {
        return getSize().getX();
    }

    /**
     * Permet de définir la largeur du champ en m
     *
     * @param width La largeur du champ en m
     */
    public void setWidth(double width) {
        setSize(new SVector3d(width, getHeight()));
    }
}
