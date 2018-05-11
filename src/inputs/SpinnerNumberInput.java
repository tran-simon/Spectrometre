package inputs;


import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe qui permet de créer un JPanel contenant des entrées permettant de modifier une valeur numérique
 *
 * L'entrée comporte une zone de texte et 2 boutons, similairement à un tourniquet
 *
 * @author Simon Tran
 */
public class SpinnerNumberInput extends NumberInput {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnUp;
    private JButton btnDown;


    /**
     * Permet de construire le tourniquet
     * @param value La valeur initiale
     * @param inputName Le nom de l'entrée
     * @param min La valeur minimale
     * @param max La valeur maximale
     */
    public SpinnerNumberInput(double value, String inputName, double min, double max) {
        super(value, inputName, min, max);
        setWithSlider(false);
    }

    /**
     * Construit le tourniquet
     * @param value La valeur initiale
     */
    public SpinnerNumberInput(double value) {
        this(value, "", 0.0, 0.0);
    }

    /**
     * Construit le tourniquet
     * @param value La valeur initiale
     * @param inputName Le nom de l'entrée
     */
    public SpinnerNumberInput(double value, String inputName) {
        this(value);
        setInputName(inputName);
    }

    @Override
    public void initInput() {
        super.initInput();
        btnUp = new BasicArrowButton(SwingConstants.NORTH);
        btnDown = new BasicArrowButton(SwingConstants.SOUTH);
        JPanel panelButtons = new JPanel();
        panelButtons.add(btnUp);
        panelButtons.add(btnDown);
        this.add(panelButtons);
    }

    @Override
    public void initListeners() {
        super.initListeners();

        btnUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double step = (getNumber() == 0.0) ? 1 : getNumber() * 0.1;
                changeValue((double) getValue() + step);
            }
        });
        btnDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double step = (getNumber() == 0.0) ? 1 : getNumber() * 0.1;
                changeValue((double) getValue() - step);
            }
        });

    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        btnUp.setEnabled(enabled);
        btnDown.setEnabled(enabled);
    }



}

