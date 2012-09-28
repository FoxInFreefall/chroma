/*
 * A Backdrop is a visible Stage Element (a Presence) that can't be collided
 * with.
 */

package Core.Theatrics.StageElements.Presences;

import Core.Theatrics.StageElements.Presence;
import Wrappers.Direction;

/**
 *
 * @author Mecha Fox
 */
public class Backdrop extends Presence
{

    /***** CONSTRUCTOR *****/

        public Backdrop(String imageName)
        {
            super(imageName, imageName);
        }


    /***** OVERRIDING *****/

        //Define (run once at startup)
        //TODO: Which of these end up being overriden?
            @Override
            public void setupElement()
            {
                
            }

        //Behave (looped)
            @Override
            public void behave()
            {
                
            }

        //React (triggered)
            @Override
            public void react()
            {

            }

        //Progress (looped)
            @Override
            protected boolean progressSpecial(Direction direction)
            {
                return false;
            }

}
