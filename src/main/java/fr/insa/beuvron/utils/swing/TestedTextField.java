/*
    Copyright 2000-2014 Francois de Bertrand de Beuvron

    This file is part of UtilsBeuvron.

    UtilsBeuvron is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    UtilsBeuvron is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with UtilsBeuvron.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.insa.beuvron.utils.swing;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 * Un JtextField dont la valeur doit toujours être correcte. Note : attention :
 * aucune valeur incorrecte, même transitoire n'est acceptée Le "truc" sur les
 * filtre de documents a été trouvé sur :
 * https://stackoverflow.com/questions/11093326/restricting-jtextfield-input-to-integers/11093360
 *
 * @author francois
 */
public abstract class TestedTextField extends JTextField {

    /**
     * détermine si le contenu curValue est valide ou pas
     *
     * @param curValue
     * @return true si curValue est une valeur valide pour le textfield
     */
    public abstract boolean test(String curValue);

    public class FilterByTest extends DocumentFilter {

        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) {
            Document doc = fb.getDocument();
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(doc.getText(0, doc.getLength()));
                sb.insert(offset, string);
                if(test(sb.toString())) {
                    // TODO
                }
            } catch (BadLocationException ex) {
                // on ne fait rien : donc le texte est refusé
            }
        }

    }

}
