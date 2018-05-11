package view;

import physics.component.*;
import physics.component.field.ElectricField;
import physics.component.field.MagneticField;
import view.dessinable.componentview.ComponentView;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.util.ArrayList;

/**
 * Permet d'afficher une liste de composants sous la forme d'un arbre.
 * La liste des composants permet à l'utilisateur de sélectionner un composant à ajouter à la simulation
 *
 * @author Simon Tran
 */
public class ComponentTreeView extends JTree {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
    private ComponentView selectedView;


    /**
     * Permet de construire le ComponentTreeView
     */
    public ComponentTreeView() {
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        initializeComponentList();


        this.setModel(new DefaultTreeModel(root));
        this.setRootVisible(false);
        expand();


    }

    /**
     * Permet d'initialiser la liste des composants. La méthode ajoute une feuille au JTree pour chaque composant
     */
    private void initializeComponentList() {
        ArrayList<String> componentNames = new ArrayList<>();
        componentNames.add(ElectricField.name);//new ElectricField().getName());
        componentNames.add(MagneticField.name);
        addCategory("Champs", componentNames);
        componentNames.clear();

        componentNames.add(ChargedShell.name);
        componentNames.add(PPIUC.name);
        componentNames.add(TRIUC.name);
        addCategory("Objets Chargés", componentNames);
        componentNames.clear();

        componentNames.add(Particle.name);
        componentNames.add(ParticleGenerator.name);
        addCategory("Particules", componentNames);
        componentNames.clear();

        componentNames.add(Cyclotron.name);
        componentNames.add(SelecteurDeVitesse.name);
        componentNames.add(Deflecteur.name);

        addCategory("Appareils", componentNames);
    }


    /**
     * Permet d'ajouter une feuille à l'arbre pour chaque nom de composant
     *
     * @param categoryName   Le nom de la catégorie
     * @param componentNames Le nom des composants
     */
    public void addCategory(String categoryName, ArrayList<String> componentNames) {

        DefaultMutableTreeNode categoryNode = new DefaultMutableTreeNode(categoryName);
        for (String name : componentNames) {
            if (name != null) {
                categoryNode.add(new DefaultMutableTreeNode(name));
            }
        }
        if (!categoryNode.isLeaf()) {
            /* On évite d'ajouter la catégorie si elle ne contient aucun élément */
            root.add(categoryNode);
        }
    }

    /**
     * Permet de développer toutes les branches de l'arbre.
     */
    public void expand() {
        for (int i = 0; i < this.getRowCount(); i++) {
            this.expandRow(i);
        }
    }

    /**
     * Permet d'obtenir un objet de type PhysicComponent en fonction d'un nom de composant.
     * Par exemple, cela permet d'obtenir un objet "MagneticField" pour le nom "Champ Magnétique Uniforme"
     *
     * @param componentName Le nom du composant
     * @return Le PhysicComponent
     */
    public static PhysicComponent componentNameToComponent(String componentName) {
        switch (componentName) {
            case ElectricField.name:
                return new ElectricField();
            case MagneticField.name:
                return new MagneticField();
            case Cyclotron.name:
                return new Cyclotron();
            case Deflecteur.name:
                return new Deflecteur();
            case Particle.name:
                return new Particle();
            case SelecteurDeVitesse.name:
                return new SelecteurDeVitesse();
            case PPIUC.name:
                return new PPIUC();
            case TRIUC.name:
                return new TRIUC();
            case ParticleGenerator.name:
                return new ParticleGenerator();
            case ChargedShell.name:
                return new ChargedShell();
        }
        return null;
    }

    /**
     * Permet d'obtenir un objet de type ComponentView en fonction d'un nom de composant.
     * Par exemple, cela permet d'obtenir la vue d'un objet "MagneticField" pour le nom "Champ Magnétique Uniforme"
     *
     * @param componentName Le nom du composant
     * @return La vue
     */
    public static ComponentView componentNameToView(String componentName) {
        PhysicComponent component = componentNameToComponent(componentName);
        if (component != null) {
            return ComponentView.generateView(component);
        }
        else{
            System.err.println("ERREUR: Le nom du composant est inconnu: " + componentName);
            return null;
        }
    }

    /* -------------------Getters & Setters------------------- */

    /**
     * Permet d'obtenir la vue sélectionnée
     *
     * @return La vue
     */
    public ComponentView getSelectedView() {
        return selectedView;
    }

    /**
     * Permet de définir la vue sélectionnée
     *
     * @param selectedView La vue sélectionnée
     */
    public void setSelectedView(ComponentView selectedView) {
        this.selectedView = selectedView;
    }
}
