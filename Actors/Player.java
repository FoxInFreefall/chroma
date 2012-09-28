/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Actors;

import Core.Stage;
import Core.Theatrics.StageElements.Presences.Actor;
import Wrappers.Direction;
import java.awt.Color;

/**
 *
 * @author Mecha Fox
 */
public class Player extends Actor
{

    /***** VARIABLES *****/

        public double maxSpeed = 10;


    /***** CONSTRUCTOR *****/

        public Player()
        {
            super("Player", "char_w");
        }


    /***** OVERRIDING *****/

        @Override
        public void setupElement()
        {
            name = "Player";
            material = Material.rubber;

            //Physics
            mass = 1; //60/30 = 2;
//            maxSpeed = 5; //10/30 = 0.333;
        }

        @Override
        public void behave()
        {
//            physics1();

            //Running State truth checker
            boolean runningLeft = forces.containsKey("runningLeft");
            boolean runningRight = forces.containsKey("runningRight");
            if
            (
                (runningLeft && runningRight && forces.get("runningLeft").magnitude + forces.get("runningRight").magnitude != 0) ||
                (runningLeft && !runningRight) ||
                (!runningLeft && runningRight)
            )
                addState("running");
            else
                removeState("running");

            if(is("default"))
            {
                //FIND: blue border
//                display(Color.blue);
            }
            if(is("running"))
            {
                //Max running speed
                int xDir = velocity.x > 0 ? 1 : -1;
                if(Math.abs(velocity.x) > maxSpeed)
                    velocity.x = maxSpeed * xDir;

                //Flip image the right way
                if(xDir > 0)
                {
                    if(is("facingLeft"))
                    {
                        callCue("facedRight");
                        removeState("facingLeft");
                    }
                    addState("facingRight");
                    stamp.setFlip(false, null);
                }
                else
                {
                    if(is("facingRight"))
                    {
                        callCue("facedLeft");
                        removeState("facingRight");
                    }
                    addState("facingLeft");
                    stamp.setFlip(true, null);
                }
            }

//            physics2();
        }

        @Override
        public void react()
        {
            //A: run left
            if(Stage.controller.just("pressed:Left"))
                attachForce("runningLeft", 1000, Math.toRadians(180));
            else if(Stage.controller.just("released:Left"))
                detachForce("runningLeft");

            //D: run right
            if(Stage.controller.just("pressed:Right"))
                attachForce("runningRight", 1000, 0);
            else if(Stage.controller.just("released:Right"))
                detachForce("runningRight");

            //SPACE: jump
            if(Stage.controller.just("pressed:Space"))
            {
                if(is("grounded"))
                    burstForce("jump", 13, Math.toRadians(90), 100);
            }
            else if(Stage.controller.just("released:Space"))
                detachForce("jump");

            if(this.just("facedLeft"))
                stamp.swapImage("char_b");
            else if(this.just("facedRight"))
                stamp.swapImage("char_w");
        }

        @Override
        protected boolean progressSpecial(Direction direction)
        {
            return false;
        }
    
}
