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

package Core.Theatrics;

import Core.Theatric;
import Core.Theatrics.StageElements.Presence;
import Wrappers.Direction;
import Wrappers.Force;
import Wrappers.Vector2D;
import java.util.HashMap;

/**
 *
 * @author Kai Vulpes
 */
public abstract class StageElement extends Theatric
{
    /***** VARIABLES *****/
            
        //Name
            public String name = "unnamed";

        //Status -- what the actor is doing: offstage, entering, acting, exiting
            public boolean onstage = false;

        //Position
            public double x = 0;
            public double y = 0;

        //Forces acting on this element
            public HashMap<String, Force> forces = new HashMap<String, Force>();

        //Visibility
        //TODO: make this a state
            public boolean visible = true;

        //Depth (how far back the element is, 0: front, 10: back
            public int depth = 0;

        //Mass
            public double mass = 0;
            

    /***** CONSTRUCTOR *****/

        //Constructor (name element)
            public StageElement() 
            {
                init();
                setupElement();
                
                //Initialize states
                //TODO: IDEA: use enum (contained in States class), not strings
                states.add("default");
            }

            
    /***** OVERRIDABLE *****/

        //Initialize variables, etc.
            public void init() {};

        //Init: Define (name element, add states)
            public abstract void setupElement();

        //Behave: (looped) Defines AI of Actors, nature of props, etc., uses no cues (based on what state(s) its in)
            public abstract void behave();

        //React: (triggered) Defines what this should do in case a cue is called
            public abstract void react();
            
        //To be overridden when something extends this class
            protected abstract boolean progressSpecial(Direction direction);


    /***** EDITABLE *****/
            
        //Run constantly to progress the actor toward its goals
            protected boolean progress(Direction direction)
            {                
                //Enter/exit
                if(direction.get("direction").equals("changeStatus"))
                {
                    onstage = (Boolean) direction.get("newStatus");

                    if(onstage)
                        callCue("entered");
                    else
                        callCue("exited");
                    return true;
                }

                //Apply force
                else if(direction.get("direction").equals("applyForce"))
                {
                    String forceName = (String) direction.get("forceName");
                    double mag = (Double) direction.get("magnitude");
                    double tht = (Double) direction.get("theta");
                    Force force = new Force(mag, tht);
                    addForce(forceName, force);
                    return true;
                }

                //Burst force
                else if(direction.get("direction").equals("burstForce"))
                {
                    Long startTime = (Long) direction.get("startTime");
                    String forceName = (String) direction.get("forceName");
                    if(startTime == null)
                    {
                        double mag = (Double) direction.get("magnitude");
                        double tht = (Double) direction.get("theta");
                        Force force = new Force(mag, tht);
                        addForce(forceName, force);
                        direction.put("startTime", System.currentTimeMillis());
                    }
                    else
                    {
                        Long duration = (Long) direction.get("duration");
                        if
                        (
                            System.currentTimeMillis() - startTime >= duration ||
                            !forces.containsKey(forceName)
                        )
                        {
                            forces.remove(forceName);
                            return true;
                        }
                    }
                }

                //Relieve force
                else if(direction.get("direction").equals("relieveForce"))
                {
                    String forceName = (String) direction.get("forceName");
                    removeForce(forceName);
                    return true;
                }

                //Set pos
                else if(direction.get("direction").equals("setPos"))
                {
                    x = (Integer) direction.get("x");
                    y = (Integer) direction.get("y");

                    return true;
                }

                return false;
            }


    /***** DO NOT TOUCH *****/

        //Manages directions and removes them if they've been completed
            @Override
            public void progressShell()
            {
                super.progressShell();
            }


    /***** CONVENIENCE *****/

        //Add a continuous force
            public void addForce(String forceName, Force force)
            {
                if(!forces.containsKey(forceName))
                    forces.put(forceName, force);
            }

        //Remove a force 
            public void removeForce(String forceName)
            {
                forces.remove(forceName);
            }


    /***** DIRECTIONS (QUEUED, HEADERS, LOGIC DEFINED IN PROGRESS) *****/

        //Enter the scene
            public void enterScene()
            {
                changeStatus(true);
            }

        //Exit the scene
            public void exitScene()
            {
                changeStatus(false);
            }

        //Change status
        //TODO: make onstage/offstage a state, make this direction -> changeState(String state)
            public void changeStatus(boolean onstage)
            {                
                Direction direction = new Direction();
                direction.put("direction", "changeStatus");
                direction.put("newStatus", onstage);

                directions.add(direction);
            }

        //Apply force to this element
            public void attachForce(String forceName, double magnitude, double theta)
            {
                Direction direction = new Direction();
                direction.put("direction", "applyForce");
                direction.put("forceName", forceName);
                direction.put("magnitude", magnitude);
                direction.put("theta", theta);

                directions.add(direction);
            }

        //Apply force to this element for a certain time
            public void burstForce(String forceName, double magnitude, double theta, long duration)
            {
                Direction direction = new Direction();
                direction.put("direction", "burstForce");
                direction.put("forceName", forceName);
                direction.put("magnitude", magnitude);
                direction.put("theta", theta);
                direction.put("duration", duration);

                directions.add(direction);
            }

        //Relieve (remove) force acting on this element
            public void detachForce(String forceName)
            {
                Direction direction = new Direction();
                direction.put("direction", "relieveForce");
                direction.put("forceName", forceName);

                directions.add(direction);
            }

        //Set position (no transition)
            public void setPos(int x, int y)
            {
                Direction direction = new Direction();
                direction.put("direction", "setPos");
                direction.put("x", x);
                direction.put("y", y);

                directions.add(direction);
            }

        //Fade in
            public void fadeIn(long durationMillis)
            {
                Direction direction = new Direction();
                direction.put("direction", "fade");
                direction.put("isFadeIn", true);
                direction.put("duration", durationMillis);

                directions.add(direction);
            }

        //Fade out
            public void fadeOut(long durationMillis)
            {
                Direction direction = new Direction();
                direction.put("direction", "fade");
                direction.put("isFadeIn", false);
                direction.put("duration", durationMillis);

                directions.add(direction);
            }
}