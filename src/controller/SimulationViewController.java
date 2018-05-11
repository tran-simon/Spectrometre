package controller;

import physics.component.PhysicComponent;
import view.SimulationView;
import view.dessinable.DrawableView;
import view.dessinable.componentview.ComponentView;
import view.dessinable.componentview.WorldView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Permet à l'application de mettre à jour automatiquement la vue de la simulation (SimulationView) en fonction du monde physique (PhysicsWorld)
 *
 * @author Simon Tran
 */
public class SimulationViewController extends ViewController {


    /**
     * Constructeur par défaut
     */
    public SimulationViewController() {

    }

    /**
     * Permet de réorganiser l'ordre des vues du monde
     *
     * @param view      La vue à bouger
     * @param direction La direction dans laquelle il faut bouger la vue (Positif pour vers le premier plan, négatif pour vers l'arrière plan)
     */
    public void reorder(ComponentView view, int direction) {
        int index = getWorldView().getViewList().indexOf(view);
        if (index + direction > 0 && index + direction < getWorldView().getViewList().size()) {
            Collections.swap(getWorldView().getViewList(), index, index + direction);
        }
        getSimulationView().repaint();
    }

    /**
     * Permet de rammener une vue vers le premier ou l'arrière plan
     *
     * @param view      La vue
     * @param direction Vrai pour rammener vers le premier plan, faux pour l'arrière.
     */
    public void bringViewToFront(ComponentView view, boolean direction) {
        ArrayList<DrawableView> viewList = getWorldView().getViewList();
        viewList.remove(view);
        int index = direction ? viewList.size() : 0;
        viewList.add(index, view);
        getSimulationView().repaint();
    }

    /**
     * Permet d'ajouter une sous-vue de composant à la vue.
     * La méthode ajoute aussi le composant associé à la vue au monde physique
     *
     * @param componentView La vue du composant
     */
    public void addToWorld(ComponentView componentView) {
        getPhysicsWorld().add(componentView.getPhysicComponent());
        getWorldView().addChild(componentView);
        updateView();
    }

    /**
     * Permet de reconstruire la vue du monde
     */
    public void rebuildWorld(){
        getSimulationView().setWorldView(new WorldView(getSimulationView(), getPhysicsWorld()));
        updateView();
    }

    /* ------------------Méthodes "Override"------------------- */

    /**
     * Permet de mettre à jour la vue en fonction du monde physique
     */
    @Override
    public void updateView() {
        super.updateView();
        if (getWorldView() == null) {
            getSimulationView().setWorldView(new WorldView(getSimulationView(), getPhysicsWorld()));
        }
        getSimulationView().repaint();
    }

    /**
     * Permet de retirer un composant du monde et de la vue de la simulation
     *
     * @param component Le composant
     */
    @Override
    public void removeFromWorld(PhysicComponent component) {
        getPhysicsWorld().remove(component);
        getSimulationView().getWorldView().removeChildForComponent(component);
    }


    /* -------------------Getters & Setters------------------- */

    /**
     * Permet de définir la vue de la simulation
     *
     * @param simulationView La nouvelle vue de la simulation
     */
    public void setSimulationView(SimulationView simulationView) {
        setView(simulationView);
        rebuildWorld();
    }

    /**
     * Permet d'obtenir la vue de la simulation
     *
     * @return simulationView La vue de la simulation
     */
    public SimulationView getSimulationView() {
        return (SimulationView) getView();
    }

    /**
     * Permet d'obtenir la vue du monde
     *
     * @return La vue du monde
     */
    public WorldView getWorldView() {
        return getSimulationView().getWorldView();

    }

}
