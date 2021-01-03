import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *Java class example
 * The class illustrates how to write comments used 
 * to generate JavaDoc documentation
 * @author priya
 */
public class MainClass
{
    FileInputStream fis;
    BufferedInputStream bis;
    
    /**
     *
     */
    public Player player;
    
    /**
     *
     */
    public long pauseLocation;

    /**
     *
     */
    public long song_tot_len;

    /**
     *
     */
    public String file_loc;
    
    /**
     *
     */
    public void stop()
    {
        if(player!=null)
        {
            player.close();
            pauseLocation=0;
            song_tot_len=0;
            
            music_player.Display.setText("");
        }
    }
    
    /**
     *
     * @throws IOException
     */
    public void pause() throws IOException
    {
        if(player!=null)
        {
            try
            {
            pauseLocation=fis.available();
            player.close();
            }
            catch(IOException ex)
            {
                Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE,null,ex);
                        
            }
        }
    }

    /**
     *
     * @param path
     * @throws FileNotFoundException
     * @throws JavaLayerException
     * @throws IOException
     */
    public void play(String path) throws FileNotFoundException, JavaLayerException, IOException
    {
        try
        {
            fis=new  FileInputStream(path);
            bis=new BufferedInputStream(fis);
            
            player=new Player(bis);
            song_tot_len=fis.available();
            file_loc = path + "";
            
        }
        catch(FileNotFoundException | JavaLayerException ex)
        {
            
        }
        
        new Thread()
        {
           @Override
           public void run()
           {
               try {
                   player.play();
                   
                   if(player.isComplete() && music_player.cnt==1)
                   {
                       play(file_loc);
                   }
                   if(player.isComplete())
                   {
                       music_player.Display.setText("");
                   }
                   
               } catch (JavaLayerException | IOException ex) {
                   Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
        }.start();
    }

    /**
     *
     * @throws FileNotFoundException
     * @throws JavaLayerException
     * @throws IOException
     */
    public void resume() throws FileNotFoundException, JavaLayerException, IOException
    {
        try
        {
            fis=new  FileInputStream(file_loc);
            bis=new BufferedInputStream(fis);
            
            player=new Player(bis);
            fis.skip(song_tot_len - pauseLocation);
        }
        catch(FileNotFoundException | JavaLayerException ex)
        {
            
        }
        
        new Thread()
        {
           @Override
           public void run()
           {
               try {
                   player.play();
               } catch (JavaLayerException ex) {
                   Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
        }.start();
    }
}
