/*
 * A Prop is a visible Stage Element (a Presence) that can be collided with.
 */

package Core.Theatrics.StageElements.Presences;

import Core.Theatrics.StageElements.Presence;
import Wrappers.Direction;

/**
 *
 * @author Mecha Fox
 */
public class Prop extends Presence
{

    /***** CONSTRUCTOR *****/

        public Prop(String imageName)
        {
            super(imageName, imageName);
        }


    /***** OVERRIDING *****/

        //Define (run once at startup)
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
