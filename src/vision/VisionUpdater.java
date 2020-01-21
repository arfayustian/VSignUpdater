/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vision;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import javax.swing.JOptionPane;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author Arfa
 */
public class VisionUpdater {

    public static void visionUpdater() throws IOException {

        Properties propsHost = new Properties();
        propsHost.load(new FileInputStream(new File("config/host.properties")));
        String server = propsHost.getProperty("ftp.server");
        String portString = propsHost.getProperty("ftp.port");
        int port = Integer.parseInt(portString);
        String username = propsHost.getProperty("ftp.username");
        String password = propsHost.getProperty("ftp.password");

        FTPClient fTPClient = new FTPClient();
        fTPClient.connect(server, port);
        fTPClient.login(username, password);
        fTPClient.enterLocalPassiveMode();
        fTPClient.setFileType(FTP.BINARY_FILE_TYPE);

        Properties propsResource = new Properties();
        propsResource.load(new FileInputStream(new File("config/Resource.properties")));
        //TODO get value last TextFile in Server
        String txtLast = propsResource.getProperty("path.txtLast");
        InputStream lastTxt = fTPClient.retrieveFileStream(txtLast);
        Scanner valueLast = new Scanner(lastTxt);
        String numberLast = valueLast.nextLine();
        System.out.println(numberLast);

        //TODO get value update TextFile in local VSign
        String txtUpdate = propsResource.getProperty("path.txtUpdate");
        File versionFile = new File(txtUpdate);
        Scanner valueVersion = new Scanner(versionFile);
        String numberVer = valueVersion.nextLine();
        System.out.println(numberVer);

        if (numberLast.equals(numberVer)) {
            System.out.println("Belum Ada Versi Terbaru");
            JOptionPane.showMessageDialog(null, "Belum Ada Versi Terbaru", "VisionSign Updater", JOptionPane.PLAIN_MESSAGE);
        } else {

            //TODO backup Dir before update
            BackupDirectory.backupDirectory();

            //TODO get path dir last in Server
            String pathUpdate = propsResource.getProperty("path.dirUpdate");
            String dirUpdaterPath = pathUpdate + numberLast;

            //TODO get path to Save dir in local VSign
            String pathSaveDir = propsResource.getProperty("path.saveDirUpdate");
            String dirSavePath = pathSaveDir;

            fTPClient.connect(server, port);
            fTPClient.login(username, password);
            fTPClient.enterLocalPassiveMode();
            fTPClient.setFileType(FTP.BINARY_FILE_TYPE);

            //TODO Download update
            UpdateDirectory.downloaddirectoryutil(fTPClient, dirUpdaterPath, "", dirSavePath);

            //TODO Rewrite TextFile update in local VSIgn with newer update dir lasr update
            FileWriter writer = new FileWriter(txtUpdate);
            writer.write(numberLast);
            writer.close();

            fTPClient.logout();
            fTPClient.disconnect();
        }
    }
}
