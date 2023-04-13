package lista5;

import java.util.*;
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
	public static Link createLink(String link) {
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
	public int[] getWeights() {
		int[] weights=new int[links.size()];
		int i=0;
		for(Link link:links) {
			weights[i] = link.weight;
			i++;
		}
		return weights;
	}
	public void showArray(int[] weights){
		//int[] weights = getWeights();
		boolean isFirst = true;
		for (int weight : weights) {
			if (isFirst) {
				System.out.print(weight);
				isFirst = false;
			} else {
				System.out.print(" ");
				System.out.print(weight);
			}
		}
		System.out.println();
	}
	public void bubbleSort(int[] weights){
		int n=weights.length;
		showArray(weights);
		for(int i=0;i<n;i++){
			for(int j=n-1;j>0;j--){
				if(weights[j-1]>weights[j]){
					int temp=weights[j-1];
					weights[j-1]=weights[j];
					weights[j]=temp;
				}
			}
			showArray(weights);
		}
	}
	public void selectSort(int[] weights){
		int n = weights.length;
		showArray(weights);
		// One by one move boundary of unsorted subarray
		for (int i = n-1; i >0; i--)
		{
			// Find the minimum element in unsorted array
			int max_idx = i;
			for (int j = i-1; j >=0; j--)
				if (weights[j]>weights[max_idx])
					max_idx = j;

			// Swap the found minimum element with the first
			// element
			int temp = weights[max_idx];
			weights[max_idx] = weights[i];
			weights[i] = temp;
			showArray(weights);
		}

	}
	public void insertSort(int[] weights){
		int n = weights.length;
		showArray(weights);
		for(int i=n-2;i>=0;i--){
			int key=weights[i];
			int j=i+1;
			while(j<n && weights[j]<key){
				weights[j-1]=weights[j];
				j++;
			}
			weights[j-1]=key;
			showArray(weights);
		}
	}

}

