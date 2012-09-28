package Threads;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import Core.Theatrics.Scene;
import Core.Stage;
import Core.Theatric;
import Core.Theatrics.StageElement;
import Wrappers.Direction;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author Kai Vulpes
 */
public class React implements Runnable
{
    private Thread thread;
    private String cueToCall = null;
    private Theatric theatric = null;

    public React(String cueToCall, Theatric theatric)
    {
        this.cueToCall = cueToCall;
        this.theatric = theatric;
        thread = new Thread(this, "React thread: " + theatric + "." + cueToCall);
        thread.start();
    }

    public void run()
    {
        if(cueToCall != null)
        {
            theatric.setCue(cueToCall, true);
            Scene currentScene = Stage.currentScene;
            if(currentScene != null)
            {
                Direction.reset();

                //Read script, direct based on cues called
                currentScene.script();

                //Prompt actors to react
                for(StageElement element : currentScene.actors)
                    element.react();
            }

            theatric.setCue(cueToCall, false);
            Direction.removeThreadSpaces(thread);
        }

        try {
            thread.sleep(1);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
