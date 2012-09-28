package Core.Theatrics;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import Core.Theatric;
import Wrappers.Direction;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Kai Vulpes
 */
public class Controller extends Theatric implements KeyListener
{

    /***** VARIABLES *****/


    /***** KEYBOARD *****/

        public void keyTyped(KeyEvent e) {}

        public void keyPressed(KeyEvent e)
        {
            String cueName = KeyEvent.getKeyText(e.getKeyCode());
            if(!states.contains("holding:" + cueName))
            {
                callCue("pressed:" + cueName);
                states.add("holding:" + cueName);
            }
        }

        public void keyReleased(KeyEvent e)
        {
            String cueName = KeyEvent.getKeyText(e.getKeyCode());
            callCue("released:" + KeyEvent.getKeyText(e.getKeyCode()));
            states.remove("holding:" + cueName);
        }


    /***** DUMMIES *****/

        @Override
        protected boolean progressSpecial(Direction direction)
        {
            return false;
        }

        @Override
        protected boolean progress(Direction direction)
        {
            return false;
        }

}
