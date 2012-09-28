w/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

import Wrappers.Cue;
import Threads.React;
import Wrappers.CueGroup;
import Wrappers.Direction;
import java.util.ArrayList;

/**
 *
 * @author Fox
 */
public abstract class Theatric
{
    /***** VARIABLES *****/

        //Cues that can be called
            public ArrayList<String> cues = new ArrayList<String>();

        //States -- the theatric will behave differently based on which state it's in
            public ArrayList<String> states = new ArrayList<String>();

        //Queue of directions to be followed
            public ArrayList<Direction> directions = new ArrayList<Direction>();


    /***** CUE METHODS *****/

        //Call
            public void callCue(String cueName)
            {
                new React(cueName, this);
            }

        //Add
            protected void addCue(String cueName)
            {
                cues.add(cueName);
            }

        //Set
            public void setCue(String cueName, boolean isCalled)
            {
                if(isCalled)
                {
                    if(!cues.contains(cueName))
                        cues.add(cueName);
                }
                else
                    cues.remove(cueName);

            }

        //Check if(actor.just("fadedIn"))
            public boolean just(String cueName)
            {
                Direction.reset();
                return
                (
                    Thread.currentThread().getName().equals
                    (
                        "React thread: " + this + "." + cueName
                    ) &&
                    cues.contains(cueName)
                );
            }

        //Cancel all directions
        //TODO: add cancel specific direction
            public final void cancelAllDirections()
            {
                for(Direction direction : directions)
                    ((Cue) direction.get("cue")).set(true);
                directions.clear();
            }

        //Concurrent direction setting
            public final void concurrent()
            {
                if(!Direction.theseDirectionsAreConcurrent())
                {
                    Direction.concCues.put(Thread.currentThread(), new CueGroup());
                    Direction.directionsAreConcurrent.put(Thread.currentThread(), true);
                }
            }

        //Sequential direction setting
            public final void endConcurrent()
            {
                if(Direction.theseDirectionsAreConcurrent())
                {
                    Direction.tempCues.put(Thread.currentThread(), Direction.concCues.get(Thread.currentThread()));
                    Direction.directionsAreConcurrent.put(Thread.currentThread(), false);
                }
            }


    /***** STATE METHODS *****/

        //Has state
        public boolean is(String state)
        {
            Direction.reset();
            return states.contains(state);
        }

        //TODO: make these directions
        public void addState(String state)
        {
            if(!states.contains(state))
                states.add(state);
        }

        public void removeState(String state)
        {
            states.remove(state);
        }


    /***** OVERRIDE *****/

        //Progress special (extend past StageElement)
            protected abstract boolean progressSpecial(Direction direction);

        //Progress
            protected abstract boolean progress(Direction direction);


    /***** DO NOT TOUCH *****/

        //Manages directions and removes them if they've been completed
            public void progressShell()
            {
                ArrayList<Direction> removalQueue = new ArrayList();

                //size: any directions added after are ignored right now
//                int size = directions.size();
                for(int i = 0; i < directions.size(); i++)
                {
                    Direction direction = (Direction) directions.get(i);

                    //Failsafe: in case direction is removed
                    if(direction != null)
                    {
                        //Skip if this direction isn't ready to be run
                        if(!((Cue) direction.get("when")).isCalled())
                            continue;

                        //Queue this direction to be removed if it's been completed
                        if(progress(direction) || progressSpecial(direction))
                        {
                            ((Cue) direction.get("cue")).set(true);
                            removalQueue.add(direction);
                        }
                    }
                }

                //Remove directions the actor is done wit
                for(Direction direction : removalQueue)
                    directions.remove(direction);
            }
}
