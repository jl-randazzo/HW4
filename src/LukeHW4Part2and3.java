import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;


/*
 * I Machined this so that you could read the dmas or the regions as the keys and then let you output in preorder or Inorder
 * I just made it output to 'output.txt' no matter what. I only counted the first time my balance method was called in
 * theBalancingMachine rather than every time it was called in recursion.
* */

public class LukeHW4Part2and3 {

    public static void main(String[] args) throws IOException {
        String fileName = "dma.txt";
        String outputFile = "output.txt";
        String log = "";
        FileWriter fw = new FileWriter(outputFile);
        LukeBinaryTree3 Part2and3 = new LukeBinaryTree3();
        boolean selector;
        Scanner kb = new Scanner(System.in);
        String inp;

        while(true) {
            System.out.println("Please select the data you'd like to use as a key\n1)dma code\n2)region");
            inp = kb.nextLine();
            if(inp.equals("1")){selector = true; break;}
            else if(inp.equals("2")){selector = false; break;}
            else System.out.println("-----Invalid entry-----\n\n");
        }
        long startTime = System.currentTimeMillis();
        Part2and3.read(fileName, selector);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("\n\n--------------------------\n"+
                            " TOTAL CPU TIME: " + totalTime +
                            "\n TOTAL NODES: " + Part2and3.totalNodes +
                            "\n TOTAL REBALANCING OPERATIONS: " + Part2and3.balances +
                           "\n--------------------------");
        while(true){
            System.out.println("Please select the output algorithm you'd like to use\n1)Preorder\n2)Inorder");
            inp = kb.nextLine();
            if(inp.equals("1")){selector = true; break;}
            else if(inp.equals("2")){selector = false; break;}
            else System.out.println("-----Invalid entry-----\n\n");
        }

        fw.write("\n\n--------------------------\n"+
                " TOTAL CPU TIME: " + totalTime +
                "\n TOTAL NODES: " + Part2and3.totalNodes +
                "\n TOTAL REBALANCING OPERATIONS: " + Part2and3.balances +
                "\n--------------------------\n\n");
        Part2and3.output(selector, fw);
        fw.close();
        System.out.println("Your output has been saved in the root folder as 'output.txt'");

    }
}
