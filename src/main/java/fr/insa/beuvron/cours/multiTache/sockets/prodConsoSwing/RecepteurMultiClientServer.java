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
 * Un serveur permettant à plusieurs clients de se connecter. Pour les
 * explications de base, voir {@link RecepteurServer}
 *
 * En fait, un ServerSocket peut accepter plusieurs clients. A chaque nouveau
 * client qui se connecte, un nouveau Socket est créé. Tous ces sockets partagent
 * la même adresse, et le même port. Chaque socket serveur reçoit bien les
 * données du socket client correspondant et de lui seul.
 *
 * Pour que le serveur puisse accepter plusieurs clients, il faut séparer la
 * connection de l'attente des données de chaque client. Il nous faut donc un
 * Thread ThreadConnect qui attend les connections des clients, et à chaque fois
 * qu'un client se connecte effectivement, on crée un nouveau ThreadListener qui
 * s'occupe de la reception des données de ce client particulier.
 *
 * Plusieurs threads travaillent donc en parallèle : . le thread gérant
 * l'interface graphique : il est créé automatiquement par java et s'occupe du
 * rafraichissement de l'interface (exemple lorsque la taille de la fenètre est
 * modifiée), et de la gestion des évènements utilisateur (clic bouton...) . un
 * thread attendant les connections des clients . un thread pour chacun des
 * clients actuellement connectés
 *
 * @author francois
 */
public class RecepteurMultiClientServer extends javax.swing.JPanel {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
      RecepteurMultiClientServer rs = new RecepteurMultiClientServer();
      TestePanel.pack(rs);
   }

   /**
    * Creates new form RecepteurServer
    */
   public RecepteurMultiClientServer() {
      initComponents();

      ThreadConnect tr = new ThreadConnect();
      tr.start();
   }

    /** todoDoc. */
    public class ThreadConnect extends Thread {

      private ServerSocket sockServer;

       /** todoDoc. */
       @Override
      public void run() {
         try {
            InetAddress addr = INetAdressUtil.premiereAdresseNonLoopback();
            this.sockServer = new ServerSocket(0, 1, addr);
            jtfIP.setText(this.sockServer.getInetAddress().getHostAddress());
            jtfPort.setText("" + this.sockServer.getLocalPort());
            while (true) {
               Socket inSock = this.sockServer.accept();
               jtaMessages.append("client : " + inSock + "\n");
               new ThreadListener(inSock).start();
            }
         } catch (IOException ex) {
            throw new Error(ex);
         } finally {
            if (this.sockServer != null) {
               try {
                  this.sockServer.close();
               } catch (IOException ex) {
               }
            }
         }

      }

   }

    /** todoDoc. */
    public class ThreadListener extends Thread {

      private Socket inSock;

       /**
        *
        * @param inSock
        */
       public ThreadListener(Socket inSock) {
         this.inSock = inSock;
      }

        /** todoDoc. */
        @Override
      public void run() {
         try (BufferedReader buf = new BufferedReader(new InputStreamReader(inSock.getInputStream(), Charset.forName("UTF8")))) {
            String mess = buf.readLine();
            while (mess != null) {
               jtaMessages.append(this.inSock.getRemoteSocketAddress() + " : " + mess + "\n");
               mess = buf.readLine();
            }
         } catch (IOException ex) {
            throw new Error(ex);
         } finally {
            if (this.inSock != null) {
               try {
                  this.inSock.close();
               } catch (IOException ex) {
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
