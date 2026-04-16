package controller;

import java.text.DecimalFormat;

public class InputValidator {

    public static boolean isPositiveDouble(String str) {
        try {
            double value = Double.parseDouble(str);
            return value > 0; // chỉ nhận số nguyên không âm
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isPositiveInteger(String str) {
        try {   
            double value = Integer.parseInt(str);
            return value >= 0; // chỉ nhận số nguyên không âm
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static double getDoublePrice(String str) {
        try {
            if(str.length() < 2){
                return -1.0;
            }
            char lastChar = str.toUpperCase().charAt(str.length() - 1);
            if (lastChar == 'B') {
                return Double.parseDouble(str.substring(0, str.length() - 1));

            } else {
                return -1.0;
            }
        } catch (NumberFormatException e) {
            return -1.0;
        }
    }

    public static boolean isValidText(String str) {
        for (char c : str.toCharArray()) {
            if (!(Character.isLetter(c) || c == ' ')) {
                return false;
            }
        }
        return true;
    }

    public static boolean isFrameID(String id) {
        String startID = id.substring(0, 1);
        String ID = id.substring(1);
        
        if(id.length() != 6){
            return false;
        }
        if(!startID.equals("F")){
            return false;
        }

        if (!isPositiveInteger(ID)) {
            return false;
        }
        return true;

    }
    
    
     public static boolean isEngineID(String id) {
        String startID = id.substring(0, 1);
        String ID = id.substring(1);
        
        if(id.length() != 6){
            return false;
        }
        if(!startID.equals("E")){
            return false;
        }

        if (!isPositiveInteger(ID)) {
            return false;
        }
        return true;

    }
     

    public static String formatFee(double fee) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(fee);
    }

}
