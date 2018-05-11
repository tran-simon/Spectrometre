package view.dessinable.componentview;

import aaplication.AppUtil;
import inputs.*;
import math.MathUtil;
import math.SVector3d;
import physics.component.Particle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

/**
 * Classe dessinable qui permet de dessiner une particule
 *
 * @author Simon Tran
 */
public class ParticleView extends ComponentView {
    public static final Color DEFAULT_COLOR = Color.magenta;
    public static final double DEFAULT_VIEW_DIAMETER = AppUtil.COMPONENT_MIN_WIDTH * 10;

    public static final SVector3d DEFAULT_VIEW_SIZE = new SVector3d(DEFAULT_VIEW_DIAMETER, DEFAULT_VIEW_DIAMETER);


    /**
     * Permet de créer un ParticleView à partir d'une particule
     *
     * @param particle la particule
     */
    public ParticleView(Particle particle) {
        super(particle);

        this.setColor(DEFAULT_COLOR);
    }

    /**
     * Permet de construire la forme qui sera dessinée.
     *
     * @return La forme
     */
    @Override
    public Shape getShape() {
        SVector3d position = getParticle().getPosition();

        return new Ellipse2D.Double(position.getX() - DEFAULT_VIEW_DIAMETER / 2, position.getY() - DEFAULT_VIEW_DIAMETER / 2, DEFAULT_VIEW_SIZE.getX(), DEFAULT_VIEW_SIZE.getY());
    }

    /**
     * Permet d'obtenir la particule associée à la ParticleView
     *
     * @return Une particule
     */
    public Particle getParticle() {
        return (Particle) getPhysicComponent();
    }

    /**
     * Permet d'obtenir le JPanel contenant les entrées pour les options du composant physique
     *
     * @return Le JPanel
     */
    @Override
    public JPanel getOptionsPanel() {
        /*
        Il nous faut une entrée pour:
            moveable
            mass
            charge
         */
        JPanel optionsPanel = super.getOptionsPanel();

        SpinnerNumberInput massInput = new SpinnerNumberInput(getParticle().getMass(), "Masse (Kg) ", Particle.MASS_MIN, Particle.MASS_MAX);
        massInput.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                getParticle().setMass(massInput.getNumber());
                update();

            }
        });
        optionsPanel.add(massInput);
        SpinnerNumberInput chargeInput = new SpinnerNumberInput(getParticle().getCharge(),"Charge (C) ", Particle.CHARGE_MIN, Particle.CHARGE_MAX);
        chargeInput.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                getParticle().setCharge(chargeInput.getNumber());
                update();
            }
        });

       BooleanInput moveableInput = new BooleanInput(getParticle().isMoveable(), "Mobile: ");
        moveableInput.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                getParticle().setMoveable(moveableInput.getBoolean());
                update();

            }
        });

        BooleanInput createsFieldInput = new BooleanInput(getParticle().getCreatesField(), "Crée un champ: ");
        createsFieldInput.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                getParticle().setCreatesField(createsFieldInput.getBoolean());
                update();
            }
        });


        SpinnerNumberInput speedModulusInput = new SpinnerNumberInput(getParticle().getSpeedModulus(), "Vitesse (m/s): ", 0.0, 1e6);
        NumberInput speedAngleInput = new NumberInput(MathUtil.toDegrees(getParticle().getSpeed().angle()), "Direction (degrés): ", 0.0, 360);
        speedAngleInput.setWithSlider(true);
        speedModulusInput.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                double angle = Math.toRadians(speedAngleInput.getNumber());
                getParticle().setSpeed(SVector3d.vectorFromSizeAndAngle(speedModulusInput.getNumber(), angle));
                update();
            }
        });

        speedAngleInput.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                getParticle().setSpeed(SVector3d.vectorFromSizeAndAngle(speedModulusInput.getNumber(), Math.toRadians(speedAngleInput.getNumber())));
                update();
            }
        });


        ImageButton helpButton = new ImageButton(new Dimension(20, 20));
        helpButton.readImage("help.png");
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(optionsPanel,
                        "Particule chargée.\n" +
                                "Le bouton radio \"mobile\" défini si la particule doit bouger.\n" +
                                "Le bouton radio \"Crée un champ\" défini si la particule doit créer un champ"
                );
            }
        });

        optionsPanel.add(massInput);
        optionsPanel.add(new JSeparator());
        optionsPanel.add(chargeInput);
        optionsPanel.add(new JSeparator());
        optionsPanel.add(moveableInput);
        optionsPanel.add(new JSeparator());
        optionsPanel.add(createsFieldInput);
        optionsPanel.add(new JSeparator());
        optionsPanel.add(speedModulusInput);
        optionsPanel.add(new JSeparator());
        optionsPanel.add(speedAngleInput);
        optionsPanel.add(new JSeparator());
        optionsPanel.add(helpButton);
        return optionsPanel;
    }
}
