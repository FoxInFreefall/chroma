/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Scenes;

import Core.Theatrics.Scene;
import Core.Stage;
import Core.Theatrics.StageElements.Audio;
import Core.Theatrics.StageElements.Presence;
import Core.Theatrics.StageElements.Presences.Backdrop;

/**
 *
 * @author Mecha Fox
 */
public class _00Boot extends Scene
{

    @Override
    public void setScene()
    {
        addActor("Studio", new Backdrop("Studio"));
    }

    @Override
    public void script()
    {
        if(getActor("Studio").just("fadedIn"))
        {
            addCheckpoint("studioFadedIn");
        }

        if(Stage.controller.just("pressed:Space"))
        {
            if(checkpointReached("studioFadedIn"))
            {
                getActor("Studio").cancelAllDirections();
                getActor("Studio").exitScene();
            }
        }
    }

    @Override
    public void runBeat(String beat)
    {
        if(beat.equals("action"))
        {            
            delay(1000);

            ((Presence) getActor("Studio")).stamp.setAlpha(0);
            getActor("Studio").enterScene();
            getActor("Studio").fadeIn(2000);

            delay(500);

            getActor("Studio").fadeOut(500);

            changeScene(new _01Title());
        }
    }

}
