package me.nrubin29.circlesorter;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

class UpdateChecker implements Runnable {

    private static final String VERSION = "0.1";

    private JFrame frame;

    public UpdateChecker(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void run() {
        String remoteVersion;

        try {
            URL url = new URL("https://github.com/nrubin29/circlesorter/raw/gh-pages/version.html?raw=true");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            remoteVersion = in.readLine();
            in.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (!remoteVersion.equals(VERSION)) {
            try {
                URL url = new URL("https://github.com/nrubin29/circlesorter/raw/gh-pages/circlesorter.jar?raw=true");
                ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                FileOutputStream fos = new FileOutputStream(new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()));
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                fos.close();
                rbc.close();
                JOptionPane.showMessageDialog(frame, "Updated. Quitting. Please reopen.");
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}