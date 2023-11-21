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
 */
/**
 * synchronisation par sleep/interrupt Cette fois, on utilise un flag
 * (interrupt) pour distinguer si le sleep est sortie normalement (donc l'autre
 * thread n'a pas fait d'interrupt) ou par interruption. Si l'on est sorti
 * normalement, c'est que l'on avait pas attendu assez, et donc on attend de
 * nouveau (le sleep est mis dans un while)
 *
 * @author bm
 */
public class Pair_2 implements Runnable {

    private Thread other;
    private boolean interrupt = false;

    /** todoDoc. */
    @Override
    public void run() {
        int i;
        for (i = 2; i <= 10; i += 2) {
            System.out.printf("pair   : %5d pause\n", i);
            while (!interrupt) {
                try {
                    System.out.println("pair en attente");
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    interrupt = true;
                }
            }
            interrupt = false;
            System.out.printf(">>>>>%50d\n", i);
            try {
                // simule une execution lente pour voir que
                // l'autre thread peut se "reveiller" plusieurs
                // fois et se rendormir avant de continuer
                // effectivement à s'executer
                Thread.sleep(30);
            } catch (InterruptedException ex) {
                // 
                throw new Error(ex);
            }
            System.out.printf("pair   :%5d interrupt\n", i);
            other.interrupt();
        }

    }

    /**
     * @param other the other to set
     */
    public void setOther(Thread other) {
        this.other = other;
    }

}
