package physics;

import math.SVector3d;

/**
 * Classe permettant d'effectuer des calculs physiques et qui contient des constantes physiques
 *
 * @author Simon Tran
 */
public final class Physics {

    /**
     * Constante de coulomb en (N * m^2)/C^2
     */
    public static final double k = 8.99e9;
    /**
     * Charge élémentaire en Coulombs
     */
    public static final double e = 1.6e-19;

    /**
     * Masse d'un électron en kg
     */
    public static final double MASS_ELECTRON = 9.11e-31;

    /**
     * Masse d'un proton en kg
     */
    public static final double MASS_PROTON = 1.67e-27;

    /**
     * La constante électrique en F/m
     */
    public static final double ELECTRIC_CONSTANT = 8.85e-12;

    /**
     * La vitesse de la lumière en m/s
     */
    public static final double c = 3e8;

    /**
     * Permet d'obtenir la position d'un objet en fonction du temps, de sa vitesse et de l'accélération selon la formule
     * x(t)=x0+v0*t+1/2 *a*t^2
     *
     * @param x0 La position initiale
     * @param v0 La vitesse initiale
     * @param a  l'accélération
     * @param t  le temps
     * @return la position de l'objet
     */
    public static SVector3d position(SVector3d x0, SVector3d v0, SVector3d a, double t) {
        return x0.add(v0.multiply(t)).add(a.multiply(0.5 * Math.pow(t, 2)));
    }

    /**
     * Permet d'obtenir la vitesse d'un objet en fonction du temps et de l'accélération
     *
     * @param v0 La vitesse initiale
     * @param a  l'accélération
     * @param t  le temps
     * @return La vitesse de l'objet
     */
    public static SVector3d speed(SVector3d v0, SVector3d a, double t) {
        return v0.add(a.multiply(t));
    }


    /**
     * Retourne l'accélération d'un objet en fonction de la force appliquée à celui-ci et la masse de l'objet en utilisant la formule a=F/m dérivée de F=ma
     *
     * @param force La force
     * @param mass  La masse
     * @return l'accélération
     */
    public static SVector3d acceleration(SVector3d force, double mass) {
        return force.multiply(1 / mass);
    }

    /**
     * Permet d'obtenir la force magnétique en fonction de la charge de la particule, de sa vitesse et du champ électrique
     *
     * @param charge        la charge de la particule
     * @param speed         la vitesse de la particule
     * @param magneticField le champ magnétique
     * @return la force magnétique
     */
    public static SVector3d magneticForce(double charge, SVector3d speed, SVector3d magneticField) {
        return speed.cross(magneticField).multiply(charge);
    }

    /**
     * Permet d'obtenir la force électrique en fonction de la charge de la particule et du champ électrique
     *
     * @param charge        la charge de la particule
     * @param electricField le champ électrique
     * @return la force électrique
     */
    public static SVector3d electricForce(double charge, SVector3d electricField) {
        return electricField.multiply(charge);
    }


    /**
     * Retourne le module du champ électrique créé par une particule de charge "charge" à une distance "r"
     * @param charge la charge de la particule
     * @param r la distance
     * @return le module du champ électrique
     */
    public static double electricField(double charge, double r){
        return Physics.k * charge / Math.pow(r, 2);
    }





}