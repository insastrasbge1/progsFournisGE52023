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
public class Exo7Runnable implements Runnable {

    private long nbrIter;
    
    private long res;

    /**
     *
     * @param nbrIter
     */
    public Exo7Runnable(long nbrIter) {
        this.nbrIter = nbrIter;
    }
    
    /** todoDoc. */
    @Override
    public void run() {
        // uniquement pour faire "bosser" le processeur
        for (long i = 0; i < this.nbrIter; i++) {
        }
        // this.res = (int) (Math.random() * 100);
        this.res = this.nbrIter;  // pour debug : compte simplement le nombre d'executions
    }

    /**
     * @return the nbrIter
     */
    public long getNbrIter() {
        return nbrIter;
    }

    /**
     * @return the res
     */
    public long getRes() {
        return res;
    }

    /**
     * @param res the res to set
     */
    public void setRes(int res) {
        this.res = res;
    }
    
}
