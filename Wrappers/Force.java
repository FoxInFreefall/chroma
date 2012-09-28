/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Wrappers;

import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author Fox
 */
public class Force
{
    /***** VARIABLES *****/

        public double magnitude = 0;
        public double theta = 0;


    /***** CONSTRUCTOR *****/

        public Force(double magnitude, double theta)
        {
            this.magnitude = magnitude;
            this.theta = theta;
        }


    /***** CONVENIENCE METHODS *****/

        public Components getComponents()
        {
            double currentSin = magnitude * Math.sin(theta);
            double currentCos = magnitude * Math.cos(theta);

            return new Components(currentSin, currentCos);
        }


    /***** CLASS METHODS *****/
        
        public static Force compoundForces(Collection<Force> forces)
        {
            Iterator it = forces.iterator();

            double resSin = 0;
            double resCos = 0;
//            for(int i = 0; i < forces.size(); i++)
            while(it.hasNext())
            {
                Force thisForce = (Force) it.next();
                double thisMag = thisForce.magnitude;
                double thisTht = thisForce.theta;

                double thisSin = thisMag * Math.sin(thisTht);
                double thisCos = thisMag * Math.cos(thisTht);

                resSin += thisSin;
                resCos += thisCos;
            }

            double resMag = Math.sqrt(Math.pow(resSin, 2) + Math.pow(resCos, 2));
            double resTht = Math.atan2(resSin, resCos);

            return new Force(resMag, resTht);
        }

        @Override
        public String toString()
        {
            return super.toString() + ":" + getComponents();
        }

    public class Components
    {
        public double sin = 0;
        public double cos = 0;

        public Components(double sin, double cos)
        {
            this.sin = sin;
            this.cos = cos;
        }

        @Override
        public String toString()
        {
            return "(" + sin + "," + cos + ")";
        }
    }
}
