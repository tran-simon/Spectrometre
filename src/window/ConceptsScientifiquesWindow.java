package window;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Classe qui permet de créer l'interface de la fenêtre des concepts scientifiques
 * @author Sofianne
 *
 */
public class ConceptsScientifiquesWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Commandes qui créent l'interface
	 */
	public ConceptsScientifiquesWindow() {
		setTitle("Concepts scientifiques");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1002, 627);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ImageAvecDefilement panConcepts = new ImageAvecDefilement();


		panConcepts.setBounds(35, 11, 941, 541);
		contentPane.add(panConcepts);

		//Pour fixer couleur du cadre
		panConcepts.setBackground(Color.CYAN);
		//Pour modifier la largeur du cadre 
		panConcepts.setLargeurCadre(10);
		//Pour charger l'image pre-fabriquee
		panConcepts.setFichierImage("ConceptsScientifiques.jpg");

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
		btnQuitter.setBounds(887, 566, 89, 23);
		contentPane.add(btnQuitter);
	}

}
