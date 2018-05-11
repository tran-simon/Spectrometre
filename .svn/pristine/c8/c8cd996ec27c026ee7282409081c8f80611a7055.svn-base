package window;

import aaplication.AppUtil;
import controller.FieldMapViewController;
import inputs.ImageButton;
import inputs.InputAdapter;
import inputs.SpinnerNumberInput;
import math.SVector3d;
import physics.PhysicsWorld;
import physics.component.field.Field;
import view.CameraControlsView;
import view.FieldMapView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * Fenêtre affichant un FieldMapView
 * Elle permet d'afficher une carte des intensités de champs électriques et/ou magnétiques
 *
 * @author Simon Tran
 */
public class FieldMapWindow extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static final double INPUT_INTENSITY_DEFAULT_VALUE = 1e-8;

    private JRadioButton radioBtnElectricField = new JRadioButton("Champ Électrique");
    private JRadioButton radioBtnMagneticField = new JRadioButton("Champ Magnétique");
    private JRadioButton radioBtnBothFields = new JRadioButton("Les deux");

    private JLabel lblMaxIntensity = new JLabel();

    private JButton btnRefresh = new JButton("Mettre à jour");

    private FieldMapViewController viewController;
    private final JPanel panelRadioButtons = new JPanel();
    private CameraControlsView cameraControlsView;
    private SpinnerNumberInput maxIntensityInput;
    private SVector3d mouseDragPoint = new SVector3d();

    /**
     * Permet de créer une nouvelle fenêtre FieldMapWindow
     *
     * @param physicsWorld Le monde physique
     */
    public FieldMapWindow(PhysicsWorld physicsWorld) {
        this.setBounds(100, 100, 792, 669);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
        this.setTitle("Carte des Champs");

        /* On ajoute les autres éléments de l'interface */
        JPanel panelControls = new JPanel();

        ImageButton helpButton = new ImageButton(new Dimension(20, 20));
        helpButton.readImage("help.png");
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(panelControls,
                        "La carte des champs." +
                                "Les couleurs bleuâtres représentent un champ plus intense. Les couleurs rouâtres représentent un champ moins intense.\n" +
                                "Si la carte n'affiche que de la couleur rouge, cela signifie qu'aucun champ n'est présent.\n" +
                                "Si la carte n'affiche que de la couleur bleue, c'est que toutes les intensités de champ sont plus grandes que le seuil d'intensité maximale.\n" +
                                "Le tourniquet d'intensité maximale permet de changer le seuil d'intensité afin d'observer des changements de couleurs plus ou moins prononcés.\n" +
                                "Utilisez les curseurs qui se trouvent sur la carte des champs afin de contrôler la position de la caméra. Il est possible de bouger la caméra verticalement, horizontalement ou d'agrandir"
                );
            }
        });
        panelControls.add(helpButton);


        panelControls.setLayout(new BoxLayout(panelControls, BoxLayout.Y_AXIS));
        ButtonGroup groupFields = new ButtonGroup();

        panelControls.add(panelRadioButtons);
        panelRadioButtons.setLayout(new BoxLayout(panelRadioButtons, BoxLayout.Y_AXIS));
        panelRadioButtons.add(radioBtnElectricField);

        radioBtnElectricField.setSelected(true);
        groupFields.add(radioBtnElectricField);
        groupFields.add(radioBtnMagneticField);
        groupFields.add(radioBtnBothFields);

        panelControls.add(btnRefresh);
        panelControls.add(lblMaxIntensity);
        lblMaxIntensity.setText("Intensité Maximale:");

        viewController = new FieldMapViewController(physicsWorld);
        FieldMapView fieldMapView = new FieldMapView(viewController);

        viewController.setView(fieldMapView);

        this.getContentPane().add(fieldMapView);
        this.getContentPane().add(panelControls);


        maxIntensityInput = new SpinnerNumberInput(INPUT_INTENSITY_DEFAULT_VALUE);
        panelControls.add(maxIntensityInput);

        fieldMapView.setMaxIntensity(maxIntensityInput.getNumber());

        fieldMapView.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mouseDragPoint = new SVector3d(e.getPoint());
            }
        });
        fieldMapView.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                SVector3d move = new SVector3d(e.getPoint()).substract(mouseDragPoint);
                cameraControlsView.translate((int) -move.getX(), true);
                cameraControlsView.translate((int) move.getY(), false);
                mouseDragPoint = new SVector3d(e.getPoint());
            }
        });




        cameraControlsView = new CameraControlsView(fieldMapView);
        fieldMapView.add(cameraControlsView, BorderLayout.SOUTH);

        initListeners();
    }

    /**
     * Permet de mettre à jour la vue. Cette méthode doit être appelée lorsque la fenêtre est mise visible avec 'setVisible()'
     */
    public void updateView() {
        viewController.updateView();
    }


    /**
     * Permet de mettre à jour le type de champ choisit par l'utilisateur
     */
    public void updateFieldType() {
        if (radioBtnElectricField.isSelected()) {
            viewController.setFieldType(Field.FieldType.ELECTRIC);
        }
        else if (radioBtnMagneticField.isSelected()) {
            viewController.setFieldType(Field.FieldType.MAGNETIC);
        }
        else {
            viewController.setFieldType(Field.FieldType.BOTH);
        }
        viewController.updateColors();
    }


    /* ------------------Méthodes d'initialisation------------------- */

    /**
     * Initialise les différents écouteurs pour les éléments de l'interface
     */
    public void initListeners() {

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewController.updateView();
            }
        });
        radioBtnElectricField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFieldType();
            }
        });
        panelRadioButtons.add(radioBtnMagneticField);
        radioBtnMagneticField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFieldType();

            }
        });
        panelRadioButtons.add(radioBtnBothFields);
        radioBtnBothFields.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFieldType();
            }
        });

        maxIntensityInput.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                double maxValue = (double) maxIntensityInput.getValue();
                String intensityText = "Intensité maximale (en bleu): " + AppUtil.numberToString(maxValue) + " ";
                Field.FieldType fieldType = viewController.getFieldMapView().getFieldType();
                if (fieldType.equals(Field.FieldType.ELECTRIC)) {
                    intensityText += "N/C";
                }
                else if (fieldType.equals(Field.FieldType.MAGNETIC)) {
                    intensityText += "T";
                }
                else {
                    intensityText = "Intensité Maximale (couleur la plus vive): ";
                }

                lblMaxIntensity.setText(intensityText);
                viewController.getFieldMapView().setMaxIntensity(maxValue);
                viewController.updateColors();
            }
        });
    }



    /* -------------------Getters & Setters------------------- */

}
