package inputs;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;

/**
 * Classe abstraite représentant une entrée complexe.
 * Par exemple, une classe qui hérite de Input pourrait comprendre une zone de texte lié à un curseur. La classe abstraite Input définit les méthode qui servent à relier les entrées.
 *
 * Les objets 'Input' peuvent sauvegarder une valeur qui peut être obtenue avec getValue().
 *
 * @author Simon Tran
 */
public abstract class Input extends JPanel{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public static final String DEFAULT_NAME = "";


    private final EventListenerList INPUT_LISTENERS = new EventListenerList();


    private JLabel nameLabel;

    private String inputName ;

    private Object value;

    private Object defaultValue;


    /**
     * Permet de créer un objet Input et d'initialiser les valeurs par défaut.
     * @param defaultValue  La valeur par défaut
     */
    public Input(Object defaultValue){
        this.setOpaque(false);
        nameLabel = new JLabel(DEFAULT_NAME);
        setInputName(DEFAULT_NAME);

        this.setLayout(new FlowLayout());
        this.add(nameLabel);

        this.defaultValue = defaultValue;

    }

    /**
     * Permet d'initialiser les entrées et les écouteurs
     */
    public void init(){
        initInput();
        initListeners();
    }

    /**
     * Permet d'initialiser les entrées
     */
    public abstract void initInput();

    /**
     * Permet d'initialiser les écouteurs
     */
    public abstract void initListeners();

    /**
     * Permet d'obtenir si une valeur est valide, si elle est conforme aux exigences de l'entrée
     * @param value La valeur
     * @return Vrai si la valeur est conforme
     */
    public abstract boolean valueIsValid(Object value);

    /**
     * Permet de mettre à jour les sorties des entrées
     */
    public abstract void update();

    /**
     * Permet d'obtenir la valeur sauvegarder par l'entrée
     * Si la valeur est nulle, alors la valeur par défaut sera retournée.
     * @return  La valeur
     */
    public Object getValue(){
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    /**
     * Permet de modifier la valeur et de mettre à jour les sorties des entrées
     * La nouvelle valeur doit être compatible avec la valeur par défaut.
     *
     * @throws ClassCastException Exception lancée si la valeur n'est pas compatible avec la valeur par défaut
     * @param value La nouvelle valeur
     */
    public  void changeValue(Object value){
        if(!value.equals(getValue())){
            if(value.getClass().isInstance(defaultValue)){
                if (valueIsValid(value)) {
                    this.value = value;
                    raiseEventInputChanged(value);
                }
                else{
                    raiseEventInvalidInput(value);
                }
            }
            else {
                throw new ClassCastException();
            }
        }
        update();
    }


    /**
     * Permet d'ajouter un écouteur d'entrée
     * @param listener L'écouteur d'entrée
     */
    public void addInputListener(InputListener listener){
        INPUT_LISTENERS.add(InputListener.class, listener);
    }

    /**
     * Permet de lever un événement comme quoi l'entrée a changé
     * @param input La valeur entrée
     */
    public void raiseEventInputChanged(Object input){
        for(InputListener listener : INPUT_LISTENERS.getListeners(InputListener.class)){
            listener.inputChanged(input);
        }
    }

    /**
     * Permet de lever un événement comme quoi l'entrée est invalide
     * @param input La valeur entrée
     */
    public void raiseEventInvalidInput(Object input){
        for(InputListener listener : INPUT_LISTENERS.getListeners(InputListener.class)){
            listener.invalidInput(input);
        }
        update();
    }

    /**
     * Permet d'obtenir le nom de l'entrée. C'est le nom associé à l'étiquette sur l'interface
     * @return Le nom
     */
    public String getInputName() {
        return inputName;
    }

    /**
     * Permet de définir le nom de l'entrée. C'est le nom associé à l'étiquette sur l'interface
     * @param inputName Le nom de l'entrée
     */
    public void setInputName(String inputName) {
        this.inputName = inputName;
        nameLabel.setText(getInputName());

    }
}
