package inputs;

import aaplication.ImageUtil;
import math.SVector3d;

import javax.swing.*;
import java.awt.*;

/**
 * Classe qui permet d'afficher un bouton avec une image
 * @author Sofianne Laurin
 */
public class ImageButton extends JButton {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Image image;

	/**
	 * Méthode qui ajuste la dimension des images
	 * @param dimension La dimension des images
	 */
    public ImageButton(Dimension dimension) {
        this.setSize(dimension);
    }

    /**
     * Méthode qui définit le texte et la dimension du bouton
     * @param text Le texte du bouton
     * @param dimension La dimension de l'image
     */
    public ImageButton(String text, Dimension dimension) {
        super(text);
        setSize(dimension);
    }

    /**
     * Méthode qui permet de lier une image à un bouton
     *
     * @author Caroline Houle
     */
    public void associerBoutonAvecImage() {

        if (getImage() != null) {
            //redimensionner l'image de la même grandeur que le bouton
            Image imgRedim = ImageUtil.scaleImage(getImage(), new SVector3d(getWidth(), getHeight()));
            //au cas où le fond de l’image serait transparent
            this.setOpaque(false);
            this.setContentAreaFilled(false);
            this.setBorderPainted(false);
            //associer l'image au bouton
            this.setText("");
            this.setIcon(new ImageIcon(imgRedim));
            //on se débarrasse des images intermédiaires
            imgRedim.flush();
        }
    }

    /**
     * Permet de lire le fichier qui contient l'image et d'associer l'image au bouton
     * @param imageName Le nom de l'image
     */
    public void readImage(String imageName) {
        setImage(ImageUtil.readImage(this.getClass(), imageName));
        associerBoutonAvecImage();
    }

    /**
     * Méthode qui retroune l'image
     * @return L'image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Méthode qui ajuste l'image
     * @param image L'image
     */
    public void setImage(Image image) {
        this.image = image;
    }
}
