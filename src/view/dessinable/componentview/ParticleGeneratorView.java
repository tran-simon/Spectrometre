package view.dessinable.componentview;

import inputs.ImageButton;
import inputs.InputAdapter;
import inputs.SpinnerNumberInput;
import physics.component.ParticleGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

/**
 * Permet de dessiner un générateur de particules
 *
 * @author Simon Tran
 */
public class ParticleGeneratorView extends ImageView {

    /**
     * Construit la vue
     *
     * @param particleGenerator Le générateur de particule
     */
    public ParticleGeneratorView(ParticleGenerator particleGenerator) {
        super(particleGenerator, "particleGenerator.png");
        setColor(new Color(0, 0, 0, 0));
    }

    /**
     * Permet d'obtenir le générateur de particules
     *
     * @return Le générateur de particules
     */
    public ParticleGenerator getParticleGenerator() {
        return (ParticleGenerator) getPhysicComponent();
    }

    /**
     * Crée la forme qui sera dessinée
     *
     * @return La forme
     */
    @Override
    public Shape getShape() {
        return new Rectangle2D.Double(getPhysicComponent().getPosition().getX(), getPhysicComponent().getPosition().getY(), getParticleGenerator().getSize().getX(), getParticleGenerator().getSize().getY());
    }

    @Override
    public JPanel getOptionsPanel() {
        JPanel optionsPanel = super.getOptionsPanel();
        SpinnerNumberInput inputPeriod = new SpinnerNumberInput(getParticleGenerator().getPeriod());
        inputPeriod.setInputName("Période (secondes/particule générée): ");
        inputPeriod.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                getParticleGenerator().setPeriod(inputPeriod.getNumber());
                update();
            }
        });


        ImageButton helpButton = new ImageButton(new Dimension(20, 20));
        helpButton.readImage("help.png");
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(optionsPanel,
                        "Appareil qui génère des particules à une certaine période (nombre de secondes avant de générer une particule).\n"
                );
            }
        });

        optionsPanel.add(inputPeriod);
        optionsPanel.add(new JSeparator());
        optionsPanel.add(helpButton);
        return optionsPanel;
    }
}
