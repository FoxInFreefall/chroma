/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Wrappers;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Mecha Fox
 */
public class HashList<E> extends ArrayList<E>
{

    /***** VARIABLES *****/

        //Core
            private HashMap<String, E> hash = new HashMap<String, E>();


    /***** METHODS *****/

        //Add element
            public void put(String name, E element)
            {
                hash.put(name, element);
                add(element);
            }

        //Get element
            public E get(String name)
            {
                return hash.get(name);
            }

        //Remove element
            public void remove(String name)
            {
                Object element = hash.remove(name);
                remove(element);
            }

}
