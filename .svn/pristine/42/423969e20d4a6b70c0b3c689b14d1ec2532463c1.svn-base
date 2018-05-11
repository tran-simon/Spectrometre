package view.dessinable.componentview;

import aaplication.AppUtil;
import aaplication.ImageUtil;
import inputs.InputAdapter;
import inputs.NumberInput;
import math.MathUtil;
import math.SVector3d;
import physics.component.field.ElectricField;
import physics.component.field.Field;
import physics.component.field.MagneticField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

/**
 * Classe dessinable qui permet de dessiner une vue de champ (FieldView)
 *
 * @author Simon Tran
 */
public class FieldView extends ComponentView {
    public static final SVector3d ICONS_SIZE = new SVector3d(20, 20);

    public static final Color DEFAULT_COLOR_MAGNETIC = new Color(255, 192, 203, 100);
    public static final Color DEFAULT_COLOR_ELECTRIC = new Color(0, 0, 255, 100);

    private Image icons[] = new Image[3];

    /**
     * Permet de créer un FieldView à partir d'un champ
     *
     * @param field Le champ
     */
    public FieldView(Field field) {
        super(field);
        this.setColor(
                (field instanceof ElectricField)
                        ? DEFAULT_COLOR_ELECTRIC : DEFAULT_COLOR_MAGNETIC);
        readImages();
    }

    /**
     * Permet de lire les fichiers des images afin d'initialiser les icônes
     */
    public void readImages() {
        icons[0] = ImageUtil.scaleImage(ImageUtil.readImage(getClass(), "arrow.png"), ICONS_SIZE);
        icons[1] = ImageUtil.scaleImage(ImageUtil.readImage(getClass(), "arrowIn.png"), ICONS_SIZE);
        icons[2] = ImageUtil.scaleImage(ImageUtil.readImage(getClass(), "arrowOut.png"), ICONS_SIZE);
    }

    /**
     * Permet d'obtenir le panneau qui contient les entrées de la direction du champ
     *
     * @return Le panneau d'entrées
     */
    private JPanel getFieldDirectionInput() {

        if (getField().getType() == Field.FieldType.ELECTRIC) {
            double angle = MathUtil.toDegrees(getField().getFieldDirection().angle());
            if (angle < 0) {
                angle += 360;
            }
            NumberInput angleInput = new NumberInput(angle, "Orientation (degrés): ", 0.0, 360);
            angleInput.addInputListener(new InputAdapter() {
                @Override
                public void inputChanged(Object input) {
                    super.inputChanged(input);
                    getField().setFieldDirection(SVector3d.vectorFromSizeAndAngle(1.0, Math.toRadians(angleInput.getNumber())));
                    update();
                }
            });
            return angleInput;
        }
        else if (getField().getType() == Field.FieldType.MAGNETIC) {
            JPanel panelOrientationButtons = new JPanel();
            panelOrientationButtons.setLayout(new GridLayout(2, 2, 0, 0));

            ButtonGroup groupOrientationButtons = new ButtonGroup();

            JRadioButton rdbtnZPos = new JRadioButton("Z+");
            JPanel radioBtnPanel = new JPanel();
            panelOrientationButtons.add(radioBtnPanel);
            radioBtnPanel.add(rdbtnZPos);

            JRadioButton rdbtnZNeg = new JRadioButton("Z-");
            radioBtnPanel.add(rdbtnZNeg);

            groupOrientationButtons.add(rdbtnZPos);
            groupOrientationButtons.add(rdbtnZNeg);

            boolean directionZIsPositive = getField().getFieldDirection().getZ() >= 0;
            rdbtnZPos.setSelected(directionZIsPositive);
            rdbtnZNeg.setSelected(!directionZIsPositive);

            rdbtnZPos.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getField().setFieldDirection(new SVector3d(0, 0, 1.0));
                    update();
                }
            });

            rdbtnZNeg.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getField().setFieldDirection(new SVector3d(0, 0, -1.0));
                    update();
                }
            });

            return panelOrientationButtons;
        }
        return new JPanel();
    }


    /**
     * Permet d'obtenir le champ
     *
     * @return Le champ
     */
    public Field getField() {
        return (Field) getPhysicComponent();
    }


    @Override
    public void draw(Graphics2D g2d, AffineTransform transform) {
        super.draw(g2d, transform);


        Shape shape = getTransformedShape(transform);
        Rectangle bounds = getTransformedShape(transform).getBounds();

        int type = 0;
        if (getField().getType() == Field.FieldType.MAGNETIC) {
            if (getField().getFieldDirection().getZ() >= 0) {
                type = 2;
            }
            else {
                type = 1 ;
            }
        }
        Image icon = icons[type];

        if (icon != null) {
            for (int x = 0; x < Math.round(bounds.getWidth() / ICONS_SIZE.getX()); x++) {
                for (int y = 0; y < Math.round(bounds.getHeight() / ICONS_SIZE.getY()); y++) {
                    AffineTransform imageTransform = new AffineTransform();
                    imageTransform.translate(shape.getBounds().getX() + x * icon.getWidth(null), shape.getBounds().getY() + y * icon.getHeight(null));
                    if (getField().getType() == Field.FieldType.ELECTRIC) {
                        double rotation = getField().getFieldDirection().angle();
                        imageTransform.rotate(rotation, 10, 10);
                    }
                    g2d.drawImage(icon, imageTransform, null);
                }
            }
        }

    }


    /**
     * Permet d'obtenir le JPanel contenant les entrées pour les options du composant physique
     *
     * @return Le JPanel
     */
    @Override
    public JPanel getOptionsPanel() {

        JPanel optionsPanel = super.getOptionsPanel();
        String units;
        if (getPhysicComponent() instanceof MagneticField) {
            units = "T";
        }
        else {
            units = "N/C";
        }
        NumberInput intensityInput = new NumberInput(getField().getIntensity(), "Intensité: " + units, Field.DEFAULT_MIN_INTENSITY, Field.DEFAULT_MAX_INTENSITY);

        intensityInput.changeValue(getField().getIntensity());

        intensityInput.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                getField().setIntensity(intensityInput.getNumber());
                update();
            }

        });


        NumberInput widthInput = new NumberInput(getField().getWidth(), "Largeur (m): ", AppUtil.COMPONENT_MIN_WIDTH, AppUtil.COMPONENT_MAX_WIDTH);
        NumberInput heightInput = new NumberInput(getField().getHeight(), "Hauteur (m): ", AppUtil.COMPONENT_MIN_HEIGHT, AppUtil.COMPONENT_MAX_HEIGHT);

        widthInput.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                getField().setWidth(widthInput.getNumber());
                update();
            }
        });
        heightInput.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                getField().setHeight(heightInput.getNumber());
                update();
            }
        });


        optionsPanel.add(intensityInput);
        optionsPanel.add(new JSeparator());

        optionsPanel.add(widthInput);
        optionsPanel.add(new JSeparator());
        optionsPanel.add(heightInput);
        optionsPanel.add(new JSeparator());


        optionsPanel.add(getFieldDirectionInput());
        optionsPanel.add(new JSeparator());
        return optionsPanel;
    }

}
