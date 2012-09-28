/* 
 * A Presence is a visible Stage Element, as opposed to sound or a camera.
 * Physics can be applied to it.
 *
 * The mass should be set in setupElement(). If the mass is 0, the Presence will
 * automatically be anchored, and no forces can move it.
 */

package Core.Theatrics.StageElements;

import Core.Stage;
import Core.Theatrics.StageElement;
import Core.Theatrics.StageElements.Presences.Backdrop;
import Wrappers.ByteStamp;
import Wrappers.Direction;
import Wrappers.Force;
import Wrappers.Force.Components;
import Wrappers.Vector2D;
import java.awt.Color;

/**
 *
 * @author Mecha Fox
 */
public abstract class Presence extends StageElement
{

    /***** VARIABLES *****/

        //Image
            public ByteStamp stamp = null;

        //Angle
            public double angle = 0;


    /***** PHYSICS VARIABLES *****/

        //Force (for movement, resets when move() is called)
            public double forceMag = 0;
            public double forceDir = 0;

        //Type of material (to be used for mu and friction)
            public Material material = Material.DEFAULT;

        //Velocities
            public Vector2D velocity = new Vector2D(0, 0);

        //Collision
            public class Collisions
            {
                public Presence left = null;
                public Presence right = null;
                public Presence top = null;
                public Presence bottom = null;

                public void reset()
                {
                    left = null;
                    right = null;
                    top = null;
                    bottom = null;
                }

                @Override
                public String toString()
                {
                    return left + ":" + right + ":" + top + ":" + bottom;
                }

            }
            public Collisions collisions = new Collisions();


    /***** MATERIALS *****/

        public enum Material
        {
            DEFAULT,
            asphalt,
            brakeMaterial,
            brick,
            concrete,
            glass,
            ice,
            leather,
            road,
            rubber,
            snow,
            steel,
            wood
        }
        

    /***** CONSTRUCTOR *****/

        public Presence(String name, String imageName)
        {
            super();
            name = imageName;
            setImage(imageName);
            addForce("gravity", new Force(5 * mass, Math.toRadians(-90)));
        }


    /***** OVERRIDING *****/

        @Override
        protected boolean progress(Direction direction)
        {
            //Rotate (persistant)
            if(direction.get("direction").equals("rotate"))
            {
                angle += (Double) direction.get("degrees");

                //0~360 range
                if(angle >= 360)
                    angle -= 360;
                else if(angle < 0)
                    angle += 360;

                if(!is((String) direction.get("stateRequired")))
                    return true;
            }

            //Fade in/out
            else if(direction.get("direction").equals("fade"))
            {
                if(!direction.containsKey("startTime"))
                    direction.put("startTime", System.currentTimeMillis());

                boolean isFadeIn = (Boolean) direction.get("isFadeIn");
                long timeElapsed = System.currentTimeMillis() - (Long) direction.get("startTime");
                long duration = (Long) direction.get("duration");

                if(timeElapsed > duration)
                {
                    if(isFadeIn)
                    {
                        callCue("fadedIn");
                    }
                    else
                    {
                        stamp.setAlpha(0);
                        callCue("fadedOut");
                    }
                    return true;
                }

                if(isFadeIn)
                    stamp.setAlpha((float) Math.sin((timeElapsed / (double)duration) * (Math.PI / 2)));
                else
                    stamp.setAlpha((float) Math.sin(((duration - timeElapsed) / (double)duration) * (Math.PI / 2)));
            }

            //Append-extend
            return super.progress(direction);
        }


    /***** CONVENIENCE *****/

        //Set image
            protected final void setImage(String imageName)
            {
                stamp = new ByteStamp(Stage.imageStorage.getImage(imageName));
            }


    /***** DO NOT TOUCH *****/
            
        //Move, according to current force
            public void physics1()
            {
                //Get resultant components
                Force resultant = Force.compoundForces(forces.values());
                Components components = resultant.getComponents();

                double xComp = components.cos;
                double yComp = components.sin;

                //Deadzone
                if(Math.abs(xComp) < 0.0001)
                    xComp = 0;
                if(Math.abs(yComp) < 0.0001)
                    yComp = 0;

                //Calculate friction (if there's movement, there's friction)
                if(velocity.x != 0 || velocity.y != 0)
                {
                    int xDir = velocity.x > 0 ? 1 : -1;
                    int yDir = velocity.y > 0 ? 1 : -1;

                    Vector2D friction = new Vector2D(0, 0);
                    if(velocity.x != 0)
                    {
                        friction.x = Math.abs(yComp) * -xDir; // * u
                        xComp += friction.x;
                    }
//                    if(velocity.y != 0)
//                    {
//                        friction.y = Math.abs(xComp) * -yDir; // * u
//                        yComp += friction.y;
//                    }
                }

                //Calculate acceleration
                double xAccl = xComp / mass;
                double yAccl = -yComp / mass;

                //Calculate new velocity
                velocity.x += xAccl;
                velocity.y += yAccl;

                //Deadzone 2
                if(Math.abs(velocity.x) < 0.5)
                    velocity.x = 0;
                if(Math.abs(velocity.y) < 0.5)
                    velocity.y = 0;
            }

