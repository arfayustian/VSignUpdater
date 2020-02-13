package vision;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author Arfa
 */
public class UpdateTextfile {

    public static void updateTextfile() throws FileNotFoundException, IOException {

        //TODO change TextFile update in local VSIgn with newer update dir lasr update 
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
        String txtLast = propsResource.getProperty("path.txtLast");
        InputStream lastTxt = fTPClient.retrieveFileStream(txtLast);
        Scanner valueLast = new Scanner(lastTxt);
        String numberLast = valueLast.nextLine();

        String txtUpdate = propsResource.getProperty("path.txtUpdate");
        File versionFile = new File(txtUpdate);

        versionFile.delete();
        String valueNewTextUpdate = numberLast;
        FileOutputStream newTextUpdate = new FileOutputStream(txtUpdate);
        newTextUpdate.write(valueNewTextUpdate.getBytes());
        newTextUpdate.flush();
        newTextUpdate.close();

    }
}
