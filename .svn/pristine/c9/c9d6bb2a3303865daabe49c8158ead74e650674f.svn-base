package view.dessinable;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Classe qui permet de dessiner une forme
 *
 * @author simon tran
 */
public class ShapeView extends DrawableView {
    private Shape shape;

    /**
     * Permet de construire la vue de forme
     *
     * @param shape La forme
     */
    public ShapeView(Shape shape) {
        setShape(shape);
    }




    /**
     * Permet de dessiner la vue.
     *
     * @param g2d       Le Graphics2D
     * @param transform La matrice de transformations
     */
    @Override
    public void draw(Graphics2D g2d, AffineTransform transform) {
        g2d.draw(getTransformedShape(transform));
        drawChildren(g2d, transform);
    }


    /**
     * Permet de définir la forme
     * @param shape La forme
     */
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    /**
     * Permet de créer la forme qui englobe la vue
     *
     * @return la forme
     */
    @Override
    public Shape getShape() {
        return shape;
    }

}
