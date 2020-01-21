/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vision;

import java.io.IOException;

/**
 *
 * @author Arfa
 */
public class Vision {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        
        // TODO windows service
        // 1. stopVSService() && stopVSPrograms()
        // 2. startVSService || startVSPrograms()
        
        //TODO updater service
        // 1. executeUpdate();
        
        // TODO create executable -> register as windows service
        // exe4j 
        
       StopService.stopService();
       VisionUpdater.visionUpdater();
       StartService.startService();
        
        
    }
    
}

