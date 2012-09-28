/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Tools;

import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 *
 * @author Mecha Fox
 */
public class XMLParseMachina
{
    
    public Document parse(String xmlPath)
    {
        try
        {
            InputStream file = getClass().getResourceAsStream(xmlPath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            final Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            return doc;
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
