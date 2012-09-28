/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Scenes;

import Prop.Invertable;
import Actors.Player;
import Core.Theatrics.Scene;
import Core.Theatrics.StageElement;
import Core.Theatrics.StageElements.Presence;
import Core.Theatrics.StageElements.Presences.Backdrop;

/**
 *
 * @author Mecha Fox
 */
public class _02TestLevel extends Scene
{

    @Override
    public void setScene()
    {
        addActor("Background", new Backdrop("background_w"));
        getActor("Background").addState("fixed");

        addActor("Plat1B", new Invertable("plat1b", 0));
        addActor("Player", new Player());
    }

    @Override
    public void script()
    {
        if(getActor("Player").just("facedRight"))
        {
            ((Presence) getActor("Background")).stamp.swapImage("background_w");
            for(StageElement element : actors)
            {
                if(element instanceof Invertable)
                {
                    Invertable invertable = (Invertable) element;
                    invertable.invert(0);
                }
            }
        }
        else if(getActor("Player").just("facedLeft"))
        {
            ((Presence) getActor("Background")).stamp.swapImage("background_b");
            for(StageElement element : actors)
            {
                if(element instanceof Invertable)
                {
                    Invertable invertable = (Invertable) element;
                    invertable.invert(1);
                }
            }
        }
    }

    @Override
    public void runBeat(String beat)
    {
        if(beat.equals("action"))
        {
            getActor("Background").enterScene();

            getActor("Plat1B").enterScene();
            getActor("Plat1B").setPos(300, 600);

            getActor("Player").enterScene();
            getActor("Player").setPos(100, 600);
        }
    }
    
}
