/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Storage;

import java.io.InputStream;
import java.util.HashMap;
import sun.audio.AudioStream;

/**
 *
 * @author Kai Vulpes
 */
public final class AudioStorage
{
    public HashMap<String, AudioStream> audio = new HashMap<String, AudioStream>();
//    public HashMap<String, Clip> audio = new HashMap<String, Clip>();

    public AudioStorage()
    {
        init();
    }

    private void init()
    {
        addAudio("world1", "resources/GraceK_Chroma1_0907.wav");
//        addAudio("world1", "resources/world_1.ogg");
    }

    public AudioStream getAudio(String audioName)
    {
        return audio.get(audioName);
    }

    public void addAudio(String audioName, String audioPath)
    {
        audio.put(audioName, getSrcAudio(audioPath));
    }

    public AudioStream getSrcAudio(String path)
    {
        AudioStream stream = null;

        try
        {
            InputStream file = getClass().getResourceAsStream("/" + path);
            stream = new AudioStream(file);
        }
        catch(Exception e) { e.printStackTrace(); }

        return stream;
    }
    
//    public Clip getSrcAudio(String path)
//    {
////        URL url = null;
////        try
////        {
////            url = new URL("C:/users/arque/my documents/netbeansprojects/abstraction/src/" + path);
////        }
////        catch (MalformedURLException ex) { Logger.getLogger(AudioStorage.class.getName()).log(Level.SEVERE, null, ex); }
////        Clip clip = (Clip) Applet.newAudioClip(url);
//
//
//        //Get path of audio
//        InputStream soundFile = getClass().getResourceAsStream("/" + path);
////        URL url = getClass().getResource(path); // Get the Sound URL
////        File soundFile = new File(url.toString().substring(url.toString().indexOf(":") + 1));
//
//        System.out.println("lskjdf" + soundFile);
//        //Get audio stream
//        AudioInputStream sound = null;
//        try
//        {
//            sound = AudioSystem.getAudioInputStream(soundFile);
//        }
//        catch (UnsupportedAudioFileException ex) {}
//        catch (IOException ex) {}
//
//        //Load audio stream into memory
//        DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
//        Clip clip = null;
//        try
//        {
//            clip = (Clip) AudioSystem.getLine(info);
//            clip.open(sound);
//        }
//        catch (LineUnavailableException ex) {}
//        catch (IOException ex) {}
//
//        // due to bug in Java Sound, explicitly exit the VM when
//        // the sound has stopped.
////        clip.addLineListener(new LineListener()
////        {
////            public void update(LineEvent event)
////            {
////                if (event.getType() == LineEvent.Type.STOP)
////                {
////                    event.getLine().close();
////                    System.exit(0);
////                }
////            }
////        });
////
////        // play the sound clip
////        clip.start();
//
//        return clip;
//    }
}
