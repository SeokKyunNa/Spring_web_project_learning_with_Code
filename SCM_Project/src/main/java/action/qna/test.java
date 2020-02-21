package action.qna;

import java.util.ArrayList;
import java.util.List;

public class test {
	public static void main(String args[]){
		
		List<String> list = new ArrayList<String>();
		
		list.add(0, "123");
		list.add(1, "456");
		list.add(2, "789");
		System.out.println(list.size());
		
		if(list.size()==3){
			list.add(0, "0-1-2");
		}
		System.out.println(list.size());
		System.out.println(list.get(0));
/*		String str = "/2016-3-7/3qna20163713446979.jpg'"
					+ "/2016-3-7/2qna20163750643233.jpg'"
					+ "/2016-3-7/1qna20163720198097.jpg'"
					+ "/2016-3-7/4qna20163755531603.jpg'"
					+ "/2016-3-7/6qna20163755531603.jpg'"
					+ "/2016-3-7/9qna20163755531603.jpg'"
					+ "/2016-3-7/5qna20163755531603.jpg'"
					+ "/2016-3-7/15qna20163755531603.jpg'";
		
		String strArr[] = str.split("'");		
		
		for(int i=0; i<strArr.length-1; i++){
			int index = strArr[i].indexOf("q");
			int startNum = strArr[i].lastIndexOf("/")+1;
			for(int j=i+1; j<strArr.length; j++){
				int rightIndex = strArr[j].indexOf("q");
				if(Integer.parseInt(strArr[i].substring(startNum, index)) > Integer.parseInt(strArr[j].substring(startNum, rightIndex))){
					String temp = strArr[i];
					strArr[i] = strArr[j];
					strArr[j] = temp;
				}
			}
		}
		
		for(int i=0; i<strArr.length; i++){
			System.out.println(strArr[i]);
		}*/
	}
}
