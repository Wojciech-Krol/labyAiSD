package lista3;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Document{
    public String name;
    public TwoWayUnorderedListWithHeadAndTail<Link> link;
    public Document(String name, Scanner scan) {
        this.name = name;
        link = new TwoWayUnorderedListWithHeadAndTail<Link>();
        String line = scan.nextLine();
        while(!line.equals("eod")){
            linkMatcher(line).forEach((element) -> {
                link.add(element);
            });
            line = scan.nextLine();

        }
    }
    /**Metoda przyjmuje jedną linię tekstu, wyszukuje w niej poprawne "linki" i zwraca do konsoli kaźdy link w osobnej linii.*/
    public static List<Link> linkMatcher(String line){
        List<Link> matches = new ArrayList<Link>();
        Matcher m = Pattern.compile("link=(([a-zA-Z])[a-zA-Z0-9_]*)", Pattern.CASE_INSENSITIVE).matcher(line);
        while(m.find()){
            String match = m.group().toLowerCase().substring(5);
            Link link = new Link(match);
            matches.add(link);

        }
        return matches;

    }

    @Override
    public String toString() {
        StringBuilder links_to_string = new StringBuilder("Document: " + name);
        for(Link l:link){
            links_to_string.append("\n").append(l.toString());
        }
        return links_to_string.toString();
    }


    public String toStringReverse() {
        StringBuilder links_to_string = new StringBuilder("Document: " + name);
        for(int i = link.size()-1; i > -1; i--){
            links_to_string.append("\n").append(link.get(i).toString());
        }
        return links_to_string.toString();
    }

}
