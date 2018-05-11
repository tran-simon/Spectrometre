package view.dessinable.componentview;

import aaplication.ImageUtil;
import physics.component.PhysicComponent;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Permet de dessiner une vue avec une image
 *
 * @author Sofianne Laurin
 */
public class ImageView extends ComponentView {

    private Image image;
    private double rotation = 0.0;

    /**
     * Constructeur
     * @param physicComponent Le composant physique
     * @param imageName Le nom de l'image
     */
    public ImageView(PhysicComponent physicComponent, String imageName) {

        super(physicComponent);
        setImage(ImageUtil.readImage(this.getClass(), imageName));
    }

    /* ------------------Méthodes "Override"------------------- */

    /**
     * Permet de dessiner la vue avec l'image
     *
     * @param g2d       Le Graphics2D
     * @param transform La matrice de transformations
     */
    @Override
    public void draw(Graphics2D g2d, AffineTransform transform) {
        super.draw(g2d, transform);
        Rectangle shape = getTransformedShape(transform).getBounds();
        if (image != null && shape.getWidth() != 0.0 && shape.getHeight() != 0.0) {
            Image imgRedim = getImage().getScaledInstance((int) shape.getWidth(), (int) shape.getHeight(), Image.SCALE_SMOOTH);
            AffineTransform imageTransform = new AffineTransform();
            imageTransform.translate(shape.getX(), shape.getY() );
            imageTransform.rotate(getRotation(), shape.getWidth() / 2, shape.getHeight() / 2);
//            imageTransform.scale(1, -1);
            g2d.drawImage(imgRedim, imageTransform, null);
        }
    }


    /* ------------------Getters & Setters------------------- */

    /**
     * Méthode qui retourne la rotation
     * @return La rotation
     */
    public double getRotation() {
        return rotation;
    }

    /**
     * Méthode qui ajuste la rotation
     * @param rotation La rotation
     */
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    /**
     * Méthode qui retourne l'image
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