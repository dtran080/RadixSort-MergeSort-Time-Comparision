package mergesortcode;

import java.util.Random;
import java.io.RandomAccessFile;
import java.io.IOException;

public class SimpleMergeSort {

    private int size, digitNum;
    private int maxPass;
    private RandomAccessFile F, A, B;
    private StringBuffer runInfo;

    public SimpleMergeSort() throws IOException {
        this(1024,4);
    }

    public SimpleMergeSort(int size, int digitNum) throws IOException {
        this.size = size;		//# of number
        this.digitNum=digitNum;	//digit
        maxPass = (int) Math.ceil(Math.log(size) / Math.log(2));//max number of split merge
        createFiles();
        runInfo = new StringBuffer(2048);
        runInfo.append("MaxPass = " + maxPass + "\n\n");
    }

    private void createFiles() throws IOException {

        Random rand = new Random();
        F = new RandomAccessFile("F.bin", "rw");
        A = new RandomAccessFile("A.bin", "rw");
        B = new RandomAccessFile("B.bin", "rw");
        int largestNum=20;
        for (int i = 0; i < size; i++) { //range
        	int num=rand.nextInt((int)Math.pow(10, digitNum));
        	if (num>largestNum) largestNum=num;
            F.writeInt(num);                      
        }
       // System.out.printf("Largest number: %d\nNumber of split-merge: %d\n ",largestNum,maxPass);
        for (int i = 0; i < size / 2; i++) {
            A.writeInt(0);
            B.writeInt(0);
        }  // for

    }

    public void printData() throws IOException {

        F.seek(0L);
        for (int i = 0; i < size; i++) {
            System.out.println("" + F.readInt());
        }
    }

    public void mergesort() throws IOException {

        for (int pass = 0, count = 1; pass < maxPass; pass++, count *= 2) {
            split(F, A, B, count);
            merge(F, A, B, count);
        }
    }

    private void split(RandomAccessFile F, RandomAccessFile A, RandomAccessFile B, int n) throws IOException {
        F.seek(0L);
        A.seek(0L);
        B.seek(0L);

        runInfo.append("Split: " + n + "\n");

        for (int i = 0; i < size / (2 * n); i++) {

            for (int j = 0; j < n; j++) {
                A.writeInt(F.readInt());
            }

            for (int k = 0; k < n; k++) {
                B.writeInt(F.readInt());
            }
        }  // for
    }

    private void merge(RandomAccessFile F, RandomAccessFile A, RandomAccessFile B, int n) throws IOException {
        runInfo.append("Merge: " + n + "\n");

        int a, b;  // current values from subfiles A and B
        int aCount = 0, bCount = 0;  // count of remaining elements in the A and B subgroups

        F.seek(0L);
        A.seek(0L);
        B.seek(0L);

        for (int i = 0; i < size / (2 * n); i++) {
            aCount = bCount = n;

            a = A.readInt();
            b = B.readInt();

            while (aCount != 0 && bCount != 0) {
                if (a < b) {
                    F.writeInt(a);
                    aCount--;

                    if (aCount != 0) {
                        a = A.readInt();
                    }
                } else {
                    F.writeInt(b);
                    bCount--;

                    if (bCount != 0) {
                        b = B.readInt();
                    }
                }

            }  // while

            while (aCount-- != 0) {
                F.writeInt(a);
                if (aCount != 0) {
                    a = A.readInt();
                }
            }

            while (bCount-- != 0) {
                F.writeInt(b);
                if (bCount != 0) {
                    b = B.readInt();
                }
            }

        }  // for
    }

    public String getRunInfo() {
        return runInfo.toString();
    }

}  // SimpleMergeSort
