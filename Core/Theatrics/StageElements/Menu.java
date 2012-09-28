/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Core.Theatrics.StageElements;

import Core.Stage;
import Core.Theatrics.StageElement;
import Wrappers.Direction;
import java.util.HashMap;

/**
 *
 * @author Kai Vulpes
 */
public abstract class Menu extends StageElement
{
    /***** VARIABLES *****/

        //List of options
            protected HashMap<String, MenuOption> options;

        //Selected options
            protected MenuOption selected;

            
    /***** OVERRIDING *****/

        @Override
        public void init()
        {
            options = new HashMap<String, MenuOption>();
        }

        @Override
        public final void setupElement()
        {
            designMenu();
        }

        @Override
        public void behave()
        {
            if(is("default"))
            {
                for(MenuOption option : options.values())
                {
                    if(option == selected)
                    {
                        if(!option.is("selected"))
                            option.addState("selected");
                    }
                    else
                        option.removeState("selected");
                }

                //Prompt children
                for(MenuOption option : options.values())
                    option.behave();
            }
        }

        @Override
        public void react()
        {
            //Directional
            if(Stage.controller.just("pressed:Up"))
                if(selected.tabUp != null)
                    selected = selected.tabUp;
            if(Stage.controller.just("pressed:Left"))
                if(selected.tabLeft != null)
                    selected = selected.tabLeft;
            if(Stage.controller.just("pressed:Right"))
                if(selected.tabRight != null)
                    selected = selected.tabRight;
            if(Stage.controller.just("pressed:Down"))
                if(selected.tabDown != null)
                    selected = selected.tabDown;

            //Select
            if(Stage.controller.just("pressed:Enter"))
            {
                System.out.println("chose:" + selected.name);
                callCue("chose:" + selected.name);
            }
        }

        @Override
        protected boolean progressSpecial(Direction direction)
        {
            return false;
        }

        @Override
        public void progressShell()
        {
            super.progressShell();
            
            //Prompt children
            for(MenuOption option : options.values())
                option.progressShell();
        }


    /***** OVERRIDE *****/
        
        //Add & place options, set tabs
        protected abstract void designMenu();


    /***** CONVENIENCE *****/

        public void addOption(String name, MenuOption option)
        {
            option.name = name;
            options.put(name, option);
            option.parent = this;
            if(selected == null)
                selected = option;
        }

        public MenuOption getOption(String name)
        {
            return options.get(name);
        }

    //TODO: extends Message
    public abstract class MenuOption extends StageElement
    {
        /***** VARIABLES *****/

            public String text = "";

            public Menu parent = null;

            public MenuOption tabUp = null;
            public MenuOption tabLeft = null;
            public MenuOption tabRight = null;
            public MenuOption tabDown = null;


        /***** OVERRIDING *****/

            public void setupElement() {}

            @Override
            public void behave()
            {
                if(is("default"))
                {
                    display();
                }
            }

            @Override
            public void react()
            {

            }

            @Override
            protected boolean progressSpecial(Direction direction)
            {
                return false;
            }


        /***** DO NOT TOUCH *****/

            //TODO: draw temp image to Mise using alpha, etc.
            private void display()
            {
                if(visible)
                    designOption();
            }


        /***** OVERRIDE *****/

            //TODO: needs better name (?)
            protected abstract void designOption();


        /***** CONVENIENCE *****/

            public void setText(String text)
            {
                this.text = text;
            }

        }

}
