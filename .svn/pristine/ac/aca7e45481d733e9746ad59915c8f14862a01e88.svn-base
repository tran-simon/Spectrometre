package view.dessinable.componentview;

import aaplication.AppUtil;
import inputs.*;
import math.MathUtil;
import math.SVector3d;
import physics.component.PPIUC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * Classe dessinable qui permet de dessiner une PPIUC (Plaque plane infinie uniformément chargée)
 *
 * @author Simon Tran
 */
public class PPIUCView extends ComponentView {
    public static final Color DEFAULT_COLOR = Color.green;
    public static final double DEFAULT_WIDTH = AppUtil.COMPONENT_MIN_WIDTH * 5;

    /**
     * Permet de créer une PPIUCView à partir d'une PPIUC
     *
     * @param ppiuc La PPIUC
     */
    public PPIUCView(PPIUC ppiuc) {
        super(ppiuc);
        this.setColor(DEFAULT_COLOR);
    }


    /**
     * Permet d'obtenir la PPIUC
     *
     * @return la PPIUC
     */
    public PPIUC getPPIUC() {
        return (PPIUC) getPhysicComponent();
    }

    @Override
    public Shape getShape() {
        Rectangle2D.Double shape = (Rectangle2D.Double) super.getShape();
        return MathUtil.setRectangleSize(shape, new SVector3d(DEFAULT_WIDTH, shape.getHeight()));
    }

    /**
     * Permet d'obtenir la forme après qu'elle ait reçu des transformations
     * La vue subira une rotation en fonction de l'orientationd e la normale de la PPIUC
     *
     * @param transform La matrice de transformations
     * @return La forme transformée
     */
    @Override
    public Shape getTransformedShape(AffineTransform transform) {
        Shape shape = super.getTransformedShape(transform);
        double rotation = getPPIUC().getNormal().angle();

        SVector3d anchor = new SVector3d(shape.getBounds().getLocation());

        AffineTransform rotationTransform = AffineTransform.getRotateInstance(rotation, anchor.getX(), anchor.getY());
        return rotationTransform.createTransformedShape(shape);
    }

    /**
     * Permet d'obtenir le JPanel contenant les entrées pour les options du composant physique
     *
     * @return Le JPanel
     */
    @Override
    public JPanel getOptionsPanel() {
        JPanel optionsPanel = super.getOptionsPanel();
        PPIUC ppiuc = getPPIUC();

        SpinnerNumberInput chargeDensityInput = new SpinnerNumberInput(ppiuc.getChargeDensity());
        chargeDensityInput.setInputName("Densité surfacique de charge (C/m^2) ");
        chargeDensityInput.setUseMin(false);
        chargeDensityInput.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                ppiuc.setChargeDensity(chargeDensityInput.getNumber());
                update();
            }
        });

        double rotation = MathUtil.toDegrees(getPPIUC().getNormal().angle());
        NumberInput normalInput = new NumberInput(rotation, "Orientation de la normale (degrés): ", 0, 360);
        normalInput.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                ppiuc.setNormal(SVector3d.vectorFromSizeAndAngle(1.0, Math.toRadians(normalInput.getNumber())));
                update();

            }
        });

        NumberInput inputHeight = new NumberInput(ppiuc.getHeight(), "Hauteur de la plaque (m): ", 0, AppUtil.COMPONENT_MAX_HEIGHT);
        inputHeight.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                ppiuc.setHeight(inputHeight.getNumber());
                update();
            }
        });

        BooleanInput infiniteInpute = new BooleanInput(getPPIUC().isInfinite(), "Infinie?: ");
        infiniteInpute.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                ppiuc.setInfinite(infiniteInpute.getBoolean());
                update();
            }
        });

        ImageButton helpButton = new ImageButton(new Dimension(20, 20));
        helpButton.readImage("help.png");
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(optionsPanel,
                        "Plaque plane qui crée un champ constant perpendiculaire à celle-ci.\n" +
                                "Afin de simplifier la simulation, il est possible de définir la plaque comme n'étant pas infinie avec la case à cocher \"infinie?\".\n" +
                                "Si la plaque n'est pas infinie, elle crée un champ seulement directement en haut ou en bas de la plaque\n" +
                                "Si la plaque est infinie, elle est quand même dessinée avec un rectangle limité, afin de simplifier l'affichage.");
            }
        });


        optionsPanel.add(chargeDensityInput);
        optionsPanel.add(new JSeparator());
        optionsPanel.add(normalInput);
        optionsPanel.add(new JSeparator());
        optionsPanel.add(inputHeight);
        optionsPanel.add(new JSeparator());
        optionsPanel.add(infiniteInpute);
        optionsPanel.add(new JSeparator());
        optionsPanel.add(helpButton);

        return optionsPanel;
    }
}
