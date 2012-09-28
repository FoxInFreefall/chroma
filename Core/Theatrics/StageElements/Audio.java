/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Core.Theatrics.StageElements;

import Wrappers.Direction;
import Core.Stage;
import Core.Theatrics.StageElement;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author Kai Vulpes
 */
public class Audio extends StageElement
{
    /***** VARIABLES *****/

        //Core
        public AudioStream audio = null;
//        public Clip audio = null;


    /***** CONSTRUCTOR *****/

        public Audio(String audioName)
        {
            super();
            name = audioName;
            setAudio(audioName);
        }


    /***** OVERRIDING *****/

        //Define (run once at startup)
            @Override
            public void setupElement() {}

        //Behave (triggered)
            @Override
            public void behave()
            {
                if(states.contains("default"))
                {

                }
            }

        //React (looped)
            @Override
            public void react()
            {

            }

        //Progress (looped)
            @Override
            protected boolean progressSpecial(Direction direction)
            {
                //Play audio only once
                if(direction.get("direction").equals("playAudio"))
                {
//                    if(!audio.isActive())
//                    {
//                        if(!((Boolean) direction.get("started")))
//                        {
//                            audio.start();
//                            direction.put("started", true);
//                        }
//                        else
//                            return true;
//                    }
                    AudioPlayer.player.start(audio);
                    return true;
                }
                else if(direction.get("direction").equals("loopAudio"))
                {
//                    try
//                    {
//                        if(audio == null)
//                            return false;
//                        ContinuousAudioDataStream cad = new ContinuousAudioDataStream(audio.getData());
//                        AudioPlayer.player.start(cad);
//                    }
//                    catch (IOException ex) { Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex); }
                    return true;
                }

                return false;
            }


    /***** CONVENIENCE METHODS *****/

        //Set image
            protected final void setAudio(String audioName)
            {
                audio = Stage.audioStorage.getAudio(audioName);
            }


    /***** DIRECTION *****/

        public void loop()
        {
//            audio.loop(Clip.LOOP_CONTINUOUSLY); // Play
            Direction direction = new Direction();
            direction.put("direction", "loopAudio");

            directions.add(direction);
        }

        public void stop()
        {
//            audio.stop(); // Stop
        }

        public void play()
        {            
            Direction direction = new Direction();
            direction.put("direction", "playAudio");
            direction.put("started", false);

            directions.add(direction);
        }

}
