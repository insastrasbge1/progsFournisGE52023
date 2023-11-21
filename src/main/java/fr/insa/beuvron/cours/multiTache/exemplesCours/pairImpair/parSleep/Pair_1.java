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
 *
 * @author bm
 */
public class Pair_1 implements Runnable {

    private Thread other;

    /** todoDoc. */
    @Override
    public void run() {
        int i;
        for (i = 2; i <= 10; i += 2) {
            System.out.printf("pair   : %5d pause\n", i);
            try {
                //pause();
                Thread.sleep(10000);
            } catch (InterruptedException ex) {}
            System.out.printf(">>>>>%50d\n", i);
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
