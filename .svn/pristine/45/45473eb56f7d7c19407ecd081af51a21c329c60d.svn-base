package view.dessinable.componentview;

import aaplication.AppUtil;
import aaplication.ImageUtil;
import inputs.*;
import math.MathUtil;
import math.SVector3d;
import physics.component.TRIUC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * Classe qui permet de créer la vue de la TRIUC
 * @author e1651372
 *
 */
public class TRIUCView extends ComponentView {

    public static final Color DEFAULT_COLOR = Color.red;
    public static final double DEFAULT_WIDTH = AppUtil.COMPONENT_MIN_WIDTH * 25;
    public static final double DEFAULT_VIEW_DIAMETER = DEFAULT_WIDTH;
    public static final SVector3d DEFAULT_VIEW_SIZE = new SVector3d(DEFAULT_VIEW_DIAMETER, DEFAULT_VIEW_DIAMETER);

    private Image icons[] = new Image[2];

    /**
     * Permet de créer une TRIUCView à partir d'une TRIUC
     *
     * @param triuc La TRIUC
     */
    public TRIUCView(TRIUC triuc) {
        super(triuc);
        this.setColor(DEFAULT_COLOR);
        icons[0] = ImageUtil.readImage(getClass(), "arrowIn.png");
        icons[1] = ImageUtil.readImage(getClass(), "arrowOut.png");
    }

    /**
     * Permet d'obtenir la TRIUC
     *
     * @return la TRIUC
     */
    public TRIUC getTRIUC() {
        return (TRIUC) getPhysicComponent();
    }


