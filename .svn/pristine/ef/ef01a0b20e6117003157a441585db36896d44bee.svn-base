package view;

import controller.ViewController;
import math.MathUtil;
import math.SVector3d;
import physics.PhysicsWorld;
import physics.component.field.Field;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * Classe qui permet d'afficher une carte des intensités des champs électriques et/ou magnétiques
 * Il est possible de choisir entre trois types de champs.
 * Si le type de champ est 'ELECTRIC' ou 'MAGNETIC', la couleur rouge correspond à une intensitée de zéro tandis qu'une couleur bleue correspond à l'intensitée maximale définie avec 'setMaxIntensity()'
 * Si le type de champ est 'BOTH', la luminosité du rouge correspond à l'intensité du champ électrique tandis que la luminosité du bleu correspond à l'intensité du champ magnétique
 *
 * @author Simon Tran
 */
public class FieldMapView extends View {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<ArrayList<Double[]>> fieldIntensityList = new ArrayList<>();
    public ArrayList<ArrayList<Color>> colorList = new ArrayList<>();

    private Image image;
    private Field.FieldType fieldType = Field.FieldType.ELECTRIC;
    private double maxIntensity;

    /**
     * Constructeur par défaut d'une vue
     * @param viewController Le controlleur
     */
    public FieldMapView(ViewController viewController) {
        super(viewController);
    }

    /**
     * Permet de calculer les intensités des champs.
     * Cette méthode doit être appelée lorsque les valeurs des champs changent dans le monde physique
     *
     * @param physicsWorld Le monde physique dans lequel il faut calculer les intensités
     */
    public void calculateFieldValues(PhysicsWorld physicsWorld) {
        if (!(getWidth() == 0 || getHeight() == 0)) {
            fieldIntensityList.clear();
            for (int x = 0; x < getWidth(); x++) {
                ArrayList<Double[]> values = new ArrayList<>();
                for (int y = 0; y < getHeight(); y++) {
                    SVector3d point = new SVector3d(x, y);
                    Double[] valueArray = getFieldValueForPoint(this.UIToSimulation(point), physicsWorld, getViewController().getTime());
                    values.add(valueArray);
                }
                fieldIntensityList.add(values);
            }
            updateColors();
        }
    }

    /**
     * Permet de mettre à jour les couleurs sans recalculer toutes les intensités des champs.
     * Cette méthode doit être appelée lorsque les variables 'maxIntensity' ou 'fieldType' sont modifiées.
     */
    public void updateColors() {
        ArrayList<ArrayList<Color>> newColorList = generateColors(fieldIntensityList, fieldType, maxIntensity);

        if (!newColorList.equals(colorList)) {
            colorList = newColorList;
            image = generateImage(colorList);
        }
        repaint();
    }

    /* ------------------Méthodes Statiques------------------- */

    /**
     * Permet d'obtenir la liste des couleurs qui seront dessinées
     *
     * @param fieldIntensityList La liste des intensitées
     * @param fieldType          Le type des champs à afficher
     * @param maxIntensity       L'intensité maximale des champs
     * @return La liste des couleurs
     */
    public static ArrayList<ArrayList<Color>> generateColors(ArrayList<ArrayList<Double[]>> fieldIntensityList, Field.FieldType fieldType, double maxIntensity) {
        ArrayList<ArrayList<Color>> colorList = new ArrayList<>();

        for (int x = 0; x < fieldIntensityList.size(); x++) {
            ArrayList<Color> colors = new ArrayList<>();
            for (int y = 0; y < fieldIntensityList.get(x).size(); y++) {
                colors.add(calculateColor(fieldIntensityList.get(x).get(y), fieldType, maxIntensity));
            }
            colorList.add(colors);
        }
        return colorList;
    }

