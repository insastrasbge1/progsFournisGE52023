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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * v
 * Contrairement à l'interrupt qui sera pris en compte par le sleep même si 
 * l'interrupt arrive avant le sleep, un notify sur un thread qui n'est pas
 * en wait sera perdu.
 * @author mglt
 */
public class MissedNotify {

    static final private Object lock = new Object();

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        String threadName = Thread.currentThread().getName();

        Runnable runA = new Runnable() {

            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                long delay;
                delay = 500;    // ok: wait avant notify
                delay = 2000; // missed notify: wait après notify
                try {
                    System.out.printf("%s %.1f %s%n", threadName
                            + ": j'hésite", (float) (delay / 1000f), " secondes");
                    Thread.sleep(delay);
                    synchronized (lock) {
                        System.out.println(threadName
                                + ": je prends place en salle d'attente");
                        lock.wait();
                        System.out.println(threadName
                                + ": je sors de la salle d'attente: notification ok");
                    }
                } catch (InterruptedException e) {
                    System.out.println(threadName
                            + ": je sors de la salle d'attente par la porte de secours"
                            + ": notification ratée");
                }
            }
        };

        Runnable runB = new Runnable() {

            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                try {
                    System.out.println(threadName
                            + ": j'hésite une seconde");
                    Thread.sleep(1000);
                    synchronized (lock) {
                        System.out.println(threadName
                                + ": alerte générale");
                        lock.notifyAll();
                        System.out.println(threadName
                                + ": j'ai réveillé tout le monde: terminé");
                    }
                } catch (InterruptedException e) { // sleep
                }
            }
        };

        Thread riri = new Thread(runA, "Riri");
        Thread loulou = new Thread(runB, "Loulou");
        riri.start();
        loulou.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }
        System.out.println();
        System.out.println(threadName + ": t=10s");
        if (riri.isAlive()) {
            riri.interrupt();
        }
    }
}

/*
C:>java MissedNotify
Loulou: j'hésite une seconde
Riri: j'hésite 0,5  secondes
Riri: je prends place en salle d'attente
Loulou: alerte générale
Loulou: j'ai réveillé tout le monde: terminé
Riri: je sors de la salle d'attente: notification ok

main: t=10s


Une notification ratée se produit lorsqu'un thread notifie un autre thread
mais que cet autre thread n'est pas déjà en train d'attendre une notification

C:>java MissedNotify
Loulou: j'hésite une seconde
Riri: j'hésite 2,0  secondes
Loulou: alerte générale
Loulou: j'ai réveillé tout le monde: terminé
Riri: je prends place en salle d'attente

main: t=10s
Riri: je sors de la salle d'attente par la porte de secours: notification ratée
*/
