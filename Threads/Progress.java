package Threads;

/*
 * A series of unconscious movements made to reach a conscious goal.
 */


import Core.Theatrics.Scene;
import Core.Stage;
import Core.Theatrics.StageElement;

/**
 *
 * @author Kai Vulpes
 */
public class Progress implements Runnable
{
    Thread thread;

    public Progress()
    {
        thread = new Thread(this, "Progress thread");
        thread.start();
    }

    public void run()
    {
        while(true)
        {
            //Make physical changes
            Scene currentScene = Stage.currentScene;
            if(currentScene != null)
            {
                currentScene.progressShell();
                
//                for(StageElement element : currentScene.actors)
                for(int i = 0; i < currentScene.actors.size() && currentScene.actors.get(i) != null; i++)
                    currentScene.actors.get(i).progressShell();
            }
            
            try {
                thread.sleep(((int)1000/30));
            } catch(Exception e) {
                e.printStackTrace();
            }
            
        }
    }
    
}
