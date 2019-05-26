package com.pl.pik.FTP;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.util.Base64;

public class FtpConnection {

    private final String ftpUsername = "borowik";
    private final String ftpPassword = "borowik";
    private final int ftpPort = 21;
    private final String ftpAddress = "10.1.243.220";


    public String getPassengerPicture(String filename) {
        FTPClient ftpClient;

        ftpClient = new FTPClient();
        String encodedFile = "";
        try {
            ftpClient.connect(this.ftpAddress, this.ftpPort);
            ftpClient.login(this.ftpUsername, this.ftpPassword);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType((FTP.BINARY_FILE_TYPE));

            String remoteFile = "/" + filename;

            byte[] buffer = new byte[1024];

            int length;

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            InputStream inputStream = ftpClient.retrieveFileStream(remoteFile);

            while ((length = inputStream.read(buffer)) != -1) {
                // write bytes from the buffer into output stream
                byteArrayOutputStream.write(buffer, 0, length);
            }

            byte[] fileBytes = byteArrayOutputStream.toByteArray();
            encodedFile = Base64.getEncoder().encodeToString(fileBytes);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return encodedFile;
    }
}
