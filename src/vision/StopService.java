/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vision;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Arfa
 */
public class StopService {
    
    public static void stopService() throws IOException, InterruptedException {
        try {
           //TODO Stop ViewService
           Runtime viewServiceStop = Runtime.getRuntime();
           viewServiceStop.exec("Taskkill /IM viewservice.exe /F");
           
           //TODO Stop VSignClient
           Runtime vSignClientStop = Runtime.getRuntime();
           vSignClientStop.exec("Taskkill /IM vsignclient.exe /F");
           
           //TODO STOP VSign Server Service
           String[] stop = {"cmd.exe", "/c", "sc", "stop", "VSignServer"};
           Process p = Runtime.getRuntime().exec(stop);
           p.waitFor();
           BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
           String line = reader.readLine();
           while (line != null) {
               System.out.println(line);
               line = reader.readLine();
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
