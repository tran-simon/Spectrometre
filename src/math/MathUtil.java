package math;


import java.awt.geom.Rectangle2D;

/**
 * Classe permettant d'effectuer des calculs mathématiques et qui contient des constantes mathématiques
 *
 * @author Simon Tran
 */
public final class MathUtil {

    /**
     * Permet d'obtenir un rectangle redimensionné. La position et la taille du rectangle sont multiplié par les valeurs de redimensionnement
     *
     * @param rectangle Le rectangle
     * @param scaleX    Le redimensionnement en X
     * @param scaleY    Le redimensionnement en Y
     * @return Le rectangle redimensionné
     */
    public static Rectangle2D.Double scaleRect(Rectangle2D.Double rectangle, double scaleX, double scaleY) {
        return new Rectangle2D.Double(rectangle.getX() * scaleX, rectangle.getY() * scaleY, rectangle.getWidth() * scaleX, rectangle.getHeight() * scaleY);
    }

    /**
     * Permet d'obtenir un rectangle redimensionné. La position et la taille du rectangle sont multiplié par les valeurs de redimensionnement
     *
     * @param rectangle Le rectangle
     * @param scale     Vecteur représentant les valeurs de redimensionnement
     * @return Le rectangle redimensionné
     */
    public static Rectangle2D.Double scaleRect(Rectangle2D.Double rectangle, SVector3d scale) {
        return scaleRect(rectangle, scale.getX(), scale.getY());
    }

    /**
     * Permet de créer un rectangle à partir d'une position et d'une taille
     *
     * @param position La position du rectangle
     * @param size     La taille du rectangle
     * @return Le rectangle
     */
    public static Rectangle2D.Double createRectangle(SVector3d position, SVector3d size) {
        return new Rectangle2D.Double(position.getX(), position.getY(), size.getX(), size.getY());
    }

