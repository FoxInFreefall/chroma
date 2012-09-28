/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Core.Theatrics.StageElements;

import Actors.Player;
import Core.Stage;
import Core.Theatrics.StageElement;
import Wrappers.Direction;

/**
 *
 * @author Kai Vulpes
 */
public class Camera extends StageElement
{
    //TODO: remove, unecessary
    public Camera(String name)
    {
        super();
        this.name = name;
    }

    /***** VARIABLES *****/

        //Zoom / scale
            public double zoom = 1.0;


    /***** OVERRIDING *****/

        @Override
        public void setupElement()
        {
//            name = "camera";
        }
        
        @Override
        public void behave()
        {
            if(is("active"))
            {
                
            }

            if(is("active"))
            {
                Player player = (Player) Stage.currentScene.getActor("Player");
                if(player != null)
                {
                    x = Stage.currentScene.getActor("Player").x - Stage.width*0.5 + player.stamp.getWidth()*0.5;
                    y = Stage.currentScene.getActor("Player").y - Stage.height*0.5 + player.stamp.getHeight()*0.5;
                }
                

//                double zoomCompound = 0;
//                if(Controller.isHeld(KeyEvent.VK_CLOSE_BRACKET))
//                    zoomCompound += 0.01;
//                if(Controller.isHeld(KeyEvent.VK_OPEN_BRACKET))
//                    zoomCompound += -0.01;
//                if(zoomCompound != 0)
//                    zoom(zoomCompound);
            }
        }

        @Override
        public void react()
        {

        }

        @Override
        protected boolean progressSpecial(Direction direction)
        {
            if(direction.get("direction").equals("zoom"))
            {
                zoom += (Double) direction.get("zoomCompound");
                return true;
            }
            
            return false;
        }


    /***** DIRECTIONS *****/

        //Zoom
            public void zoom(double zoomCompound)
            {
                
                if(zoom + zoomCompound != 0)
                {
                    if(zoom + zoomCompound < 1.0)
                        zoomCompound = 1.0 - zoom;

//                    else if(zoom + zoomCompound > 1.0)
//                        zoomCompound = 1.0 - zoom;
                    
                    Direction direction = new Direction();

                    direction.put("direction", "zoom");
                    direction.put("zoomCompound", zoomCompound);

                    directions.add(direction);
                }
            }

}
