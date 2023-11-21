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
public class PairImpairWaitNotify {

    static final private Object lock = new Object();
    static private boolean flagImpair;
    static private boolean flagPair;
    // variable lue par le thread qui attend
    // et positionnée par le thread faisant la notification

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Runnable impair = new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                for (int i = 1; i <= 10; i += 2) {
                    synchronized (lock) {
                        while (flagImpair == false) {
                            try {
                                System.out.println(threadName + " attend avec " + i);
                                lock.wait();
                                System.out.println(threadName + " a fini d'attendre avec " + i);
                            } catch (InterruptedException e) {
                                System.out.println(threadName+" interrupted");
                            }
                        }
                        flagImpair = false;
                        System.out.printf("%40d ---> %s%n", i, threadName);
                        flagPair = true;
                        lock.notifyAll();
                        
                    }
                }
            }
        };

        Runnable pair = new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                for (int i = 2; i <= 10; i += 2) {
                    synchronized (lock) {
                        while (flagPair == false) {
                            try {
                                System.out.println(threadName + " attend avec " + i);
                                lock.wait();
                                System.out.println(threadName + " a fini d'attendre avec " + i);
                            } catch (InterruptedException e) {
                                System.out.println(threadName+" interrupted");
                            }
                        }
                        flagPair = false;
                        System.out.printf("%40d ---> %s%n", i, threadName);
                        flagImpair = true;
                        lock.notifyAll();
                    }
                }
            }
        };
        flagImpair = true;
        flagPair = false;
        new Thread(impair, "impair").start();
        new Thread(pair, "pair").start();
    }
}
/*
run:
                                       1 ---> impair
impair attend avec 3
                                       2 ---> pair
pair attend avec 4
impair a fini d'attendre avec 3
                                       3 ---> impair
impair attend avec 5
pair a fini d'attendre avec 4
                                       4 ---> pair
pair attend avec 6
impair a fini d'attendre avec 5
                                       5 ---> impair
pair a fini d'attendre avec 6
                                       6 ---> pair
pair attend avec 8
                                       7 ---> impair
pair a fini d'attendre avec 8
                                       8 ---> pair
pair attend avec 10
                                       9 ---> impair
pair a fini d'attendre avec 10
                                      10 ---> pair
BUILD SUCCESSFUL (total time: 0 seconds)
*/