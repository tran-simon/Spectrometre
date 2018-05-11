package physics.component;

import math.SVector3d;
import physics.Physics;
import physics.PhysicsWorld;
import physics.SpectrometreListener;

/**
 * Classe qui permet de créer un spectromètre
 * @author Sofianne
 *
 */
public class Spectrometre extends PhysicsWorld {
    public enum Stage {NONE, CYCLOTRON, SELECTEUR_DE_VITESSE, DEFLECTEUR}

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    Cyclotron cyclotron;
    SelecteurDeVitesse selecteurDeVitesse;
    Deflecteur deflecteur;
    Particle particle = new Particle();

    private double selecteurDeVitessePrecision = 0.1;
    private Stage stage = Stage.NONE;


    /**
     * Création d'un spectromètre
     */
    public Spectrometre() {

        cyclotron = new Cyclotron();
        cyclotron.setPeriod(0);


        cyclotron.setFinalSpeed(1e-3 * Physics.c);
        cyclotron.setPosition(new SVector3d(0, 2.5));

        this.selecteurDeVitesse = new SelecteurDeVitesse();
        selecteurDeVitesse.setPosition(new SVector3d(cyclotron.getPosition().getX() + cyclotron.getWidth(), cyclotron.getPosition().getY()));

        this.deflecteur = new Deflecteur();
        deflecteur.setPosition(new SVector3d(selecteurDeVitesse.getPosition().getX() + selecteurDeVitesse.getWidth(), selecteurDeVitesse.getPosition().getY() - deflecteur.getHeight() / 2.0));


        this.add(cyclotron);
        this.add(selecteurDeVitesse);
        this.add(deflecteur);

//        generateParticle();
//        this.add(particle);
    }


    /**
     * Méthode qui génère une particule
     * @return La particule
     */
    public Particle generateParticle() {
        stage = Stage.NONE;
        particle = cyclotron.generateParticle();
        return particle;
    }

    /**
     * Un événement est levé lorsque la particule entre dans le sélecteur de vitesse
     * @param particle la particule
     */
    public void raiseEventParticleEnteredSelecteur(Particle particle) {
        for (SpectrometreListener spectrometreListener : LISTENERS.getListeners(SpectrometreListener.class)) {
            spectrometreListener.particleEnteredSelecteur(particle);
        }

    }
    /**
     * Un événement est levé lorsque la position de la particule change
     * @param particle la particule
     */
    public void raiseEventParticlePositionChanged(Particle particle){

        for (SpectrometreListener spectrometreListener : LISTENERS.getListeners(SpectrometreListener.class)) {
            spectrometreListener.particlePositionChanged(particle);
        }
    }

    /**
     * Un événement est levé lorsque la particule entre dans le déflecteur
     * @param particle la particule
     */
    public void raiseEventParticleEnteredDeflecteur(Particle particle) {

        for (SpectrometreListener spectrometreListener : LISTENERS.getListeners(SpectrometreListener.class)) {
            spectrometreListener.particleEnteredDeflecteur(particle);
        }
    }

    /**
     * Un événement est levé lorsque la particule arrête
     * @param particle la particule
     */
    public void raiseEventParticleStopped(Particle particle) {

        for (SpectrometreListener spectrometreListener : LISTENERS.getListeners(SpectrometreListener.class)) {
            spectrometreListener.particleStopped(particle);
        }
    }

    /**
     * Un événement est levé lorsque la masse de la particule est détectée
     * @param particle la particule
     */
    public void raiseEventMassDetected(double diameter, double detectedMass) {
        for (SpectrometreListener spectrometreListener : LISTENERS.getListeners(SpectrometreListener.class)) {
            spectrometreListener.massDetected(diameter, detectedMass);
        }
    }

    @Override
    /**
     * Un événement est levé lorsque le step change
     */
    public void raiseEventStep(double delta_t) {
        super.raiseEventStep(delta_t);
        for (SpectrometreListener spectrometreListener : LISTENERS.getListeners(SpectrometreListener.class)) {
            spectrometreListener.step(delta_t);
        }
    }

    /**
     * Méthode qui ajoute les événements personnalisés du spectromètre
     * @param listener les événements personnalisés
     */
    public void addSpectrometreListener(SpectrometreListener listener) {
        LISTENERS.add(SpectrometreListener.class, listener);
    }

    @Override
    /**
     * Méthode qui calcule la valeur du step
     */
    public void step(double delta_t, double time) {
        super.step(delta_t, time);
        raiseEventParticlePositionChanged(particle);

        if (stage.compareTo(Stage.CYCLOTRON) <= 0 && cyclotron.getBounds().contains(particle.getPosition().toPoint())) {
            stage = Stage.CYCLOTRON;

        }
        else if (stage.compareTo(Stage.SELECTEUR_DE_VITESSE) <= 0 && selecteurDeVitesse.getBounds().contains(particle.getPosition().toPoint())) {
            if (stage != Stage.SELECTEUR_DE_VITESSE) {
                selecteurDeVitesse.setEntrancePosition(particle.getPosition());
                selecteurDeVitesse.setEntranceSpeed(particle.getSpeedModulus());
                stage = Stage.SELECTEUR_DE_VITESSE;
                raiseEventParticleEnteredSelecteur(particle);
            }
        }
        else if (deflecteur.getBounds().contains(particle.getPosition().toPoint())) {
            if (stage != Stage.DEFLECTEUR) {
                if (Math.abs(particle.getPosition().getY() - selecteurDeVitesse.getEntrancePosition().getY()) <= selecteurDeVitessePrecision) {
                    /* La particule est détectée sur la plaque */

                    deflecteur.setEntrancePosition(particle.getPosition());
                    stage = Stage.DEFLECTEUR;

                    raiseEventParticleEnteredDeflecteur(particle);

                }
                else {
                    /* La particule n'a pas passée par le trou à la sortie du sélecteur de vitesse */
                    raiseEventParticleStopped(particle);
                }
            }
        }
        else {
            if (stage == Stage.SELECTEUR_DE_VITESSE) {
                /* Le selecteur de vitesse a intercepté la particule */
                raiseEventParticleStopped(particle);
            }
            else if (stage == Stage.DEFLECTEUR) {
                double radius = Math.abs(particle.getPosition().getY() - deflecteur.getEntrancePosition().getY()) / 2.0;
                double calculatedMass = deflecteur.calculateMass(particle.getCharge(), particle.getSpeedModulus(), radius);
                raiseEventMassDetected(radius * 2.0, calculatedMass);
            }
        }
    }


    /**
     * Retourne le cyclotron
     *
     * @return le cyclotron
     */
    public Cyclotron getCyclotron() {
        return this.cyclotron;
    }

    /**
     * Retourne le sélecteur de vitesse
     *
     * @return le sélecteur de vitesse
     */
    public SelecteurDeVitesse getSelecteurDeVitesse() {
        return this.selecteurDeVitesse;
    }

    /**
     * Retourne le déflecteur
     *
     * @return le déflecteur
     */
    public Deflecteur getDeflecteur() {
        return this.deflecteur;
    }

    /**
     * Retourne la particule
     *
     * @return la particule
     */
    public Particle getParticle() {
        return cyclotron.getParticle();
    }

    /**
     * Méthode qui retourne le niveau
     * @return Le niveau
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Méthode qui ajuste le niveau
     * @param stage le niveau
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
	


