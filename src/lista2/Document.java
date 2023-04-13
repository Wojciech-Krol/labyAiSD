package lista2;

import java.util.Objects;
import java.util.Scanner;

public class Document{
    public String name;
    public OneWayList<Link> links;
    public Document(String name, Scanner scan) {
        links=new OneWayList<>();
        this.name=name;
        load(scan);
    }
    public void load(Scanner scan) {
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
                    links.add(new Link(link));
                }
                link="";
            }
            line=scan.nextLine();
        }
    }
    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    private static boolean correctLink(String link) {
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

    @Override
    public String toString() {
        if(links.size()==0){
            return "Document: " + name;
        }
        String output= "Document: " + name + "\n";
        for(int i=0;i<links.size();i++){
            if(i+1==links.size())
                output+=links.get(i).ref;
            else
                output+=links.get(i).ref+"\n";
        }
        return output;
    }

}