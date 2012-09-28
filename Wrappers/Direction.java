/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Wrappers;

import java.util.HashMap;

/**
 *
 * @author Mecha Fox
 */
public class Direction extends HashMap<String, Object>
{

    //Directions (for scenes)
        public static HashMap<Thread, Cue> tempCues = new HashMap<Thread, Cue>();
        public static HashMap<Thread, CueGroup> concCues = new HashMap<Thread, CueGroup>();

    //Concurrent or sequential
        public static HashMap<Thread, Boolean> directionsAreConcurrent = new HashMap<Thread, Boolean>();

    public Direction()
    {
        //Grab when from tempCue
        put("when", getThreadTempCue());

        //Make new cue to give off
        Cue cueToGiveOff = new Cue(false);
        put("cue", cueToGiveOff);

        //If directions are concurrent, put the new cue in a jic place
        if(theseDirectionsAreConcurrent())
            getThreadConcCue().add(cueToGiveOff);
        //If they're sequential, just put the cue in tempCue for the next dir
        else
            tempCues.put(Thread.currentThread(), cueToGiveOff);
    }

    public static void reset()
    {
        directionsAreConcurrent.put(Thread.currentThread(), false);
        tempCues.put(Thread.currentThread(), new Cue(true));
    }

    private static Cue getThreadTempCue()
    {
        if(!tempCues.containsKey(Thread.currentThread()))
        {
            tempCues.put(Thread.currentThread(), new Cue(true));
        }
        return tempCues.get(Thread.currentThread());
    }

    private static CueGroup getThreadConcCue()
    {
        if(!concCues.containsKey(Thread.currentThread()))
        {
            concCues.put(Thread.currentThread(), new CueGroup());
        }
        return concCues.get(Thread.currentThread());
    }

    public static boolean theseDirectionsAreConcurrent()
    {
        if(!directionsAreConcurrent.containsKey(Thread.currentThread()))
        {
            directionsAreConcurrent.put(Thread.currentThread(), false);
        }
        return directionsAreConcurrent.get(Thread.currentThread());
    }

    public static void removeThreadSpaces(Thread thread)
    {
        tempCues.remove(thread);
        concCues.remove(thread);
        directionsAreConcurrent.remove(thread);
    }

}
