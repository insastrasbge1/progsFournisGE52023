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
 *
 * @author mglt
 */
public class MissedNotifyFix {

    static final private Object lock = new Object();
    static private boolean okToGo = false;

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
                //delay = 2000; // missed notify: wait après notify
                try {
                    Thread.sleep((long) (Math.random() * 100));
                } catch (InterruptedException e) {
                }
                try {
                    System.out.println(threadName + ": j'hésite deux secondes");
                    Thread.sleep(delay);
                    synchronized (lock) {
                        while (okToGo == false) {
                            System.out.println(threadName
                                    + ": je prends place en salle d'attente");
                            lock.wait();
                            System.out.println(threadName
                                    + ": j'ai bon espoir de sortir de la salle d'attente");
                        }
                        //okToGo = false; // s'iln'y a de la place que pour un
                        System.out.println(threadName + ": je suis sorti");
                    }
                } catch (InterruptedException e) {
                    System.out.println(threadName
                            + ": je sors de la salle d'attente par la porte de secours");
                    return;
                }
                System.out.println(threadName + ": into the wild");
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
                        okToGo = true;
                        //lock.notify();
                        lock.notifyAll();
                        System.out.println(threadName
                                + ": tout le monde a été réveillé");
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        Thread riri = new Thread(runA, "Riri");
        Thread fifi = new Thread(runA, "Fifi");
        Thread loulou = new Thread(runB, "Loulou");
        riri.start();
        fifi.start();
        loulou.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }

        System.out.println();
        System.out.println(threadName + ": t=10s");
        int active = Thread.activeCount();
        Thread[] all = new Thread[active];
        Thread.enumerate(all);
        for (Thread t : all) {
            if (!t.getName().equals("main")) {
                System.out.println("in main: sur le point d'interrompre " + t.getName());
                t.interrupt();
            }
        }
    }
}

/*
okToGo non repositionné par celui qui sort
C:>java  MissedNotifyFix
Loulou: j'hésite une seconde
Riri: j'hésite deux secondes
Fifi: j'hésite deux secondes
Loulou: alerte générale
Loulou: tout le monde a été réveillé
Riri: je suis sorti
Riri: into the wild
Fifi: je suis sorti
Fifi: into the wild


C:>java  MissedNotifyFix
Loulou: j'hésite une seconde
Fifi: j'hésite deux secondes
Riri: j'hésite deux secondes
Fifi: je prends place en salle d'attente
Riri: je prends place en salle d'attente
Loulou: alerte générale
Loulou: tout le monde a été réveillé
Riri: j'ai bon espoir de sortir de la salle d'attente
Riri: je suis sorti
Riri: into the wild
Fifi: j'ai bon espoir de sortir de la salle d'attente
Fifi: je suis sorti
Fifi: into the wild

main: t=10s

Le premier qui réacquiert le verrou
repositionne okToGo avant de sortir de la secion critique
C:>java  MissedNotifyFix
Loulou: j'hésite une seconde
Fifi: j'hésite deux secondes
Riri: j'hésite deux secondes
Loulou: alerte générale
Loulou: tout le monde a été réveillé
Fifi: je suis sorti
Fifi: into the wild
Riri: je prends place en salle d'attente

main: t=10s
in main: sur le point d'interrompre Riri
Riri: je sors de la salle d'attente par la porte de secours


*/