    /**
     * Permet de s'assurer qu'une valeur est comprise entre deux bornes. Si la valeur est plus petite que la borne inférieur, la méthode retourne la borne inférieur.
     * Si la valeur est plus grande que la borne supérieur, la méthode retourne la borne supérieur.
     * Si la valeur est entre les deux bornes, la valeur est retournée inchangée.
     *
     * @param value La valeur
     * @param min   La borne inférieur
     * @param max   La borne supérieur
     * @return La valeur comprise entre les bornes
     */
    public static double getValueInRange(double value, double min, double max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    /**
     * Permet de mettre à l'échelle une valeur afin qu'une valeur égale à 'min' retourne 0.0 et qu'une valeur égale à 'max' retourne 1.0
     * Si la valeur ne se trouve pas entre 'min' et 'max', alors la méthode retournera un nombre qui n'est pas entre 0.0 et 1.0
     *
     * @param value La valeur
     * @param min   La valeur qui correspond à 0.0
     * @param max   La valeur qui correspond à 1.0
     * @return La valeur mise à l'échelle
     */
    public static double scaleValue(double value, double min, double max) {
        return (value - min) / (max - min);
    }

    /**
     * Permet de mettre à l'échelle une valeur afin qu'une valeur égale à '0.0' retourne 0.0 et qu'une valeur égale à 'max' retourne 1.0
     * Si la valeur ne se trouve pas entre 'min' et 'max', alors la méthode retournera un nombre qui n'est pas entre 0.0 et 1.0
     *
     * @param value La valeur
     * @param max   La valeur qui correspond à 1.0
     * @return La valeur mise à l'échelle
     */
    public static double scaleValue(double value, double max) {
        return scaleValue(value, 0.0, max);
    }

    /**
     * Permet de mettre à l'échelle une valeur afin qu'une valeur égale à 'min' retourne 0.0 et qu'une valeur égale à 'max' retourne 1.0
     * Si la valeur ne se trouve pas entre '0.0' et 'max', alors la méthode retournera respectivement '0.0' ou '1.0'
     * <p>
     * Cette méthode peut servir par exemple pour convertir des pourcentages.
     * Pour une 'value' de 50.0 et un 'max' de 100.0, la méthode retournerait '0.5'
     * <p>
     *
     * @param value La valeur
     * @param min   La valeur qui correspond à 0.0
     * @param max   La valeur qui correspond à 1.0
     * @return La valeur mise à l'échelle
     */
    public static double scaleValueInRange(double value, double min, double max) {
        return getValueInRange(scaleValue(value, min, max), 0.0, 1.0);
    }

    /**
     * Permet de mettre à l'échelle une valeur afin qu'une valeur égale à '0.0' retourne 0.0 et qu'une valeur égale à 'max' retourne 1.0
     * Si la valeur ne se trouve pas entre '0.0' et 'max', alors la méthode retournera respectivement '0.0' ou '1.0'
     * <p>
     * Cette méthode peut servir par exemple pour convertir des pourcentages.
     * Pour une 'value' de 50.0 et un 'max' de 100.0, la méthode retournerait '0.5'
     * <p>
     *
     * @param value La valeur
     * @param max   La valeur qui correspond à 1.0
     * @return La valeur mise à l'échelle
     */
    public static double scaleValueInRange(double value, double max) {
        return getValueInRange(scaleValue(value, 0.0, max), 0.0, 1.0);
    }

    /**
     * Méthode qui effectue l'opération inverse de la méthode 'scaleValue()'
     * Elle prend une valeur entre 0.0 - 1.0 et la convertit en valeur entre newMin et newMax
     * La valeur retournée peut ne pas être entre newMin et newMax si la valeur passée en paramêtre n'était pas comprise entre 0.0 et 1.0
     *
     * @param value  La valeur entre 0.0 et 1.0
     * @param newMin La valeur qui correspond à une valeur initiale de 0.0
     * @param newMax La valeur qui correspond à une valeur initiale de 1.0
     * @return La valeur mise à l'échelle
     */
    public static double unscaleValue(double value, double newMin, double newMax) {
        return (newMax - newMin) * value + newMin;
    }

    /**
     * Permet d'obtenir un vecteur position à partir d'un rectangle
     *
     * @param rectangle Le rectangle
     * @return Le vecteur position
     */
    public static SVector3d getRectanglePosition(Rectangle2D.Double rectangle) {
        return new SVector3d(rectangle.getX(), rectangle.getY());
    }

    /**
     * Permet d'obtenir un vecteur dimensions à partir d'un rectangle
     *
     * @param rectangle Le rectangle
     * @return Le vecteur dimensions
     */
    public static SVector3d getRectangleSize(Rectangle2D.Double rectangle) {
        return new SVector3d(rectangle.getWidth(), rectangle.getHeight());
    }

    /**
     * Permet d'obtenir un nouveau rectangle avec la position modifiée
     *
     * @param rectangle Le rectangle
     * @param position  La nouvelle position
     * @return Le nouveau rectangle
     */
    public static Rectangle2D.Double setRectanglePosition(Rectangle2D.Double rectangle, SVector3d position) {
        return new Rectangle2D.Double(position.getX(), position.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    /**
     * Permet d'obtenir un nouveau rectangle avec la taille modifiée
     *
     * @param rectangle Le rectangle
     * @param size      La nouvelle taille
     * @return Le nouveau rectangle
     */
    public static Rectangle2D.Double setRectangleSize(Rectangle2D.Double rectangle, SVector3d size) {
        return new Rectangle2D.Double(rectangle.getX(), rectangle.getY(), size.getX(), size.getY());
    }


    /**
     * Permet d'obtenir l'aire d'une sphère
     *
     * @param radius Le rayon de la sphère en m
     * @return L'aire en m^2
     */
    public static double sphereArea(double radius) {
        return 4 * Math.PI * Math.pow(radius, 2);

    }


    /**
     * Permet de convertir un angle en radians en angle en degrés positifs.
     * Si l'angle n'est pas entre 0 et 360 degrés, il est converti pour  être entre ces valeurs
     *
     * @param angle L'angle en radians
     * @return L'angle en degrés
     */
    public static double toDegrees(double angle) {
        double degrees = Math.toDegrees(angle);
        return getAngleBetween0And360(degrees);
    }

    /**
     * Permet de mettre un angle entre 0 et 360 degrés
     *
     * @param angle L'angle en degrés
     * @return L'angle entre 0 et 360 degrés
     */
    public static double getAngleBetween0And360(double angle) {
        if (angle > 360.0) {
            return getAngleBetween0And360(angle - 360.0);
        }
        else if (angle < 0) {
            return getAngleBetween0And360(angle + 360.0);
        }
        return angle;
    }

}
