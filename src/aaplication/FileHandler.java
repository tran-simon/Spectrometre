package aaplication;

import physics.PhysicsWorld;
import physics.component.Particle;
import physics.component.ParticleGenerator;
import physics.component.PhysicComponent;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Classe qui permet de sauvegarder et d'ouvrir un monde physique à l'aide d'un JFileChooser
 * <p>
 *
 * @author Sofianne Laurin
 */
public class FileHandler {
    private static final int PARTICLE_LIST_INDEX = 0, COMPONENT_LIST_INDEX = 1, PARTICLE_GENERATOR_LIST_INDEX = 2;

    private JFileChooser fileChooser = new JFileChooser();
    private URI filePath;
    private Container parent;

    /**
     * Constructeur par défaut
     *
     * @param parent Le parent
     */
    public FileHandler(Container parent) {
        this.parent = parent;

        /* On met l'interface en français */
        fileChooser.setLocale(Locale.CANADA_FRENCH);

        fileChooser.setCurrentDirectory(new File("../"));

        /* On s'assure que les fichiers auront la bonne extension */
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Mondes Physiques", AppUtil.FILE_EXTENSION);
        fileChooser.setFileFilter(filter);

        fileChooser.updateUI();
    }

    /**
     * Permet d'ouvrir un fichier de monde physique et de le lire afin de construire un nouveau monde
     *
     * @return Le monde physique
     */
    public PhysicsWorld openWorld() {
        PhysicsWorld physicsWorld = null;
        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            filePath = getSelectedPath(fileChooser);
            try {
                return openWorldAt(filePath);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(parent, "Fichier incorrecte: " + filePath.toString());
                openWorld();
                return null;
            }
        }
        return physicsWorld;
    }

    /**
     * Permet d'ouvrir un fichier de monde physique à une emplacement afin de construire un nouveau monde
     *
     * @param filePath L'emplacement du fichier
     * @return Le monde physique
     * @throws FileNotFoundException Si l'emplacement est invalide
     */
    public static PhysicsWorld openWorldAt(URI filePath) throws FileNotFoundException {
        PhysicsWorld physicsWorld;
        if (filePath == null || new File(filePath) == null || !getExtension(filePath).equals(AppUtil.FILE_EXTENSION)) {
            throw new FileNotFoundException();
        }
        else {
            physicsWorld = deserializeWorld(filePath);
        }
        return physicsWorld;
    }


    /**
     * Permet de sauvegarder le monde dans le fichié original.
     * Si le fichier original n'existe pas, la méthode demande à l'utilisateur de sélectionner un nouveau fichier
     * Si le fichier sélectionné ne possède pas la bonne extension, l'extension par défaut est rajoutée automatiquement
     *
     * @param physicsWorld Le monde physique
     */
    public void saveWorld(PhysicsWorld physicsWorld) {
        if (filePath == null || new File(filePath) == null) {
            saveWorldAtChooser(physicsWorld);
        }
        else {
            try {
                if (!getExtension(filePath).equals(AppUtil.FILE_EXTENSION)) {
                    filePath = new URI(filePath.toString() + "." + AppUtil.FILE_EXTENSION);
                }
                serializeWorld(physicsWorld, filePath);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(parent, "Erreur dans l'écriture du fichier");
            }
        }
    }

    /**
     * Permet de sauvegarder le monde à un emplacement choisi par l'utilisateur
     *
     * @param physicsWorld Le monde physique
     */
    public void saveWorldAtChooser(PhysicsWorld physicsWorld) {
        int result = fileChooser.showSaveDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            filePath = getSelectedPath(fileChooser);
            saveWorld(physicsWorld);
        }

    }

    /**
     * Permet de renommer le fichier du monde
     * @param name Le nouveau nom
     */
    public void rename(String name) {
        name += ".physicsworld";
        Path path = Paths.get(filePath);
        try {
            filePath = Files.move(path, path.resolveSibling(name)).toUri();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /* -------------------Méthodes Statiques------------------- */

    /**
     * Permet d'obtenir l'adresse du fichier sélectionné
     *
     * @param fileChooser L'objet JFileChooser
     * @return L'adresse du fichier
     */
    public static URI getSelectedPath(JFileChooser fileChooser) {
        URI filePath = null;
        if (fileChooser.getSelectedFile() != null) {
            File selectedFile = fileChooser.getSelectedFile();
            filePath = selectedFile.toURI();
        }

        return filePath;
    }

    /**
     * Permet de lire un fichier et d'obtenir le monde physique
     *
     * @param path L'emplacement du fichier
     * @return Le monde physique
     */
    public static PhysicsWorld deserializeWorld(URI path) {
        PhysicsWorld physicsWorld = new PhysicsWorld();
        File file = new File(path);

        ArrayList arrayList = new ArrayList();
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            arrayList = (ArrayList) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }


        physicsWorld.setParticleList((ArrayList<Particle>) arrayList.get(PARTICLE_LIST_INDEX));
        physicsWorld.setComponentList((ArrayList<PhysicComponent>) arrayList.get(COMPONENT_LIST_INDEX));
        physicsWorld.setParticleGeneratorsList((ArrayList<ParticleGenerator>) arrayList.get(PARTICLE_GENERATOR_LIST_INDEX));

        return physicsWorld;
    }


    /**
     * Permet de sérialiser un monde et de le sauvegarder
     *
     * @param world Le monde
     * @param path  Le chemin où il doit être sauvegardé
     */
    public static void serializeWorld(PhysicsWorld world, URI path) {
        ArrayList<ArrayList> objectsToWrite = new ArrayList<>();
        objectsToWrite.add(PARTICLE_LIST_INDEX, world.getParticleList());
        objectsToWrite.add(COMPONENT_LIST_INDEX, world.getComponentList());
        objectsToWrite.add(PARTICLE_GENERATOR_LIST_INDEX, world.getParticleGeneratorsList());

        try {
            FileOutputStream fileOut =
                    new FileOutputStream(new File(path));
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(objectsToWrite);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }


    /**
     * Permet d'obtenir l'extension d'un fichier
     *
     * @param filePath L'adresse du fichier
     * @return L'extension du fichier
     */
    public static String getExtension(URI filePath) {
        String name = filePath.toString();
        int dotIndex = name.lastIndexOf(".");
        if (dotIndex != -1) {
            return name.substring(dotIndex + 1);
        }

        return "";
    }





    /* -------------------Getters & Setters------------------- */

    /**
     * Méthode qui ajuste l'adresse du fichier
     * @param filePath L'adresse du fichier
     */
    public void setFilePath(URI filePath) {
        this.filePath = filePath;
    }

    /**
     * Méthode qui retourne l'adresse du fichier
     * @return L'adresse du fichier
     */
    public URI getFilePath() {
        return filePath;
    }
}
