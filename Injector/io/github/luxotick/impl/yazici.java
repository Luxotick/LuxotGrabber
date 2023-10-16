package io.github.luxotick.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class yazici {
    public static void main(String hmm){
        try {
            File logs = new File("C:\\Users\\Public\\Documents\\dothack.dev");
            FileWriter yazici = new FileWriter("C:\\Users\\Public\\Documents\\dothack.dev", true);
            System.out.println("C:\\Users\\Public\\Documents\\dothack.dev");
            boolean fileCreated = logs.createNewFile();
            String time = LocalDateTime.now().toString();
            yazici.flush();
            if(time.contains("T")){
                time = time.replace("T", " ");
            }
            if (time.contains(".")){
                time = time.substring(0, time.indexOf("."));
            }
            yazici.append("\n").append("[" + time + "] " + hmm);
            yazici.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
