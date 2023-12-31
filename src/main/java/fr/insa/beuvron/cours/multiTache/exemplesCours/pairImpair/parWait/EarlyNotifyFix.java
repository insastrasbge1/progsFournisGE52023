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


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mglt
 */
@SuppressWarnings("unchecked")
public class EarlyNotifyFix {

    private final List list;

    /** todoDoc. */
    public EarlyNotifyFix() {
        list = Collections.synchronizedList(new LinkedList());
    }

    /**
     *
     * @return
     * @throws InterruptedException
     */
    public String removeItem() throws InterruptedException {

        print("in removeItem() - entering");
        synchronized (list) {
            while (list.isEmpty()) {
                print("in removeItem() - about to wait()");
                list.wait();
                print("in removeItem() - done with wait()");
            }

// extract the new first item
            String item = (String) list.remove(0);
            print("in removeItem() - leaving");
            return item;
        }
    }

    /**
     *
     * @param item
     */
    public void addItem(String item) {
        print("in addItem() - entering");
        synchronized (list) {
            // There’ll always be room to add to this List
            // because it expands as needed.
            list.add(item);
            print("in addItem() - just added: ‘" + item + "‘");
            // After adding, notify any and all waiting
            // threads that the list has changed.
            list.notifyAll();
            print("in addItem() - just notified");
        }
        print("in addItem() - leaving");
    }

    private static void print(String msg) {
        String name = Thread.currentThread().getName();
        System.out.println(name + ": " + msg);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        final EarlyNotifyFix enf = new EarlyNotifyFix();
        Runnable runA = new Runnable() {
            @Override
            public void run() {
                try {
                    String item = enf.removeItem();
                    print("in run() - returned: ‘"
                            + item + "‘");
                } catch (InterruptedException ix) {
                    print("interrupted!");
                } catch (Exception x) {
                    print("threw an Exception!!!\n" + x);
                }
            }
        };
        Runnable runB = new Runnable() {
            @Override
            public void run() {
                enf.addItem("Hello!");
            }
        };
        try {
            Thread threadA1 = new Thread(runA, "threadA1");
            threadA1.start();
            Thread.sleep(500);
            // start a *second* thread trying to remove
            Thread threadA2 = new Thread(runA, "threadA2");
            threadA2.start();
            Thread.sleep(500);
            Thread threadB = new Thread(runB, "threadB");
            threadB.start();
            Thread.sleep(10000); // wait 10 seconds
            threadA1.interrupt();
            threadA2.interrupt();
        } catch (InterruptedException x) {
            // ignore
        }
    }
}
