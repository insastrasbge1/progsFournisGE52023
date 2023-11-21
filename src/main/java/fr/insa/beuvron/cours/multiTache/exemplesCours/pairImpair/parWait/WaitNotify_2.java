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



/* Illustre notify et notifyAll
 * notify ne réveille qu'un thread, notifyAll les réveille tous mais un par un
 * puisqu'ils doivent acquérir le verrou.
 */

/**
 *
 * @author francois
 */

public class WaitNotify_2 {

    static final Object lock = new Object();

    static void localWait() {
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName() + " entre dans localWait");
            try {
                lock.wait();
            } catch (InterruptedException ex) {
                System.out.println(Thread.currentThread().getName() + " interrupted");
            } finally {
                System.out.println(Thread.currentThread().getName() + " woke up");
            }
            System.out.println(Thread.currentThread().getName() + " sort de localWait");
        }
    }

    static void localNotify() {
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName() + " entre dans localNotify");
            lock.notify();
            //ock.notifyAll();
            System.out.println(Thread.currentThread().getName()
                    + " all notified but will keep the lock 10 s");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
            }
            System.out.println(Thread.currentThread().getName() + " sort de localNotify");
        }
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        for (int i = 0; i < 10; ++i) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    WaitNotify_2.localWait();
                }
            }).start();
        }

        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException ex) {
        }

        //*
        WaitNotify_2.localNotify();
        //*/
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                WaitNotify_2.localNotify();
            }
        }).start();
        //*/

        /*
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException ex) {
        }
        //*/
        
        //*
        int active = Thread.activeCount();
        Thread[] all = new Thread[active];
        Thread.enumerate(all);
        for (Thread t : all) {
            if (!t.getName().equals("main")) {
                System.out.println("in main: sur le point d'interrompre " + t.getName());
                t.interrupt();
            }
        }
        //*/
    }
}
