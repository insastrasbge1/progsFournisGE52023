/*
Copyright 2000- Francois de Bertrand de Beuvron

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
package fr.insa.beuvron.cours.multiTache.exemplesCours.trieurs;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO ; voir https://www.baeldung.com/java-fork-join
 *
 * @author francois
 */
public class TriMergeForkJoin extends Trieur {

    public TriMergeForkJoin(long timeLimit, AtomicInteger threadCounter, int[] tab, int minIndex, int maxIndex) {
        super(timeLimit, threadCounter, tab, minIndex, maxIndex);
    }

    public TriMergeForkJoin(Trieur createur, int minIndex, int maxIndex) {
        super(createur, minIndex, maxIndex);
    }

//    
//    protected TrieStatus compute() {
//        try {
//            ensureNoTimeout();
//            if (max - min <= 1) {
//                return TrieStatus.OK;
//            } else {
//                int milieu = (max + min) / 2;
//                TrieTask gauche = new (this, min, milieu);
//                // j'exécute cette première partie dans un nouveau thread
//                declareNewThread();
//                Thread bGauche = new Thread(gauche);
//                bGauche.start();
//                TriMergeParallel droite = new TriMergeParallel(this, milieu, max);
//                // j'exécute cette seconde partie dans le Thread courant
//                droite.run();
//                bGauche.join();
//                if (gauche.keepStatus == TrieStatus.OK && droite.keepStatus == keepStatus.OK) {
//                    TriMergeSequentiel.fusion(tab, min, milieu, max);
//                    this.keepStatus = TrieStatus.OK;
//                } else {
//                    if (gauche.keepStatus != TrieStatus.OK) {
//                        this.keepStatus = gauche.keepStatus;
//                    } else {
//                        this.keepStatus = droite.keepStatus;
//                    }
//                }
//            }
//        } catch (Trieur.TimeoutException ex) {
//            return TrieStatus.TIMEOUT;
//        } catch (Trieur.TooMuchThreadException ex) {
//            return TrieStatus.TOO_MUCH_THREADS;
//        }
//    }
//    @Override
    public TrieStatus effectueTrie() {
//        ForkJoinPool common = ForkJoinPool.commonPool();
//        common.invoke(new RecursiveTask<TrieStatus>() {
//            protected TrieStatus compute() {
//            }
//        });
        return null;
    }
//}

}