    /**
     * Permet de générer une image à partir des couleurs
     *
     * @param colorList La liste des couleurs
     * @return L'image
     */
    public static Image generateImage(ArrayList<ArrayList<Color>> colorList) {
        BufferedImage image = new BufferedImage(colorList.size(), colorList.get(0).size(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < colorList.size(); x++) {
            for (int y = 0; y < colorList.get(x).size(); y++) {
                image.setRGB(x, y, colorList.get(x).get(y).getRGB());
            }
        }
        return image;
    }

    /**
     * Permet de calculer une couleur à partir d'une intensité de champ
     * L'intensité maximale du champ correspond à la valeur pour laquelle la couleur sera bleue
     *
     * @param intensity    L'intensité du champ
     * @param maxIntensity L'intensité maximale du champ
     * @return La couleur
     */
    public static Color fieldIntensityToColor(double intensity, double maxIntensity) {
        float hue = (float) MathUtil.scaleValueInRange(intensity, 0.f, maxIntensity);

        /* Pour une valeur de 1.f, on désire avoir la couleur bleue qui correspond à une valeur de 2/3 hue */
        float maxHue = 2.f / 3.f;
        hue *= maxHue;

        return Color.getHSBColor(hue, 1.f, 1.f);
    }

    /**
     * Permet de calculer une couleur à partir de 2 intensités de champ correspondant à [0] l'intensité du champ électrique et [1] l'intensité du champ magnétique
     *
     * @param intensities  Un tableau contenant les intensités des champs électrique et magnétique
     * @param maxIntensity L'intensité maximale des champs qui correspond à la valeur pour laquelle la luminosité de la couleur sera 100%
     * @return La couleur
     */
    public static Color fieldIntensityToColor(Double[] intensities, double maxIntensity) {
        float electricIntensity = (float) MathUtil.scaleValueInRange(intensities[0], maxIntensity);
        float magneticIntensity = (float) MathUtil.scaleValueInRange(intensities[1], maxIntensity);

        return new Color(electricIntensity, 0.f, magneticIntensity);
    }


    /**
     * Permet de calculer une couleur à partir de 2 intensités de champ, d'un type de champ et d'une intensité maximale
     *
     * @param intensities  Les intensités de champ
     * @param fieldType    Le type de champ
     * @param maxIntensity L'intensité maximale du champ
     * @return La couleur
     */
    public static Color calculateColor(Double[] intensities, Field.FieldType fieldType, double maxIntensity) {
        Color color = new Color(0, 0, 0);
        switch (fieldType) {
            case ELECTRIC:
                color = fieldIntensityToColor(intensities[0], maxIntensity);
                break;
            case MAGNETIC:
                color = fieldIntensityToColor(intensities[1], maxIntensity);
                break;
            case BOTH:
                color = fieldIntensityToColor(intensities, maxIntensity);
                break;
        }
        return color;
    }

    /**
     * Permet de calculer les champs électriques et magnétiques au point 'point' en coordonnées de la simulation
     *
     * @param point        Le point en coordonnées de la simulation
     * @param physicsWorld Le monde physique dans lequel il faut calculer les champs
     * @param time Le temps
     * @return un tableau contenant les valeurs des champs. La première valeur correspond à l'intensité du champ électrique. La seconde correspond à l'intensité du champ magnétique
     */
    public static Double[] getFieldValueForPoint(SVector3d point, PhysicsWorld physicsWorld, double time) {
        Double[] field = new Double[2];
        field[0] = physicsWorld.getElectricField(point, time).modulus();
        field[1] = physicsWorld.getMagneticField(point, time).modulus();
        return field;
    }

    /* ------------------Méthodes "Override"------------------- */


    /**
     * Permet de dessiner le FieldMapView
     *
     * @param g Le Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, null);
    }


    /* -------------------Getters & Setters------------------- */

    /**
     * Permet d'obtenir le type de champ
     *
     * @return Le type de champ
     */
    public Field.FieldType getFieldType() {
        return fieldType;
    }

    /**
     * Permet de définir le type de champ
     *
     * @param fieldType Le nouveau type de champ
     */
    public void setFieldType(Field.FieldType fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * Permet d'obtenir l'intensité maximale
     *
     * @return L'intensité maximale
     */
    public double getMaxIntensity() {
        return maxIntensity;
    }

    /**
     * Permet de définir l'intensité maximale
     *
     * @param maxIntensity La nouvelle intensité maximale
     */
    public void setMaxIntensity(double maxIntensity) {
        this.maxIntensity = maxIntensity;
    }
}
