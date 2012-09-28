/*
 * This is the template for an actor. When creating an actor, the actor will
 * have a list of ways it can behave. When the actor is added to a scene, it
 * will be given direction for that specific scene.
 *
 * Whenever a change is made to the actor during the scene, it will give off a
 * cue. This cue can be listened for by other actors, letting them know they
 * have to do something.
 *
 * This also contains a list of directions an actor can follow
 *
 * TODO: State machine
 */

package Core.Theatrics.StageElements.Presences;

import Core.Theatrics.StageElements.Presence;
import Wrappers.Direction;

/**
 *
 * @author Mecha Fox
 */
public abstract class Actor extends Presence
{

    /***** VARIABLES *****/
    

    /***** CONSTRUCTOR *****/

        public Actor(String name, String imageName)
        {
            super(name, imageName);
        }
            

    /***** OVERRIDE *****/

        //Define (run once at startup)
            public abstract void setupElement();

        //Behave (looped, AI)
            public abstract void behave();

        //React (triggered)
            public abstract void react();

        //Progress (looped)
            protected abstract boolean progressSpecial(Direction direction);

}