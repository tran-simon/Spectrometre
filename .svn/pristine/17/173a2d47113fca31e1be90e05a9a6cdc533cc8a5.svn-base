package view.dessinable;

import math.SVector3d;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Permet de créer un fichier de sauvegarde
 * @author Sofianne
 *
 */
public class LabelView extends DrawableView {
	private String text;

	private SVector3d position;


	
    /**
     * Constructeur
     * @param text Le texte à afficher
     * @param position  La position de l'étiquette
     */
    public LabelView(String text, SVector3d position) {
        setText(text);
        setPosition(position);
    }

	@Override
	/**
	 * Méthode quiretourne une forme
	 */
	public Shape getShape() {
		return null;
	}

	@Override
	/**
	 * Méthode qui dessine le composant graphique
	 */
	public void draw(Graphics2D g2d, AffineTransform transform) {
		AffineTransform originalTransform = g2d.getTransform();


		//        g2d.draw(getShape());


		g2d.scale(1, -1);

		g2d.drawString(getText(),(float) getPosition().getX(), (float) getPosition().getY());
		g2d.setTransform(originalTransform);

		drawChildren(g2d, transform);
        //g2d.drawString(getText(), (float) getPosition().getX(), (float) getPosition().getY());
      


	}

	/**
	 * Méthode qui retourne le texte sauvegardé
	 * @return le texte
	 */
	public String getText() {
		return text;
	}

	/**
	 * Méthode qui ajuste le texte 
	 * @param text le texte
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Méthode qui retourne la position
	 * @return la position
	 */
    public SVector3d getPosition() {
        return position;
    }

    /**
     * Méthode qui permet de définir la position
     * @param position La position
     */
    public void setPosition(SVector3d position) {
        this.position = position;
    }
}
