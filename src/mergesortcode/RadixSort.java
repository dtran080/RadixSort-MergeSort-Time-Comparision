package mergesortcode;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

/**
 *
 * @author Dat Tran && Duy Tang
 */
public class RadixSort {
    private int size,  maxPass, Acount, Bcount, digitNum;
    private RandomAccessFile F,A,B;
    
    public RadixSort()throws IOException{
    	this(1024,2); //default
    }
    public RadixSort(int size,int digit)throws IOException{
        this.size=size;		//size of numbers
        this.digitNum=digit; //<--- number of digits 
        //System.out.printf("Number of digit: %d\nSize: %d\n",digitNum,size);
        createFile();
    }

    private void createFile() throws IOException{
        F=new RandomAccessFile("F.bin", "rw");
        A=new RandomAccessFile("A.bin","rw"); //0 column
        B=new RandomAccessFile("B.bin","rw"); //1 column
        int largestNum=0;
        for (int i=0;i<size;i++){
            int num=new Random().nextInt((int)Math.pow(10, digitNum)); //generate random number
            if (num>largestNum) largestNum=num; //find the largest number
        	F.writeInt(num); //range of num from 0 to 99
        }
        maxPass=(int)Math.ceil(Math.log(largestNum)/Math.log(2)); 
        //finding the number of split-merge by the largest number
      //  System.out.printf("Largest number: %d\nNumber of split-merge:%d\n ",largestNum,maxPass);
    }
    
    public void sorting() throws IOException{
        for (int i=0;i<maxPass;i++){
            split(F,A,B,i); 
            merge(F,A,B,i);
        }
    }

    private void split(RandomAccessFile F, RandomAccessFile A, RandomAccessFile B, int k) throws IOException{
        F.seek(0L); //seek to the beginning of the file
        A.seek(0L);
        B.seek(0L);
        int mask=0x00000001; //create mask
        mask<<=k; //shift mask to the compare digit in binary
        Acount=Bcount=0; //instantiate counters for column A and B
        for(int i=0;i<size;i++){	
            int num=F.readInt();
            if((num&mask)>>k==0){ //compare the k-position bit with mask 
                A.writeInt(num);//put number in A file if the digit is 0 
                Acount++;		//count elements in A
            } else {
                B.writeInt(num); //put number in B if the digit is 1
                Bcount++;//count elements in B
            }
        }
    }

    private void merge(RandomAccessFile F, RandomAccessFile A, RandomAccessFile B, int k) throws IOException{
        F.seek(0L); //seek to the beginning of files
        A.seek(0L);
        B.seek(0L);
        for(int i=0;i<Acount;i++){
            F.writeInt(A.readInt()); //put elements in file A to F first
        }
        for(int i=0;i<Bcount;i++){
            F.writeInt(B.readInt()); //put elements in file B to F
        }
       // System.out.printf("Split #:%d\t1st Column:%d \t2nd Column:%d\n",k+1, Acount,Bcount); 
        //inform the amount of elements split to file A and B
    }
    
    public void printData() throws IOException {
        F.seek(0L);//seek to the beginning to print all nummbers
        for (int i = 0; i < size; i++) {
            System.out.printf("%4s",F.readInt());
            if ((i+1)%36==0) System.out.println(); //println after display 36 elements
        }
        System.out.println();
    }
    
}
