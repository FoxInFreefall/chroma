package Core;

import Core.Theatrics.Scene;
import Storage.AudioStorage;
import Storage.ImageStorage;
import Core.Theatrics.Controller;
import Core.Theatrics.StageElement;
import Core.Theatrics.StageElements.Presence;
import Scenes._00Boot;
import Threads.Behave;
import Threads.Progress;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;



/**
 *
 * @author Mecha Fox
 */
public class Stage extends javax.swing.JFrame
{    
    
    /***** VARIABLES *****/

        //Storage
            public static ImageStorage imageStorage = new ImageStorage();
            public static AudioStorage audioStorage = new AudioStorage();

        //Controller
            public static Controller controller = new Controller();

        //Threads
            public Behave behave = new Behave(this);
            public Progress progress = new Progress();
            //React should be called from Theatrics' callCue()

        //Double-buffering
            public static Image offScreen = null;
            public static Graphics mise = null;
            public static Dimension dim;

        //Current scene -- scene currently being played out on stage
            public static Scene currentScene = null;

        //Dimensions
            public static int width = 0, height = 0;

    public Stage()
    {
        //Dimensions
//        width = 1080;
//        height = 720;
        width = 1920;
        height = 1080;

        //TODO: Initial scene, replace with properties file later
        changeScene(new _00Boot());

        //Connect controller
        addKeyListener(controller);
        
        //Double-buffering
        offScreen = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        mise = offScreen.getGraphics();

        setSize(width, height);
    }

    public static void changeScene(Scene scene)
    {
        if(currentScene != null)
            currentScene.exit();
        currentScene = scene;
        currentScene.init();
    }

    @Override
    public void paint(Graphics g)
    {
        if(currentScene != null)
        {
            mise.setColor(Color.white);
            mise.fillRect(0, 0, Stage.width, Stage.height);

            //Prompt actors to behave
//            for(StageElement actor : currentScene.actors)
            for(int i = 0; i < currentScene.actors.size(); i++)
            {
                StageElement actor = currentScene.actors.get(i);
                if(actor != null)
                {
                    if(actor instanceof Presence)
                    {
                        Presence presence = (Presence) actor;
                        if(presence.mass != 0)
                        {
                            presence.physics1();
                            presence.behave();
                            presence.physics2();
                        }
                        else
                            actor.behave();
                    }
                    else
                        actor.behave();

                    if(actor instanceof Presence)
                        if(actor.onstage)
                        {
                            Presence presence = (Presence) actor;
                            presence.display();
                        }
                }
            }
        }

        //Display mise
        g.drawImage(offScreen, 0, 0, this);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Breaking Ground");
        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 602, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 448, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    private static Stage launcher;
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                launcher = new Stage();
                launcher.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