            public void physics2()
            {
                //Collisions
                double bLeft = x + velocity.x;
                double bRight = x + stamp.getWidth() + velocity.x;
                double bTop = y + velocity.y;
                double bBottom = y + stamp.getHeight() + velocity.y;

                collisions.reset();

                for(StageElement element : Stage.currentScene.actors)
                {
                    if(element.onstage)
                    {
                        if(element instanceof Presence && !(element instanceof Backdrop))
                        {
                            if(element != this)
                            {
                                Presence presence = (Presence) element;
                                double pLeft = presence.x;
                                double pRight = presence.x + presence.stamp.getWidth();
                                double pTop = presence.y;
                                double pBottom = presence.y + presence.stamp.getHeight();

                                //TODO: account for Presence's velocities (where IT'S going to be)
                                //LEFT & RIGHT
                                if(y < pBottom)
                                {
                                    if(y + stamp.getHeight() > pTop)
                                    {
                                        //LEFT
                                        if(x >= pRight)
                                        {
                                            if(bLeft <= pRight)
                                            {
                                                if(collisions.left != null)
                                                {
                                                    if(pRight > collisions.left.x + collisions.left.stamp.getWidth())
                                                        collisions.left = presence;
                                                }
                                                else
                                                    collisions.left = presence;
                                            }
                                        }
                                        //RIGHT
                                        if(x + stamp.getWidth() <= pLeft)
                                        {
                                            if(bRight >= pLeft)
                                            {
                                                if(collisions.right != null)
                                                {
                                                    if(pLeft < collisions.right.x)
                                                        collisions.right = presence;
                                                }
                                                else
                                                    collisions.right = presence;
                                            }
                                        }
                                    }
                                }
                                //TOP & BOTTOM
                                if(x < pRight)
                                {
                                    if(x + stamp.getWidth() > pLeft)
                                    {
                                        //TOP
                                        if(y >= pBottom)
                                        {
                                            if(bTop <= pBottom)
                                            {
                                                if(collisions.top != null)
                                                {
                                                    if(pBottom > collisions.top.y + collisions.top.stamp.getHeight())
                                                        collisions.top = presence;
                                                }
                                                else
                                                    collisions.top = presence;
                                            }
                                        }
                                        //BOTTOM
                                        if(y + stamp.getHeight() <= pTop)
                                        {
                                            if(bBottom >= pTop)
                                            {
                                                if(collisions.bottom != null)
                                                {
                                                    if(pTop < collisions.bottom.y)
                                                        collisions.bottom = presence;
                                                }
                                                else
                                                    collisions.bottom = presence;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                //State required for jump
                if(collisions.bottom != null)
                    addState("grounded");
                else
                    removeState("grounded");

//                System.out.println(collisions);
                
                //TODO: DEBUG: Collision (bottom frame border)
//                if(y + stamp.getHeight() + velocity.y > Stage.height)
//                {
//                    y = Stage.height - stamp.getHeight();
//                    velocity.y = 0;
//                    addState("grounded");
//                }

                if(collisions.bottom != null)
                {
                    y = (collisions.bottom.y - stamp.getHeight());
                    velocity.y = 0;
                }
                if(collisions.top != null)
                {
                    y = (collisions.top.y + collisions.top.stamp.getHeight());
                    velocity.y = 0;
                }
                if(collisions.right != null)
                {
                    x = (collisions.right.x - stamp.getWidth());
                    velocity.x = 0;
                }
                if(collisions.left != null)
                {
                    x = (collisions.left.x + collisions.left.stamp.getWidth());
                    velocity.x = 0;
                }

                //Calculate new position
                x += velocity.x;
                y += velocity.y;

                //Out of bounds
            }


    /***** DIRECTIONS *****/

            //Rotate (persistant)
            public void rotate(double degrees, String stateRequired)
            {
                Direction oldDirection = null;
                for(Direction dir : directions)
                    if(((String) dir.get("direction")).equals("rotate"))
                    {
                        oldDirection = dir;
                        break;
                    }

                //If there's an old direction, update its degree amount
                if(oldDirection != null)
                {
                    oldDirection.put("degrees", degrees);
                }
                else
                {
                    Direction direction = new Direction();
                    direction.put("direction", "rotate");
                    direction.put("degrees", degrees);
                    direction.put("stateRequired", stateRequired);

                    directions.add(direction);
                }
            }


    /***** IFFY *****/

            public void display(Color color)
            {
                Stage.mise.setColor(color);
                Stage.mise.drawRect((int) x, (int) y, stamp.getWidth(), stamp.getHeight());
                display();
            }

        //Display an image
        //TODO: Queue image for buffer
            public void display()
            {
                if(onstage && visible)
                {
                    Camera camera = Stage.currentScene.currentCam;

//                    double nx = (image.getWidth(null) - image.getWidth(null) * camera.zoom) / 2 - camera.x;
//                    double ny = (image.getHeight(null) - image.getHeight(null) * camera.zoom) / 2 - camera.y;

                    double nx = x;
                    double ny = y;
                    if(!is("fixed"))
                    {
                        nx -= camera.x;
                        ny -= camera.y;
                    }

//                    Stage.mise.drawImage
//                    (
//                        image,
//                        (int) nx,
//                        (int) ny,
//                        (int) (image.getWidth(null) * camera.zoom),
//                        (int) (image.getHeight(null) * camera.zoom),
//                        null
//                    );

//                    BufferedImage bi = (BufferedImage) image;
//                    BufferedImage aimg = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TRANSLUCENT);
//                    Graphics2D g2d = (Graphics2D) aimg.getGraphics();
//
////                    g2d.rotate(Math.toRadians(angle));
//
//                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
//                    g2d.drawImage(bi, null, 0, 0);

//                    Graphics2D superCanvas = (Graphics2D) Stage.mise;
//                    superCanvas.rotate(Math.toRadians(angle));

//                    Stage.mise.drawImage(aimg, (int) nx, (int) ny, null);
                    stamp.stamp(Stage.mise, (int) nx, (int) ny);

//                    superCanvas.rotate(Math.toRadians(-angle));
                }
            }

}
