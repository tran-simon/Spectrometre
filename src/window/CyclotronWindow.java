package window;

import controller.SimulationViewController;
import inputs.InputAdapter;
import inputs.SimulationControls;
import inputs.SpinnerNumberInput;
import math.SVector3d;
import physics.ComplexCyclotronListener;
import physics.component.ComplexCyclotron;
import physics.component.Particle;
import view.CameraControlsView;
import view.SimulationView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Classe qui permet de créer l'interface de la fenêtre des paramètres avancés du cyclotron
 *
 * @author Sofianne
 */
public class CyclotronWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnQuitter;
	private SpinnerNumberInput inputChampMagnetique;
	private final EventListenerList OBJETS_ENREGISTRES = new EventListenerList();
	private SpinnerNumberInput inputDifferencePotentiel;
	private SpinnerNumberInput inputRayon;
	private SpinnerNumberInput inputDistance;
	private ComplexCyclotron cyclotron;
	private Particle particle = new Particle();
	private SimulationControls controls;
	private SimulationViewController controller;

	private ArrayList<SVector3d> graphCoordinates = new ArrayList<>();
	private double calculatedSpeed = -1.0;
	private GraphiqueWindow popupGraphique;
	private CameraControlsView cameraControlsView;

	/**
	 * Méthode qui initialise la simulation
	 * @param panel Le panel
	 */
	public void initSimulation(JPanel panel) {
		controller = new SimulationViewController();
		SimulationView simulationView = new SimulationView(controller);
		cyclotron = new ComplexCyclotron(particle);

		simulationView.setBackground(Color.white);


		controller.setPhysicsWorld(cyclotron);
		controller.setSimulationView(simulationView);
		panel.add(simulationView);

		cameraControlsView = new CameraControlsView(simulationView);
		simulationView.add(cameraControlsView, BorderLayout.SOUTH);

		/**
		 * Méthode qui ajoute les écoputeurs sur le cyclotron
		 */
		cyclotron.addCyclotronListener(new ComplexCyclotronListener() {
			@Override
			public void speedCalculated(double speed) {
				setCalculatedSpeed(speed);
				leverEventSpeedCalculated(getCalculatedSpeed());
				leverEventSimulating(false);
				controller.pause();

				popupGraphique.getGraph().setMax(new SVector3d(controller.getTime(), graphCoordinates.get(graphCoordinates.size() - 1).getY()));
				popupGraphique.getGraph().setSpeedValues(graphCoordinates);
				popupGraphique.getGraph().creerApproxCourbe();
				popupGraphique.repaint();

			}

			@Override
			public void step(double delta_t) {
				if (!cyclotron.getParticleList().isEmpty()) {

					Particle particle = cyclotron.getParticleList().get(0);

					SVector3d coordinate = new SVector3d(controller.getTime(), particle.getSpeedModulus());
					graphCoordinates.add(coordinate);
				}
			}

			@Override
			public void particleGenerated() {

			}
		});

	}


	/**
	 * Méthode qui calcule la fréquence du cyclotron
	 * @return La fréquence du cyclotron
	 */
	public double calculateCyclotronFrequency() {
		return cyclotron.calculateFrequency(particle.getCharge(), particle.getMass());
	}

	/**
	 * Commandes qui créent l'interface
	 * @param particle La particule
	 * @param popupGraphique Le graphique
	 */
	public CyclotronWindow(Particle particle, GraphiqueWindow popupGraphique) {
		this.popupGraphique = popupGraphique;


		this.particle = new Particle(particle);
		this.particle.setSpeed(new SVector3d());

		setTitle("Paramètres du cyclotron");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 807, 753);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);


		JLabel lblParamtresDuCyclotron = new JLabel("Paramètres du cyclotron");
		lblParamtresDuCyclotron.setHorizontalAlignment(SwingConstants.CENTER);
		lblParamtresDuCyclotron.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblParamtresDuCyclotron.setBounds(186, 11, 418, 43);
		contentPane.add(lblParamtresDuCyclotron);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new BorderLayout(0, 0));
		panel_1.setBounds(46, 70, 699, 367);
		contentPane.add(panel_1);
		initSimulation(panel_1);


		JPanel panel = new JPanel();
		panel.setBounds(0, 481, 791, 222);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblChampMagntique = new JLabel("Champ magnétique :");
		lblChampMagntique.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblChampMagntique.setBounds(10, 25, 165, 20);
		panel.add(lblChampMagntique);

		JLabel lblDiffrenceDePotentiel = new JLabel("Différence de potentiel: ");
		lblDiffrenceDePotentiel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDiffrenceDePotentiel.setBounds(10, 94, 177, 20);
		panel.add(lblDiffrenceDePotentiel);

		JLabel lblRayonDesDemicylindres = new JLabel("Rayon  des demi-cylindres:");
		lblRayonDesDemicylindres.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblRayonDesDemicylindres.setBounds(374, 25, 192, 20);
		panel.add(lblRayonDesDemicylindres);

		JLabel lblDistanceEntreLes = new JLabel("Distance entre les dés :");
		lblDistanceEntreLes.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDistanceEntreLes.setBounds(374, 94, 192, 20);
		panel.add(lblDistanceEntreLes);

		inputChampMagnetique = new SpinnerNumberInput(cyclotron.getMagneticFieldIntensity());
		/**
		 * Méthode qui ajoute un tourniquet pour les valeurs du champ magnétique
		 */
		inputChampMagnetique.addInputListener(new InputAdapter() {
			@Override
			public void inputChanged(Object input) {
				super.inputChanged(input);
				cyclotron.setMagneticFieldIntensity(inputChampMagnetique.getNumber());
				controller.rebuildWorld();
			}
		});
		inputChampMagnetique.setBounds(176, 15, 158, 41);
		panel.add(inputChampMagnetique);

		inputDifferencePotentiel = new SpinnerNumberInput(cyclotron.getVoltage());
		/**
		 * Méthode qui ajoute un tourniquet pour les valeurs du champ électrique
		 */
		inputDifferencePotentiel.addInputListener(new InputAdapter() {
			@Override
			public void inputChanged(Object input) {
				super.inputChanged(input);
				cyclotron.setVoltage(inputDifferencePotentiel.getNumber());
				controller.rebuildWorld();
			}
		});
		inputDifferencePotentiel.setBounds(176, 82, 158, 36);
		panel.add(inputDifferencePotentiel);

		inputRayon = new SpinnerNumberInput(cyclotron.getCylenderRadius(), "", 0.0, 100.0);
		/**
		 * Méthode qui ajoute un tourniquet pour les valeurs du rayon
		 */
		inputRayon.addInputListener(new InputAdapter() {
			@Override
			public void inputChanged(Object input) {
				super.inputChanged(input);
				cyclotron.setCylenderRadius(inputRayon.getNumber());
				controller.rebuildWorld();
			}
		});
		inputRayon.setBounds(572, 16, 158, 39);

		panel.add(inputRayon);

		inputDistance = new SpinnerNumberInput(cyclotron.getDistanceBetweenCylenders());
		/**
		 * Méthode qui ajoute un tourniquet pour les valeurs de distance
		 */
		inputDistance.addInputListener(new InputAdapter() {
			@Override
			public void inputChanged(Object input) {
				super.inputChanged(input);
				cyclotron.setDistanceBetweenCylenders(inputDistance.getNumber());
				controller.rebuildWorld();
			}
		});
		inputDistance.setBounds(572, 85, 158, 39);
		panel.add(inputDistance);
		btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(new ActionListener() {
			/**
			 * Ecouteur sur le bouton quitter
			 */
			public void actionPerformed(ActionEvent e) {
				//debut
				dispose();
				//fin
			}
		});
		btnQuitter.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnQuitter.setBounds(655, 175, 100, 36);
		panel.add(btnQuitter);

		JLabel lblT = new JLabel("T");
		lblT.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblT.setBounds(325, 25, 15, 20);
		panel.add(lblT);

		JLabel lblC = new JLabel("C");
		lblC.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblC.setBounds(325, 94, 15, 20);
		panel.add(lblC);

		JLabel lblM = new JLabel("m");
		lblM.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblM.setBounds(740, 25, 15, 20);
		panel.add(lblM);

		JLabel lblM1 = new JLabel("m");
		lblM1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblM1.setBounds(740, 94, 15, 20);
		panel.add(lblM1);


		controls = new SimulationControls(controller, new Dimension(50, 50), BoxLayout.X_AXIS);
		controls.getBtnReset().setBounds(251, 11, 77, 66);
		controls.getBtnStep().setBounds(169, 11, 77, 66);
		controls.getBtnPause().setBounds(87, 11, 77, 66);
		controls.getBtnPlay().setBounds(5, 11, 77, 66);
		controls.setSize(337, 100);
		controls.setLocation(0, 122);
		/**
		 * Méthode qui ajoute un écouteur sur le bouton play
		 */
		controls.getBtnPlay().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				leverEventSimulating(true);

				controller.removeAllParticles();
				cyclotron.changeParticle(particle, controller);


				inputChampMagnetique.setEnabled(false);
				inputDifferencePotentiel.setEnabled(false);
				inputDistance.setEnabled(false);
				inputRayon.setEnabled(false);
			}
		});
		/**
		 * Méthode qui ajoute un écouteur sur le bouton reset
		 */
		controls.getBtnReset().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graphCoordinates.clear();
				controller.removeAllParticles();
				cyclotron.changeParticle(particle, controller);
				inputChampMagnetique.setEnabled(true);
				inputDifferencePotentiel.setEnabled(true);
				inputDistance.setEnabled(true);
				inputRayon.setEnabled(true);
				leverEventSimulating(false);
			}
		});
		//        controls.setSize(500, 100);
		panel.add(controls);
		controls.setLayout(null);

		JButton btnGraphiqueDeLa = new JButton("Graphique de la vitesse");
		/**
		 * Ecouteur sur l'item Graphique de la vitesse
		 */
		btnGraphiqueDeLa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//debut
				if (popupGraphique.isVisible() == false) {
					popupGraphique.setVisible(true);
				}
				//fin
			}
		});
		btnGraphiqueDeLa.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnGraphiqueDeLa.setBounds(436, 175, 209, 36);
		panel.add(btnGraphiqueDeLa);


	}

	/**
	 * Méthode qu ajoute des écouteurs personnalisés sur le cyclotron
	 * @param parametresCyclotronListener Écouteur sur le cyclotron
	 */
	public void addParametresCyclotronListener(ParametresCyclotronListener parametresCyclotronListener) {
		//enregistre un autre objet qu'on devra aviser au moment de l'evenement
		OBJETS_ENREGISTRES.add(ParametresCyclotronListener.class, parametresCyclotronListener);
	}

	/**
	 * Un événement est levé lorsque la vitesse est calculée
	 * @param speed La vitesse
	 */
	private void leverEventSpeedCalculated(double speed) {
		for (ParametresCyclotronListener ecout : OBJETS_ENREGISTRES.getListeners(ParametresCyclotronListener.class)) {
			ecout.speedCalculated(speed);
		}
	}

	/**
	 * Un écouteur est levé lorsque la simulation commence
	 * @param simulating Si la simulation est commencée
	 */
	private void leverEventSimulating(boolean simulating) {

		for (ParametresCyclotronListener ecout : OBJETS_ENREGISTRES.getListeners(ParametresCyclotronListener.class)) {
			ecout.simulating(simulating);
		}
	}


	/**
	 * Méthode qu retourne la vitesse calculée
	 * @return La vitesse calculée
	 */
	public double getCalculatedSpeed() {
		return calculatedSpeed;
	}

	/**
	 * Méthode qu ajuste la vitesse calculée
	 * @param calculatedSpeed La vitesse calculée
	 */
	public void setCalculatedSpeed(double calculatedSpeed) {
		this.calculatedSpeed = calculatedSpeed;
	}
}
