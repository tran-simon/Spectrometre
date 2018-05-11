package window;

import aaplication.AppUtil;
import aaplication.FileHandler;
import controller.SimulationViewController;
import controller.ViewController;
import inputs.InputAdapter;
import inputs.SimulationControls;
import inputs.SpinnerNumberInput;
import math.SVector3d;
import physics.SpectrometreListener;
import physics.component.Deflecteur;
import physics.component.Particle;
import physics.component.SelecteurDeVitesse;
import physics.component.Spectrometre;
import physics.component.field.ElectricField;
import physics.component.field.MagneticField;
import view.CameraControlsView;
import view.SimulationView;
import view.dessinable.componentview.ComponentView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.net.URISyntaxException;

/**
 * Classe qui permet de créer l'interface principale
 *
 * @author Sofianne
 */

public class SpectrometreWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private CyclotronWindow popupCyclotron;
	private GraphiqueWindow popupGraphique;
	private GuideUtilisationWindow popupGuideUtilisation;
	private ConceptsScientifiquesWindow popupConceptsScientifiques;
	private AProposWindow popupAPropos;
	private EditorWindow popupEditorWindow;

	private SimulationControls controls;
	private JButton btnParamtresAvancsDu;
	private JMenu mnAffichage;
	private JMenu mnEdition;
	private JMenu mnFichier;
	private JMenuItem mntmOuvrirModeCreation;
	private JMenuItem mntmQuitter;
	private JMenuItem mntmediterSpectrometre;
	private JMenuItem mntmParametresCyclotron;
	private JCheckBox chckbxPleinEcran;
	private JCheckBox mntmZooMode;
	private JMenu mnAide;
	private JMenuItem mntmGuideDutilisation;
	private JMenuItem mntmConceptsScientifiques;
	private JMenuItem mntmAPropos;
	private JPanel pnlBoutons;
	private JLabel lblControles;
	private JPanel pnlParametres;
	private JSeparator separator;
	private JSeparator separator_1;
	private JSeparator separator_2;
	private JLabel lblParticule;
	private JLabel lblCyclotron;
	private JLabel lblSelecteurDeVitesse;
	private JLabel lblDeflecteur;
	private JLabel lblMasse;
	private JLabel lblCharge;
	private JLabel lblVitesseDeSlection;
	private SpinnerNumberInput inputVitesseSelection;
	private JCheckBox chckbxModifierDirectementLa;
	private JLabel lblKg;
	private JLabel lblC;
	private JLabel lblMs;
	private JLabel lblMs1;
	private JLabel lblT;
	private JLabel lblC1;
	private JLabel lblChampMagntiqueSelecteur;
	private JLabel lblChampElectrique;
	private SpinnerNumberInput inputChampMagnetic;
	private SpinnerNumberInput inputChampElectric;
	private JLabel lblT1;
	private JPanel pnlVitesseSelection;
	private SpinnerNumberInput inputMass;
	private SpinnerNumberInput inputCharge;
	private SpinnerNumberInput inputVitesseParticule;
	private SpinnerNumberInput inputChampMagneticDeflecteur;
	private JLabel lblResFrequenceCyclotron;
	private JLabel lblResVitesseEntreeSelecteur;
	private JLabel lblResVitesseSelection;
	private JLabel lblResDiametreTrajectoire;
	private JLabel lblResMasseDetectee;
	private JMenuBar menuBar;
	private SimulationViewController worldViewController;
	private JPanel pnlSimulation;
	private JLabel lblCyclotronEtSpectrometre;
	private Spectrometre spectrometre;
	private SpinnerNumberInput inputStepTime = new SpinnerNumberInput(ViewController.DEFAULT_STEP);
	private JPanel panel_1;
	private JLabel lblPasDeLa;
	private JLabel lblBontonsDe;
	private JLabel lblContrles;
	private JLabel lblPasDeLa_1;
	private JLabel lblS;
	private CameraControlsView cameraControlsView;
    private SVector3d mouseDragPoint = new SVector3d();


	/**
	 * Méthode qui gère l'accès aux boutons
	 * @param enable Si l'accès est autorisé
	 */
	public void setEnableInputs(boolean enable) {

		if (!enable) {
			chckbxModifierDirectementLa.setEnabled(false);
			inputMass.setEnabled(false);

			inputCharge.setEnabled(false);
			inputVitesseParticule.setEnabled(false);
			btnParamtresAvancsDu.setEnabled(false);

			inputChampMagnetic.setEnabled(false);
			inputChampElectric.setEnabled(false);
			inputVitesseSelection.setEnabled(false);

			lblMs1.setEnabled(false);
			lblVitesseDeSlection.setEnabled(false);

			inputChampMagneticDeflecteur.setEnabled(false);
		}
		else {

			chckbxModifierDirectementLa.setEnabled(true);

			inputMass.setEnabled(true);

			inputCharge.setEnabled(true);
			inputVitesseParticule.setEnabled(true);
			btnParamtresAvancsDu.setEnabled(true);

			inputChampMagnetic.setEnabled(!chckbxModifierDirectementLa.isSelected());
			inputChampElectric.setEnabled(!chckbxModifierDirectementLa.isSelected());
			inputVitesseSelection.setEnabled(chckbxModifierDirectementLa.isSelected());

			inputChampMagneticDeflecteur.setEnabled(true);
		}
	}

	/**
	 * Commandes qui créent l'interface
	 */
	public SpectrometreWindow() {
		contentPane = new JPanel();
		contentPane.setLayout(null);

		pnlSimulation = new JPanel();
		pnlSimulation.setBounds(148, 88, 1264, 594);
		contentPane.add(pnlSimulation);
		pnlSimulation.setLayout(new BorderLayout(0, 0));


		Dimension btnDimension = new Dimension(100, 100);
		controls = new SimulationControls(worldViewController, btnDimension, BoxLayout.Y_AXIS);
		controls.getBtnReset().setBounds(10, 395, 105, 105);
		controls.getBtnStep().setBounds(10, 270, 105, 105);
		controls.getBtnPause().setBounds(10, 145, 105, 105);
		controls.getBtnPlay().setBounds(10, 20, 105, 105);
		controls.setLocation(13, 53);
		controls.setBounds(11, 121, 127, 522);
		contentPane.add(controls);
		controls.setLayout(null);

		controls.getBtnPlay().addActionListener(new ActionListener() {
			@Override
			/**
			 * Écouteur sur le bouton play
			 */
			public void actionPerformed(ActionEvent e) {
				if (spectrometre.getStage() == Spectrometre.Stage.NONE) {
					worldViewController.addToWorld(ComponentView.generateView(spectrometre.generateParticle()));
				}
				setEnableInputs(false);
			}
		});

		controls.getBtnStep().addActionListener(new ActionListener() {
			@Override
			/**
			 * Écouteur sur le bouton un pas
			 */
			public void actionPerformed(ActionEvent e) {
				if (spectrometre.getStage() == Spectrometre.Stage.NONE) {
					worldViewController.addToWorld(ComponentView.generateView(spectrometre.generateParticle()));
				}
				setEnableInputs(false);
			}
		});
		controls.getBtnReset().addActionListener(new ActionListener() {
			@Override
			/**
			 * Écouteur sur le bouton reset
			 */
			public void actionPerformed(ActionEvent e) {
				//debut
				worldViewController.removeAllParticles();
				setEnableInputs(true);
				spectrometre.setStage(Spectrometre.Stage.NONE);
				lblResFrequenceCyclotron.setText("--");
				lblResVitesseEntreeSelecteur.setText("--");
				lblResVitesseSelection.setText("--");
				lblResDiametreTrajectoire.setText("--");
				lblResMasseDetectee.setText("--");
				//fin
			}
		});

		initSimulation();

		setTitle("Cyclotron et spectromètre de masse");

		popupGraphique = new GraphiqueWindow(spectrometre);
		popupCyclotron = new CyclotronWindow(spectrometre.getParticle(), popupGraphique);
		//        popupCyclotron.setController(worldViewController);



		popupGuideUtilisation = new GuideUtilisationWindow();
		popupConceptsScientifiques = new ConceptsScientifiquesWindow();
		popupAPropos = new AProposWindow();
		popupEditorWindow = new EditorWindow();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(50, 50, 1800, 1000);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		pnlBoutons = new JPanel();
		pnlBoutons.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlBoutons.setBounds(11, 0, 127, 522);
		//		contentPane.add(pnlBoutons);
		pnlBoutons.setLayout(null);


		lblControles = new JLabel("Contrôles");
		lblControles.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblControles.setHorizontalAlignment(SwingConstants.CENTER);
		lblControles.setBounds(7, 16, 113, 21);
		pnlBoutons.add(lblControles);

		pnlParametres = new JPanel();
		pnlParametres.setBounds(10, 693, 1764, 246);
		pnlParametres.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlParametres.setForeground(Color.BLACK);
		contentPane.add(pnlParametres);
		pnlParametres.setLayout(null);

		separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(439, 15, 2, 215);
		pnlParametres.add(separator);

		separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setForeground(Color.BLACK);
		separator_1.setBounds(880, 15, 2, 215);
		pnlParametres.add(separator_1);

		separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setForeground(Color.BLACK);
		separator_2.setBounds(1321, 15, 2, 215);
		pnlParametres.add(separator_2);

		lblParticule = new JLabel("Particule");
		lblParticule.setHorizontalAlignment(SwingConstants.CENTER);
		lblParticule.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblParticule.setBounds(123, 15, 155, 21);
		pnlParametres.add(lblParticule);

		lblCyclotron = new JLabel("Cyclotron");
		lblCyclotron.setHorizontalAlignment(SwingConstants.CENTER);
		lblCyclotron.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblCyclotron.setBounds(569, 15, 155, 21);
		pnlParametres.add(lblCyclotron);

		lblSelecteurDeVitesse = new JLabel("Sélecteur de vitesse");
		lblSelecteurDeVitesse.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelecteurDeVitesse.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblSelecteurDeVitesse.setBounds(1016, 15, 155, 21);
		pnlParametres.add(lblSelecteurDeVitesse);

		lblDeflecteur = new JLabel("Déflecteur");
		lblDeflecteur.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeflecteur.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblDeflecteur.setBounds(1469, 15, 155, 21);
		pnlParametres.add(lblDeflecteur);

		lblMasse = new JLabel("Masse");
		lblMasse.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMasse.setBounds(61, 80, 57, 21);
		pnlParametres.add(lblMasse);

		lblCharge = new JLabel("Charge");
		lblCharge.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCharge.setBounds(61, 155, 57, 21);
		pnlParametres.add(lblCharge);

		lblChampMagntiqueSelecteur = new JLabel("Champ magnétique");
		lblChampMagntiqueSelecteur.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblChampMagntiqueSelecteur.setBounds(910, 44, 142, 21);
		pnlParametres.add(lblChampMagntiqueSelecteur);

		lblChampElectrique = new JLabel("Champ électrique");
		lblChampElectrique.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblChampElectrique.setBounds(910, 80, 142, 21);
		pnlParametres.add(lblChampElectrique);

		pnlVitesseSelection = new JPanel();
		pnlVitesseSelection.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlVitesseSelection.setBounds(910, 117, 383, 110);
		pnlParametres.add(pnlVitesseSelection);
		pnlVitesseSelection.setLayout(null);

		chckbxModifierDirectementLa = new JCheckBox("Modifier directement la vitesse de sélection");
		chckbxModifierDirectementLa.addChangeListener(new ChangeListener() {
			/**
			 * Ecouteur sur la case à cocher Modifier directement la vitesse de sélection
			 */
			public void stateChanged(ChangeEvent arg0) {
				//debut
				if (chckbxModifierDirectementLa.isSelected()) {
					lblVitesseDeSlection.setEnabled(true);
					inputVitesseSelection.setEnabled(true);
					lblMs1.setEnabled(true);
					lblChampMagntiqueSelecteur.setEnabled(false);
					lblChampElectrique.setEnabled(false);
					inputChampMagnetic.setEnabled(false);
					inputChampElectric.setEnabled(false);
					lblC1.setEnabled(false);
					lblT.setEnabled(false);

				}
				else {
					lblVitesseDeSlection.setEnabled(false);
					inputVitesseSelection.setEnabled(false);
					lblMs1.setEnabled(false);
					lblChampMagntiqueSelecteur.setEnabled(true);
					lblChampElectrique.setEnabled(true);
					inputChampMagnetic.setEnabled(true);
					inputChampElectric.setEnabled(true);
					lblC1.setEnabled(true);
					lblT.setEnabled(true);
				}
				//fin
			}
		});
		chckbxModifierDirectementLa.setFont(new Font("Tahoma", Font.PLAIN, 15));
		chckbxModifierDirectementLa.setBounds(6, 22, 311, 23);
		pnlVitesseSelection.add(chckbxModifierDirectementLa);

		lblVitesseDeSlection = new JLabel("Vitesse de sélection");
		lblVitesseDeSlection.setEnabled(false);
		lblVitesseDeSlection.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblVitesseDeSlection.setBounds(10, 65, 137, 21);
		pnlVitesseSelection.add(lblVitesseDeSlection);

		inputVitesseSelection = new SpinnerNumberInput(SelecteurDeVitesse.DEFAULT_SELECTION_SPEED, "", SelecteurDeVitesse.MIN_SELECTION_SPEED, SelecteurDeVitesse.MAX_SELECTION_SPEED);
		inputVitesseSelection.addInputListener(new InputAdapter() {
			@Override
			/**
			 * Écouteur sur le tourniquet de la vitesse de sélection
			 */
			public void inputChanged(Object input) {
				super.inputChanged(input);
				spectrometre.getSelecteurDeVitesse().setSelectionSpeed(inputVitesseSelection.getNumber());
			}
		});
		inputVitesseSelection.setEnabled(false);
		inputVitesseSelection.setBounds(144, 56, 188, 30);
		pnlVitesseSelection.add(inputVitesseSelection);

		lblMs1 = new JLabel("m/s");
		lblMs1.setEnabled(false);
		lblMs1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMs1.setBounds(339, 65, 26, 21);
		pnlVitesseSelection.add(lblMs1);

		inputMass = new SpinnerNumberInput(spectrometre.getParticle().getMass(), "", Particle.MASS_MIN, Particle.MASS_MAX);
		inputMass.addInputListener(new InputAdapter() {
			@Override
			/**
			 * Écouteur sur le tourniquet de la masse
			 */
			public void inputChanged(Object input) {
				super.inputChanged(input);
				spectrometre.getParticle().setMass(inputMass.getNumber());
			}
		});
		inputMass.setBounds(158, 74, 158, 39);
		pnlParametres.add(inputMass);

		inputCharge = new SpinnerNumberInput(spectrometre.getParticle().getCharge(), "", Particle.CHARGE_MIN, Particle.CHARGE_MAX);
		inputCharge.addInputListener(new InputAdapter() {
			@Override
			/**
			 * Écouteur sur le tourniquet de la charge
			 */
			public void inputChanged(Object input) {
				super.inputChanged(input);
				spectrometre.getParticle().setCharge(inputCharge.getNumber());
			}
		});
		inputCharge.setBounds(158, 155, 158, 39);
		pnlParametres.add(inputCharge);

		JLabel lblVitesseDeLa = new JLabel("Vitesse de la particule à la");
		lblVitesseDeLa.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblVitesseDeLa.setBounds(479, 80, 176, 21);
		pnlParametres.add(lblVitesseDeLa);

		JLabel lblSortieDuCyclotron = new JLabel("sortie du cyclotron");
		lblSortieDuCyclotron.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSortieDuCyclotron.setBounds(479, 108, 176, 21);
		pnlParametres.add(lblSortieDuCyclotron);

		inputVitesseParticule = new SpinnerNumberInput(spectrometre.getCyclotron().getFinalSpeed());
		inputVitesseParticule.addInputListener(new InputAdapter() {
			@Override
			/**
			 * Écouteur sur le tourniquet de la vitesse de la particule
			 */
			public void inputChanged(Object input) {
				super.inputChanged(input);
				spectrometre.getCyclotron().setFinalSpeed(inputVitesseParticule.getNumber());
			}
		});
		inputVitesseParticule.setBounds(655, 87, 158, 39);
		pnlParametres.add(inputVitesseParticule);

		btnParamtresAvancsDu = new JButton("Paramètres avancés du cyclotron");
		btnParamtresAvancsDu.addActionListener(new ActionListener() {
			/**
			 * Ecouteur sur le bouton Paramètres avancés du cyclotron
			 */
			public void actionPerformed(ActionEvent arg0) {
				//debut
				if (popupCyclotron.isVisible() == false) {
					popupCyclotron.setVisible(true);
				}
				//fin
			}
		});
		btnParamtresAvancsDu.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnParamtresAvancsDu.setBounds(524, 172, 261, 32);
		pnlParametres.add(btnParamtresAvancsDu);

		inputChampMagnetic = new SpinnerNumberInput(SelecteurDeVitesse.DEFAULT_MAGNETIC_FIELD_INTENSITY, "", MagneticField.DEFAULT_MIN_INTENSITY, MagneticField.DEFAULT_MAX_INTENSITY);
		inputChampMagnetic.addInputListener(new InputAdapter() {
			@Override
			/**
			 * Écouteur sur le tourniquet du champ magnétique du sélecteur de vitesse
			 */
			public void inputChanged(Object input) {
				super.inputChanged(input);
				spectrometre.getSelecteurDeVitesse().setMagneticFieldIntensity(inputChampMagnetic.getNumber());
			}
		});
		inputChampMagnetic.setBounds(1036, 47, 155, 32);
		pnlParametres.add(inputChampMagnetic);

		inputChampElectric = new SpinnerNumberInput(SelecteurDeVitesse.DEFAULT_ELECTRIC_FIELD_INTENSITY, "", ElectricField.DEFAULT_MIN_INTENSITY, ElectricField.DEFAULT_MAX_INTENSITY);
		inputChampElectric.addInputListener(new InputAdapter() {
			@Override
			/**
			 * Écouteur sur le tourniquet du champ électrique du sélecteur de vitesse
			 */
			public void inputChanged(Object input) {
				super.inputChanged(input);
				spectrometre.getSelecteurDeVitesse().setElectricFieldIntensity(inputChampElectric.getNumber());
			}
		});
		inputChampElectric.setBounds(1026, 77, 170, 28);
		pnlParametres.add(inputChampElectric);

		JLabel lblChampMagnetiqueDeflecteur = new JLabel("Champ magnétique");
		lblChampMagnetiqueDeflecteur.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblChampMagnetiqueDeflecteur.setBounds(1362, 83, 142, 21);
		pnlParametres.add(lblChampMagnetiqueDeflecteur);

		inputChampMagneticDeflecteur = new SpinnerNumberInput(spectrometre.getDeflecteur().getIntensity(), "", Deflecteur.DEFAULT_MIN_INTENSITY, Deflecteur.DEFAULT_MAX_INTENSITY);
		inputChampMagneticDeflecteur.addInputListener(new InputAdapter() {
			@Override
			/**
			 * Écouteur sur le tourniquet du chap magétique du déflecteur
			 */
			public void inputChanged(Object input) {
				super.inputChanged(input);
				spectrometre.getDeflecteur().setIntensity(inputChampMagneticDeflecteur.getNumber());
			}
		});
		inputChampMagneticDeflecteur.setBounds(1504, 74, 168, 39);
		pnlParametres.add(inputChampMagneticDeflecteur);

		lblKg = new JLabel("kg");
		lblKg.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblKg.setBounds(351, 92, 23, 21);
		pnlParametres.add(lblKg);

		lblC = new JLabel("C");
		lblC.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblC.setBounds(355, 155, 14, 21);
		pnlParametres.add(lblC);

		lblMs = new JLabel("m/s");
		lblMs.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMs.setBounds(823, 92, 26, 21);
		pnlParametres.add(lblMs);

		lblT = new JLabel("T");
		lblT.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblT.setBounds(1207, 44, 26, 21);
		pnlParametres.add(lblT);

		lblC1 = new JLabel("C");
		lblC1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblC1.setBounds(1207, 80, 26, 21);
		pnlParametres.add(lblC1);

		lblT1 = new JLabel("T");
		lblT1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblT1.setBounds(1682, 80, 15, 21);
		pnlParametres.add(lblT1);

		JLabel lblParametres = new JLabel("Paramètres");
		lblParametres.setBounds(10, 654, 132, 28);
		lblParametres.setHorizontalAlignment(SwingConstants.CENTER);
		lblParametres.setFont(new Font("Tahoma", Font.PLAIN, 23));
		contentPane.add(lblParametres);

		JPanel panel = new JPanel();
		panel.setBounds(1422, 11, 352, 522);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblRsultats = new JLabel("Résultats");
		lblRsultats.setBounds(121, 11, 110, 43);
		lblRsultats.setHorizontalAlignment(SwingConstants.CENTER);
		lblRsultats.setFont(new Font("Tahoma", Font.PLAIN, 22));
		panel.add(lblRsultats);

		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(Color.BLACK);
		separator_3.setBounds(10, 140, 269, 2);
		panel.add(separator_3);

		JSeparator separator_4 = new JSeparator();
		separator_4.setForeground(Color.BLACK);
		separator_4.setBounds(10, 329, 269, 2);
		panel.add(separator_4);

		JLabel lblFrquenceDuCyclotron = new JLabel("Fréquence du cyclotron:");
		lblFrquenceDuCyclotron.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblFrquenceDuCyclotron.setBounds(10, 88, 178, 21);
		panel.add(lblFrquenceDuCyclotron);

		JLabel lblVitesseDeLa_1 = new JLabel("Vitesse de la particule à");
		lblVitesseDeLa_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblVitesseDeLa_1.setBounds(10, 173, 178, 21);
		panel.add(lblVitesseDeLa_1);

		JLabel lblLentreDuSlecteur = new JLabel("l'entrée du sélecteur de vitesse:");
		lblLentreDuSlecteur.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblLentreDuSlecteur.setBounds(10, 205, 246, 21);
		panel.add(lblLentreDuSlecteur);

		JLabel lblVitesseDeSlection_1 = new JLabel("Vitesse de sélection:");
		lblVitesseDeSlection_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblVitesseDeSlection_1.setBounds(10, 277, 154, 21);
		panel.add(lblVitesseDeSlection_1);

		JLabel lblDiametre = new JLabel("Diamètre de la trajectoire");
		lblDiametre.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblDiametre.setBounds(10, 362, 195, 21);
		panel.add(lblDiametre);

		JLabel lblCirculaire = new JLabel("circulaire:");
		lblCirculaire.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblCirculaire.setBounds(10, 394, 154, 21);
		panel.add(lblCirculaire);

		JLabel blbMasseDetectee = new JLabel("Masse détectée:");
		blbMasseDetectee.setFont(new Font("Tahoma", Font.PLAIN, 17));
		blbMasseDetectee.setBounds(10, 466, 154, 21);
		panel.add(blbMasseDetectee);

		lblResFrequenceCyclotron = new JLabel("--");
		lblResFrequenceCyclotron.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblResFrequenceCyclotron.setHorizontalAlignment(SwingConstants.RIGHT);
		lblResFrequenceCyclotron.setHorizontalTextPosition(SwingConstants.LEADING);
		lblResFrequenceCyclotron.setBounds(232, 92, 110, 14);

		panel.add(lblResFrequenceCyclotron);

		lblResVitesseEntreeSelecteur = new JLabel("--");
		lblResVitesseEntreeSelecteur.setHorizontalTextPosition(SwingConstants.LEADING);
		lblResVitesseEntreeSelecteur.setHorizontalAlignment(SwingConstants.RIGHT);
		lblResVitesseEntreeSelecteur.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblResVitesseEntreeSelecteur.setBounds(232, 179, 110, 14);
		panel.add(lblResVitesseEntreeSelecteur);

		lblResVitesseSelection = new JLabel("--");
		lblResVitesseSelection.setHorizontalTextPosition(SwingConstants.LEADING);
		lblResVitesseSelection.setHorizontalAlignment(SwingConstants.RIGHT);
		lblResVitesseSelection.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblResVitesseSelection.setBounds(232, 283, 110, 14);
		panel.add(lblResVitesseSelection);

		lblResDiametreTrajectoire = new JLabel("--");
		lblResDiametreTrajectoire.setHorizontalTextPosition(SwingConstants.LEADING);
		lblResDiametreTrajectoire.setHorizontalAlignment(SwingConstants.RIGHT);
		lblResDiametreTrajectoire.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblResDiametreTrajectoire.setBounds(232, 368, 110, 14);
		panel.add(lblResDiametreTrajectoire);

		lblResMasseDetectee = new JLabel("--");
		lblResMasseDetectee.setHorizontalTextPosition(SwingConstants.LEADING);
		lblResMasseDetectee.setHorizontalAlignment(SwingConstants.RIGHT);
		lblResMasseDetectee.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblResMasseDetectee.setBounds(232, 472, 110, 14);
		panel.add(lblResMasseDetectee);

		lblCyclotronEtSpectrometre = new JLabel("Cyclotron et spectromètre de masse");
		lblCyclotronEtSpectrometre.setBounds(376, 27, 809, 50);
		lblCyclotronEtSpectrometre.setForeground(Color.RED);
		lblCyclotronEtSpectrometre.setFont(new Font("Tahoma", Font.PLAIN, 45));
		lblCyclotronEtSpectrometre.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblCyclotronEtSpectrometre);

		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(1422, 539, 352, 143);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		lblPasDeLa = new JLabel("Pas de la simulation");
		lblPasDeLa.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblPasDeLa.setBounds(76, 18, 200, 37);
		panel_1.add(lblPasDeLa);


		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnFichier = new JMenu("Fichier");
		mnFichier.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		menuBar.add(mnFichier);

		mntmOuvrirModeCreation = new JMenuItem("Ouvrir le mode de création");
		mntmOuvrirModeCreation.addActionListener(new ActionListener() {
			/**
			 * Écouteur sur l'item Ouvrir le mode de création
			 */
			public void actionPerformed(ActionEvent arg0) {
				//debut
				if (popupEditorWindow.isVisible() == false) {
					popupEditorWindow = new EditorWindow();
					popupEditorWindow.setVisible(true);
				}
				dispose();
				//Fin
			}
		});
		mntmOuvrirModeCreation.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		mnFichier.add(mntmOuvrirModeCreation);

		mntmQuitter = new JMenuItem("Quitter");
		mntmQuitter.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		/**
		 * Ecouteur sur l'item Quitter
		 */
		mntmQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFichier.add(mntmQuitter);

		mnEdition = new JMenu("Édition");
		mnEdition.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		menuBar.add(mnEdition);

		mntmediterSpectrometre = new JMenuItem("Ouvrir le spectromètre de masse dans le mode de création");
		mntmediterSpectrometre.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		mnEdition.add(mntmediterSpectrometre);

		mntmediterSpectrometre.addActionListener(new ActionListener() {
			@Override
			/**
			 * Écouteur sur le menu Éditer le spectromètre
			 */
			public void actionPerformed(ActionEvent e) {
				//debut
				worldViewController.removeAllParticles();
				worldViewController.addToWorld(ComponentView.generateView(spectrometre.generateParticle()));

				Spectrometre spectrometre = new Spectrometre();
				spectrometre.add(spectrometre.generateParticle());
				popupEditorWindow = new EditorWindow(spectrometre);
				popupEditorWindow.setVisible(true);
				dispose();
				//fin
			}
		});

		mntmParametresCyclotron = new JMenuItem("Paramètres du cyclotron");
		mntmParametresCyclotron.addActionListener(new ActionListener() {
			/**
			 * Ecouteur sur l'item Paramètres avancés du cyclotron
			 */
			public void actionPerformed(ActionEvent arg0) {
				//Debut
				if (popupCyclotron.isVisible() == false) {
					popupCyclotron.setVisible(true);
				}
				//Fin
			}
		});
		mntmParametresCyclotron.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		mnEdition.add(mntmParametresCyclotron);

		mnAffichage = new JMenu("Affichage");
		mnAffichage.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		mnAffichage.setHorizontalTextPosition(SwingConstants.CENTER);
		mnAffichage.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(mnAffichage);

		chckbxPleinEcran = new JCheckBox("Plein écran");
		chckbxPleinEcran.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		chckbxPleinEcran.addActionListener(new ActionListener() {
			@Override
			/**
			 * Écouteur sur la case à cocher Plein écran
			 */
			public void actionPerformed(ActionEvent e) {
				fullscreen(chckbxPleinEcran.isSelected());
			}
		});
		mnAffichage.add(chckbxPleinEcran);



		mntmZooMode = new JCheckBox("Mode Zoologique");
		mntmZooMode.addActionListener(new ActionListener() {
			@Override
			/**
			 * Écouteur sur la case à cocher Mode zoo
			 */
			public void actionPerformed(ActionEvent e) {
				controls.setZooMode(mntmZooMode.isSelected());

			}
		});

		mnAffichage.add(mntmZooMode);

		mnAide = new JMenu("Aide");
		mnAide.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		menuBar.add(mnAide);

		mntmGuideDutilisation = new JMenuItem("Guide d'utilisation");
		mntmGuideDutilisation.addActionListener(new ActionListener() {
			/**
			 * Ecouteur sur l'item Guide d'utilisation
			 */
			public void actionPerformed(ActionEvent e) {
				//debut
				if (popupGuideUtilisation.isVisible() == false) {
					popupGuideUtilisation.setVisible(true);
				}
				//fin
			}
		});
		mntmGuideDutilisation.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		mnAide.add(mntmGuideDutilisation);

		mntmConceptsScientifiques = new JMenuItem("Concepts scientifiques");
		mntmConceptsScientifiques.addActionListener(new ActionListener() {
			/**
			 * Ecouteur sur l'item Concepts Scientifiques
			 */
			public void actionPerformed(ActionEvent e) {
				//debut
				if (popupConceptsScientifiques.isVisible() == false) {
					popupConceptsScientifiques.setVisible(true);
				}
				//fin
			}
		});
		mntmConceptsScientifiques.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		mnAide.add(mntmConceptsScientifiques);

		mntmAPropos = new JMenuItem("À propos");
		mntmAPropos.addActionListener(new ActionListener() {
			/**
			 * Ecouteur sur l'item À propos
			 */
			public void actionPerformed(ActionEvent e) {
				//debut
				if (popupAPropos.isVisible() == false) {
					popupAPropos.setVisible(true);
				}
				//fin
			}
		});
		mntmAPropos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		mnAide.add(mntmAPropos);

		/**
		 * Événements personnalisés
		 */
		popupCyclotron.addParametresCyclotronListener(new ParametresCyclotronListener() {

			@Override
			/**
			 * Écouteur lorsque la vitesse est calculée
			 */
			public void speedCalculated(double speed) {
				inputVitesseParticule.changeValue(speed);

			}

			@Override
			/**
			 * Écouteur lorsque l'application est en simulation
			 */
			public void simulating(boolean simulating) {
				controls.setEnabled(!simulating);
				setEnableInputs(!simulating);
			}
		});
		inputStepTime.setSize(168, 40);
		inputStepTime.setLocation(158, 78);

		inputStepTime.setToolTipText("Le pas de la simulation en secondes");

		inputStepTime.addInputListener(new InputAdapter() {
			@Override
			/**
			 * Écouteur sur le tourniquet du step
			 */
			public void inputChanged(Object input) {
				super.inputChanged(input);
				worldViewController.setStep(inputStepTime.getNumber());
			}
		});

		panel_1.add(inputStepTime);

		lblPasDeLa_1 = new JLabel("Pas de la simulation");
		lblPasDeLa_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPasDeLa_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPasDeLa_1.setBounds(10, 73, 137, 50);
		panel_1.add(lblPasDeLa_1);

		lblS = new JLabel("s");
		lblS.setHorizontalAlignment(SwingConstants.CENTER);
		lblS.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblS.setBounds(318, 81, 24, 37);
		panel_1.add(lblS);

		lblBontonsDe = new JLabel("Boutons de");
		lblBontonsDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblBontonsDe.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblBontonsDe.setBounds(10, 27, 127, 50);
		contentPane.add(lblBontonsDe);

		lblContrles = new JLabel("contrôles");
		lblContrles.setHorizontalAlignment(SwingConstants.CENTER);
		lblContrles.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblContrles.setBounds(10, 70, 127, 50);
		contentPane.add(lblContrles);

	}

	/**
	 * Méthode qui retourne si l'application est en plein écran
	 * @param fullscreen Si c'est en plein écran
	 */
	public void fullscreen(boolean fullscreen) {
		if (fullscreen) {
			setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		else {
			setSize(1387, 866);
		}
	}

	/**
	 * Permet d'initialiser la simulation et de l'ajouter à une fenêtre d'édition
	 */

	public void initSimulation() {
		worldViewController = new SimulationViewController();
		SimulationView simulationView = new SimulationView(worldViewController);
		spectrometre = new Spectrometre();

		simulationView.setBackground(Color.white);


		worldViewController.setPhysicsWorld(spectrometre);
		worldViewController.setSimulationView(simulationView);
		pnlSimulation.add(simulationView);



		controls.setController(worldViewController);
		cameraControlsView = new CameraControlsView(simulationView);
		simulationView.add(cameraControlsView, BorderLayout.SOUTH);
		simulationView.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				mouseDragPoint = new SVector3d(e.getPoint());
			}
		});
		simulationView.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				SVector3d move = new SVector3d(e.getPoint()).substract(mouseDragPoint);
				cameraControlsView.translate((int) -move.getX(), true);
				cameraControlsView.translate((int) move.getY(), false);
				mouseDragPoint = new SVector3d(e.getPoint());
			}
		});


		/**
		 * Ajoute des écouteurs sur le spectromètre
		 */
		spectrometre.addSpectrometreListener(new SpectrometreListener() {
			@Override
			/**
			 * Un événement est levé lorsque la particule est dans le sélecteur de vitesse
			 */
			public void particleEnteredSelecteur(Particle particle) {
				lblResFrequenceCyclotron.setText(AppUtil.numberToString(popupCyclotron.calculateCyclotronFrequency()) + "Hz");

				lblResVitesseEntreeSelecteur.setText(AppUtil.numberToString(particle.getSpeedModulus()) + "m/s");
				lblResVitesseSelection.setText(AppUtil.numberToString(spectrometre.getSelecteurDeVitesse().getSelectionSpeed()) + "m/s");
			}

			@Override
			/**
			 * Un événement est levé lorsque la particule est dans le déflecteur
			 */
			public void particleEnteredDeflecteur(Particle particle) {

			}

			@Override
			/**
			 * Un événement est levé lorsque la particule est arrêtée
			 */
			public void particleStopped(Particle particle) {
				worldViewController.pause();

			}

			@Override
			/**
			 * Un événement est levé lorsque la masse est détectée
			 */
			public void massDetected(double diameter, double detectedMass) {
				lblResDiametreTrajectoire.setText(AppUtil.numberToString(diameter) + "m");
				lblResMasseDetectee.setText(AppUtil.numberToString(detectedMass) + "kg");
				worldViewController.pause();
			}

			@Override
			/**
			 * Un événement est levé lorsque la position change
			 */
			public void particlePositionChanged(Particle particle) {


			}



			@Override
			/**
			 * Un événement est levé lorsque le step change
			 */
			public void step(double delta_t) {
			}

			@Override
			/**
			 * Un événement est levé lorsqu'une particule est générée
			 */
			public void particleGenerated() {

			}
		});


	}





}