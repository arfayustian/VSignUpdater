package vision;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.nio.charset.CodingErrorAction.REPLACE;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;
import javafx.scene.shape.Path;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
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

        Properties propsResource = new Properties();
        propsResource.load(new FileInputStream(new File("config/Resource.properties")));
        String txtUpdate = propsResource.getProperty("path.txtUpdate");
        String txtLast = propsResource.getProperty("path.txtLast");
        String pathVSign = propsResource.getProperty("path.VSign");

        FTPClient fTPClient = new FTPClient();
        fTPClient.connect(server, port);
        fTPClient.login(username, password);
        fTPClient.enterLocalPassiveMode();
        fTPClient.setFileType(FTP.BINARY_FILE_TYPE);

        //TODO get value last TextFile in Server
        InputStream lastTxt = fTPClient.retrieveFileStream(txtLast);
        Scanner valueLast = new Scanner(lastTxt);
        String numberLast = valueLast.nextLine();
        System.out.println("Versi Terbaru di server : " + numberLast);

        //TODO check availability update.txt
        File updateTextfile = new File(txtUpdate);
        boolean updateTextfileExists = updateTextfile.exists();

        //TODO get date to create dir backup
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        Scanner dateScanner = new Scanner(formatDate.format(date));
        String dateBackup = dateScanner.next();

        if (updateTextfileExists) {

            //TODO get value update TextFile in local VSign
            File versionFile = new File(txtUpdate);
            Scanner valueVersion = new Scanner(versionFile);
            String numberVer = valueVersion.nextLine();
            System.out.println("Versi Terbaru di local : " + numberVer);

            if (numberLast.equals(numberVer)) {
                System.out.println("Belum Ada Versi Terbaru");
            } else {

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

                //TODO check the availability of the directory that will be backup
                File lastDir = new File(pathVSign + numberVer);
                boolean exists = lastDir.exists();

                //TODO backup Dir before update
                if (exists) {
                    File srcDir = new File(pathVSign + numberVer);
                    File destDir = new File(pathVSign + "Backup\\" + dateBackup + "\\" + numberVer);
                    FileUtils.moveDirectory(srcDir, destDir);
                }
            }
        } else {

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
        }
        //TODO list file in new update folder
        String[] fileNames;
        File fileNewUpdate = new File(pathVSign + numberLast);
        fileNames = fileNewUpdate.list();
        for (String fileName : fileNames) {
            System.out.println(fileName);

            //TODO create backup filesbefore
            File srcFileNew = new File(pathVSign + fileName);
            File destFileNew = new File(pathVSign + "filesBeforeUpdate" + numberLast + "\\" + fileName);
            FileUtils.moveFile(srcFileNew, destFileNew);
        }
        //Todo Backup dir before update 2
        File srcDirNew = new File(pathVSign + "filesBeforeUpdate" + numberLast);
        File destDirNew = new File(pathVSign + "Backup\\" + dateBackup + "\\" + "filesBeforeUpdate" + numberLast);
        FileUtils.moveDirectory(srcDirNew, destDirNew);
        System.out.println("sd");

        //TODO copy file from new update dir to VSign
        for (String fileUpdateName : fileNames) {
            File srcCopyUpdate = new File(pathVSign + numberLast + "\\" + fileUpdateName);
            File destCopyUpdate = new File(pathVSign + fileUpdateName);
            FileUtils.copyFile(srcCopyUpdate, destCopyUpdate);
        }
    }
}
