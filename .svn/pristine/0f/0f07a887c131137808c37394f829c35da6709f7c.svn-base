package view.dessinable;

import aaplication.AppUtil;
import math.MathUtil;
import math.SVector3d;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.ArrayList;


/**
 * Classe qui permet de créer un plan cartésien
 * @author Sofianne
 *
 */
public class PlanCartesien extends DrawableView {
	




    private Path2D.Double axes, ligneBrisee, grille;

    private double tickSize = 10;/* Taille d'un tick */
    private double tickNumber = 15;/* Nombre de Tick par axe */

    private ShapeView ligneView = new ShapeView(new Path2D.Double()); 

    private SVector3d size = new SVector3d(500, 500);

    private ArrayList<SVector3d> speedValues = new ArrayList<>();
    

    private SVector3d max;

    private double xValue = 0;
    

    /**
     * Constructeur du plan cartésien
     * @param maxSize La taille maximale du plan
     */
    public PlanCartesien(SVector3d maxSize) {
        max = maxSize;
        addChild(ligneView);

    }

    /**
     * Constructeur du plan cartésien
     * @param maxSizeX La taille maximale en x du plan
     * @param maxSizeY La taille maximale en y du plan
     */
    public PlanCartesien(double maxSizeX, double maxSizeY) {
        this(new SVector3d(maxSizeX, maxSizeY));
    }

    
    @Override
    /**
     * Retourne null car il n'y a pas de shape dans un plan cartésien
     */
    public Shape getShape() {
        return null;
    }

    @Override
    /**
     * Méthode qui dessine la fonction, le grillage et les axes
     */
    public void draw(Graphics2D g2d, AffineTransform transform) {

        getViewList().clear();

        creerAxes();
        creerGrille();
        creerApproxCourbe();

        drawChildren(g2d, transform);

        g2d.setColor(Color.RED);
        g2d.draw(ligneBrisee);

    }


    /**
     * Permet de créer les axes
     */
    private void creerAxes() {
        /* Calcul du path */
        axes = new Path2D.Double();
        axes.moveTo(0, 0);
        axes.lineTo(size.getX(), 0);
        axes.moveTo(0, 0);
        axes.lineTo(0, size.getY());

        addChild(new ShapeView(axes));


        LabelView etiquetteAxeX = new LabelView("Temps (min)", new SVector3d(200, 50 - 10));
        LabelView etiquetteAxeY = new LabelView("f(x)", new SVector3d(-20, -40));


        addChild(etiquetteAxeX);
        addChild(etiquetteAxeY);



    }

    /**
     * Permet de créer la grille
     */
    private void creerGrille() {
        double axeLenght = size.getX();
        grille = new Path2D.Double();

        double tickSpace = axeLenght / tickNumber;

        /* ticks horizontaux */
        for (int i = 1; i < tickNumber; i++) {
            double startX = i * tickSpace;
            double startY = 0;


            if ((i & 1) == 0) {
                double value = MathUtil.scaleValue(startX, 0, max.getX()) * size.getX();
                LabelView label = new LabelView(AppUtil.numberToString(value), new SVector3d(startX,
                        startY + 20));
                addChild(label);


            }else {
            	
            }

            grille.moveTo(startX, startY);
            grille.lineTo(startX, axeLenght);

            /* ticks verticaux */
            startX = 0;
            startY = i * tickSpace;

            if ((i & 1) == 0) {
                double value = MathUtil.scaleValue(startY, 0, max.getY()) * size.getY();
                LabelView label = new LabelView(AppUtil.numberToString(value), new SVector3d(startX - 45,
                        -startY));
                addChild(label);

            }else {
            }

            grille.moveTo(startX, startY);
            grille.lineTo(axeLenght, startY);
        }


        addChild(new ShapeView(grille));
    }


    /**
     * Méthode qui retourne la valeur en x
     * @return La valeur en x
     */
    public double getxValue() {
        return xValue;
    }

    /**
     * Méthode qui ajuste la valeur en x
     * @param xValue La valeur en x
     */
    public void setxValue(double xValue) {
        this.xValue = xValue;
    }

    /**
     * Permet de créer la courbe
     */
    public void creerApproxCourbe() {


        double y;
        ligneBrisee = new Path2D.Double();

        double k = 0;
        boolean move = true;


        for (SVector3d value : speedValues) {
            k = MathUtil.scaleValueInRange(value.getX(), 0, max.getX())* size.getX();
            y = MathUtil.scaleValueInRange(value.getY(), 0, max.getY()) * size.getY() ;

            if (move) {
                ligneBrisee.moveTo(k, y);
                move = false;
            }
            else {
                ligneBrisee.lineTo(k, y);
            }
        }
        ligneView.setShape(ligneBrisee);
    }




    /* Getters & Setters */

    /**
     * Retourne le tick size.
     *
     * @return le tick size
     */
    public double getTickSize() {
        return tickSize;
    }

    /**
     * Fixe la taille des ticks.
     *
     * @param tickSize la nouvelle taille des ticks
     */
    public void setTickSize(double tickSize) {
        this.tickSize = tickSize;
    }


    /**
     * Méthode qui retourne les valeurs de vitesse
     * @return Les valeurs de vitesse
     */
    public ArrayList<SVector3d> getSpeedValues() {
        return speedValues;
    }

    /**
     * Méthode qui ajuste les valeurs de vitesse
     * @param speedValues Les valeurs de vitesse
     */
    public void setSpeedValues(ArrayList<SVector3d> speedValues) {
        this.speedValues = speedValues;
    }


    /**
     * Méthode qui retourne le maximum
     * @return Le maximum
     */
    public SVector3d getMax() {
        return max;
    }

    /**
     * Méthode qui ajuste le maximum
     * @param max Le maximum
     */
    public void setMax(SVector3d max) {
        this.max = max;
    }
}