package physics.component;

import math.MathUtil;
import math.SVector3d;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 * Interface qui définie les méthodes que les composants doivent implémenter
 *
 * @author Simon Tran
 */
public abstract class PhysicComponent implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Le nom du composant en français tel qu'il sera indiqué sur l'interface
     */
    public static final String name = null;

    private Rectangle2D.Double bounds = new Rectangle2D.Double();
    private boolean isInteractive = true;

    /**
     * Permet de calculer le champ électrique vectoriel à la position absolue 'position' au temps t créé par le composant
     *
     * @param position La position absolue où il faut calculer le champ
     * @param t        Le temps auquel il faut calculer le champ
     * @return Un vecteur décrivant le champ électrique à cette position en Coulomb
     */
    public abstract SVector3d getElectricField(SVector3d position, double t);

    /**
     * Permet de calculer le champ magnétique vectoriel à la position absolue 'position' au temps t créé par le composant
     *
     * @param position La position absolue où il faut calculer le champ
     * @param t        Le temps auquel il faut calculer le champ
     * @return Un vecteur décrivant le champ magnétique à cette position en Tesla
     */
    public abstract SVector3d getMagneticField(SVector3d position, double t);



    /**
     * Permet d'obtenir le nom du composant en français tel qu'il sera indiqué sur l'interface
     *
     * @return Le nom
     */
    public abstract String getName();


    /**
     * Permet de bouger un composant en fonction d'un vecteur déplacement
     * @param deplacement Le vecteur déplacement
     */
    public void moveBy(SVector3d deplacement) {
        setPosition(getPosition().add(deplacement));
    }

    /**
     * Permet d'obtenir si le composant intéragit avec les autres objets
     *
     * @return Si le composant est interactif
     */
    public boolean isInteractive() {
        return isInteractive;
    }

    /**
     * Permet de définir si le composant doit intéragir avec les autres composants du monde physique
     *
     * @param interactive Si le composant est interactif
     */
    public void setInteractive(boolean interactive) {
        this.isInteractive = interactive;
    }

    /**
     * Permet de définir la position (m)
     *
     * @param position La nouvelle position (m)
     */
    public void setPosition(SVector3d position) {
        setBounds(MathUtil.setRectanglePosition(getBounds(), position));
    }

    /**
     * Permet d'obtenir la position (m)
     *
     * @return la position (m)
     */
    public SVector3d getPosition() {
        return MathUtil.getRectanglePosition(getBounds());
    }


    /**
     * Permet d'obtenir la taille
     * @return La taille (m)
     */
    public SVector3d getSize() {
        return MathUtil.getRectangleSize(getBounds());
    }

    /**
     * Permet de définir la taille (m)
     *
     * @param size La nouvelle taille (m)
     */
    public void setSize(SVector3d size) {
        setBounds(MathUtil.setRectangleSize(getBounds(), size));
    }

    /**
     * Permet d'obtenir la largeur (m)
     * @return La largeur
     */
    public double getWidth(){
        return getSize().getX();
    }

    /**
     * Permet d'obtenir la hauteur (m)
     * @return La hauteur (m)
     */
    public double getHeight(){
        return getSize().getY();
    }

    /**
     * Permet de définir la largeur (m)
     * @param width La largeur (m)
     */
    public void setWidth(double width) {
        setSize(new SVector3d(width, getHeight()));
    }

    /**
     * Permet de définir la hauteur (m)
     * @param height la hauteur (m)
     */
    public void setHeight(double height) {
        setSize(new SVector3d(getWidth(), height));
    }

    /**
     * Permet  d'obtenir les bornes (m)
     * @return Les bornes
     */
    public Rectangle2D.Double getBounds() {
        return bounds;
    }

    /**
     * Permet de définir les bornes du composant (m)
     * @param bounds Les bornes (m)
     */
    public void setBounds(Rectangle2D.Double bounds) {
        this.bounds = bounds;
    }
}
