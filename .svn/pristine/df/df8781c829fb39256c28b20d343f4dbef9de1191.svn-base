package inputs;

import math.MathUtil;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import static inputs.NumberInput.DEFAULT_MAX;
import static inputs.NumberInput.DEFAULT_MIN;

/**
 * Permet de créer une entrée avec un curseur permettant de choisir un nombre 'double'
 * La classe de la valeur choisit par cet entrée est le 'double'
 *
 * @author Simon Tran
 */
public class SliderInput extends Input {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final double SLIDER_MIN = 0.0;
    public static final double SLIDER_MAX = 1000.0;

    private Double min;
    private Double max;

    private JSlider sliderField;

    /**
     * Permet de créer l'entrée
     *
     * @param value La valeur initiale
     */
    public SliderInput(double value) {
        super(value);
        sliderField = new JSlider();
        sliderField.setOpaque(false);
        this.setMin(DEFAULT_MIN);
        this.setMax(DEFAULT_MAX);

        init();
    }

    /**
     * Permet de créer l'entrée
     *
     * @param value     La valeur initiale
     * @param inputName Le nom de l'entrée
     * @param min       La valeur minimale du curseur
     * @param max       La valeur maximale du curseur
     */
    public SliderInput(double value, String inputName, double min, double max) {
        this(value);
        this.setInputName(inputName);
        this.setMin(min);
        this.setMax(max);

        changeValue(value);
    }

    /**
     * Permet de convertir une valeur sur le curseur en valeur réelle
     *
     * @param value La valeur sur le curseur
     * @return La valeur réelle
     */
    public double toRealValue(int value) {
        return toRealValue(value, getMin(), getMax());
    }

    /**
     * Permet de convertir une valeur réelle en valeur sur le curseur
     *
     * @param value la valeur réelle
     * @return La valeur sur le curseur
     */
    public int toSliderValue(double value) {
        return toSliderValue(value, getMin(), getMax());
    }

    /**
     * Permet de définir si le curseur devrait être vertical ou horizontal
     * @param vertical Si le curseur devrait être vertical
     */
    public void setVertical(boolean vertical) {
        sliderField.setOrientation(vertical ? JSlider.VERTICAL : JSlider.HORIZONTAL);
    }

    /* ------------------Méthodes Statiques------------------- */

    /**
     * Permet de convertir une valeur sur le curseur en valeur réelle
     *
     * @param value La valeur sur le curseur
     * @param min   La valeur minimale de la valeur réelle
     * @param max   La valeur maximale de la valeur réelle
     * @return La valeur réelle
     */
    public static double toRealValue(int value, double min, double max) {
        return MathUtil.unscaleValue((double) value / SLIDER_MAX, min, max);
    }

    /**
     * Permet de convertir une valeur réelle en valeur sur le curseur
     *
     * @param value La valeur réelle
     * @param min   La valeur minimale de la valeur réelle
     * @param max   La valeur maximale de la valeur réelle
     * @return La valeur sur le curseur
     */
    public static int toSliderValue(double value, double min, double max) {
        return (int) (MathUtil.scaleValueInRange(value, min, max) * SLIDER_MAX);
    }


    /* ------------------Méthodes "Override"------------------- */

    /**
     * Permet d'initialiser les entrées
     */
    @Override
    public void initInput() {

        sliderField.setMinimum((int) SLIDER_MIN);
        sliderField.setMaximum((int) SLIDER_MAX);

        this.add(sliderField);

    }

    /**
     * Permet d'initialiser les écouteurs
     */
    @Override
    public void initListeners() {
        sliderField.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                double sliderValue = toSliderValue(getNumber());
                if (sliderField.getValue() != sliderValue) {
                    changeValue(toRealValue(sliderField.getValue(), getMin(), getMax()));
                }
            }
        });
    }


    /**
     * Permet de mettre à jour le curseur en fonction de la valeur
     */
    @Override
    public void update() {
        sliderField.setValue(toSliderValue((double) getValue(), getMin(), getMax()));
    }

    /**
     * Permet de savoir si une valeur est conforme
     *
     * @param value La valeur
     * @return Vrai si la valeur est conforme
     */
    @Override
    public boolean valueIsValid(Object value) {
        return true;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        sliderField.setEnabled(enabled);
    }

    /* -------------------Getters & Setters------------------- */

    /**
     * Permet d'obtenir la valeur en nombre
     *
     * @return Le nombre
     */
    public double getNumber() {
        return (double) getValue();
    }


    /**
     * Permet d'obtenir la valeur minimal de l'entrée
     *
     * @return La valeur minimal
     */
    public Double getMin() {
        return min;
    }

    /**
     * Permet de définir la valeur minimal de l'entrée
     *
     * @param min La valeur minimal
     */
    public void setMin(Double min) {
        this.min = min;
    }

    /**
     * Permet de définir la valeur maximal de l'entrée
     *
     * @return La valeur maximal
     */
    public Double getMax() {
        return max;
    }

    /**
     * Permet de définir la valeur maximal de l'entrée
     *
     * @param max La valeur maximal
     */
    public void setMax(Double max) {
        this.max = max;
    }


}
