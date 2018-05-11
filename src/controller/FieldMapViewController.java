package controller;

import physics.PhysicsWorld;
import physics.component.field.Field;
import view.FieldMapView;

/**
 * Permet de faire le lien entre un physique world et un FieldMapView.
 * Cela permet aux classes d'interfaces de ne pas avoir à gérer l'affichage du monde ainsi que la simulation du monde
 *
 * @author Simon Tran
 */
public class FieldMapViewController extends ViewController {


    /**
     * Permet de créer un FieldMapViewController
     * @param physicsWorld  Le monde physique
     */
    public FieldMapViewController(PhysicsWorld physicsWorld) {
        super(physicsWorld);
    }


    /**
     * Permet de définir le type du champ analysé
     * @param fieldType Le type du champ
     */
    public void setFieldType(Field.FieldType fieldType) {
        getFieldMapView().setFieldType(fieldType);
    }

    /**
     * Permet de mettre à jour les couleurs
     */
    public void updateColors(){
        getFieldMapView().updateColors();
    }

    /* ------------------Méthodes "Override"------------------- */

    /**
     * Permet de mettre à jour la vue en fonction des nouvelles valeurs du monde physique
     */
    @Override
    public void updateView() {
        getFieldMapView().calculateFieldValues(getPhysicsWorld());
        getFieldMapView().repaint();
    }


    /* -------------------Getters & Setters------------------- */

    /**
     * Permet d'obtenir la vue des champs
     * @return  La vue des champs
     */
    public FieldMapView getFieldMapView(){
        return (FieldMapView) getView();
    }
}
