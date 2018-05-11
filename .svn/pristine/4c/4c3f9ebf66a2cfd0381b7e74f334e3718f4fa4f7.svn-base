package window;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe qui permet de créer l'interface principale
 * @author Sofianne
 *
 */
public class AProposWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/**
	 * Commandes qui créent l'interface
	 */
	public AProposWindow() {
		setTitle("À propos");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 950, 619);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ImageAvecDefilement panConcepts = new ImageAvecDefilement();


		panConcepts.setBounds(0, 11, 941, 541);
		contentPane.add(panConcepts);

		//Pour fixer couleur du cadre
		panConcepts.setBackground(Color.CYAN);
		//Pour modifier la largeur du cadre 
		panConcepts.setLargeurCadre(10);
		//Pour charger l'image pre-fabriquee
		panConcepts.setFichierImage("A-propos.jpg");

		JButton btnQuitter = new JButton("Quitter");
		/**
		 * Ecouteur sur le bouton quitter
		 */
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//debut
				dispose();
				//fin
			}
		});
		btnQuitter.setBounds(845, 551, 89, 23);
		contentPane.add(btnQuitter);
	}

}
