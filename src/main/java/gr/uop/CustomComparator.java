package gr.uop;

import java.util.Comparator;

public class CustomComparator implements Comparator<String>{
private String[] item1;
private String[] item2;
private int num1,num2;
    public int compare(String s1,String s2){

        item1=s1.split(" ");    //splits the 2 strings
        item2=s2.split(" ");    //to get the last digits



        num1 = Integer.parseInt(item1[1]);  //stores the last digits
        num2 = Integer.parseInt(item2[1]);


        if(num1<=num2){             //Compares the 2 numbers and returns the appropriate string
            return 0;

        }


        return 1;
    }



    
}
