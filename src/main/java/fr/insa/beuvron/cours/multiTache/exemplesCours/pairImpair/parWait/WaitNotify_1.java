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


/**
 *
 * @author mglt
 */
public class WaitNotify_1 {

    private boolean value = false;

    /**
     *
     * @throws InterruptedException
     */
    public synchronized void waitUntilTrue()
            throws InterruptedException {
        while (value == false) {
            wait();
        }
    }

    /**
     *
     * @param newValue
     */
    public synchronized void setValue(boolean newValue) {
        if (newValue != value) {
            value = newValue;
            notify(); // notifyAll() might be safer...
        }
    }

    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        final WaitNotify_1 lock = new WaitNotify_1();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " attend un feu vert");
                try {
                    lock.waitUntilTrue();
                } catch (InterruptedException ex) {}
                System.out.println(Thread.currentThread().getName() + " a eu le feu vert");
            }
        }).start();
        Thread.sleep(1000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " donne le feu vert");
                lock.setValue(true);
            }
        }).start();
    }
}
