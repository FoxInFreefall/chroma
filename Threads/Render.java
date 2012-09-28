package Threads;

import Core.Stage;
import Core.Theatrics.StageElement;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kai Vulpes
 */
public class Render implements Runnable
{
    Thread thread;
    javax.swing.JFrame show;

    public Render(javax.swing.JFrame show)
    {
        this.show = show;
        thread = new Thread(this, "Render thread");
        thread.start();
    }

    public void run()
    {
        while(true)
        {            
            show.repaint();
            
            try {
                thread.sleep(((int)1000/30));
            } catch(Exception e) {
                e.printStackTrace();
            }
            
        }
    }
    
}
