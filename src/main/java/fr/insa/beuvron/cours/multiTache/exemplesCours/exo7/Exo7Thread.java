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
package fr.insa.beuvron.cours.multiTache.exemplesCours.exo7;

/**
 *
 * @author francois
 */
public class Exo7Thread {
    
    /**
     *
     * @param nbrThread
     * @param nbrIter
     * @return
     */
    public static long test(int nbrThread, long nbrIter) {
        Exo7Runnable[] rs = new Exo7Runnable[nbrThread];
        Thread[] ts = new Thread[nbrThread];
        for (int i = 0; i < rs.length; i++) {
            rs[i] = new Exo7Runnable(nbrIter);
        }
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new Thread(rs[i]);
        }
        long debut = System.currentTimeMillis();
        for (int i = 0; i < ts.length; i++) {
            ts[i].start();
        }
        for (int i = 0; i < ts.length; i++) {
            try {
                ts[i].join();
            } catch (InterruptedException ex) {
                throw new Error(ex);
            }
        }
        long som = 0;
        for (int i = 0; i < rs.length; i++) {
            som = som +rs[i].getRes();
        }
        System.out.println("resultat combine : " + som);
        long fin = System.currentTimeMillis();
        return fin - debut;
    }
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        long total = 1000000000L;
        
        int nbrcore = Runtime.getRuntime().availableProcessors();
        System.out.println("nombre de cpu : " + nbrcore);
        
        for (int nbrThread = 1; nbrThread < 10 ; nbrThread ++) {
            long nbrIter = total/nbrThread;
            System.out.println(nbrThread + " Threads ; " + nbrIter + " iters ==> " +
                    test(nbrThread,nbrIter) + " ms");
        }
        for (int nbrThread = 10; nbrThread <=10000 ; nbrThread = nbrThread * 10) {
            long nbrIter = total/nbrThread;
            System.out.println(nbrThread + " Threads ; " + nbrIter + " iters ==> " +
                    test(nbrThread,nbrIter) + " ms");
        }
    }
    
}
