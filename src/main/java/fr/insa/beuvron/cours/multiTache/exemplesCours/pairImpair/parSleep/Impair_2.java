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
package fr.insa.beuvron.cours.multiTache.exemplesCours.pairImpair.parSleep;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 La boucle while est néessaire:
 - au bout du temps fixé par sleep, le signal n'est toujours pas arrivé
 Cette solution est acceptable mais certainement pas la meilleure solution.

 */
/**
 * voir commentaire dans Pair_2
 * @author bm
 */
public class Impair_2 implements Runnable {

    private Thread other;
    private boolean interrupt = false;

    /** todoDoc. */
    @Override
    public void run() {
        int i;
        for (i = 1; i <= 10; i += 2) {
            System.out.printf(">>>>>%50d\n", i);
            System.out.printf("impair : %5d interrupt\n", i);
            other.interrupt();
            System.out.printf("impair : %5d pause\n", i);
            while (!interrupt) {
                try {
                    System.out.println("impair en attente");
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    interrupt = true;
                }
            }
            interrupt = false;
        }
    }

    /**
     * @param other the other to set
     */
    public void setOther(Thread other) {
        this.other = other;
    }

}
