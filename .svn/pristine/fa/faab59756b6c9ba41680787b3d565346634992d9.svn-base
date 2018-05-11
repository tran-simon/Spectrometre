package math;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * La classe <b>SVector3d</b> représente une vecteur <i>x</i>, <i>y</i> et
 * <i>z</i> à trois dimensions.
 * Les opérations mathématiques supportées sont les suivantes :
 * - L'addition.
 * - La soustraction.
 * - La multiplication par un scalaire.
 * - Le produit scalaire (<i>dot product</i>).
 * - Le produit vectoriel (<i>cross product</i>).
 * - La normalisation (vecteur de module 1).
 *
 * @author Simon Vezina
 * @author Simon Tran
 * @version 2017-12-20 (version labo – Le ray tracer v2.1)
 * @since 2014-12-16
 */
public class SVector3d implements  Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * La constante 'DEFAULT_COMPONENT' correspond à la composante par défaut des
     * variables x,y et z du vecteur étant égale à {@value}.
     */
    private static final double DEFAULT_COMPONENT = 0.0;

    // --------------
    // VARIABLES //
    // --------------

    /**
     * La variable <b>x</b> correspond à la composante x du vecteur 3d.
     */
    private final double x;

    /**
     * La variable <b>y</b> correspond à la composante y du vecteur 3d.
     */
    private final double y;

    /**
     * La variable <b>z</b> correspond à la composante z du vecteur 3d.
     */
    private final double z;

    // -----------------
    // CONSTRUCTEURS //
    // -----------------

    /**
     * Constructeur représentant un vecteur 3d à l'origine d'un système d'axe xyz.
     */
    public SVector3d() {
        this(DEFAULT_COMPONENT, DEFAULT_COMPONENT, DEFAULT_COMPONENT);
    }

    /**
     * Constructeur avec composante <i>x</i>,<i>y</i> et <i>z</i> du vecteur 3d.
     *
     * @param x La composante <i>x</i> du vecteur.
     * @param y La composante <i>y</i> du vecteur.
     * @param z La composante <i>z</i> du vecteur.
     */
    public SVector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructeur avec composante <i>x</i> et <i>y</i> vecteur 3d. La composante <i>z</i> est assignée à zéro.
     *
     * @param x La composante <i>x</i> du vecteur.
     * @param y La composante <i>y</i> du vecteur.
     */
    public SVector3d(double x, double y) {
        this(x, y, DEFAULT_COMPONENT);
    }

    /**
     * Permet de construire un vecteur avec une variable de type Point
     * PAR SIMON TRAN
     *
     * @param point le point
     */
    public SVector3d(Point point) {
        this(point.getX(), point.getY());
    }

    /**
     * Permet de construire un vecteur avec une variable de type Point2D
     * PAR SIMON TRAN
     *
     * @param point2D le point
     */
    public SVector3d(Point2D point2D) {
        this(point2D.getX(), point2D.getY());
    }

    /**
     * Permet de construire un vecteur à partir d'un tableau de composantes
     * Le tableau peut contenir 2 ou 3 composantes
     * PAR SIMON TRAN
     *
     * @param components Le tableau de composantes
     */
    public SVector3d(double[] components) {
        this(components[0], components[1], (components.length >= 3) ? components[2] : 0.0);
    }


    /**
     * Permet d'obtenir un nouveau vecteur ayant la composante à l'index 'index' changée pour 'value'
     * PAR SIMON TRAN
     *
     * @param index L'index de la composante à changer
     * @param value La nouvelle valeur
     * @return Le vecteur
     */
    public SVector3d changeComponentAtIndex(int index, double value) {
        double[] components = getComponents();
        components[index] = value;
        return new SVector3d(components);
    }


    /**
     * Permet d'obtenir les composantes du vecteur sous la forme d'un tableau
     * PAR SIMON TRAN
     *
     * @return Un tableau de composantes
     */
    public double[] getComponents() {
        return new double[]{getX(), getY(), getZ()};
    }


    /**
     * Permet de convertir un SVector3d en point de 2 dimensions. La troisième composante est ignorée
     * PAR SIMON TRAN
     *
     * @return le point
     */
    public Point2D toPoint() {
        return new Point2D.Double(getX(), getY());
    }

    /**
     * Permet de créer un vecteur en 2 dimensions à partir d'un objet Dimension
     *
     * @param dimension La dimension
     */
    public SVector3d(Dimension dimension) {
        this(dimension.getWidth(), dimension.getHeight());
    }

    /**
     * Permet d'obtenir un vecteur à partir d'une taille et d'un angle en radiant
     * PAR SIMON TRA
     *
     * @param size  La taille du vecteur
     * @param angle L'angle en radiant
     * @return Le vecteur
     */
    public static SVector3d vectorFromSizeAndAngle(double size, double angle) {
        return new SVector3d(size * Math.cos(angle), size * Math.sin(angle));
    }
    // ------------
    // MÉTHODES //
    // ------------

    /**
     * Méthode qui donne accès à la coordonnée x du vecteur.
     *
     * @return La coordonnée x.
     */
    public double getX() {
        return x;
    }

    /**
     * Méthode qui donne accès à la coordonnée y du vecteur.
     *
     * @return La coordonnée y.
     */
    public double getY() {
        return y;
    }

    /**
     * Méthode qui donne accès à la coordonnée z du vecteur.
     *
     * @return La coordonnée z.
     */
    public double getZ() {
        return z;
    }

    /**
     * Méthode qui calcule <b>l'addition</b> de deux vecteurs.
     *
     * @param v Le vecteur à ajouter.
     * @return La somme des deux vecteurs.
     */
    public SVector3d add(SVector3d v) {
        return new SVector3d(x + v.x, y + v.y, z + v.z);
    }

    /**
     * Méthode qui calcul la <b>soustraction</b> de deux vecteurs.
     *
     * @param v - Le vecteur à soustraire au vecteur présent.
     * @return La soustraction des deux vecteurs.
     */
    public SVector3d substract(SVector3d v) {

        return new SVector3d(x - v.x, y - v.y, z - v.z);
    }

    /**
     * Méthode qui effectue la <b>multiplication d'un vecteur par une scalaire</b>.
     *
     * @param m Le scalaire.
     * @return La multiplication d'un vecteur par un scalaire.
     */
    public SVector3d multiply(double m) {

        return new SVector3d(m * x, m * y, m * z);
    }

    /**
     * Permet de redimensionner un vecteur en 2 dimensions avec 2 scalaires de grandeurs différentes
     * PAR SIMON TRAN
     *
     * @param x Le redimensionnement en x
     * @param y Le redimensionnement en y
     * @return Le nouveau vecteur redimensionné
     */
    public SVector3d scale(double x, double y) {
        return new SVector3d(getX() * x, getY() * y);
    }

    /**
     * Permet de redimensionner un vecteur en 2 dimensions avec un autre vecteur en 2 dimensions. Les composantes du premier vecteur seront multipliées à celles du second.
     * PAR SIMON TRAN
     *
     * @param vector le vecteur de redimensionnement
     * @return Le nouveau vecteur redimensionné
     */
    public SVector3d scale(SVector3d vector) {
        return this.scale(vector.getX(), vector.getY());
    }

    /**
     * Permet d'obtenir un nouveau vecteur avec les composantes inversées.
     * Par exemple, le vecteur [1 3 5] deviendrait [1/1 1/3 1/5]
     * PAR SIMON TRAN
     *
     * @return Le vecteur inversé
     */
    public SVector3d inverse() {
        return new SVector3d(1 / getX(), 1 / getY(), 1 / getZ());
    }

    /**
     * Permet de savoir si un vecteur est nul
     * PAR SIMON TRAN
     *
     * @return Vrai si le vecteur est nul
     */
    public boolean isNull() {
        return this.equals(new SVector3d());
    }

    /**
     * Permet de savoir si un vecteur est normalisée
     *
     * @return Vrai si la norme du vecteur est 1.0
     */
    public boolean isNormalized() {
        return this.modulus() == 1.0;
    }

    /**
     * Méthode pour obtenir le <b>module</b> d'un vecteur.
     *
     * @return Le module du vecteur.
     */
    public double modulus() {

        return Math.sqrt((x * x) + (y * y) + (z * z));
    }

    /**
     * Méthode pour <b>normaliser</b> un vecteur à trois dimensions. Un vecteur
     * normalisé possède la même orientation, mais possède une <b> longeur unitaire
     * </b>. Si le <b>module du vecteur est nul</b>, le vecteur normalisé sera le
     * <b> vecteur nul </b> (0.0, 0.0, 0.0).
     *
     * @return Le vecteur normalisé.
     */
    public SVector3d normalize() {
        double mod = modulus();

        /* Si le module est trop petit, alors on retourne u vecteur nul */
        if (Double.compare(mod, 0.0) == 0)
            return new SVector3d();
        else
            return new SVector3d(x / mod, y / mod, z / mod);
    }


    /**
     * Méthode pour effectuer le <b>produit scalaire</b> entre deux vecteurs.
     *
     * @param v Le vecteur à mettre en produit scalaire.
     * @return Le produit scalaire entre les deux vecteurs.
     */
    public double dot(SVector3d v) {

        return (x * v.x + y * v.y + z * v.z);
    }

    /**
     * Méthode pour effectuer le <b>produit vectoriel</b> avec un autre vecteur v.
     * Cette version du produit vectoriel est implémenté en respectant la <b> règle
     * de la main droite </b>. Il est important de rappeler que le produit vectoriel
     * est <b> anticommutatif </b> (AxB = -BxA) et que l'ordre des vecteurs doit
     * être adéquat pour obtenir le résultat désiré. Si l'un des deux vecteurs est
     * <b> nul </b> ou les deux vecteurs sont <b> parallèles </b>, le résultat sera
     * un <b> vecteur nul </b>.
     *
     * @param v Le second vecteur dans le produit vectoriel.
     * @return Le résultat du produit vectoriel.
     */
    public SVector3d cross(SVector3d v) {

        return new SVector3d(y * v.z - z * v.y, -1 * (x * v.z - z * v.x), x * v.y - y * v.x);
    }

    /**
     * Permet d'obtenir un vecteur représentant la projection du vecteur A sur le vecteur B
     * PAR SIMON TRAN
     *
     * @param A Le vecteur A
     * @param B Le vecteur B
     * @return Le vecteur de la projection A sur B
     */
    public static SVector3d projection(SVector3d A, SVector3d B) {
        return B.multiply(A.dot(B) / (B.dot(B)));
    }

    /**
     * Méthode pour obtenir la distance entre deux points.
     *
     * @param A Le premier point.
     * @param B Le deuxième point.
     * @return La distance entre le point A et B.
     */
    public static double distance(SVector3d A, SVector3d B) {
        return B.substract(A).modulus();
    }

    /**
     * Méthode permettant d'obtenir l'angle entre deux vecteurs A et B. L'angle sera
     * obtenu en radian.
     *
     * @param A Le premier vecteur.
     * @param B Le second vecteur.
     * @return L'angle entre les deux vecteurs.
     */
    public static double angleBetween(SVector3d A, SVector3d B) {
        return Math.acos(A.dot(B) / (A.modulus() * B.modulus()));
    }

    /**
     * Méthode pour obtenir l'angle d'un vecteur par rapport à un vecteur horizontale dirigé vers la droite
     *
     * @return L'angle du vecteur en radiants
     */
    public double angle() {
        return Math.atan2(getY(), getX());
    }

    /**
     * Retourne un vecteur ayant subit une rotation de 'angle' radians
     *
     * @param angle L'angle en radians
     * @return Le nouveau vecteur
     */
    public SVector3d rotate(double angle) {
        return SVector3d.vectorFromSizeAndAngle(modulus(), this.angle() + angle);
    }

    /**
     * Permet d'obtenir la valeur textuelle du vecteur
     * @return La valeur textuelle du vecteur
     */
    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }


    /**
     * Permet de comparer 2 vecteurs
     * @param obj L'autre vecteur
     * @return Vrai si les vecteurs sont égaux
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof SVector3d))
            return false;

        SVector3d other = (SVector3d) obj;

        if (!(Double.compare(x, other.x) == 0))
            return false;

        if (!(Double.compare(y, other.y) == 0)) {
            return false;
        }

        return Double.compare(z, other.z) == 0;
    }


}// fin de la classe SVector3d
