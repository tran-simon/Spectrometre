package inputs;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Entrée représentant une valeur booléenne
 *
 * @author Simon Tran
 */
public class BooleanInput extends Input {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JCheckBox checkBox;

    /**
     * Permet de créer l'entrée
     * @param value La valeur initiale
     */
    public BooleanInput(boolean value) {
        super(value);
        checkBox = new JCheckBox("", value);
        init();
    }

    /**
     * Permet de créer l'entrée
     * @param value La valeur initiale
     * @param inputName Le nom de l'entrée
     */
    public BooleanInput(boolean value, String inputName) {
        this(value);
        setInputName(inputName);
    }

    /* ------------------Méthodes "Override"------------------- */

    /**
     * Permet d'initialiser les entrées
     */
    @Override
    public void initInput() {
        this.add(checkBox);
    }

    /**
     * Permet d'initialiser les écouteurs
     */
    @Override
    public void initListeners() {
        checkBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean value = false;
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    value = true;
                }
                changeValue(value);

            }
        });
    }


    /**
     * Permet de mettre à jour le curseur et la zone de texte en fonction de la valeur sauvegardée.
     */
    @Override
    public void update() {
        checkBox.setSelected(getBoolean());
    }


    /**
     * Permet d'obtenir si une valeur est valide, si elle est conforme aux exigences de l'entrée
     * @param value La valeur
     * @return Vrai si la valeur est conforme
     */
    @Override
    public boolean valueIsValid(Object value) {
        return (value instanceof Boolean);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        checkBox.setEnabled(enabled);
    }

    /**
     * Permet d'obtenir la valeur booléenne
     * @return La valeur
     */
    public boolean getBoolean() {
        return (boolean) getValue();
    }
}
