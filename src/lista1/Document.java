package lista1;

import java.util.Objects;
import java.util.Scanner;

public class Document {
    public static void loadDocument(String name, Scanner scan) {
        String line=scan.nextLine();
        String link ="";
        while(true) {
            if (Objects.equals(line, "eod")) {
               //System.out.println("END OF DOCUMENT");
                return;
            }
            for (int i = 0; i < line.length()-5; i++) {
               if(line.substring(i,i+5).equalsIgnoreCase("link=")){
                   i+=5;
                     while(i<line.length()&& line.charAt(i)!=' ' && line.charAt(i)!='\n'){
                         link+=line.charAt(i);
                         i++;
                     }
               }
               if(correctLink(link)){
                    System.out.println(link.toLowerCase());
               }
               link="";
            }
            line=scan.nextLine();
        }
    }



    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    public static boolean correctLink(String link) {
        if(link.length()<1){
            return false;
        }
        if((link.charAt(0)<'a' || link.charAt(0)>'z') && (link.charAt(0)<'A' || link.charAt(0)>'Z')){
            return false;
        }
        int i=1;
        while(i<link.length()){
            if((link.charAt(i)<'a' || link.charAt(i)>'z') &&( link.charAt(i)<'A' || link.charAt(i)>'Z') && (link.charAt(i)<'0' || link.charAt(i)>'9') && link.charAt(i)!='_' ){
                return false;
            }
            i++;
        }
        return true;
    }

}