/*
Copyright 2000- Francois de Bertrand de Beuvron

This file is part of CoursBeuvron.

CoursBeuvron is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

CoursBeuvron is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with CoursBeuvron.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.insa.beuvron.cours.multiTache.projets.digits;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;
import weka.core.Instances;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;

/**
 * Des petits utilitaires pour gérer des "images" de digits tels que définis par
 * le dataset MNIST. https://www.kaggle.com/datasets/oddrationale/mnist-in-csv/
 * Pour rester le plus simple possible (le but est d'être le plus neutre
 * possible car différent groupes de projet utiliserons diverses
 * représentations), nous représentons ces images comme des tableaux de
 * TAILLE*TAILLE int. t[n] contient une valeur (0-255) : niveau de gris du pixel
 * i,j de l'image avec i = n / TAILLE et j = n % TAILLE. Dans le reste de cette
 * classe, nous utiliserons (un peu improprement) le terme DigitImage pour
 * désigner un tel tableau : TAILLE*TAILLE en niveau de gris 8 bits
 *
 * @author francois
 */
public class DigitImage {

    public static final int TAILLE = 28;
    
    public static final Instances EMPTY_MNIST_DATASET = wekaMnistEmptyDataset();

    /**
     * converti n'importe quelle image en DigitImage
     */
    public static byte[] fromImage(BufferedImage img) {
        Image resize = img.getScaledInstance(TAILLE, TAILLE, Image.SCALE_SMOOTH);
        BufferedImage tampon = new BufferedImage(TAILLE, TAILLE, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D canvas = tampon.createGraphics();
        canvas.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
//        canvas.drawImage(img, 0, 0, TAILLE, TAILLE, null);
        canvas.drawImage(resize, 0, 0, TAILLE, TAILLE, null);
        byte[] res = ((DataBufferByte) tampon.getRaster().getDataBuffer()).getData();
        return res;
    }

    public static BufferedImage toImage(byte[] digitImage) {
        BufferedImage res = new BufferedImage(TAILLE, TAILLE, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = res.getRaster();
        // il faut un int[] pour le raster (dans le cas ou l'on a pluisieurs valeurs
        // pour chaque pixel (exemple canaux RGB)
        int[] poub = new int[1];
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                int n = i * TAILLE + j;
                poub[0] = digitImage[n];
                // pour une raison qui m'échappe, l'image obtenue est la transposée
                // de l'originale si je n'inverse pas les i et j 
                raster.setPixel(j, i, poub);
            }
        }
        return res;
    }

    public static String asText(byte[] digitImg) {
        StringBuilder res = new StringBuilder(TAILLE * (TAILLE * 3 + 1));
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                int n = i * TAILLE + j;
                res.append(String.format("%02X ", digitImg[n]));
            }
            res.append("\n");
        }
        return res.toString();
    }

    /**
     * lit un fichier image (jpeg, png ...) et le transforme en DigitImage. Le
     * fichier doit être dans un des format reconnu par ImageIO
     *
     * @param img
     * @return
     */
    public static byte[] fromFichierImage(InputStream input) throws IOException {
        BufferedImage img = ImageIO.read(input);
        return fromImage(img);
    }

    /**
     * lit un fichier image (jpeg, png ...) et le transforme en DigitImage. Le
     * fichier doit être dans un des format reconnu par ImageIO
     *
     * @param img
     * @return
     */
    public static byte[] fromFichierImage(File input) throws IOException {
        try (InputStream is = new FileInputStream(input)) {
            return fromFichierImage(is);
        }
    }

    public static byte[] fromFichierImageDansRessources(String relPath) throws IOException {
        return fromFichierImage(DigitImage.class.getResourceAsStream(relPath));
    }

    /**
     * ecrit la digitImage dans un fichier image. le format doit être reconnu
     * par ImageIO : JPEG, PNG, GIF, BMP and WBMP sont reconnus toujours
     */
    public static void toFichier(byte[] digitImage, File outFile, String format) throws IOException {
        BufferedImage img = toImage(digitImage);
        ImageIO.write(img, format, outFile);
    }

    /**
     * defini les attributs du dataset MNIST dans WEKA.
     * la classe (label) est le premier attribut. Il semble indispensable pour
     * définir la classe par setClassIndex, ce qui semble indispensable pour
     * avoir une Instance weka correcte.
     * Les attribut suivant sont les valeurs de chaque pixel, avec pour nom
     * de l'attribut : ixj : exemple "3x18" pour le pixel en 3ième ligne 18ième colonne.
     * les lignes/colonnes sont numérotés en partant de 1 (et non de 0): 
     * le premier attribut est l'attribut "1x1" 
     * voir partie "using the API" de la documentation WEKA.
     * https://deac-fra.dl.sourceforge.net/project/weka/documentation/3.8.x/WekaManual-3-8-3.pdf
     *
     * @param digitImage
     * @return
     */
    public static Instances wekaMnistEmptyDataset() {
        // 1) definition des attributs 
        ArrayList<Attribute> attrs = new ArrayList<>(TAILLE * TAILLE);
        attrs.add(new Attribute("label"));
        for (int i = 1; i <= TAILLE; i++) {
            for (int j = 1; j <= TAILLE; j++) {
                attrs.add(new Attribute(i + "x" + j));
            }
        }
        Instances res = new Instances("mnist", attrs, 0);
        res.setClassIndex(0);
        return res;
    }
    
    /**
     * Transforme un digitImage en une instance au sens de WEKA.
     * L'instance doit faire référence au dataset (Instances weka) pour la
     * définition des attributs.
     * Le premier attribut est la classe (label). Il n'a normalement pas de sens
     * pour une instance que l'on veut classifier, mais semble indispensable
     * pour que weka interprète correctement l'instance.
     * @param digitImage
     * @return 
     */
    public static Instance toWekaInstance(byte[] digitImage) {
        double[] asDoubles = IntStream.range(0, digitImage.length+1)
                .mapToDouble(i -> i == 0 ? 0.0 : (double) digitImage[i-1]).toArray();
        Instance res = new DenseInstance(1.0, asDoubles);
        res.setDataset(EMPTY_MNIST_DATASET);
        return res;
    }
    
    public static void testRessource(String imgRess) throws IOException {
        System.out.println("image trouvée dans ressource " + imgRess + " : ");
        byte[] img = fromFichierImageDansRessources(imgRess);
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.print(asText(img));
        System.out.println("-----------------------------------------------------------------------------------");
        byte[] imgV2 = fromImage(toImage(img));
        System.out.println("après passage par image et retour en digitImage");
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.print(asText(imgV2));
        System.out.println("-----------------------------------------------------------------------------------");

    }

    public static void test1() {
        try {
            testRessource("img/img2.png");
        } catch (IOException ex) {
            throw new Error(ex);
        }

    }

    public static void test2() {
        try {
            String pathRacine = "c:/temp/";
            System.out.println("repertoire courant : " + new File("").getAbsolutePath());
            System.out.println();
            String path = pathRacine + "img1.jpg";
            byte[] img = fromFichierImage(new File(path));
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.print(asText(img));
            System.out.println("-----------------------------------------------------------------------------------");
            String pathOut = pathRacine + "digitImage1.png";
            toFichier(img, new File(pathOut), "PNG");
            System.out.println("Sauvegarde de img2.png des ressources dans " + pathRacine);

        } catch (IOException ex) {
            throw new Error(ex);
        }

    }

    public static void main(String[] args) {
        test1();
    }

}
