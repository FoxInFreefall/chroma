package Threads;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kai Vulpes
 */
public class Behave implements Runnable
{
    Thread thread;
    javax.swing.JFrame show;
    long startTime = 0;

    public Behave(javax.swing.JFrame show)
    {
        startTime = System.currentTimeMillis();
        this.show = show;
        thread = new Thread(this, "Behave & Repaint thread");
        thread.start();
    }

    public void run()
    {
        while(true)
        {
            //Update
            show.repaint();

            //FPS
            try {
                thread.sleep(((int)1000/30));
            } catch(Exception e) {
                e.printStackTrace();
            }

            //Quit javaw if screen is closed
            if(System.currentTimeMillis() - startTime > 500 && !show.isShowing())
                System.exit(0);
        }
    }
    
}
