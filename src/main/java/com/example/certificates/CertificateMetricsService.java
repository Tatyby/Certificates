package com.example.certificates;


import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CertificateMetricsService {
    public List<String> getCertificateInfo(String keystorePath, String alias) {
        List<String> certificateInfo = new ArrayList<>();
        try {
            String command = String.format("keytool -list %s -keystore %s", alias, keystorePath);
          //  System.out.println("команда: " + command);
            Process process = Runtime.getRuntime().exec(command);
          //  System.out.println("запустился процесс: " + process.getClass().getName());
            OutputStream outputStream = process.getOutputStream();
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream));
            printWriter.print("");
            printWriter.flush();
            printWriter.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
          //  System.out.println("создали поток: " + reader.getClass().getName());
            String line;
            while ((line = reader.readLine()) != null) {
                certificateInfo.add(line);
            }
            int exitCode = process.waitFor();
        //    System.out.println("Процесс завершился с кодом: " + exitCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return certificateInfo;
    }
}
