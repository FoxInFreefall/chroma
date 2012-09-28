/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Wrappers;

import Core.Stage;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author Mecha Fox
 */
public class ByteStamp
{

    /***** VARIABLES *****/

        //Core
            private Image image = null;

        //Bytematerial
            private Image stamp = null;
            private Graphics canvas = null;

        //Settings
            private boolean isXFlipped = false;
            private boolean isYFlipped = false;
            private Vector2D scale = new Vector2D(1, 1);
            private float alpha = 1;


    /***** CONSTRUCTOR *****/

        public ByteStamp(Image image)
        {
            this.image = image;

            stamp = image;
            canvas = image.getGraphics();
        }


    /***** TRANSFORMATIONS *****/

        public void swapImage(String newImageName)
        {
            image = Stage.imageStorage.getImage(newImageName);
            restamp();
        }

        public void setFlip(Boolean isXFlipped, Boolean isYFlipped)
        {
            if(isXFlipped != null)
                this.isXFlipped = isXFlipped;
            if(isYFlipped != null)
                this.isYFlipped = isYFlipped;
            restamp();
        }

        public void flipHorizontally()
        {
            isXFlipped = !isXFlipped;
            restamp();
        }

        public void flipVertically()
        {
            isYFlipped = !isYFlipped;
            restamp();
        }

        public void setScale(float scaleX, float scaleY)
        {
            scale.x = scaleX;
            scale.y = scaleY;
            restamp();
        }

        public void setAlpha(float alpha)
        {
            this.alpha = alpha;
            restamp();
        }


    /***** INFO *****/

        public int getWidth()
        {
            return image.getWidth(null);
        }

        public int getHeight()
        {
            return image.getHeight(null);
        }


    /***** CORE *****/

        //Stamp every element to canvas at their positions
            public void restamp()
            {
                //Exception check
                if(scale.x < 0) scale.x = 0.0001;
                if(scale.y < 0) scale.y = 0.0001;

                //Create buffer image
                Image buffer = new BufferedImage((int) (image.getWidth(null) * scale.x), (int) (image.getHeight(null) * scale.y), BufferedImage.TRANSLUCENT);
                Graphics2D g2d = (Graphics2D) buffer.getGraphics();

                //Draw with transformations
                g2d.scale(scale.x * (isXFlipped ? -1 : 1), scale.y * (isYFlipped ? -1 : 1));
                if(isXFlipped) g2d.translate(-image.getWidth(null), 0);
                if(isYFlipped) g2d.translate(0, -image.getHeight(null));

                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g2d.drawImage((BufferedImage) image, null, 0, 0);

                //Replace
                stamp = buffer;
            }

        //Clear bytepaper
            public void clear()
            {
                image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                canvas = image.getGraphics();
            }

        //Stretch bounds
            public void stretch(int xBound, int yBound)
            {
                if
                (
                    xBound > image.getWidth(null) ||
                    yBound > image.getHeight(null)
                )
                {
                    int maxWidth = (xBound > image.getWidth(null) ? xBound : image.getWidth(null));
                    int maxHeight = (yBound > image.getHeight(null) ? yBound : image.getHeight(null));
                    
                    Image newStamp = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);
                    newStamp.getGraphics().drawImage(image, 0, 0, null);
                    image = newStamp;
                    canvas = image.getGraphics();
                }
            }

        //Stamp image onto a Graphics
            public void stamp(Graphics g, int x, int y)
            {
                g.drawImage(stamp, x, y, null);
            }
}
