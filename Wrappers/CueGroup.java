/*
 * Used for concurrent direction
 */

package Wrappers;

import java.util.ArrayList;

/**
 *
 * @author Mecha Fox
 */
public class CueGroup extends Cue
{
    
    //List of Cues
        ArrayList<Cue> cues = new ArrayList<Cue>();

    public void add(Cue cue)
    {
        cues.add(cue);
    }

    //If all cues in this (concurrent) group are called, this group is called
    @Override
    public Boolean isCalled()
    {
        for(int i = 0; i < cues.size(); i++)
        {
            if(!cues.get(i).isCalled())
                return false;
        }

        return true;
    }
}