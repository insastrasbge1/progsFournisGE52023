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


/**
 * synchronisation par sleep/interrupt mais sans tester si l'on sort du sleep
 * parceque le temps est écoulé ou par interruption : dans l'absolue il n'y a 
 * pas de garantie (ici cela fonctionne car il ne faut pas plus de 10s pour
 * afficher un entier !!!)
 * Ce problème est corrigé dans la version 2
 * @author bm
 */
public class SynchroDemo_1 {

    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Impair_1 impair = new Impair_1();
        Thread t1 = new Thread(impair);
        Pair_1 pair = new Pair_1();
        Thread t2 = new Thread(pair);
        impair.setOther(t2);
        pair.setOther(t1);

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("back to main");
    }

}
