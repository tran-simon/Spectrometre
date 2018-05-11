package view;

import aaplication.AppUtil;
import controller.ViewController;
import math.SVector3d;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Classe qui définit les méthodes et les variables que les vues incluent
 *
 * @author Simon Tran
 */
public class View extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ModeleAffichage modeleAffichage;
    private SVector3d scale = new SVector3d(AppUtil.DEFAULT_WORLD_SCALE, AppUtil.DEFAULT_WORLD_SCALE);

    /**
     * La translation en mètres (unitées du monde physique)
     */
    private SVector3d translation = new SVector3d();

    private ViewController viewController;

    /**
     * Constructeur par défaut d'une vue
     *
     * @param viewController Le controlleur de la vue
     */
    public View(ViewController viewController) {
        setViewController(viewController);
        this.setLayout(new BorderLayout());
    }


    /**
     * Permet de convertir une coordonnée de l'interface graphique en une coordonnée de la simulation
     * Dans l'interface graphique, l'origine se trouve en haut à gauche tandis que dans la simulation, l'origine se trouve en bas à gauche.
     * La méthode effectue une translation du point, puis redimensionne ses coordonnées.
     * @param point Le point dans l'interface
     * @return Le point dans la simulation
     */
    public SVector3d UIToSimulation(SVector3d point) {
        return this.scaleToSimulation(new SVector3d(point.getX(), this.getHeight() - point.getY())).add(translation);
    }

    /**
     * Permet de redimensionner un point afin d'obtenir ses coordonnées dans l'interface graphique
     *
     * @param point Le point dans la simulation
     * @return  Le point dans l'interface
     */
    public SVector3d scaleToUI(SVector3d point) {
        return point.scale(this.getScale());
    }

    /**
     * Permet de redimensionner un point afin d'obtenir ses coordonnées dans la simulation
     * @param point Le point dans l'interface
     * @return  Le point dans la simulation
     */
    public SVector3d scaleToSimulation(SVector3d point){
        return point.scale(this.getScale().inverse());
    }


    /**
     * Permet de mettre è jour le ModeleAffichage avec la nouvelle taille 'size'
     *
     * @param size la nouvelle taille
     */
    public void resize(SVector3d size) {
        this.setModeleAffichage(new ModeleAffichage(size.getX(), size.getY(), translation.getX(), translation.getY(), size.getX() / getScale().getX(), size.getY() / getScale().getY()));
    }

    /**
     * Permet de mettre à jour le ModeleAffichage avec la nouvelle taille de la vue
     */
    public void resize() {
        resize(new SVector3d(getWidth(), getHeight()));
    }



    /* -------------------Getters & Setters------------------- */


    /**
     * Permet d'obtenir le ModeleAffichage
     *
     * @return le ModeleAffichage
     */
    public ModeleAffichage getModeleAffichage() {
        return modeleAffichage;
    }

    /**
     * Permet de définir le ModeleAffichage
     *
     * @param modeleAffichage Le nouveau ModeleAffichage
     */
    public void setModeleAffichage(ModeleAffichage modeleAffichage) {
        this.modeleAffichage = modeleAffichage;
    }

    /**
     * Permet d'obtenir la matrice de transformations provenant du ModeleAffichage
     *
     * @return La matrice de transformations
     */
    public AffineTransform getTransform() {
        return getModeleAffichage().getMatMC();
    }

    /**
     * Permet d'obtenir l'échelle de la vue
     *
     * @return Vecteur représentant l'échelle en x et en y
     */
    public SVector3d getScale() {
        return scale;
    }

    /**
     * Permet de définir l'échelle de la vue
     *
     * @param scale L'échelle
     */
    public void setScale(SVector3d scale) {
        this.scale = scale;
    }

//    public void updateColors(){
//        resize();
//        repaint();
//    }


    /**
     * Permet d'obtenir la translation
     *
     * @return La translation
     */
    public SVector3d getTranslation() {
        return translation;
    }

    /**
     * Définit la translation.
     *
     * @param translation la nouvelle translation
     */
    public void setTranslation(SVector3d translation) {
        this.translation = translation;
    }

    /**
     * Retourne le controlleur de la vue.
     *
     * @return le controlleur
     */
    public ViewController getViewController() {
        return viewController;
    }

    /**
     * Définit le controlleur de la vue.
     *
     * @param viewController le nouveau controlleur
     */
    public void setViewController(ViewController viewController) {
        this.viewController = viewController;
    }
}
