package Storage;

/*
 * TODO: combine with AudioStorage
 */


import java.awt.Image;
import java.io.InputStream;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author Mecha Fox
 */
public final class ImageStorage
{
    private HashMap<String,Image> images = new HashMap<String, Image>();

    public ImageStorage()
    {
        init();
    }

    public void init()
    {
        //Studio
        addImage("Studio", "resources/studio.jpg");

        //Null dummy
        addImage("null", "resources/null.png");

        //Logo
        addImage("Logo", "resources/logo.jpg");

        //Platform black 1b
        addImage("plat1b", "resources/platform1b_trim.png");

        //Char
        addImage("char_w", "resources/chart.png");
        addImage("char_b", "resources/chart.png");

        //Backround
        addImage("background_w", "resources/background_w.png");
        addImage("background_b", "resources/background_b.png");

        //Tiles
        addImage("block_w", "resources/block_w.png");
        addImage("block_b", "resources/block_b.png");
        addImage("block_g", "resources/block_g.png");
    }

    public Image getImage(String imageName)
    {
        return images.get(imageName);
    }

    public void addImage(String imageName, String imagePath)
    {
        images.put(imageName,getSrcImage(imagePath));
    }

    public Image getSrcImage(String path)
    {
        Image im = null;
        try
        {
            InputStream file = getClass().getResourceAsStream("/" + path);
            System.out.println("C:/users/Arque/my documents/netbeansprojects/abstraction/dist/" + path);
//            File file = new File("C:/users/Arque/my documents/netbeansprojects/abstraction/dist/" + path);
//            File file = new File("src/" + path);
            im = ImageIO.read(file);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return im;
    }
}
