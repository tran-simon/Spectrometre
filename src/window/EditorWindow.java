package window;

import aaplication.AppUtil;
import aaplication.FileHandler;
import controller.SimulationViewController;
import controller.ViewController;
import inputs.ImageButton;
import inputs.InputAdapter;
import inputs.SimulationControls;
import inputs.SpinnerNumberInput;
import math.MathUtil;
import math.SVector3d;
import physics.PhysicsWorld;
import physics.PhysicsWorldListener;
import physics.component.Particle;
import physics.component.PhysicComponent;
import view.CameraControlsView;
import view.ComponentTreeView;
import view.SimulationView;
import view.dessinable.DrawableView;
import view.dessinable.componentview.ComponentView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;

/**
 * Fenêtre de création
 *
 * @author Simon Tran
 */
public class EditorWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * L'écart entre deux panels en pixels.
     */
    public static final int GUI_GAP = 8;

    private FileHandler fileHandler = new FileHandler(this);

    /* Éléments de l'interface */
    private JPanel panelContent;
    private ComponentTreeView componentTreeView;
    private JPanel panelComponentOptions = new JPanel();
    private CameraControlsView cameraControlsView;
    private JPanel panelSouthEast = new JPanel();

    /* Étiquettes */
    private JLabel lblSelectedComponentName = new JLabel();
    private JLabel lblElectricFieldValue = new JLabel("Champ Électrique: 0.0");
    private JLabel lblMagneticFieldValue = new JLabel("Champ Magnétique: 0.0");
    private JLabel lblMousePosition = new JLabel("");
    private JLabel lblTime = new JLabel("Temps (s): 0.0");

    /* Entrées */
    private SpinnerNumberInput inputStepTime = new SpinnerNumberInput(ViewController.DEFAULT_STEP);

    /* Boutons */
    private SimulationControls simulationControls;
    private JButton btnShowFieldMap = new JButton("Afficher la carte des champs");
    private JButton btnDelete = new JButton("Supprimer");
    private JButton btnDuplicate = new JButton("Dupliquer");
    private JRadioButton rdbtnEuler = new JRadioButton("Euler");
    private JRadioButton rdbtnRK4 = new JRadioButton("RK4");


    private ComponentView selectedView;

    private SimulationViewController worldViewController;


    private boolean shiftHeld = false;
    private boolean ctrlHeld = false;

    /**
     * L'emplacement où l'utilisateur a commencé à déplacer le composant
     */
    private SVector3d mouseDragPoint = new SVector3d();
    private SVector3d mouseDragPointUI = new SVector3d();

    /**
     * Méthode qui crée la fenêtre.
     */
    public EditorWindow() {
        setTitle("Menu Création");
        /* Options de la fenêtre */
        this.setFocusable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1500, 900);
        panelContent = new JPanel();
        panelContent.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panelContent);
        initMenuBar();
        initSimulation();
        initEastPanel();
        initWestPanel();
        initListeners();


    }

    /**
     * Permet de créer une fenêtre d'édition avec une adresse pour un fichier qui contient un monde
     *
     * @param path L'adresse du monde
     */
    public EditorWindow(URI path) {
        this();
        PhysicsWorld physicsWorld = null;
        try {
            physicsWorld = FileHandler.openWorldAt(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (physicsWorld != null) {
            changePhysicsWorld(physicsWorld);
        }

    }

    /**
     * Permet de créer une fenêtre d'édition à partir d'un monde physique
     * @param physicsWorld Le monde physique
     */
    public EditorWindow(PhysicsWorld physicsWorld) {
        this();
        if (physicsWorld != null) {
            changePhysicsWorld(physicsWorld);
        }
    }

    /**
     * Événement lancé lorsque la souris est bougée. Il faut alors bougé le composant sélectionné dans la simulation
     *
     * @param point La position de la souris en coordonnées de la simulation
     */
    public void eventMouseMoved(SVector3d point) {
        PhysicsWorld physicsWorld = worldViewController.getPhysicsWorld();

        SVector3d electricField = physicsWorld.getElectricField(point, worldViewController.getTime());

        SVector3d magneticField = physicsWorld.getMagneticField(point, worldViewController.getTime());

        lblElectricFieldValue.setText("Champ Électrique: " + AppUtil.numberToString((electricField).modulus()) + "N/C, angle: " + AppUtil.numberToString(MathUtil.toDegrees(electricField.angle())));
        String magneticFieldDirection = ((magneticField.getZ() != 0.0) ? (magneticField.getZ() > 0 ? "+" : "-") : "--");

        lblMagneticFieldValue.setText("Champ Magnétique: " + AppUtil.numberToString(magneticField.modulus()) + "T, Z: " + magneticFieldDirection);
        lblMousePosition.setText("Position de la souris (m): " + AppUtil.numberToString(point.getX()) + " : " + AppUtil.numberToString(point.getY()));

    }

    /**
     * Événement lancé lorsque la souris est bougé avec le bouton gauche maintenu enfoncé
     *
     * @param point La position de la souris en coordonnées de la simulation
     */
    public void eventMouseDragged(SVector3d point) {
        SVector3d deplacement = point.substract(mouseDragPoint);
        getSelectedView().getPhysicComponent().moveBy(deplacement);
        mouseDragPoint = point;
        repaint();
    }


    /**
     * Événement lancé lorsque le bouton gauche de la souris est pressé
     *
     * @param point La position de la souris en coordonées de la simulation
     */
    public void eventMousePressed(SVector3d point) {
        requestFocus();
        deselectComponent();

        ArrayList<DrawableView> views = worldViewController.getWorldView().childrenThatContain(point, worldViewController.getView().getTransform());
        if (views.size() != 0) {
            for (int i = views.size() - 1; i >= 0; i--) {
                DrawableView view = views.get(i);
                if (view instanceof ComponentView) {
                    selectComponent((ComponentView) view);
                    break;
                }
            }
        }

        mouseDragPoint = point;
        repaint();

    }


    /**
     * Permet de sélectionner un composant dans la simulation à partir de sa vue
     *
     * @param componentView La vue sélectionné
     */
    public void selectComponent(ComponentView componentView) {
        worldViewController.pause();
        if (componentView != null) {
            lblSelectedComponentName.setText(componentView.getPhysicComponent().getName());
            PhysicComponent physicComponent = componentView.getPhysicComponent();
            physicComponent.setInteractive(false);
            componentView.setSelected(true);
        }
        else {
            lblSelectedComponentName.setText("");
        }

        setSelectedView(componentView);
        updateButtons();

    }

    /**
     * Permet de supprimer l'élément sélectionné
     */
    public void removeSelectedComponent() {
        if (getSelectedView() != null) {
            worldViewController.removeFromWorld(getSelectedView().getPhysicComponent());
        }
        deselectComponent();
        repaint();
    }

    /**
     * Permet de désélectionner le composant dans la simulation
     */
    public void deselectComponent() {
        if (getSelectedView() != null) {
            getSelectedView().getPhysicComponent().setInteractive(true);
            getSelectedView().setSelected(false);
        }
        componentTreeView.clearSelection();
        selectComponent(null);
        updateButtons();
    }

    /**
     * Permet d'ajouter un nouveau composant à la simulation à partir d'un nom de composant
     *
     * @param name Le nom du composant
     */
    public void addComponentFromName(String name) {
        addComponent(ComponentTreeView.componentNameToView(name));
    }

    /**
     * Permet d'ajouter un nouveau composant à la simulation à partir de sa vue
     *
     * @param view La vue
     */
    public void addComponent(ComponentView view) {
        deselectComponent();
        SimulationView simulationView = worldViewController.getSimulationView();
        worldViewController.addToWorld(view);
        SVector3d centerPosition = new SVector3d(simulationView.getWidth() / 2, simulationView.getHeight() / 2);
        centerPosition = simulationView.UIToSimulation(centerPosition);
        view.getPhysicComponent().setPosition(centerPosition);
        selectComponent(view);
        repaint();
    }

    /**
     * Permet de mettre à jour le panneau des options du composants en fonction du composant sélectionné
     *
     * @param selectedComponent La vue du composant sélectionné
     */
    public void updateComponentOptions(ComponentView selectedComponent) {
        panelComponentOptions.removeAll();
        if (selectedComponent != null) {
            JPanel optionPanel = selectedComponent.getOptionsPanel();
            if (optionPanel != null) {
                panelComponentOptions.add(optionPanel);
            }
        }
        repaint();
    }

    /**
     * Permet de mettre à jour les boutons de l'interface.
     */
    public void updateButtons() {
        if (getSelectedView() != null) {
            btnDuplicate.setEnabled(true);
            btnDelete.setEnabled(true);
        }
        else {
            btnDuplicate.setEnabled(false);
            btnDelete.setEnabled(false);
        }

        updateComponentOptions(getSelectedView());

    }


    /**
     * Permet de créer un nouveau fichier
     */
    public void newFile() {

        int shouldSave = JOptionPane.showConfirmDialog(this, "Sauvegarder?");
        if (shouldSave != JOptionPane.CANCEL_OPTION && shouldSave != JOptionPane.CLOSED_OPTION) {
            if (shouldSave == JOptionPane.YES_OPTION) {
                saveFile();
            }
            changePhysicsWorld(new PhysicsWorld());
        }
    }

    /**
     * Permet d'ouvrir un fichier existant.
     * La méthode demande à l'utilisateur de sauvegarder les changements du canevas déjà présent
     */
    public void openFile() {
        int shouldSave = JOptionPane.showConfirmDialog(this, "Sauvegarder?");
        if (shouldSave != JOptionPane.CANCEL_OPTION && shouldSave != JOptionPane.CLOSED_OPTION) {
            if (shouldSave == JOptionPane.YES_OPTION) {
                saveFile();
            }
            PhysicsWorld physicsWorld = fileHandler.openWorld();
            if (physicsWorld != null) {
                changePhysicsWorld(physicsWorld);
            }
        }
    }

    /**
     * Permet de renommer le fichier
     */
    public void renameFile() {
        if (fileHandler.getFilePath() != null) {
            String input = JOptionPane.showInputDialog(this, "Nouveau nom: ");
            if (input != null && !input.isEmpty()) {
                fileHandler.rename(input);
            }
        }
        else {
            saveFile();
        }
    }

    /**
     * Permet de sauvegarder le fichier
     */
    public void saveFile() {
        try {
            fileHandler.saveWorld(worldViewController.getPhysicsWorld());
        } catch (Exception e) {
            saveAtFile();
        }
    }

    /**
     * Permet de sauvegarder le fichier à un nouvel emplacement
     */
    public void saveAtFile() {
        fileHandler.saveWorldAtChooser(worldViewController.getPhysicsWorld());
    }

    /**
     * Permet de sauvegarder et deretourner au spectromètre de masse
     */
    public void backToSpectrometre() {
        saveFile();

        SpectrometreWindow spectrometreWindow = new SpectrometreWindow();
        spectrometreWindow.setVisible(true);

        dispose();
    }

    /**
     * Permet de changer le monde physique et de reconstruire la vue du monde
     *
     * @param physicsWorld Le nouveau monde physique
     */
    public void changePhysicsWorld(PhysicsWorld physicsWorld) {
        worldViewController.setPhysicsWorld(physicsWorld);
        worldViewController.setTime(0.0);
        physicsWorld.addPhysicsWorldListener(new PhysicsWorldListener() {
            @Override
            public void step(double delta_t) {
                lblTime.setText("Temps (s): " + AppUtil.numberToString(worldViewController.getTime()));
            }

            @Override
            public void particleGenerated() {
                worldViewController.rebuildWorld();
            }
        });
        rdbtnEuler.setSelected(worldViewController.getPhysicsWorld().getAlgorithm() == PhysicsWorld.Algorithm.EULER);
        rdbtnRK4.setSelected(worldViewController.getPhysicsWorld().getAlgorithm() == PhysicsWorld.Algorithm.RK4);
        worldViewController.rebuildWorld();
    }

    /* ------------------Méthodes d'initialisation------------------- */

    /**
     * Permet d'initialiser la simulation et de l'ajouter à une fenêtre d'édition
     */

    public void initSimulation() {
        panelContent.setLayout(new BorderLayout(0, 0));

        worldViewController = new SimulationViewController();

        SimulationView simulationView = new SimulationView(worldViewController);
        PhysicsWorld physicsWorld = new PhysicsWorld();//new Spectrometre();
        simulationView.setBackground(Color.white);

        panelContent.add(simulationView, BorderLayout.CENTER);


        cameraControlsView = new CameraControlsView(simulationView);

        simulationView.add(cameraControlsView, BorderLayout.SOUTH);

        worldViewController.setPhysicsWorld(physicsWorld);
        worldViewController.setSimulationView(simulationView);
        changePhysicsWorld(physicsWorld);

    }


    /**
     * Permet d'initialiser la barre de menu
     */
    public void initMenuBar() {
        MenuBar menuBar = new MenuBar();
        this.setMenuBar(menuBar);

        Menu menuFile = new Menu(AppUtil.FILE_MENU_FILE);
        menuBar.add(menuFile);

        MenuItem menuNew = new MenuItem(AppUtil.FILE_MENU_NEW);
        menuFile.add(menuNew);

        MenuItem menuOpen = new MenuItem(AppUtil.FILE_MENU_OPEN);
        menuFile.add(menuOpen);

        MenuItem menuRename = new MenuItem(AppUtil.FILE_MENU_RENAME);
        menuFile.add(menuRename);

        MenuItem menuSave = new MenuItem(AppUtil.FILE_MENU_SAVE);
        menuFile.add(menuSave);

        MenuItem menuSaveAt = new MenuItem(AppUtil.FILE_MENU_SAVEAT);
        menuFile.add(menuSaveAt);

        MenuItem menuBack = new MenuItem(AppUtil.FILE_MENU_BACK);
        menuFile.add(menuBack);


        Menu menuHelp = new Menu(AppUtil.FILE_MENU_HELP);
        menuBar.add(menuHelp);

        MenuItem menuGuide = new MenuItem("Guide d'utilisation");
        menuHelp.add(menuGuide);

        MenuItem menuHelpEditor = new MenuItem("Aide de la fenêtre d'édition");
        menuHelp.add(menuHelpEditor);

        MenuItem menuConcepts = new MenuItem("Concepts scientifiques");
        menuHelp.add(menuConcepts);

        MenuItem menuAPropos = new MenuItem("À Propos");
        menuHelp.add(menuAPropos);



        /* Écouteurs */

        menuNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newFile();
            }
        });

        menuOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
        menuRename.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renameFile();
            }

        });
        menuSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });

        menuSaveAt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAtFile();
            }
        });

        menuBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToSpectrometre();
            }
        });

        menuGuide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuideUtilisationWindow guideUtilisationWindow = new GuideUtilisationWindow();
                guideUtilisationWindow.setVisible(true);
            }
        });

        menuAPropos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AProposWindow aProposWindow = new AProposWindow();
                aProposWindow.setVisible(true);
            }
        });
        menuConcepts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConceptsScientifiquesWindow conceptsScientifiquesWindow = new ConceptsScientifiquesWindow();
                conceptsScientifiquesWindow.setVisible(true);
            }
        });

        menuHelpEditor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(getContentPane(),
                        "Ajoutez des composants au canevas en cliquant sur une entrée de la liste des composants (à gauche)\n" +
                                "Déplacez un composant avec un glisser-déposer dans le mode de création\n" +
                                "Déplacez la caméra en utilisant les curseurs qui se trouvent sur le canevas, ou utilisez la molette de la souris:\n" +
                                "   La molette de la souris seule permet de déplacer la caméra verticalement\n" +
                                "   La molette de la souris avec le bouton \"Shift\" enfoncé permet de déplacer la caméra horizontalement\n" +
                                "   La molette de la souris avec le bouton \"Contrôle\" enfoncé permet d'agrandir le canevas\n" +
                                "\nModifiez les options des composants en sélectionnant un composant à l'aide d'un clique gauche sur un composant qui se trouve sur le canevas\n" +
                                "Modifiez le pas de la simulation à l'aide du tourniquet approprié. Le pas définit la vitesse à laquelle la simulation se déroule. C'est la durée de temps qui se déroule entre deux nouvelles images.\n" +
                                "Démarrez ou mettez la simulation sur pause à l'aide de la barre d'espacement ou des boutons.\n" +
                                "\n" +
                                "NOTE: Si les raccourcis clavier ne semblent pas fonctionner, cliquez à l'intérieur du canevas afin de le mettre au premier plan"
                );
            }
        });


    }

    /**
     * Permet d'initialiser le panneau de droite (à l'est) qui contient les options de composants
     */
    public void initEastPanel() {
        JPanel panelEast = new JPanel();
        panelEast.setLayout(new BorderLayout(0, 0));
        panelEast.setPreferredSize(new Dimension(getWidth() / 5, getHeight()));

        lblSelectedComponentName.setHorizontalAlignment(SwingConstants.CENTER);
        panelEast.add(lblSelectedComponentName, BorderLayout.NORTH);
        panelEast.add(panelComponentOptions, BorderLayout.CENTER);
        panelComponentOptions.setLayout(new BoxLayout(panelComponentOptions, BoxLayout.Y_AXIS));

        lblElectricFieldValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblElectricFieldValue.setHorizontalAlignment(SwingConstants.LEFT);

        lblMagneticFieldValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblMousePosition.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblTime.setAlignmentX(Component.CENTER_ALIGNMENT);



        /* On ajoute les éléments à l'interface */
        panelContent.add(panelEast, BorderLayout.EAST);

        /* Boutons */
        panelSouthEast.setLayout(new BoxLayout(panelSouthEast, BoxLayout.Y_AXIS));

        ButtonGroup groupAlgoBtns = new ButtonGroup();

        JPanel radioBtnPanel = new JPanel();
        panelSouthEast.add(radioBtnPanel);
        radioBtnPanel.add(rdbtnEuler);

        radioBtnPanel.add(rdbtnRK4);
        rdbtnRK4.setSelected(true);

        groupAlgoBtns.add(rdbtnEuler);
        groupAlgoBtns.add(rdbtnRK4);


        inputStepTime.setInputName("Pas de la simulation (s): ");
        inputStepTime.setToolTipText("Le pas de la simulation en secondes");
        inputStepTime.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                worldViewController.setStep(inputStepTime.getNumber());
            }
        });
        panelSouthEast.add(inputStepTime);
        panelSouthEast.add(btnShowFieldMap);

        simulationControls = new SimulationControls(worldViewController, new Dimension(30, 30), BoxLayout.X_AXIS);
        simulationControls.getBtnReset().setVisible(false);
        panelSouthEast.add(simulationControls);

        /* Étiquettes */
        panelSouthEast.add(lblElectricFieldValue);
        panelSouthEast.add(lblMagneticFieldValue);
        panelSouthEast.add(lblMousePosition);
        panelSouthEast.add(lblTime);
        double simulationWidth = 1 / AppUtil.DEFAULT_WORLD_SCALE;
        String scale = "Échelle: " + simulationWidth + " m/pixels";
        JLabel lblScale = new JLabel(scale);
        lblScale.setAlignmentX(ComponentTreeView.CENTER_ALIGNMENT);
        panelSouthEast.add(lblScale);


        panelEast.add(panelSouthEast, BorderLayout.SOUTH);

    }

    /**
     * Permet d'initialiser le panneau de gauche (à l'ouest) qui contient la liste des composants et les options de positionnement
     */
    public void initWestPanel() {
        JPanel panelWest = new JPanel();
        panelWest.setLayout(new BorderLayout(0, GUI_GAP));
        panelContent.add(panelWest, BorderLayout.WEST);


        JLabel lblModeCration = new JLabel("Mode Création");
        panelContent.add(lblModeCration, BorderLayout.NORTH);
        lblModeCration.setFont(new Font("Stencil", Font.PLAIN, 20));

        /* On ajoute la liste des composants */
        componentTreeView = new ComponentTreeView();

        JScrollPane scrollPaneComponentList = new JScrollPane(componentTreeView, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panelWest.add(scrollPaneComponentList, BorderLayout.CENTER);


        /* On ajoute les options de positionnement */
        JPanel panelPositioningOptions = new JPanel();
        panelPositioningOptions.setLayout(new BoxLayout(panelPositioningOptions, BoxLayout.Y_AXIS));

        panelPositioningOptions.setBorder(new LineBorder(Color.black));

        JLabel lblPositioningOptions = new JLabel(AppUtil.POSITIONING_OPTIONS_LABEL);
        lblPositioningOptions.setAlignmentX(JLabel.LEFT);

        panelPositioningOptions.add(lblPositioningOptions);

        JPanel panelReordering = new JPanel();

        Dimension btnDimension = new Dimension(20, 20);
        ImageButton btnBringToFront = new ImageButton(btnDimension);
        ImageButton btnBringForward = new ImageButton(btnDimension);
        ImageButton btnSendBackward = new ImageButton(btnDimension);
        ImageButton btnSendToBack = new ImageButton(btnDimension);

        btnBringToFront.setToolTipText("Mettre au premier plan");
        btnBringForward.setToolTipText("Avancer");
        btnSendBackward.setToolTipText("Reculer");
        btnSendToBack.setToolTipText("Mettre en arrière plan");


        btnBringToFront.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                worldViewController.bringViewToFront(getSelectedView(), true);
            }
        });

        btnBringForward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                worldViewController.reorder(getSelectedView(), 1);
            }
        });

        btnSendBackward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                worldViewController.reorder(getSelectedView(), -1);
            }
        });
        btnSendToBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                worldViewController.bringViewToFront(getSelectedView(), false);
            }
        });


        panelReordering.add(btnBringToFront);
        panelReordering.add(btnBringForward);
        panelReordering.add(btnSendBackward);
        panelReordering.add(btnSendToBack);

        panelPositioningOptions.add(panelReordering);
        btnDuplicate.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnDuplicate.setEnabled(false);
        JPanel panelButtons = new JPanel();

        panelButtons.add(btnDuplicate, BorderLayout.NORTH);
        panelButtons.add(btnDelete, BorderLayout.SOUTH);


