package view.dessinable.componentview;

import inputs.ImageButton;
import inputs.InputAdapter;
import inputs.NumberInput;
import inputs.SpinnerNumberInput;
import math.MathUtil;
import math.SVector3d;
import physics.component.Cyclotron;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe dessinable qui permet d'afficher un cyclotron
 *
 * @author Sofianne Laurin
 */
public class CyclotronView extends ImageView {

    /**
     * Permet de créer un nouveau CyclotronView
     *
     * @param cyclotron Le composant physique
     */
    public CyclotronView(Cyclotron cyclotron) {
        super(cyclotron, "cyclotron.png");
        setColor(new Color(0, 0, 0, 0));

        setRotation(cyclotron.getDirection().angle());

//		FieldView electricFieldView = new FieldView( getCyclotron().getElectricField());
//		this.addChild(electricFieldView);
//		FieldView magneticFieldView1 = new FieldView(getCyclotron().getMagneticField1());
//		this.addChild(magneticFieldView1);
//		FieldView magneticFieldView2 = new FieldView(getCyclotron().getMagneticField2());
//		this.addChild(magneticFieldView2);
    }

    /**
     * Permet d'obtenir le cyclotron
     *
     * @return le cyclotron
     */
    public Cyclotron getCyclotron() {
        return (Cyclotron) getPhysicComponent();
    }

    @Override
    /**
     * Méthode qui met à jour les valeurs de la vue
     */
    public void update() {
        super.update();
        setRotation(getCyclotron().getDirection().angle());
    }
//	/**
//	 * Permet de créer la forme d'un cyclotron
//	 * @return La forme d'un cyclotron
//	 */
//	public Shape createShape() {
//		Rectangle2D.Double shape = new Rectangle2D.Double(getCyclotron().getPosition().getX(), getCyclotron().getPosition().getY(), getCyclotron().getSize().getX(), getCyclotron().getSize().getY());
//        return shape;
//	}

    /**
     * Permet d'obtenir le JPanel contenant les entrées pour les options du composant physique
     *
     * @return Le JPanel
     */
    @Override
    public JPanel getOptionsPanel() {

        JPanel optionsPanel = super.getOptionsPanel();

        Cyclotron cyclotron = getCyclotron();
        optionsPanel.add(new ParticleGeneratorView(cyclotron).getOptionsPanel());


        SpinnerNumberInput inputFinalSpeed = new SpinnerNumberInput(cyclotron.getFinalSpeed());
        inputFinalSpeed.setInputName("Vitesse de sortie (m/s): ");
        inputFinalSpeed.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                cyclotron.setFinalSpeed(inputFinalSpeed.getNumber());
                update();
            }
        });

        NumberInput inputOrientation = new NumberInput(MathUtil.toDegrees(cyclotron.getDirection().angle()), "Direction (degrés): ", 0.0, 360);


        inputOrientation.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                cyclotron.setDirection(SVector3d.vectorFromSizeAndAngle(1.0, Math.toRadians(inputOrientation.getNumber())));
                update();
            }
        });
        ImageButton helpButton = new ImageButton(new Dimension(20, 20));
        helpButton.readImage("help.png");
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(optionsPanel,
                        "Appareil qui génère des particules à une certaine période (nombre de secondes avant de générer une particule).\n" +
                                "Les particules sont accélérées pour qu'elles aient une certaine vitesse à la sortie du cyclotron"
                );
            }
        });
        optionsPanel.add(inputFinalSpeed);
        optionsPanel.add(new JSeparator());
        optionsPanel.add(inputOrientation);
        optionsPanel.add(new JSeparator());
        optionsPanel.add(helpButton);
        return optionsPanel;
    }
}
