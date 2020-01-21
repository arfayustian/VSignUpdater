/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vision;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Arfa
 */
public class BackupDirectory {

    public static void backupDirectory() throws FileNotFoundException, IOException {

        //TODO get date to create dir backup
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        Scanner dateScanner = new Scanner(formatDate.format(date));
        String dateBackup = dateScanner.next();
        //TODO get version value textfile update to get last dir update in local 
        Properties propsResource = new Properties();
        propsResource.load(new FileInputStream(new File("config/Resource.properties")));
        String txtUpdate = propsResource.getProperty("path.txtUpdate");
        File versionFile = new File(txtUpdate);
        Scanner valueVersion = new Scanner(versionFile);
        String numberVer = valueVersion.nextLine();

        //Todo Backup dir before update
        String pathVsign = propsResource.getProperty("path.VSign");
        File srcDir = new File(pathVsign + numberVer);
        File destDir = new File(pathVsign + "Backup\\" + dateBackup + "\\" + numberVer);
        FileUtils.moveDirectory(srcDir, destDir);

    }

}
