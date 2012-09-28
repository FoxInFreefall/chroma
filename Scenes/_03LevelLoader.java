/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Scenes;

import Actors.Player;
import Core.Theatrics.Scene;
import Core.Theatrics.StageElement;
import Core.Theatrics.StageElements.Audio;
import Core.Theatrics.StageElements.Presence;
import Core.Theatrics.StageElements.Presences.Backdrop;
import Core.Theatrics.StageElements.Presences.Null;
import Invertables.BlackInvertable;
import Invertables.GrayInvertable;
import Invertables.WhiteInvertable;
import Prop.Invertable;
import Tools.XMLParseMachina;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/**
 *
 * @author Mecha Fox
 */
public class _03LevelLoader extends Scene
{

    @Override
    public void setScene()
    {
        addActor("Background", new Backdrop("background_w"));
        getActor("Background").addState("fixed");

        //Run xml parse core
        XMLParseMachina machina = new XMLParseMachina();
        Document doc = machina.parse("/resources/xml/testlevel.xml");

        NodeList entities = doc.getDocumentElement().getElementsByTagName("Entity");
        for(int i = 0; i < entities.getLength(); i++)
        {
            NamedNodeMap instructions = entities.item(i).getAttributes();

            Presence presence = new Null();
            String name = instructions.getNamedItem("Name").getNodeValue();
            if(name.equals("block_b"))
                presence = new BlackInvertable("block_b");
            else if(name.equals("block_w"))
                presence = new WhiteInvertable("block_w");
            else if(name.equals("block_g"))
                presence = new GrayInvertable("block_g");
            else if(name.equals("char_new_w"))
                presence = new Player();
            presence.x = Integer.parseInt(instructions.getNamedItem("xLocation").getNodeValue());
            presence.y = Integer.parseInt(instructions.getNamedItem("yLocation").getNodeValue());

            if(name.equals("char_new_w"))
                name = "Player";
            addActor(name, presence);
        }
        for(StageElement element : actors)
        {
            if(element instanceof Invertable)
            {
                Invertable inv = (Invertable) element;
                if(inv.exists())
                    inv.enterScene();
            }
        }
        addActor("Player", new Player());

        addActor("world1", new Audio("world1"));
    }

    @Override
    public void script()
    {
        if(getActor("Player").just("facedRight"))
            invert(0);
        else if(getActor("Player").just("facedLeft"))
            invert(1);
    }

    @Override
    public void runBeat(String beat)
    {
        if(beat.equals("action"))
        {
            getActor("Player").setPos(128, 2176);
            getActor("Player").enterScene();
            getActor("Background").enterScene();
            ((Audio)getActor("world1")).play();
        }
    }

    public void invert(int world)
    {
        if(world == 0)
            ((Presence) getActor("Background")).stamp.swapImage("background_w");
        else
            ((Presence) getActor("Background")).stamp.swapImage("background_b");
        for(StageElement element : actors)
        {
            if(element instanceof Invertable)
            {
                Invertable invertable = (Invertable) element;
                invertable.invert(world);
            }
        }
    }

}
