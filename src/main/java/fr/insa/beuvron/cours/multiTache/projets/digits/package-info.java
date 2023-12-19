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

/**
 * Projet proposé en 2023S2 pour les GE5 IoT à l'INSA de Strasbourg.
 * Reconnaissance d'images de chiffres manuscrits.
 * Projet commun programmation concurrente et machine learning.
 * Coté machine learning : tester divers algo de classification.
 * Coté programmation concurrente : système basé sur les socket : 
 * <ul>
 *   <li> le classifieur principal (CP) démarre et attends que des
 * classifieurs secondaires (CS) se connectent </li>
 *   <li> un "client" peut envoyer une requète de classification au 
 * CP. Dans ce cas le CP : 
 *   <ul>
 *     <li> dispatche la requète à tous les CS connectés </li>
 *     <li> attends la réponse des CS (prévoir timeout et erreurs) </li>
 *     <li> combine les réponses (classification collaborative) </li>
 *     <li> renvoie la réponse au client </li>
 *   </ul>
 *   </li>
 * </ul>
 */
package fr.insa.beuvron.cours.multiTache.projets.digits;
