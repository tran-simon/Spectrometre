package view;

import controller.SimulationViewController;
import math.SVector3d;
import view.dessinable.DrawableView;
import view.dessinable.componentview.ComponentView;
import view.dessinable.componentview.WorldView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 * Permet d'afficher la simulation et de dessiner les différents composants du PhysicsWorld
 *
 * @author Simon Tran
 */
public class SimulationView extends View {
    private static final long serialVersionUID = 1L;


    private WorldView worldView;

    /**
     * Permet de construire une SimulationView
     *
     * @param viewController Le controlleur
     */
    public SimulationView(SimulationViewController viewController) {
        super(viewController);
        this.setToolTipText("Simulation");
    }

    /**
     * Permet de définir le texte de l'étiquette qui apparait lorsque la souris passe sur un composant
     * @param event L'événement de la souris
     * @return  Le texte de l'étiquette
     */
    @Override
    public String getToolTipText(MouseEvent event) {
        SimulationViewController viewController = (SimulationViewController) this.getViewController();
        SVector3d point = viewController.getSimulationView().UIToSimulation(new SVector3d(event.getPoint()));
        ArrayList<DrawableView> views = viewController.getWorldView().childrenThatContain(point, viewController.getView().getTransform());
        String tooltip = null;
        if (views.size() != 0) {
            for (int i = views.size() - 1; i >= 0; i--) {
                DrawableView view = views.get(i);
                if (view instanceof ComponentView) {
                    tooltip = ((ComponentView) view).getPhysicComponent().getName();
                    break;
                }
            }
        }
        return tooltip;
    }
    /* ------------------Méthodes "Override"------------------- */

    /**
     * Permet de dessiner le SimulationView
     *
     * @param g le Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldTransform = g2d.getTransform();

        g2d.scale(1, -1);
        g2d.translate(0.0, -getHeight());

        resize();

        getWorldView().draw(g2d, getTransform());

        g2d.setTransform(oldTransform);
    }



    /* ------------------Getters & Setters------------------- */


    /**
     * Retourne la vue du monde
     *
     * @return la vue du monde
     */
    public WorldView getWorldView() {
        return worldView;
    }

    /**
     * Définit la vue du monde
     *
     * @param worldView le nouvelle vue du monde
     */
    public void setWorldView(WorldView worldView) {
        this.worldView = worldView;
    }


}
