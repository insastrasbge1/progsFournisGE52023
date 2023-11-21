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
package fr.insa.beuvron.cours.multiTache.exemplesCours.pairImpair.parWait;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mglt
 */
public class EarlyNotify {

    static final private List<String> list = new LinkedList<>();

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        String threadName = Thread.currentThread().getName();

        Runnable runA = new Runnable() {
            @Override
            public void run() { // remove item
                String threadName = Thread.currentThread().getName();
                String message;
                synchronized (list) {
                    try {
                        if (list.isEmpty()) { // early notification
                            //while (list.isEmpty()) { // early notification fix
                            System.out.println(threadName + ": liste vide: j'attends");
                            list.wait();
                            System.out.println(threadName + ": j'ai fini d'attendre");
                        }
                        message = list.remove(0);
                    } catch (InterruptedException e) {
                        System.out.println(threadName
                                + ": je sors par l'escalier de secours");
                        return;
                    }
                    System.out.println(threadName + ": le message est: " + message);
                }
            }
        };

        Runnable runB = new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                synchronized (list) {
                    System.out.println(threadName + ": je vais déposer un message");
                    list.add("Hello!!!");
                    list.notifyAll();
                    System.out.println(threadName
                            + ": mon message est déposé et je l'ai fait savoir");
                }
            }
        };
        Thread riri = new Thread(runA, "Riri");
        Thread fifi = new Thread(runA, "Fifi");
        Thread loulou = new Thread(runB, "Loulou");

        try {
            riri.start();
            Thread.sleep(500);
            fifi.start();
            Thread.sleep(500);
            loulou.start();
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }
        System.out.println();

        System.out.println(threadName
                + ": t=10s");
        try {
            riri.join();
            fifi.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(EarlyNotify.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (riri.isAlive()) {
            riri.interrupt();
        }

        if (fifi.isAlive()) {
            fifi.interrupt();
        }
    }
}
/*
 Une notification précosse se produit quand un thread reçoit une notification
 mais que la condition qui contrôle le wait n'est pas au rendez-vous.
 Cette situation peut aussi se produire si la condition a été remplie
 mais rapidement changée de sorte qu'elle n'est plus au rendez-vous.

 C:>java EarlyNotify
 Riri: liste vide: j'attends
 Fifi: liste vide: j'attends
 Loulou: je vais déposer un message
 Loulou: mon message est déposé et je l'ai fais savoir
 Fifi: j'ai fini d'attendre
 Fifi: le message est: Hello!!!
 Exception in thread "Riri" java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
 Riri: j'ai fini d'attendre



 early notification fix
 C:>java EarlyNotify
 Riri: liste vide: j'attends
 Fifi: liste vide: j'attends
 Loulou: je vais déposer un message
 Loulou: mon message est déposé et je l'ai fais savoir
 Fifi: j'ai fini d'attendre
 Fifi: le message est: Hello!!!
 Riri: j'ai fini d'attendre
 Riri: liste vide: j'attends

 main: t=10s
 Riri: je sors par l'escalier de secours
 */
