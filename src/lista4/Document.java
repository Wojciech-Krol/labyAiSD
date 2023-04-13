package lista4;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Document{
	public String name;
	public TwoWayCycledOrderedListWithSentinel<Link> links;
	public Document(String name, Scanner scan) {
		this.name=name.toLowerCase();
		links=new TwoWayCycledOrderedListWithSentinel<Link>();
		String line = scan.nextLine();
		while(!line.equals("eod")){
			linkMatcher(line).forEach((element) -> {
				links.add(element);
			});
			line = scan.nextLine();

		}
	}

	public static List<Link> linkMatcher(String line){
		ArrayList<Link> linksToReturn = new ArrayList<>();
		String[] links = line.split(" ");
		for(String s : links){
			if(s.toLowerCase().startsWith("link=")){
				String tempLink = s.substring(5);
				if(checkIfCorrectLink(tempLink)){
					linksToReturn.add(createLink(tempLink));
				}
			}
		}
		return linksToReturn;
	}

	public static boolean checkIfCorrectLink(String linkToCheck){
		return (linkToCheck.matches("([a-zA-Z]+[(][0-9]+[)])") || !linkToCheck.contains("("));
	}

	public static boolean isCorrectId(String id) {
		return Pattern.compile("^[A-Za-z]").matcher(id).find();
	}

	// accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
	private static Link createLink(String link) {
		String ref = "";
		int weight = 1;
		if(link.contains("(")){
			String[] values = link.split("[(]");
			ref = values[0].toLowerCase();
			weight = Integer.parseInt(values[1].substring(0,values[1].length()-1));
		}else{
			ref = link.toLowerCase();
		}
		return new Link(ref, weight);
	}

@Override
public String toString() {
	String result = "Document: " + name;
	if(this.links.size() == 0){
			return result;
	}
	int counter = 10;

	for (Link link : this.links) {
		if (counter < 10) {
			result += link.toString() + " ";
		} else {
			result = result.trim();
			result += "\n" + link.toString() + " ";
			counter = 0;
		}
		counter++;
	}
	result = result.trim();
	return result;
}

	public String toStringReverse() {
		String retStr="Document: "+name;
		if(this.links.size() == 0){
			return retStr;
		}
		ListIterator<Link> iter=links.listIterator();
		while(iter.hasNext())
			iter.next();

		int counter=10;

		while(iter.hasPrevious()){
			Link link=iter.previous();
			if(counter<10) {
				retStr += link.toString() + " ";
			}else {
				retStr=retStr.trim();
				retStr+="\n"+link.toString()+" ";
				counter=0;
			}
			counter++;
		}
		retStr=retStr.trim();
		return retStr;
	}
}

