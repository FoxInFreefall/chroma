/*
 * An invertable is a special Presence that belongs to a particular world. When
 * the invert method is called, it checks whether the Invertable belongs to it
 * or not. If it does, it appears. If not, it is invisible and non-interactable;
 * technically it's not even updated.
 *
 * If the Invertable belongs to world -1, it exists in every world.
 *
 * An Invertable can be sent to another world at any time.
 */

package Prop;

import Core.Theatrics.StageElements.Presences.Prop;

/**
 *
 * @author Mecha Fox
 */
public class Invertable extends Prop
{

    private int world = 0;
    private int activeWorld = 0;

    public Invertable(String imageName, int world)
    {
        super(imageName);
        this.world = world;
    }

    public void invert(int activeWorld)
    {
        this.activeWorld = activeWorld;
        if(activeWorld == world || world == -1)
            onstage = true;
        else
            onstage = false;
    }

    public void changeWorlds(int targetWorld)
    {
        world = targetWorld;
        invert(activeWorld);
    }

    public boolean exists()
    {
        return world == activeWorld || world == -1;
    }

}
