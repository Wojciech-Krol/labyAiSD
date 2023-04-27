package lista7;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Document implements IWithName{
	public static final int MODVALUE=100000000;
	public String name;
	public TwoWayCycledOrderedListWithSentinel<Link> link;
	public Document(String name) {
		this.name=name.toLowerCase();
		link =new TwoWayCycledOrderedListWithSentinel<Link>();
	}
	public Document(String name, Scanner scan){
		this.name=name.toLowerCase();
		link =new TwoWayCycledOrderedListWithSentinel<Link>();
		String line = scan.nextLine();
		while(!line.equals("eod")){
			linkMatcher(line).forEach((element) -> {
				link.add(element);
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
	if(this.link.size() == 0){
			return result;
	}
	int counter = 10;

	for (Link link : this.link) {
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
		if(this.link.size() == 0){
			return retStr;
		}
		ListIterator<Link> iter= link.listIterator();
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
		int[] weights=new int[link.size()];
		int i=0;
		for(Link link: link) {
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
		for(int i=0;i<n-1;i++){
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
		for (int i = n-1; i >0; i--)
		{
			int max= i;
			for (int j = i-1; j >=0; j--)
				if (weights[j]>weights[max])
					max = j;

			int temp = weights[max];
			weights[max] = weights[i];
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

	public void iterativeMergeSort(int[] weights){
		int n = weights.length;
		System.out.println(n);
		showArray(weights);
		for(int i=1;i<n;i*=2){
			for(int j=0;j<n;j+=2*i){
				int l=j;
				int m=Math.min(j+i-1,n-1);
				int r=Math.min(j+2*i-1,n-1);
				merge(weights,l,m,r);
			}
			showArray(weights);
		}
	}
	public void merge(int[] weights,int l,int m,int r){
		int n1=m-l+1;
		int n2=r-m;
		int[] L=new int[n1];
		int[] R=new int[n2];
		for(int i=0;i<n1;i++){
			L[i]=weights[l+i];
		}
		for(int i=0;i<n2;i++){
			R[i]=weights[m+1+i];
		}
		int i=0,j=0,k=l;
		while(i<n1 && j<n2){
			if(L[i]<=R[j]){
				weights[k]=L[i];
				i++;
			}else{
				weights[k]=R[j];
				j++;
			}
			k++;
		}
		while(i<n1){
			weights[k]=L[i];
			i++;
			k++;
		}
		while(j<n2){
			weights[k]=R[j];
			j++;
			k++;
		}
	}

	public void radixSort(int[] weights){
		int n = weights.length;
//		int max = weights[0];
//		for(int i=1;i<n;i++){
//			if(weights[i]>max){
//				max=weights[i];
//			}
//		}
		showArray(weights);
		int exp=1;
//		while(max/exp>0 || exp<1000){
		while(exp<1000){
			int[] output = new int[n];
			int i;
			int[] count = new int[10];
			for (i = 0; i < n; i++) {
				count[(weights[i] / exp) % 10]++;
			}
			for (i = 1; i < 10; i++) {
				count[i] += count[i - 1];
			}
			for (i = n - 1; i >= 0; i--) {
				output[count[(weights[i] / exp) % 10] - 1] = weights[i];
				count[(weights[i] / exp) % 10]--;
			}
			for (i = 0; i < n; i++)
				weights[i] = output[i];
			showArray(weights);
			exp*=10;
		}
	}

	public int hashCode(){
		int hash=name.charAt(0);
		int[] arr ={7,11,13,17,19};
		for(int i=1;i<name.length();i++){
			hash=(hash*arr[(i-1)%5]+name.charAt(i))%MODVALUE;
		}
		return hash;
	}

	@Override
	public String getName() {
		return name;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Document) {
			return name.equals(((Document) obj).name);
		}
		return false;
	}

}

