/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Core.Theatrics.StageElements.Presences;

import Core.Theatrics.StageElements.Presence;
import Wrappers.Direction;

/**
 *
 * @author Mecha Fox
 */
public class Null extends Presence
{

    public Null()
    {
        super("null", "null");
    }

    @Override
    public void setupElement()
    {
        
    }

    @Override
    public void behave()
    {
        
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

}
