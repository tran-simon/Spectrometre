package view.dessinable;

import math.SVector3d;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;


/**
 * Classe abstraite qui contient les méthodes que les vues dessinables doivent posséder
 *
 * Une vue qui hérite de cette classe possède une liste de vues 'enfants'.
 *
 * @author Simon Tran
 */
public abstract class DrawableView implements Dessinable {

    /**
     * Liste des sous-vues.
     */
    private ArrayList<DrawableView> viewList = new ArrayList<>();

    /**
     * La vue mère
     */
    private DrawableView parent;

    /**
     * Le panneau qui contient la vue
     */
    private JPanel panel;

    private Color color = Color.BLACK;



    /**
     * Permet de dessiner la vue.
     *
     * @param g2d       Le Graphics2D
     * @param transform La matrice de transformations
     */
    @Override
    public abstract void draw(Graphics2D g2d, AffineTransform transform);

    /**
     * Permet d'obtenir la forme qui sera dessinée avant les transformations.
     * Les unitées sont dans l'échelle de la simulation
     * @return La forme
     */
    public abstract Shape getShape();

    /**
     * Permet de dessiner les vues enfants
     * @param g2d Le graphics2D
     * @param transform La matrice de transformation
     */
    public void drawChildren(Graphics2D g2d, AffineTransform transform){
        for (DrawableView view : getViewList()) {
            if (view != null) {
                view.draw(g2d, transform);
            }
        }
    }
    /**
     * Permet d'obtenir la forme après qu'elle ait reçu des transformations
     *
     * @param transform La matrice de transformations
     * @return La forme transformée
     */
    public Shape getTransformedShape(AffineTransform transform) {
        Shape shape = getShape();
        if (shape == null) {
            return null;
        }
        AffineTransform tempTransform = new AffineTransform(transform);

        return tempTransform.createTransformedShape(shape);
    }

    /**
     * Permet de savoir si la vue contient une sous-vue
     *
     * @param child La sous-vue
     * @return vrai si la vue contient la sous-vue
     */
    public boolean contains(DrawableView child) {
        return getViewList().contains(child);
    }

    /**
     * Permet de savoir si la vue contient le point Point en coordonnées de la simulation
     *
     * @param point     Le point en coordonnées de la simulation
     * @param transform La transformation appliquée à la vue dessinée
     * @return Vrai si la vue contient le point
     */
    public boolean contains(SVector3d point, AffineTransform transform) {
        Point2D.Double point2d = new Point2D.Double();
        transform.transform(point.toPoint(), point2d);
        Shape transformedShape = getTransformedShape(transform);
        if (transformedShape != null) {
            return transformedShape.getBounds().contains(point2d);
        }
        return false;
    }

    /**
     * Permet d'obtenir une liste des sous-vues qui contiennent le point 'point' en coordonnées de la simulation
     *
     * @param point     Le point en coordonnées de la simulation
     * @param transform La transformation appliquée à la vue dessinée
     * @return Une liste de sous-vues qui contiennent le point 'point'
     */
    public ArrayList<DrawableView> childrenThatContain(SVector3d point, AffineTransform transform) {
        ArrayList<DrawableView> views = new ArrayList<>();
        for (DrawableView view : viewList) {
            if (view != null && view.contains(point, transform)) {
                views.add(view);
            }
        }
        return views;
    }


    /**
     * Permet de mettre à jour les vues mères pour qu'elles affichent la version à jour de la vue
     */
    public void update() {
        if (getParent() != null) {
            getParent().update();
        }
        if (getPanel() != null) {
            getPanel().repaint();
        }
    }

    /**
     * Permet d'ajouter une sous-vue
     * La sous-vue aura comme parent 'this'
     *
     * @param child La sous-vue
     */
    public void addChild(DrawableView child) {
        getViewList().add(child);
        child.setParent(this);
    }

    /**
     * Permet de supprimer une sous-vue
     *
     * @param child La sous-vue à supprimer
     */
    public void removeChild(DrawableView child) {
        getViewList().remove(child);
    }



    /* -------------------Getters & Setters------------------- */


    /**
     * Permet d'obtenir la liste des sous-vues
     *
     * @return La liste des sous-vues
     */
    public ArrayList<DrawableView> getViewList() {
        return viewList;
    }

    /**
     * Permet de définir la liste des sous-vues
     *
     * @param viewList La nouvelle liste des sous-vues
     */
    public void setViewList(ArrayList<DrawableView> viewList) {
        this.viewList = viewList;
    }


    /**
     * Retourne la couleur de la vue.
     *
     * @return la couleur
     */
    public Color getColor() {
        return color;
    }

    /**
     * Définit la couleur.
     *
     * @param color la nouvelle couleur
     */
    public void setColor(Color color) {
        this.color = color;
    }


    /**
     * Permet d'obtenir la vue parente
     * @return La vue parente
     */
    public DrawableView getParent() {
        return parent;
    }

    /**
     * Permet de définir la vue parente
     * @param parent La vue parente
     */
    public void setParent(DrawableView parent) {
        this.parent = parent;
    }


    /**
     * Permet d'obtenir le panneau qui contient la vue
     * @return  Le panneau
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Permet de définir le panneau qui contient la vue
     * @param panel Le panneau
     */
    public void setPanel(JPanel panel) {
        this.panel = panel;
    }
}
