package view.dessinable.componentview;


import physics.PhysicsWorld;
import physics.component.Particle;
import physics.component.ParticleGenerator;
import physics.component.PhysicComponent;

import javax.swing.*;
import java.awt.*;

/**
 * Classe de vue représentant un monde
 * @author simon
 */
public class WorldView extends ComponentView {

    /**
     * Permet de créer un nouveau WorldView avec un monde physique
     *
     * @param panel le panneau qui contient la vue
     * @param physicsWorld Le monde physique
     */
    public WorldView(JPanel panel, PhysicsWorld physicsWorld) {
        super(physicsWorld);
        this.setPanel(panel);

        /* On ajoute les vues provenant des composants */
        for (PhysicComponent component : physicsWorld.getComponentList()) {
            this.addChild(ComponentView.generateView(component));
        }

        /* On ajoute les vues provenant des générateurs de particules */
        for (ParticleGenerator particleGenerator : physicsWorld.getParticleGeneratorsList()) {
            this.addChild(ComponentView.generateView(particleGenerator));
        }

        /* On ajoute les vues provenant des particules */
        for (Particle particle : physicsWorld.getParticleList()) {
            this.addChild(ComponentView.generateView(particle));
        }

    }



    /**
     * Permet d'obtenir la forme qui sera dessinée
     * Dans le cas d'un WorldView, on ne dessine que les enfants de la vue. La forme est donc nulle
     * @return La forme
     */
    @Override
    public Shape getShape() {
        return null;
    }

    /**
     * Permet d'obtenir le JPanel contenant les entrées pour les options du composant physique
     * @return  Le JPanel
     */
    @Override
    public JPanel getOptionsPanel() {
        return null;
    }


}
