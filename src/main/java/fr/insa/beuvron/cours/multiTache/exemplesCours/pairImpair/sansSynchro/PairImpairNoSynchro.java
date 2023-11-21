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
package fr.insa.beuvron.cours.multiTache.exemplesCours.pairImpair.sansSynchro;

import fr.insa.beuvron.cours.multiTache.utils.ThreadUtils;

/**
 * Un thread crée les nombre pairs, un autre les nombres impairs, le but est
 * qu'ils soient générés dans l'ordre.
 *
 * <p>
 *
 * Sans mechanisme de synchronisation, les nombres seront générés dans le
 * désordre. c'est ce qui se passe dans cette première implémentation
 *
 */
public class PairImpairNoSynchro {

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
                    ThreadUtils.sleepAleaMilliSecondes(100);
                    System.out.printf("%40d ---> %s%n", i, threadName);
                }
            }
        };

        Runnable pair = new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                for (int i = 2; i <= 10; i += 2) {
                    ThreadUtils.sleepAleaMilliSecondes(100);
                    System.out.printf("%40d ---> %s%n", i, threadName);
                }
            }
        };

        new Thread(impair, "impair").start();
        new Thread(pair, "pair").start();
    }
}
