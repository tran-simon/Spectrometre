package aaplication;

import math.SVector3d;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Classe contenant des méthodes statiques utiles pour manipuler des images
 *
 * @author Simon Tran
 */
public final class ImageUtil {

    /**
     * Permet d'obtenir une image à partir d'un nom d'image
     * @param theClass La classe
     * @param imageName Le nom de l'image
     * @return l'image
     */
    public static Image readImage(Class theClass, String imageName) {
        Image imgLue = null;
        URL urlImage = theClass.getClassLoader().getResource(imageName);
        if (urlImage == null) {
            System.err.println("Erreur pendant la lecture du fichier d'image:" + imageName);
            return null;
        }
        try {
            imgLue = ImageIO.read(urlImage);
        } catch (IOException e) {
            System.err.println("Erreur pendant la lecture du fichier d'image" );
            e.printStackTrace();
        }

        return imgLue;
    }


    /**
     * Permet de redimensionner une image
     * @param image L'image originale
     * @param size La taille voulue
     * @return L'image redimensionnée
     */
    public static Image scaleImage(Image image, SVector3d size) {
        if (image == null) {
            return null;
        }
        return image.getScaledInstance((int) size.getX(), (int) size.getY(), Image.SCALE_SMOOTH);

    }
}
