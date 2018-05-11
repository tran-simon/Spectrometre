package view.dessinable.componentview;


import inputs.InputAdapter;
import inputs.SpinnerNumberInput;
import physics.component.PhysicComponent;
import physics.component.SelecteurDeVitesse;

import javax.swing.*;
import java.awt.*;

/**
 * Classe qui permet de créer la vue d'un sélecteur de vitesse
 * @author Sofianne
 *
 */
public class SelecteurDeVitesseView extends ImageView {

    /**
     * Permet de créer un nouveau SelecteurDeVitesseView avec un composant physique et une forme non-redimensionnée
     * Les informations de la forme doivent être en unités du monde physique
     *
     * @param selecteurDeVitesse Le composant physique
     */
    public SelecteurDeVitesseView(PhysicComponent selecteurDeVitesse) {
        super(selecteurDeVitesse, "selecteurDeVitesse.png");

        setColor(Color.pink);
    }


    /**
     * Permet d'obtenir le sélecteur de vitesse
     *
     * @return le sélecteur de vitesse
     */
    public SelecteurDeVitesse getSelecteurDeVitesse() {
        return (SelecteurDeVitesse) getPhysicComponent();
    }


    /**
     * Permet d'obtenir le JPanel contenant les entrées pour les options du composant physique
     *
     * @return Le JPanel
     */
    @Override
    public JPanel getOptionsPanel() {
        JPanel optionsPanel = super.getOptionsPanel();

        SelecteurDeVitesse selecteurDeVitesse = getSelecteurDeVitesse();
        SpinnerNumberInput inputSelectionSpeed = new SpinnerNumberInput(selecteurDeVitesse.getSelectionSpeed());
        inputSelectionSpeed.setInputName("Vitesse de sélection (m/s)");
        inputSelectionSpeed.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                selecteurDeVitesse.setSelectionSpeed(inputSelectionSpeed.getNumber());
            }
        });

        optionsPanel.add(inputSelectionSpeed);
        optionsPanel.add(new JSeparator());
        return optionsPanel;
    }

}
