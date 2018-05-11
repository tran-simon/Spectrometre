package view.dessinable.componentview;

import aaplication.AppUtil;
import inputs.ImageButton;
import inputs.InputAdapter;
import inputs.NumberInput;
import inputs.SpinnerNumberInput;
import math.SVector3d;
import physics.component.ChargedShell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

/**
 * Permet de dessiner une coquille sphérique chargée
 *
 * @author Simon Tran
 */
public class ChargedShellView extends ComponentView {
    /**
     * Construit la vue
     * @param chargedShell La coquille sphérique
     */
    public ChargedShellView(ChargedShell chargedShell) {
        super(chargedShell);
        this.setColor(Color.pink);
    }

    /**
     * Permet d'obtenir la coquille chargée
     * @return La coquille chargée
     */
    public ChargedShell getChargedShell() {
        return (ChargedShell) getPhysicComponent();
    }

    /**
     * Permet d'obtenir la forme dessinée
     */
    @Override
    public Shape getShape() {
        SVector3d size = new SVector3d(1.0, 1.0).multiply(getChargedShell().getRadius() * 2.0);
        SVector3d position = getPhysicComponent().getPosition().substract(size.multiply(0.5));
        return new Ellipse2D.Double(position.getX(), position.getY(), size.getX(), size.getY());
    }


    @Override
    public JPanel getOptionsPanel() {

        JPanel optionsPanel = super.getOptionsPanel();


        SpinnerNumberInput chargeInput = new SpinnerNumberInput(getChargedShell().getChargeDensity(),"Densité surfacique de charge (C/m^2) ");
        chargeInput.setUseMin(false);
        chargeInput.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                getChargedShell().setChargeDensity(chargeInput.getNumber());
                update();
            }
        });

        NumberInput radiusInput = new NumberInput(getChargedShell().getRadius(), "Rayon (m): ", 0.0, AppUtil.COMPONENT_MAX_WIDTH);

        radiusInput.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                getChargedShell().setRadius(radiusInput.getNumber());
                update();
            }
        });

        ImageButton helpButton = new ImageButton(new Dimension(20, 20));
        helpButton.readImage("help.png");
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(optionsPanel,
                        "Coquille chargée. À l'extérieur, elle crée un champ qui serait équivalent à celui créé par une charge en son centre.\n" +
                                "À l'intérieur, le champ est nul."
                );
            }
        });

        optionsPanel.add(chargeInput);
        optionsPanel.add(new JSeparator());
        optionsPanel.add(radiusInput);
        optionsPanel.add(new JSeparator());
        return optionsPanel;
    }
}
