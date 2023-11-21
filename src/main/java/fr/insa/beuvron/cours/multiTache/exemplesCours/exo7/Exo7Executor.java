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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author francois
 */
public class Exo7Executor {

    /**
     *
     * @param nbrThread
     * @param nbrIter
     * @return
     */
    public static long test(int nbrThread, long nbrIter) {
        // on peut comme avec les Thread lancer tous les thread :
        // ExecutorService executor = Executors.newFixedThreadPool(nbrThread);
        // mais ce n'est clairement pas le plus efficace.
        // autant tenir compte du nombre réel de processeurs :
        int nbrProc = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(nbrProc);

        // mais on peut également fixer le nombre max de Thread que l'on veut
        // voir s'exécuter simultanément. Les mêmes threads seront réutiliser
        // pour plusieurs calcul, ce qui est plus efficace surtout si chaque
        // calcul n'est pas long
        // ExecutorService executor = Executors.newFixedThreadPool(8);
        List<Future<Long>> ress = new ArrayList<>();
        long debut = System.currentTimeMillis();
        for (int i = 0; i < nbrThread; i++) {
            Future<Long> res = executor.submit(new Exo7Callable(nbrIter));
            ress.add(res);
        }
        long som = 0;
        for (Future<Long> oneF : ress) {
            try {
                som = som + oneF.get();
            } catch (InterruptedException | ExecutionException ex) {
                throw new Error(ex);
            }
        }
        System.out.println("resultat combine : " + som);
        executor.shutdown();
        long fin = System.currentTimeMillis();
        return fin - debut;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        long total = 1000000000L;

        for (int nbrThread = 1; nbrThread < 10; nbrThread++) {
            long nbrIter = total / nbrThread;
            System.out.println(nbrThread + " Threads ; " + nbrIter + " iters ==> "
                    + test(nbrThread, nbrIter) + " ms");
        }
        for (int nbrThread = 10; nbrThread <= 10000; nbrThread = nbrThread * 10) {
            long nbrIter = total / nbrThread;
            System.out.println(nbrThread + " Threads ; " + nbrIter + " iters ==> "
                    + test(nbrThread, nbrIter) + " ms");
        }
    }

}
