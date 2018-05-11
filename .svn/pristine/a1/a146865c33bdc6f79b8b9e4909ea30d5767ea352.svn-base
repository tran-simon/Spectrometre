package aaplication;


import math.SVector3d;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Classe qui contient des méthodes statiques utiles dans plusieurs parties de l'application
 * Contient aussi les constantes relatives à l'application elle-même
 *
 * @author Simon Tran
 */
public final class AppUtil {

    /* -------------------Chaînes de charactères------------------- */

    /* Menu "Fichier" du mode de création */
    public static final String FILE_MENU_HELP = "Aide";
    public static final String FILE_MENU_FILE = "Fichier";
    public static final String FILE_MENU_NEW = "Nouveau";
    public static final String FILE_MENU_OPEN = "Ouvrir... (CTRL+O)";
    public static final String FILE_MENU_RENAME = "Renommer...";
    public static final String FILE_MENU_SAVE = "Sauvegarder(CTRL+S)";
    public static final String FILE_MENU_SAVEAT = "Sauvegarder Sous... (CTRL+SHIFT+S)";
    public static final String FILE_MENU_BACK = "Retour au spectromètre de masse";


    public static final String POSITIONING_OPTIONS_LABEL = "Options de positionnement";


    /* -------------------Constantes------------------- */

    /**
     * L'extension que les fichiers de sauvegarde devront avoir
     */
    public static final String FILE_EXTENSION = "physicsworld";


    /**
     * La sensibilité de la roue de la souris.
     */
    public static final int MOUSE_WHEEL_SENSIBILITY = 5;




    /**
     * Nombre de pixels de l'inteface par mètres du monde par défaut
     */
    public static final double DEFAULT_WORLD_SCALE = 100;

    public static final double MIN_WORLD_SCALE = DEFAULT_WORLD_SCALE / 10;
    public static final double MAX_WORLD_SCALE = DEFAULT_WORLD_SCALE * 5;

    /**
     * Les dimensions minimales et maximales des composants.
     */
    public static final double COMPONENT_MIN_WIDTH = 1/DEFAULT_WORLD_SCALE;
    public static final double COMPONENT_MIN_HEIGHT = 1/DEFAULT_WORLD_SCALE;
    public static final double COMPONENT_MAX_WIDTH = 1000 * COMPONENT_MIN_WIDTH;
    public static final double COMPONENT_MAX_HEIGHT = 1000 * COMPONENT_MIN_HEIGHT;
    public static final SVector3d COMPONENT_MAX_SIZE = new SVector3d(COMPONENT_MAX_WIDTH, COMPONENT_MAX_HEIGHT);

    /**
     *  La dimension par défaut de la plupart des composants.
     *  Elle équivaut à 20% de la grandeur maximale
     */
    public static final double COMPONENT_DEFAULT_WIDTH = 0.2 * COMPONENT_MAX_WIDTH;
    public static final double COMPONENT_DEFAULT_HEIGHT = 0.2 * COMPONENT_MAX_HEIGHT;
    public static final SVector3d COMPONENT_DEFAULT_SIZE = new SVector3d(COMPONENT_DEFAULT_WIDTH, COMPONENT_DEFAULT_HEIGHT);

    /**
     * Le nombre de chiffres significatifs par défaut
     */
    public static final int PRECISION = 4;

    /**
     * l'expression régulière qui correspond à un nombre avec ou sans virgule écrit en notation normale ou scientifique
     */
    public static final String REGEX_NUMBER = "^[-+]?\\d+(\\.\\d+)?([eE][-+]?\\d+)?";




    /* -------------------Méthodes------------------- */

    /**
     * Permet d'obtenir une chaîne de charactères pour un nombre avec une précision par défaut
     *
     * @param value La valeur du nombre
     * @return Une chaîne de charactères formatée
     */
    public static String numberToString(double value) {
        return numberToString(value, PRECISION);
    }

    /**
     * Permet d'obtenir une chaîne de charactères pour un nombre avec une certaine précision
     *
     * @param value     La valeur du nombre
     * @param precision Le nombre de chiffres significatifs.
     * @return Une chaîne de charactères formatée
     */
    public static String numberToString(double value, int precision) {
        boolean useSciNotation = false;
        double absValue = Math.abs(value);

        if (absValue >= Math.pow(10, precision) || absValue <= Math.pow(10, -precision + 1)) {
            useSciNotation = true;
        }
        return numberToString(value, precision, useSciNotation);

    }

    /**
     * Permet d'obtenir une chaîne de charactères pour un nombre avec une précision en notation scientifique ou non.
     *
     * @param value          La valeur du nombre
     * @param useSciNotation Si le nombre devrait être écrit avec la notation scientifique
     * @param precision      Le nombre de chiffres significatifs.
     * @return Une chaîne de charactères formatée
     */
    public static String numberToString(double value, int precision, boolean useSciNotation) {
        if (Double.isNaN(value) || value == 0.0) {
            return "0.0";
        }
        String formatString = "0";
        String numberString;
        String exponentFormatString = "";

        StringBuilder stringBuilder = new StringBuilder(formatString);
        if (precision > 0) {
            stringBuilder.append(".");
        }
        for (int i = 0; i < precision - 1; i++) {
            stringBuilder.append("0");
        }
        formatString = stringBuilder.toString();

        if (useSciNotation) {
            exponentFormatString = "E0";
        }

        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getNumberInstance(Locale.US);
        decimalFormat.applyPattern(formatString + exponentFormatString);
        if (!useSciNotation) {
            decimalFormat.setMaximumFractionDigits(precision - getNumberDigitsBeforeDot(value));
        }

        numberString = decimalFormat.format(value);
        if (numberString.equals("∞") || Double.parseDouble(numberString) == 0.0) {
            return new DecimalFormat(formatString).format(value);
        }

        return numberString;
    }


    /**
     * Permet d'obtenir le nombre de chiffres avant la virgule pour une valeur
     *
     * @param value La valeur
     * @return Le nombre de chiffres avant la virgule
     */
    public static int getNumberDigitsBeforeDot(double value) {
        value = Math.abs(value);
        NumberFormat numberFormat = new DecimalFormat("0.0");
        return numberFormat.format(value).indexOf(".");
    }

}
