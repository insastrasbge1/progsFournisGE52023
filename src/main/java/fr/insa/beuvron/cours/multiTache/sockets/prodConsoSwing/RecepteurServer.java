/*
Copyright 2000-2014 Francois de Bertrand de Beuvron

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
package fr.insa.beuvron.cours.multiTache.sockets.prodConsoSwing;

import fr.insa.beuvron.cours.multiTache.sockets.INetAdressUtil;
import fr.insa.beuvron.utils.swing.TestePanel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Un petit serveur simple acceptant un seul client. Dès la création de la
 * classe (qui s'occupe de l'interface graphique), on démarre un Thread de type
 * ThreadRecepteur qui . crée un ServerSocket . attend la connection d'un seul
 * client (méthode accept) . à la connection d'un client, on obtient un Socket
 * permettant de communiquer avec le client connecté. . reçoit les données du
 * client (indéfiniment) et les affiche un socket TCP garanti que les données
 * provenant du client . arrivent . arrivent dans l'ordre dans lequel elles ont
 * été envoyée ==> les données peuvent donc être vues comme un flux (même si
 * elles sont en fait envoyées par plusieurs paquets), et on y accède comme pour
 * un fichier au travers d'un InputStream.
 *
 * Note sur les adresses IP : Une machine peut avoir de nombreuses adresses IP
 * "réelles" : . plusieurs cartes réseau . réseau filaire + réseau wifi Elle
 * possède aussi une/des adresses "loopback" : . adresses en 127.x.x.x . au
 * moins une : 127.0.0.1 Cette adresse permet de faire référence à la machine
 * locale
 *
 * Note sur la gestion des flux textuel : un InputStream est un flux de donnée
 * binaire. Ici, on veut échanger du texte. On crée donc un flux texte (Reader
 * java) "au dessus" du flux binaire (InputStreamReader) en précisant l'encodage
 * (ici UTF8 que je conseille comme encodage par défaut) Le BufferedReader "au
 * dessus" du InputStreamReader permet de récuperer le texte ligne par ligne.
 * Attention donc à s'assurer que le client envoie bien des lignes terminées par
 * un retour à la ligne ("\n") voir
 * <a href="https://docs.oracle.com/javase/tutorial/essential/io/streams.html"> streams </a>
 *
 * Note2 : il faut bien lancer un Thread pour s'occuper de la connection et de
 * l'attente des données du client, sans cela, la création du panel serait
 * bloquée indéfiniment (attente du client puis des données du client) ==> rien
 * ne s'afficherait
 *
 * @author francois
 */
public class RecepteurServer extends javax.swing.JPanel {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
      RecepteurServer rs = new RecepteurServer();
      TestePanel.pack(rs);
   }

   /**
    * Creates new form RecepteurServer
    */
   public RecepteurServer() {
      initComponents();

      ThreadRecepteur tr = new ThreadRecepteur();
      tr.start();
   }

    /** todoDoc. */
    public class ThreadRecepteur extends Thread {

      private ServerSocket sockServer;

        /** todoDoc. */
        @Override
      public void run() {
         try {
            InetAddress addr = INetAdressUtil.premiereAdresseNonLoopback();
            this.sockServer = new ServerSocket(0, 1, addr);
            jtfIP.setText(this.sockServer.getInetAddress().getHostAddress());
            jtfPort.setText("" + this.sockServer.getLocalPort());
            Socket inSock = this.sockServer.accept();
            jtaMessages.append("client : " + inSock + "\n");
            // notez cette syntaxe spéciale du try-catch
            // la variable buf est "protégée" par ce try-catch : 
            //    tout ce passe comme s'il y avait un finally qui ferme (close) buf
            // vous pouvez voir un équivalent avec un try-catch ordinaire pour la fermeture
            // du socket (try-catch englobant)
            try (BufferedReader buf = new BufferedReader(new InputStreamReader(inSock.getInputStream(), Charset.forName("UTF8")))) {
               String mess = buf.readLine();
               while (mess != null) {
                  jtaMessages.append(mess + "\n");
                  mess = buf.readLine();
               }
            }
         } catch (IOException ex) {
            throw new Error(ex);
         } finally {
            if (this.sockServer != null) {
               try {
                  this.sockServer.close();
               } catch (IOException ex) {
                  // si même le close génère une exception, on ne voit plus ce
                  // que l'on peut faire, donc on ne fait rien
               }
            }
         }

      }

   }

   /**
    * This method is called from within the constructor to initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is always
    * regenerated by the Form Editor.
    */
   @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtfIP = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtfPort = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaMessages = new javax.swing.JTextArea();

        jLabel1.setText("IP :");

        jtfIP.setEditable(false);

        jLabel2.setText("Port : ");

        jtfPort.setEditable(false);

        jtaMessages.setEditable(false);
        jtaMessages.setColumns(20);
        jtaMessages.setRows(5);
        jScrollPane1.setViewportView(jtaMessages);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtfIP))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfPort))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtfIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtfPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jtaMessages;
    private javax.swing.JTextField jtfIP;
    private javax.swing.JTextField jtfPort;
    // End of variables declaration//GEN-END:variables
}
