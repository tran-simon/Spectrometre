package inputs;


/**
 * Classe abstraite qui permet de définir un InputListener sans avoir à implémenter toutes les méthodes
 * @author Simon Tran
 */
public abstract class InputAdapter implements InputListener{

    /**
     * Événement levé lorsque l'entrée est changée
     * @param input l'entrée
     */
    public void inputChanged(Object input){}

    /**
     * Événement levé lorsque l'entrée n'est pas valide
     * @param input L'entrée
     */
    public void invalidInput(Object input){}
}
