/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Scenes;

import Core.Theatrics.Scene;
import Core.Theatrics.StageElements.Presences.Backdrop;
import Menus.TitleMenu;

/**
 *
 * @author Kai Vulpes
 */
public class _01Title extends Scene
{

    //Run once at startup
    @Override
    public void setScene()
    {
        addActor("TitleMenu", new TitleMenu());
        addActor("logo", new Backdrop("Logo"));
        getActor("logo").setPos(100, 100);
    }

    //Triggered
    @Override
    public void script()
    {
        if(getActor("TitleMenu").just("chose:New"))
        {
            runBeat("newgame");
        }
        if(getActor("TitleMenu").just("chose:Load"))
        {
            
        }
    }

    //Called
    @Override
    public void runBeat(String beat)
    {
        if(beat.equals("action"))
        {
            getActor("logo").enterScene();
            getActor("logo").fadeIn(2500);

            delay(500);
        }

        else if(beat.equals("newgame"))
        {
            getActor("logo").fadeOut(500);
            changeScene(new _03LevelLoader());
        }
    }

}
