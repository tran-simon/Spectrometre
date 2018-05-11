package window;

import physics.component.Spectrometre;
import view.PlanCartesienPanel;
import view.dessinable.PlanCartesien;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Classe qui permet d'afficher le graphique
 * @author Sofianne
 */
public class GraphiqueWindow extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private PlanCartesienPanel graphique;



	/**
	 * Commandes qui créent l'interface
	 * @param spectrometre Le spectrometre
	 */
	public GraphiqueWindow(Spectrometre spectrometre) {
		setTitle("Graphique");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 824, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblGraphique = new JLabel("Graphique de la vitesse en fonction du temps");
		lblGraphique.setBounds(96, 11, 616, 66);
		lblGraphique.setHorizontalAlignment(SwingConstants.CENTER);
		lblGraphique.setFont(new Font("Tahoma", Font.PLAIN, 30));
		contentPane.add(lblGraphique);
		

		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.setBounds(709, 527, 89, 23);
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
		contentPane.add(btnQuitter);
		

		graphique = new PlanCartesienPanel(spectrometre , 2e6);
		graphique.setBounds(105, 74, 598, 450);
		contentPane.add(graphique);
	}

	/**
	 * Méthode qui retourne le graphique
	 * @return Le graphique
	 */
	public PlanCartesien getGraph(){
		return graphique.getPlanCartesien();
	}
}
