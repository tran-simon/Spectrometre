package inputs;

import controller.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe qui permet de créer un composant contenant les boutons d'action
 * @author Sofianne
 *
 */
public class SimulationControls extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ImageButton btnPlay;
    private ImageButton btnPause;
    private ImageButton btnStep;
    private ImageButton btnReset;

    private ViewController controller;

    private Dimension dimension;
    /**
     * Constructeur du SimulationControls
     *
     * @param buttonsDimension La dimension des boutons
     * @param layoutAxis       L'axe dans lequel les boutons seront affichés. Utiliser BoxLayout.X_AXIS pour avoir des boutons horizontaux
     */
    public SimulationControls(ViewController controller, Dimension buttonsDimension, int layoutAxis) {
        setDimension(buttonsDimension);
        setController(controller);
        this.setLayout(new BoxLayout(this, layoutAxis));
        btnPlay = new ImageButton(buttonsDimension);
        btnPause = new ImageButton(buttonsDimension);
        btnStep = new ImageButton(buttonsDimension);
        btnReset = new ImageButton(buttonsDimension);

        this.add(btnPlay);
        this.add(btnPause);
        this.add(btnStep);
        this.add(btnReset);

        btnPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnStep.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnReset.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPause.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPlay.setAlignmentY(Component.CENTER_ALIGNMENT);
        btnStep.setAlignmentY(Component.CENTER_ALIGNMENT);
        btnReset.setAlignmentY(Component.CENTER_ALIGNMENT);
        btnPause.setAlignmentY(Component.CENTER_ALIGNMENT);

        readDefaultImages();

        btnPlay.setToolTipText("Démarrer");
        btnPause.setToolTipText("Mettre sur pause");
        btnStep.setToolTipText("Avancer d'un pas");
        btnReset.setToolTipText("Recommencer");


        btnPlay.setBorder(BorderFactory.createRaisedBevelBorder());
        btnPause.setBorder(BorderFactory.createRaisedBevelBorder());
        btnStep.setBorder(BorderFactory.createRaisedBevelBorder());
        btnReset.setBorder(BorderFactory.createRaisedBevelBorder());

        initListeners();
    }

    /**
     * Méthode qui initialise les écouteurs des boutons
     */
    public void initListeners() {
        btnPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getController().start();
            }
        });
        btnPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getController().pause();
            }
        });

        btnStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!getController().isPaused()) {
                    getController().pause();
                }
                else {
                    getController().step();
                }
            }
        });
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getController().pause();
            }
        });
    }

    /**
     * Méthode qui crée un mode zoologique qui change les boutons
     * @param zooMode Si en mode zoo
     */
    public void setZooMode(boolean zooMode) {
        btnPlay.setSize(getDimension());
        btnPause.setSize(getDimension());
        btnStep.setSize(getDimension());
        btnReset.setSize(getDimension());
        if (zooMode) {
            btnPlay.readImage("boutonJouerZOO.png");
            btnPause.readImage("boutonPauseZOO.png");
            btnStep.readImage("boutonUnPasZOO.png");
            btnReset.readImage("boutonRecommencerZOO.png");
        }
        else{
            readDefaultImages();
        }
        repaint();
    }
    public void readDefaultImages(){
        btnPlay.readImage("BoutonPlay.png");
        btnPause.readImage("BoutonPause.png");
        btnStep.readImage("BoutonPasAPas.png");
        btnReset.readImage("BoutonRecomencer.png");
    }

    /**
     * Méthode qui retourne le controleur de la simulation
     * @return Le controleur de la simulation
     */
    public ViewController getController() {
        return controller;
    }

    /**
     * Méthode qui ajuste le controleur de la simulation 
     * @param controller Le controleur de la simulation
     */
    public void setController(ViewController controller) {
        this.controller = controller;
    }


    /**
     * Méthode qui retourne le bouton play
     * @return le bouton play
     */
    public ImageButton getBtnPlay() {
        return btnPlay;
    }


    /**
     * Méthode qui retourne le bouton pause
     * @return le bouton pause
     */
    public ImageButton getBtnPause() {
        return btnPause;
    }

    /**
     * Méthode qui retourne le bouton pas à pas
     * @return le bouton pas à pas
     */
    public ImageButton getBtnStep() {
        return btnStep;
    }

    /**
     * Méthode qui retourne le bouton reset
     * @return le bouton reset
     */
    public ImageButton getBtnReset() {
        return btnReset;
    }

    /**
     * Méthode qui retourne les dimensions
     * @return les dimensions
     */
    public Dimension getDimension() {
        return dimension;
    }

    /**
     * Méthode qui ajuste les dimensions
     * @param dimension les dimensions
     */
    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    @Override
    /**
     * Méthode qui ajuste l'accès aux boutons
     */
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        btnPlay.setEnabled(enabled);
        btnPause.setEnabled(enabled);
        btnStep.setEnabled(enabled);
        btnReset.setEnabled(enabled);
    }
}
