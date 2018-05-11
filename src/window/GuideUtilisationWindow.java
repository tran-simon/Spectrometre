package window;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Classe qui permet d'afficher le guide d'utilisation
 * @author Sofianne
 */
public class GuideUtilisationWindow extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/**
	 * Commandes qui créent l'interface
	 */
	public GuideUtilisationWindow() {
		setTitle("Guide d'utilisation");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 950, 613);
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
		panConcepts.setFichierImage("GuideUtilisation.jpg");


		JButton btnQuitter = new JButton("Quitter");
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
		btnQuitter.setBounds(845, 551, 89, 23);
		contentPane.add(btnQuitter);
	}

}
