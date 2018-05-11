package view.dessinable.componentview;

import physics.component.*;
import physics.component.field.Field;
import view.dessinable.DrawableView;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;


/**
 * Classe dessinable qui permet de dessiner un composant physique (PhysicComponent) avec une forme
 *
 * @author Simon Tran
 */
public abstract class ComponentView extends DrawableView {


    private PhysicComponent physicComponent;

    private boolean selected = false;

    @Override
    public Shape getShape() {
        return getPhysicComponent().getBounds();
    }

    /**
     * Permet de créer un nouveau Component View avec un composant physique
     *
     * @param physicComponent le composant physique
     */
    public ComponentView(PhysicComponent physicComponent) {
        this.setPhysicComponent(physicComponent);
    }


    /**
     * Permet de générer une vue de composant à partir d'un composant physique
     * @param physicComponent Le composant physique
     * @return La vue liée au composant
     */
    public static ComponentView generateView(PhysicComponent physicComponent) {
        if (physicComponent instanceof Field) {
            return new FieldView((Field) physicComponent);
        }
        else if (physicComponent instanceof Cyclotron) {
            return new CyclotronView((Cyclotron) physicComponent);
        }
        else if (physicComponent instanceof Particle) {
            return new ParticleView((Particle) physicComponent);
        }
        else if (physicComponent instanceof SelecteurDeVitesse) {
            return new SelecteurDeVitesseView(physicComponent);
        }
        else if (physicComponent instanceof PPIUC) {
            return new PPIUCView((PPIUC) physicComponent);
        }
        else if (physicComponent instanceof TRIUC) {
            return new TRIUCView((TRIUC) physicComponent);
        }
        else if (physicComponent instanceof ParticleGenerator) {
            return new ParticleGeneratorView((ParticleGenerator) physicComponent);
        }
        else if (physicComponent instanceof ChargedShell) {
            return new ChargedShellView((ChargedShell) physicComponent);
        }
        return null;
    }


    /**
     * Permet d'obtenir le JPanel contenant les entrées pour les options du composant physique
     *
     * @return Le JPanel
     */
    public JPanel getOptionsPanel() {
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.add(new JSeparator());
        return optionsPanel;
    }


    /**
     * Permet d'obtenir une sous-vue qui est liée à un composant
     *
     * @param component Le composant
     * @return La vue liée au composant
     */
    public ComponentView containsViewForCompoment(PhysicComponent component) {
        for (int i = 0; i < getViewList().size(); i++) {
            DrawableView view = getViewList().get(i);
            if (view instanceof ComponentView) {
                if (((ComponentView) view).getPhysicComponent().equals(component)) {
                    return (ComponentView) view;
                }
            }
        }
        return null;
    }

    /**
     * Permet de supprimer une sous-vue à partir de son composant physique
     *
     * @param component Le composant physique
     */
    public void removeChildForComponent(PhysicComponent component) {
        removeChild(containsViewForCompoment(component));
    }








    /* ------------------Méthodes "Override"------------------- */

    /**
     * Permet de dessiner la vue. La méthode dessine la forme de la vue, et ensuite les sous-vues de la vue.
     *
     * @param g2d       Le Graphics2D
     * @param transform La matrice de transformations
     */
    @Override
    public void draw(Graphics2D g2d, AffineTransform transform) {
        AffineTransform tempTransform = new AffineTransform(transform);
        Color oldColor = g2d.getColor();
        Stroke oldStroke = g2d.getStroke();

        g2d.setColor(getColor());


        Shape shape = getTransformedShape(tempTransform);
        if (shape != null) {
            if (isSelected()) {
                g2d.setColor(new Color(getColor().getRed(), getColor().getGreen(), getColor().getBlue(), getColor().getAlpha() / 2));
            }
            g2d.fill(shape);

            if (isSelected()) {
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(3));
                g2d.draw(shape);
            }
        }

        /* On restaure les valeurs du g2d telles qu'elles étaient */
        g2d.setStroke(oldStroke);
        g2d.setColor(oldColor);

        drawChildren(g2d, tempTransform);


    }




    /* -------------------Getters & Setters------------------- */


    /**
     * Retourne le composant physique
     *
     * @return le composant physique
     */
    public PhysicComponent getPhysicComponent() {
        return physicComponent;
    }

    /**
     * Définit le composant physique.
     *
     * @param physicComponent le nouveau composant physique
     */
    public void setPhysicComponent(PhysicComponent physicComponent) {
        this.physicComponent = physicComponent;
    }

    /**
     * Permet de savoir si la vue est sélectionnée
     * @return Si la vue est sélectionnée
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Permet de définir si la vue est sélectionnée
     * @param selected Si la vue est sélectionnée
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
