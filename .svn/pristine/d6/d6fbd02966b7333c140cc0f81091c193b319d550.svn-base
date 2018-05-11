package inputs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe qui permet de céer un JPanel contenant une zone de texte et une étiquette d'identification
 *
 * @author Simon Tran
 */
public class TextInput extends Input {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
    private String regex;

    /**
     * Permet de créer un TextInput
     *
     * @param value La valeur initiale
     */
    public TextInput(String value) {
        super(value);
        textField = new JTextField(value);
        textField.setColumns(7);
        regex = ".*";

        init();
    }

    /**
     * Permet de créer un TextInput
     *
     * @param value     La valeur initiale
     * @param inputName Le nom de l'entrée
     */
    public TextInput(String value, String inputName) {
        this(value);
        setInputName(inputName);
    }

    /* ------------------Méthodes "Override"------------------- */

    /**
     * Permet d'initialiser les entrées
     */
    @Override
    public void initInput() {
        this.add(textField);
    }


    /**
     * Permet d'initialiser les écouteurs
     */
    @Override
    public void initListeners() {
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (valueIsValid(textField.getText())) {
                    changeValue(textField.getText());
                }
                else {
                    raiseEventInvalidInput(textField.getText());
                    textField.setText(getText());
                }
                repaint();
            }
        });
    }

    /**
     * Permet de mettre à jour le curseur et la zone de texte en fonction de la valeur sauvegardée.
     */
    @Override
    public void update() {
        textField.setText(getText());
    }

    /**
     * Permet de savoir si une valeur est valide
     * Une valeur est valide si elle est une String et si elle respecte une expression régulière
     *
     * @param value La valeur
     * @return Vrai si la valeur est valide
     */
    @Override
    public boolean valueIsValid(Object value) {
        return (value instanceof String) && ((String) value).matches(regex);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        textField.setEnabled(enabled);
    }


    /* -------------------Getters & Setters------------------- */

    /**
     * Permet d'obtenir la valeur en texte
     *
     * @return le texte
     */
    public String getText() {
        return getValue().toString();
    }


    /**
     * Permet d'obtenir l'expression régulière
     *
     * @return L'expression régulière
     */
    public String getRegex() {
        return regex;
    }

    /**
     * Permet de définir l'expression régulière
     *
     * @param regex L'expression régulière
     */
    public void setRegex(String regex) {
        this.regex = regex;
    }

}
