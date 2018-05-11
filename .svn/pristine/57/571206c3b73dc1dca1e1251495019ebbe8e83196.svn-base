package view;

import aaplication.AppUtil;
import inputs.InputAdapter;
import inputs.SliderInput;
import math.SVector3d;

import javax.swing.*;
import java.awt.*;

/**
 * Vue comportant les entrées qui permettent de contrôler la caméra
 *
 * @author simon Tran
 */
public class CameraControlsView extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel translationControlsPanel = new JPanel();
    private JPanel zoomControlsView = new JPanel();

    private SliderInput xInput ;
    private SliderInput yInput ;

    private SliderInput zoomInput ;


    private View view;

    /**
     * Construit le CameraControlsView
     * @param view La vue liée
     */
    public CameraControlsView(View view) {
        this.view = view;
        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        SVector3d initialTranslation = new SVector3d();
        double initialZoom = view.getScale().getX();
        xInput = new SliderInput(initialTranslation.getX(), "", -AppUtil.COMPONENT_MAX_WIDTH, AppUtil.COMPONENT_MAX_WIDTH);
        yInput = new SliderInput(initialTranslation.getY(), "", -AppUtil.COMPONENT_MAX_HEIGHT, AppUtil.COMPONENT_MAX_HEIGHT);

        zoomInput = new SliderInput( initialZoom, "", AppUtil.MIN_WORLD_SCALE, AppUtil.MAX_WORLD_SCALE);

        translationControlsPanel.setOpaque(false);
        translationControlsPanel.setLayout(new BorderLayout());
        yInput.setVertical(true);

        translationControlsPanel.add(xInput, BorderLayout.SOUTH);
        translationControlsPanel.add(yInput, BorderLayout.WEST);


        zoomControlsView.setOpaque(false);
        zoomControlsView.setLayout(new BorderLayout());

        zoomInput.setOpaque(false);

        zoomControlsView.add(zoomInput, BorderLayout.SOUTH);

        this.add(translationControlsPanel, BorderLayout.WEST);
        this.add(zoomControlsView, BorderLayout.EAST);
        initListeners();
    }

    /**
     * Permet de bouger la caméra horizontalement ou verticalement
     *
     * @param translationAmount La distance qu'il faut bouger la caméra
     * @param horizontal Vrai si la caméra doit bouger horizontalement
     */
    public void translate(int translationAmount, boolean horizontal) {
        SliderInput sliderInput = horizontal ? xInput : yInput;
        int value = sliderInput.toSliderValue(sliderInput.getNumber());
        sliderInput.changeValue(sliderInput.toRealValue(translationAmount + value));
    }

    /**
     * Permet d'approcher ou d'éloigner la caméra de la simulation
     * @param zoomAmount La distance qu'il faut bouger la caméra
     */
    public void zoom(int zoomAmount) {
        int value = zoomInput.toSliderValue(zoomInput.getNumber());
        zoomInput.changeValue(zoomInput.toRealValue(zoomAmount + value));
    }

    /**
     * Permet d'initialiser les écouteurs
     */
    private void initListeners(){
        xInput.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                view.setTranslation(new SVector3d(xInput.getNumber(), yInput.getNumber()));
                view.getViewController().updateView();
            }
        });
        yInput.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);

                view.setTranslation(new SVector3d(xInput.getNumber(), yInput.getNumber()));
                view.getViewController().updateView();
            }
        });

        zoomInput.addInputListener(new InputAdapter() {
            @Override
            public void inputChanged(Object input) {
                super.inputChanged(input);
                view.setScale(new SVector3d(zoomInput.getNumber(), zoomInput.getNumber()));
                view.getViewController().updateView();
            }
        });
    }



}
