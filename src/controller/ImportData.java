package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class ImportData {




     final static List<String[]> from(String filePath) {
        List<String[]> dataList = new ArrayList<>();
        String line;
        String syntaxSplitBy = ",";
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            
            while ((line = br.readLine()) != null) {
                // Check dong do rong khong, rong thi bo qua
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                String[] columns = line.split(syntaxSplitBy); // cat chuoi thanh mang
                dataList.add(columns);
            }
        } catch (IOException e) {
            System.err.println("Error when read data brand file: " + filePath);
        }
        return dataList;
    }
}