//        panelPositioningOptions.add(btnDuplicate);
        btnDelete.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDelete.setEnabled(false);
//        panelPositioningOptions.add(btnDelete);

        panelPositioningOptions.add(panelButtons);

        panelWest.add(panelPositioningOptions, BorderLayout.SOUTH);

        btnBringForward.readImage("arrange-bring-forward.png");
        btnBringToFront.readImage("arrange-bring-to-front.png");
        btnSendToBack.readImage("arrange-send-to-back.png");
        btnSendBackward.readImage("arrange-send-backward.png");


    }

    /**
     * Initialise les différents écouteurs pour les éléments de l'interface
     */
    public void initListeners() {
        SimulationView simulationView = worldViewController.getSimulationView();
        simulationView.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                super.mouseMoved(mouseEvent);
                eventMouseMoved(simulationView.UIToSimulation(new SVector3d(mouseEvent.getPoint())));
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (getSelectedView() != null) {
                    eventMouseDragged(simulationView.UIToSimulation(new SVector3d(e.getPoint())));
                }
                else {
                    SVector3d move = new SVector3d(e.getPoint()).substract(mouseDragPointUI);
                    cameraControlsView.translate((int) -move.getX(), true);
                    cameraControlsView.translate((int) move.getY(), false);
                    mouseDragPointUI = new SVector3d(e.getPoint());
                }
            }
        });
        simulationView.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (SwingUtilities.isLeftMouseButton(e)) {
                    SVector3d point = simulationView.UIToSimulation(new SVector3d(e.getPoint()));
                    eventMousePressed(point);
                    mouseDragPointUI = new SVector3d(e.getPoint());
                }
            }
        });

        simulationView.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int rotation = e.getWheelRotation() * AppUtil.MOUSE_WHEEL_SENSIBILITY;
                if (!shiftHeld) {
                    rotation *= -1;
                }

                if (ctrlHeld) {
                    cameraControlsView.zoom(rotation);
                }
                else {
                    cameraControlsView.translate(rotation, shiftHeld);
                }
            }
        });


        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    /* Touche d'échappement */
                    case 27:
                        deselectComponent();
                        break;

                    /* Touche de suppression */
                    case 127:
                        removeSelectedComponent();
                        break;


                    /* Espace */
                    case 32:
                        worldViewController.togglePause();
                        break;

                    /* Shift */
                    case 16:
                        shiftHeld = true;
                        break;

                    /* CTRL */
                    case 17:
                        ctrlHeld = true;
                        break;

                    /* O */
                    case 79:
                        if (ctrlHeld) {
                            openFile();
                        }
                        break;

                    /* S */
                    case 83:
                        if (ctrlHeld) {
                            if (shiftHeld) {
                                saveAtFile();
                            }
                            else {
                                saveFile();
                            }
                        }
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                switch (e.getKeyCode()) {
                    case 16:
                        shiftHeld = false;
                        break;
                    case 17:
                        ctrlHeld = false;
                        break;
                }
            }
        });

        componentTreeView.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) componentTreeView.getLastSelectedPathComponent();
                if (node != null && node.isLeaf() && node.getUserObject().toString() != null) {
                    String name = node.getUserObject().toString();
                    addComponentFromName(name);
                }
            }
        });

        btnShowFieldMap.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnShowFieldMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deselectComponent();

                FieldMapWindow fieldMapWindow = new FieldMapWindow(worldViewController.getPhysicsWorld());
                fieldMapWindow.setVisible(true);
                fieldMapWindow.updateView();
            }
        });


        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelectedComponent();
            }
        });

        btnDuplicate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getSelectedView() != null) {
                    if (getSelectedView().getPhysicComponent() instanceof Particle) {
                        addComponent(ComponentView.generateView(new Particle((Particle) getSelectedView().getPhysicComponent())));
                    }
                    else {
                        addComponentFromName(getSelectedView().getPhysicComponent().getName());
                    }
                }
            }
        });
        rdbtnEuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rdbtnEuler.isSelected()) {
                    worldViewController.getPhysicsWorld().setAlgorithm(PhysicsWorld.Algorithm.EULER);
                }
                else {
                    worldViewController.getPhysicsWorld().setAlgorithm(PhysicsWorld.Algorithm.RK4);
                }
            }
        });
        rdbtnRK4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!rdbtnRK4.isSelected()) {
                    worldViewController.getPhysicsWorld().setAlgorithm(PhysicsWorld.Algorithm.EULER);
                }
                else {
                    worldViewController.getPhysicsWorld().setAlgorithm(PhysicsWorld.Algorithm.RK4);
                }
            }
        });


    }





    /* -----------------Getters & Setters-------------------- */

    /**
     * Permet d'obtenir la vue sélectionné
     *
     * @return La vue sélectionné
     */
    public ComponentView getSelectedView() {
        return selectedView;
    }

    /**
     * Permet de définir la vue sélectionné
     *
     * @param selectedView La vue sélectionnée
     */
    public void setSelectedView(ComponentView selectedView) {
        this.selectedView = selectedView;
    }


}
