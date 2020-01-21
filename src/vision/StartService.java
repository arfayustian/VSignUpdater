     /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vision;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author Arfa
 */
public class StartService {
    public static void startService(){   
{       
       try {
           
           Properties propsResource = new Properties();
           propsResource.load(new FileInputStream(new File("config/Resource.properties")));
           //TODO start ViewService exe
           String pathViewService = propsResource.getProperty("path.exeViewService");
           Runtime runTimeViewService = Runtime.getRuntime();
           runTimeViewService.exec(pathViewService);
           
           //TODO Start VSignClient.exe
           String pathVSignClient = propsResource.getProperty("path.exeVSignClient");
           Runtime runTimeVSignClient = Runtime.getRuntime();
           runTimeVSignClient.exec(pathVSignClient);
           
           //TODO Start VSign Server Service
           String[] stop = {"cmd.exe", "/c", "sc", "start", "VSignServer"};
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
}
