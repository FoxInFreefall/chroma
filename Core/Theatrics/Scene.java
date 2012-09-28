/*
 * This is the template for a scene. When creating a scene, you can use the 
 * constructor to add the actors you want, and tell each of them what to do
 * based on cues from other actors, timing, etc.
 *
 * You can also use scene-wide directions, directions each actor must follow
 * when entering, acting, or exiting.
 */

package Core.Theatrics;

import Core.Theatrics.StageElements.Camera;
import Core.Stage;
import Core.Theatric;
import Wrappers.Direction;
import Wrappers.Force;
import Wrappers.HashList;
import java.util.ArrayList;

/**
 *
 * @author Kai Vulpes
 */
public abstract class Scene extends Theatric
{
    /***** VARIABLES *****/

        //Actors
//            public HashMap<String, StageElement> actors = new HashMap<String, StageElement>();
            public HashList<StageElement> actors = new HashList<StageElement>();

        //Cameras
            public Camera currentCam = null;

        //Permanent: Place a checkpoint to record that something has happened
            public ArrayList<String> checkpoints = new ArrayList<String>();

        //Super forces acting on all elements
            public ArrayList<Force> worldForces = new ArrayList<Force>();

        //Gravity (faux force)
            public Force gravity = new Force(0.33, Math.toRadians(-90));

            
    /***** CONSTRUCTOR *****/
            
            public Scene() {}

            public void init()
            {
                init("action");
            }

            public void init(String initialBeat)
            {
                //Set up camera
                addActor("Main Camera", new Camera("Main Camera"));
                currentCam = (Camera) getActor("Main Camera");
                getActor("Main Camera").states.add("active");

                setScene();
                runBeat(initialBeat);
            }

            public void exit()
            {
                for(StageElement element : actors)
                    element.cancelAllDirections();
                cancelAllDirections();
            }

            
    /***** OVERRIDABLE *****/

        //Run once at startup (add actors)
            public abstract void setScene();

        //Looped: Used to direct actors in case a cue is called
            public abstract void script();

        //Triggered: Blocks of direction (should only be called from script())
            public abstract void runBeat(String beat);

            
    /***** EDITABLE *****/

        //Progress scene using forward direction
            protected boolean progress(Direction direction)
            {
                //Change scene
                if(direction.get("direction").equals("changeScene"))
                {
                    Stage.changeScene((Scene) direction.get("scene"));

                    return true;
                }

                //Switch cameras
                else if(direction.get("direction").equals("switchToCamera"))
                {

                    currentCam.states.remove("active");
                    currentCam = (Camera) direction.get("camera");
                    currentCam.states.add("active");

                    return true;
                }

                //Delay
                else if(direction.get("direction").equals("delay"))
                {
                    long currentTime = System.currentTimeMillis();
                    long delay = (Long) direction.get("delay");

                    if(!direction.containsKey("startTime"))
                        direction.put("startTime", currentTime);

                    if(currentTime - (Long) direction.get("startTime") >= delay)
                        return true;
                }

                return false;
            }

            
    /***** DO NOT TOUCH *****/

        //Dummy (not meant to extend past here, optimally shouldn't even exist in Scene)
            protected boolean progressSpecial(Direction direction)
            {
                return false;
            }
        

    /***** CONVENIENCE METHODS *****/

        //Add actors
            protected final void addActor(String name, StageElement actor)
            {
                actors.put(name, actor);
            }

        //Get actor
            public final StageElement getActor(String actorName)
            {
                return actors.get(actorName);
            }

        //Add a checkpoint (record that something has been completed)
            public final void addCheckpoint(String checkpoint)
            {
                if(!checkpoints.contains(checkpoint))
                    checkpoints.add(checkpoint);
            }

        //Check checkpoint
            public final boolean checkpointReached(String checkpoint)
            {
                return checkpoints.contains(checkpoint);
            }
            
        //Add a continuous force
            public void addForce(Force force)
            {
                if(!worldForces.contains(force))
                    worldForces.add(force);
            }

        //Remove a force
            public void removeForce(Force force)
            {
                worldForces.remove(force);
            }

            
    /***** DIRECTIONS (QUEUED, HEADERS, LOGIC DEFINED IN PROGRESS) *****/

        //Queue changing the scene
            protected final void changeScene(Scene scene)
            {                
                Direction direction = new Direction();
                direction.put("direction", "changeScene");
                direction.put("scene", scene);

                directions.add(direction);
            }

        //Switch to camera
            public final void switchToCamera(String cameraName)
            {
                Camera cam = (Camera) getActor(cameraName);
                if(cam != null)
                {
                    Direction direction = new Direction();
                    direction.put("direction", "switchToCamera");
                    direction.put("camera", cam);

                    directions.add(direction);
                }
            }

        //Delay next direction
            public final void delay(long millis)
            {
                Direction direction = new Direction();
                direction.put("direction", "delay");
                direction.put("delay", millis);

                directions.add(direction);
            }
}
