package inputs;

import aaplication.AppUtil;

import static java.lang.Double.NaN;

/**
 * Classe qui permet de céer un JPanel contenant des entrées permettant de modifier les valeurs d'un nombre
 * L'entrée peut constituer d'une zone de texte et d'un curseur qui sont liés entre-eux
 *
 * @author Simon Tran
 */
public class NumberInput extends Input {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static final double DEFAULT_MIN = 0.0;
    public static final double DEFAULT_MAX = 0.0;

    private SliderInput sliderInput;
    private TextInput textInput;

    private boolean useMin = true;

    /**
     * Permet de créer une entrée de nombre
     *
     * @param value La valeur
     */
    public NumberInput(double value) {
        super(value);

        textInput = new TextInput(getTextValue(value));
        sliderInput = new SliderInput(value, "", DEFAULT_MIN, DEFAULT_MAX);
        sliderInput.setOpaque(false);

        setWithSlider(true);

        init();
    }

    /**
     * Permet de créer une entrée de nombre
     * Si la valeur maximum est égale à la valeur minimale, le maximum est ignoré
     *
     * @param value     La valeur
     * @param inputName Le nom de l'entrée
     * @param min       La valeur minimal de l'entrée
     * @param max       La valeur maximal de l'entrée
     */
    public NumberInput(double value, String inputName, double min, double max) {
        this(value);
        this.setInputName(inputName);
        this.setMin(min);
        this.setMax(max);

        update();
    }

    /**
     * Permet de créer une entrée de nombre
     * Si la valeur maximum est égale à la valeur minimale, le maximum est ignoré
     *
     * @param value      La valeur
     * @param inputName  Le nom de l'entrée
     * @param min        La valeur minimal de l'entrée
     * @param max        La valeur maximal de l'entrée
     * @param withSlider Si l'entrée devrait afficher un curseur
     */
    public NumberInput(double value, String inputName, double min, double max, boolean withSlider) {
        this(value, inputName, min, max);
        this.setWithSlider(withSlider);
        update();
    }

    /* ------------------Méthodes "Override"------------------- */

    /**
     * Permet d'initialiser les entrées
     */
    @Override
    public void initInput() {
        textInput.setRegex(AppUtil.REGEX_NUMBER);
        this.add(textInput);
        this.add(sliderInput);
    }

    /**
     * Permet d'initialiser les écouteurs
     */
    @Override
    public void initListeners() {
        textInput.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                double valueEntered = Double.parseDouble(textInput.getText());
                boolean valueDidntChange = getTextValue(getNumber()).equals(getTextValue(valueEntered));
                if (!valueDidntChange && valueIsValid(valueEntered)) {
                    changeValue(Double.parseDouble((String) textInput.getValue()));
                }
                else {
                    textInput.changeValue(getTextValue(getNumber()));
                }
            }
        });

        sliderInput.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                if (sliderInput.toSliderValue((double) input) != sliderInput.toSliderValue(getNumber())) {
                    changeValue(input);
                }
            }
        });

    }

    /**
     * Permet de mettre à jour le curseur et la zone de texte en fonction de la valeur sauvegardée.
     */
    @Override
    public void update() {
        double number = getNumber();
        if (number == NaN) {
            number = 0.0;
        }

        textInput.changeValue(getTextValue(number));
        sliderInput.changeValue(number);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        textInput.setEnabled(enabled);
        sliderInput.setEnabled(enabled);
    }

    /**
     * Permet de convertir une valeur numérique en valeur textuelle
     *
     * @param value La valeur numérique
     * @return La valeur textuelle
     */
    public static String getTextValue(double value) {
        return AppUtil.numberToString(value, AppUtil.PRECISION);
    }

    /**
     * Permet d'obtenir si une valeur est valide, si elle est conforme aux exigences de l'entrée
     * Permet de savoir si la valeur se trouve entre le minimum et le maximum
     *
     * @param value La valeur
     * @return Vrai si la valeur est conforme
     */
    @Override
    public boolean valueIsValid(Object value) {
        Double doubleValue;
        if (value instanceof String) {
            if (!textInput.valueIsValid(value)) {
                return false;
            }
            doubleValue = Double.parseDouble((String) value);
        }
        else if (value instanceof Double) {
            doubleValue = (Double) value;
        }
        else {
            return false;
        }

        if (getMin() == getMax()) {
            /* Si le maximum est égal au minimum, on l'ignore */
            return !useMin || doubleValue >= getMin();
        }

        return doubleValue >= getMin() && doubleValue <= getMax();
    }



    /* -------------------Getters & Setters------------------- */

    /**
     * Permet d'obtenir la valeur minimal de l'entrée
     *
     * @return La valeur minimal
     */
    public double getMin() {
        return sliderInput.getMin();
    }

    /**
     * Permet de définir la valeur minimal de l'entrée
     *
     * @param min La valeur minimal
     */
    public void setMin(double min) {
        sliderInput.setMin(min);
    }

    /**
     * Permet de définir la valeur maximal de l'entrée
     *
     * @return La valeur maximal
     */
    public double getMax() {
        return sliderInput.getMax();
    }

    /**
     * Permet de définir la valeur maximal de l'entrée
     *
     * @param max La valeur maximal
     */
    public void setMax(double max) {
        sliderInput.setMax(max);
    }


    /**
     * Permet d'obtenir la valeur en nombre
     *
     * @return La valeur
     */
    public double getNumber() {
        return (double) getValue();
    }


    /**
     * Permet de savoir si le curseur est visible
     *
     * @return Vrai si le curseur est visible
     */
    public boolean isWithSlider() {
        return sliderInput.isVisible();
    }

    /**
     * Permet de définir si le curseur doit être visible
     *
     * @param withSlider Si le curseur doit être visible
     */
    public void setWithSlider(boolean withSlider) {
        sliderInput.setVisible(withSlider);
    }

    /**
     * Retourne si l'entrée tient compte du minimum
     *
     * @return Si l'entrée tient compte du minimum
     */
    public boolean isUseMin() {
        return useMin;
    }

    /**
     * Défini si l'entrée tient compte du minimum
     *
     * @param useMin Si l'entrée tient compte du minimum
     */
    public void setUseMin(boolean useMin) {
        this.useMin = useMin;
    }
}