    /**
     * Méthode qui retourne le champ choisi par l'utilisateur
     * @return Le champ choisi par l'utilisateur
     */
    private JPanel getFieldDirectionInput() {
        TRIUC triuc = getTRIUC();

        JRadioButton rdbtnZ0 = new JRadioButton("Z = 0");
        JRadioButton rdbtnZPos = new JRadioButton("Z+");
        JRadioButton rdbtnZNeg = new JRadioButton("Z-");

        double rotation = MathUtil.toDegrees(getTRIUC().getOrientation().angle());
        NumberInput orientationInput = new NumberInput(rotation, "Orientation de la tige (degrés): ", 0, 360);
        orientationInput.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                triuc.setOrientation(SVector3d.vectorFromSizeAndAngle(1.0, Math.toRadians(orientationInput.getNumber())));
                rdbtnZ0.setSelected(true);
                update();

            }
        });


        JPanel panelOrientationButtons = new JPanel();
        panelOrientationButtons.add(orientationInput);
        panelOrientationButtons.setLayout(new GridLayout(2, 2, 0, 0));

        ButtonGroup groupOrientationButtons = new ButtonGroup();

        JPanel radioBtnPanel = new JPanel();
        panelOrientationButtons.add(radioBtnPanel);
        radioBtnPanel.add(rdbtnZ0);
        radioBtnPanel.add(rdbtnZPos);

        radioBtnPanel.add(rdbtnZNeg);

        groupOrientationButtons.add(rdbtnZ0);
        groupOrientationButtons.add(rdbtnZPos);
        groupOrientationButtons.add(rdbtnZNeg);

        rdbtnZ0.setSelected(triuc.getOrientation().getZ() == 0);
        rdbtnZPos.setSelected(triuc.getOrientation().getZ() > 0);
        rdbtnZNeg.setSelected(triuc.getOrientation().getZ() < 0);

        rdbtnZPos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                triuc.setOrientation(new SVector3d(0, 0, 1.0));
                orientationInput.setEnabled(false);
                update();
            }
        });

        rdbtnZNeg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                triuc.setOrientation(new SVector3d(0, 0, -1.0));
                orientationInput.setEnabled(false);
                update();
            }
        });
        rdbtnZ0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                triuc.setOrientation(SVector3d.vectorFromSizeAndAngle(1.0, Math.toRadians(orientationInput.getNumber())));
                orientationInput.setEnabled(true);
                update();
            }
        });
        return panelOrientationButtons;
    }


    @Override
    /**
     * Méthode qui retourne la forme
     */
    public Shape getShape() {
        if (getTRIUC().getOrientation().getZ() == 0.0) {
            Rectangle2D.Double shape = (Rectangle2D.Double) super.getShape();
            return MathUtil.setRectangleSize(shape, new SVector3d(shape.getWidth(), DEFAULT_WIDTH));
        }
        else {
            if (Math.abs(getTRIUC().getOrientation().getZ()) == 1.0) {
                Ellipse2D.Double shape = new Ellipse2D.Double(getTRIUC().getPosition().getX() - DEFAULT_VIEW_DIAMETER / 2, getTRIUC().getPosition().getY() - DEFAULT_VIEW_DIAMETER / 2, DEFAULT_VIEW_SIZE.getX(), DEFAULT_VIEW_SIZE.getY());
                return shape;
            }
            else {
                return null;
            }
        }
    }

    @Override
    /**
     * Méthode qui dessine la TRIUC
     */
    public void draw(Graphics2D g2d, AffineTransform transform) {
        super.draw(g2d, transform);

        Rectangle shape = getTransformedShape(transform).getBounds();
        double triucZ = getTRIUC().getOrientation().getZ();
        if (Math.abs(triucZ) == 1.0) {
            Image image = icons[triucZ < 0 ? 0 : 1];
            if (image != null && shape.getWidth() != 0.0 && shape.getHeight() != 0.0) {
                Image imgRedim = image.getScaledInstance((int) shape.getWidth(), (int) shape.getHeight(), Image.SCALE_SMOOTH);
                AffineTransform imageTransform = new AffineTransform();
                imageTransform.translate(shape.getX(), shape.getY());
                g2d.drawImage(imgRedim, imageTransform, null);
            }

        }
    }

    /**
     * Permet d'obtenir la forme après qu'elle ait reçu des transformations
     * La vue subira une rotation en fonction de l'orientation de la normale de la TRIUC
     *
     * @param transform La matrice de transformations
     * @return La forme transformée
     */
    @Override
    public Shape getTransformedShape(AffineTransform transform) {
        Shape shape = super.getTransformedShape(transform);
        double rotation = getTRIUC().getOrientation().angle();

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
        TRIUC triuc = getTRIUC();

        SpinnerNumberInput chargeDensityInput = new SpinnerNumberInput(triuc.getChargeDensity());
        chargeDensityInput.setInputName("Densité linéique de charge (C/m) ");
        chargeDensityInput.setUseMin(false);
        chargeDensityInput.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                triuc.setChargeDensity(chargeDensityInput.getNumber());
                update();
            }
        });

        NumberInput inputWidth = new NumberInput(triuc.getWidth(), "Longueur de la tige (m): ", 0, AppUtil.COMPONENT_MAX_HEIGHT);
        inputWidth.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                triuc.setWidth(inputWidth.getNumber());
                update();
            }
        });

        BooleanInput infiniteInpute = new BooleanInput(getTRIUC().isInfinite(), "Infinie?: ");
        infiniteInpute.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                triuc.setInfinite(infiniteInpute.getBoolean());
                update();
            }
        });


        ImageButton helpButton = new ImageButton(new Dimension(20, 20));
        helpButton.readImage("help.png");
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(optionsPanel,
                        "Tige qui crée un champ électrique radial à celle-ci.\n" +
                                "Il est possible de définir l'orientation de la tige dans le plan de l'écran, en sortant de l'écran ou en rentrant dans l'écran\n" +
                                "Utilisez le bouton radio \"Z+\" pour une tige qui sort de l'écran\n" +
                                "Utilisez le bouton radio \"Z-\" pour une tige qui entre dans l'écran"
                );
            }
        });


        optionsPanel.add(chargeDensityInput);
        optionsPanel.add(new JSeparator());
        optionsPanel.add(getFieldDirectionInput());
        optionsPanel.add(new JSeparator());
        optionsPanel.add(inputWidth);
        optionsPanel.add(new JSeparator());
        optionsPanel.add(infiniteInpute);
        optionsPanel.add(new JSeparator());
        optionsPanel.add(helpButton);

        return optionsPanel;
    }

}
