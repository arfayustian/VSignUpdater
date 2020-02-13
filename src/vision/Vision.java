package vision;

/**
 *
 * @author Arfa
 */
public class Vision {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            StopService.stopService();
            VisionUpdater.visionUpdater();
            UpdateTextfile.updateTextfile();
            StartService.startService();
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
}
