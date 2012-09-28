/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Wrappers;

import Core.Theatric;

/**
 *
 * @author Mecha Fox
 */
public class Cue
{

    /***** VARIABLES *****/

        //Core Boolean
            private Boolean cueCalled = false;

        //Theatric piece
            private Theatric theatric;
        //CueName piece
            private String cueName;


    /***** CONSTRUCTORS *****/

        //Dummy: for cueGroup
        public Cue() {}

        //Wrapper: links and passes changeable Boolean back and forth
        public Cue(Boolean cueCalled)
        {
            this.cueCalled = cueCalled;
        }

        //Set: uses two pieces to obtain cue state
        public Cue(Theatric theatric, String cueName)
        {
            this.theatric = theatric;
            this.cueName = cueName;
        }


    /***** CUE METHODS *****/

        //Change state of core Boolean
            public void set(Boolean cueCalled)
            {
                this.cueCalled = cueCalled;
            }

        //Check if cue is essentially true
            public Boolean isCalled()
            {
                if(theatric != null && cueName.equals(""))
                    return theatric.just(cueName);
                else
                    return cueCalled;
            }

            @Override
            public String toString()
            {
                return super.toString() + ":" + cueCalled;
            }

}