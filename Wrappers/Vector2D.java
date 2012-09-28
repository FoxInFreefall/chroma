/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Wrappers;

/**
 *
 * @author Fox
 */
public class Vector2D
{
    public double x;
    public double y;

    public Vector2D(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString()
    {
        return x + ":" + y;
    }
}
