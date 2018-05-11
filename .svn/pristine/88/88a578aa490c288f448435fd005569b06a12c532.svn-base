package view;

import math.SVector3d;
import physics.component.Spectrometre;
import view.dessinable.PlanCartesien;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 * Méthode qui crée le panel du plan cartésien
 * @author Sofianne
 *
 */
public class PlanCartesienPanel extends JPanel {

    /**
     * Création d'un panel pour y mettre un graphique
     */
    private static final long serialVersionUID = 1L;
    private PlanCartesien planCartesien;
    ArrayList<SVector3d> a = new ArrayList<>();

    /**
     * Constructeur
     * @param spectrometre Le spectrometre
     * @param maxY La valeur Y maximale
     */
    public PlanCartesienPanel(Spectrometre spectrometre, double maxY) {
        planCartesien = new PlanCartesien(0, maxY);




    }

    @Override
    /**
     * Création du composant graphique
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldTransform = g2d.getTransform();

        g2d.scale(1, -1);
        g2d.translate(60, 50 - getHeight());

        planCartesien.draw(g2d, new AffineTransform());
        g2d.setTransform(oldTransform);
    }

    /**
     * Retourne le plan cartésien
     *
     * @return le plan cartésien
     */
    public PlanCartesien getPlanCartesien() {
        return planCartesien;
    }

    /**
     * Ajuste le plan cartésien
     *
     * @param planCartesien le plan cartésien
     */
    public void setPlanCartesien(PlanCartesien planCartesien) {
        this.planCartesien = planCartesien;
    }
}
