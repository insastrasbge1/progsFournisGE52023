/*
    Copyright 2000-2011 Francois de Bertrand de Beuvron

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

package fr.insa.beuvron.utils.swing;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Une classe utilitaire permettant d'afficher un panel dans une fenetre.
 *
 * @author Francois de Bertrand de Beuvron
 */
public class TestePanel {
  
  public static JFrame pack(JComponent compo) {
    return pack("test",compo);
  }
  
  public static JFrame pack(String titre,JComponent compo) {
    JFrame f = new JFrame(titre);
    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    f.add(compo);
    f.pack();
    f.setVisible(true);
    return f;
  }

  public static JFrame tailleFixee(JComponent compo,int tx,int ty) {
    return tailleFixee("test",compo,tx,ty);
  }
  
  public static JFrame tailleFixee(String titre,JComponent compo,int tx,int ty) {
    JFrame f = new JFrame(titre);
    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    f.add(compo);
    f.setSize(tx,ty);
    f.setVisible(true);
    return f;
  }
  
  public static JFrame pleinEcran(String titre,JComponent compo) {
    JFrame f = new JFrame(titre);
    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    f.add(compo);
    f.pack();
    f.setExtendedState(JFrame.MAXIMIZED_BOTH);
    f.setVisible(true);
    return f;
  }
  
}
