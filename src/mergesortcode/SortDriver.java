package mergesortcode;

import java.io.IOException;
/**
*
* @author		Dat Tran && Duy Tang
* @Objective: 	Sorting numbers written in a Random Access File using Radix Sort.
* 				Compare execution time to Merge Sort	
* 				Display result
* @version 		CS 201. UB Fall '15
*
*/
public class SortDriver {

    public static void main(String[] args) throws IOException {
    	int digitNum=4;	//numbers of digits
    	int size=1024*8; // total numbers of elements
        RadixSort rs=new RadixSort(size,digitNum); //overwritten constructor, instantiate Radix Sort
        //measure methods' completion time
        System.out.printf("Number of digit: %d\nSize: %d\n",digitNum,size);
        long startTime = System.nanoTime();
        rs.sorting(); //radix sort algorithm
        long endTime = System.nanoTime();
        long totalNanos = endTime - startTime;
        long minutes = totalNanos / 1000000000 / 60;
        totalNanos -= minutes * 60000000000L;
        long seconds = (int) (totalNanos / 1000000000.0);
        totalNanos -= seconds * 1000000000L;
        long milliSeconds = (int) (totalNanos / 1000000.0);
        System.out.printf("%nRadix Sort Time: %02d:%02d:%03d %n%n", minutes, seconds, milliSeconds);
        //rs.printData(); //print out result 
        
        System.out.println("-----------------------------------");
        SimpleMergeSort sms=new SimpleMergeSort(size,digitNum); //instantiate Merge Sort
        startTime = System.nanoTime();
        sms.mergesort(); //merge sort algorithm
        endTime = System.nanoTime();
        totalNanos = endTime - startTime;
        minutes = totalNanos / 1000000000 / 60;
        totalNanos -= minutes * 60000000000L;
        seconds = (int) (totalNanos / 1000000000.0);
        totalNanos -= seconds * 1000000000L;
        milliSeconds = (int) (totalNanos / 1000000.0);
        System.out.printf("%nMerge Sort Time: %02d:%02d:%03d %n%n", minutes, seconds, milliSeconds);
    }
}
