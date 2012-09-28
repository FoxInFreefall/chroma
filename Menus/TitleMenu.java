/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Menus;

import Core.Stage;
import Core.Theatrics.StageElements.Menu;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Kai Vulpes
 */
public class TitleMenu extends Menu
{
    
    @Override
    protected void designMenu()
    {
        //Add New option
        addOption("New", new TitleMenuOption());
        getOption("New").setText("New Game");
        getOption("New").setPos(Stage.width/2, Stage.height/2);

        //Add Load option
        addOption("Load", new TitleMenuOption());
        getOption("Load").setText("Load Game");
        getOption("Load").setPos(Stage.width/2, Stage.height/2 + 75);

        //Setup tab system
        getOption("New").tabDown = getOption("Load");
        getOption("Load").tabUp = getOption("New");
    }

    class TitleMenuOption extends MenuOption
    {

        //TODO: draw to temp image, draw image to Mise so we use alpha, etc.
        @Override
        protected void designOption()
        {
            Font font = new Font("Comic Sans", Font.PLAIN, 50);
            Stage.mise.setFont(font);
            
            double center = Stage.mise.getFontMetrics().getStringBounds(text, Stage.mise).getWidth() / 2;

            if(is("selected"))
                Stage.mise.setColor(Color.red);
            else
                Stage.mise.setColor(Color.black);
            
            Stage.mise.drawString(text, (int) (x - center), (int) y);
        }

    }

}
