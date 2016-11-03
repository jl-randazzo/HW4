import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;

public class LukeBinaryTree3 {

    public Node root;
    private boolean selector;
    //public int height = 0;
    //public int minDepth;
    //public String parentGetter;
    //final boolean left = true;
    public int balances, totalNodes;

    public LukeBinaryTree3(){}

    private class Node{
        Node left, right, parent;
        int depth;
        String key;
        CityOb item; //this is the value
        public Node(String key, CityOb item, Node parent){
            this.key = key;
            this.item = item;
            this.parent = parent;
        }
    }

    /*public Node lr(boolean direction, Node x){
        if(direction == left)return x.left;
        else return x.right;
    }*/

    public int size(Node x){
        if(x==null)return 0;
        return(size(x.left)+size(x.right)+1);
    }

    public int minLength(Node x){
        if(x==null)return 0;
        int minLength = 1000;
        int distance = 1, tempDist = 0;
        for(Node y = x.left; y != null; y = y.left){
            distance++;
            tempDist = distance + minLength(y.right);
            //else tempDist = distance;
            if(minLength > tempDist)minLength = tempDist;
        }

        distance = 1;
        for(Node y = x.right; y != null; y = y.right){
            distance++;
            tempDist = distance + minLength(y.left);
            //else tempDist = distance;
            if(minLength > tempDist)minLength = tempDist;
        }
        return minLength;
    }

    public void add(CityOb item){
        totalNodes++;
        if(root==null){root = new Node(item.key, item, null); return;}
        else{
            Node x = root, y = null;
            int cmp = 0, depth = 0;
            while(x != null && !x.key.equals(item.key)){
                y = x;
                cmp = x.key.compareTo(item.key);
                if(cmp < 0)x = x.right;
                else x = x.left;
                depth++;
            }
            x = new Node (item.key, item, y);
            if(cmp < 0) y.right = x;
            else y.left = x;

            theBalancingMachine(x.parent, x, depth);

        }
    }

    public void theBalancingMachine(Node relRoot, Node x, int depth){
        Node y = x;
        for(int i = 1; i <= depth; i++){
            if(y == relRoot.right){
                if(minLength(relRoot.left) <= i - 2){
                    x = orient(relRoot, x);

                    if(relRoot.parent!=null) {
                        Node z = relRoot.parent;
                        balances++;
                        if (relRoot == z.right) {z.right = balance(relRoot); relRoot = z.right;}
                        else {z.left = balance(relRoot); relRoot = z.left;}
                        //relRoot.left = balance(relRoot.left);
                        //relRoot.right = balance(relRoot.right);
                    }
                    else {
                        balances++;
                        root = balance(root);
                    }

                }
            }

            else if(y == relRoot.left){
                if(minLength(relRoot.right) <= i - 2){
                    x = orient(relRoot, x);

                    if(relRoot.parent!=null) {
                        Node z = relRoot.parent;
                        balances++;
                        if (relRoot == z.right) {z.right = balance(relRoot); relRoot = z.right;}
                        else {z.left = balance(relRoot); relRoot = z.left;}
                        //relRoot.left = balance(relRoot.left);
                        //relRoot.right = balance(relRoot.right);
                    }
                    else {
                        balances++;
                        root = balance(root);
                    }

                }
            }
            y = relRoot;
            relRoot = relRoot.parent;
        }

    }


    public Node orient(Node relRoot, Node x){
        if(x.parent.parent.left!=null && x == x.parent.parent.left.right && x.parent.parent.left.left == null)
        {x.parent.parent.left = rotateLeft(x.parent); x = x.left;}
        else if(x.parent.parent.right!=null && x == x.parent.parent.right.left && x.parent.parent.right.right == null)
        {x.parent.parent.right = rotateRight(x.parent); x = x.right;}

        return x;
    }

    public Node balance(Node q){
        int i = size(q);
        if(i == 1) return q;

        if(i == 7 || i == 15 || i == 31 || i == 63 || i == 127 || i == 255 || i ==511){
            while(size(q.right) < size(q.left)){
                while(q.left.right!=null)q.left=rotateLeft(q.left);
                q = rotateRight(q);
            }
            while(size(q.left) < size(q.right)){
                while(q.right.left!=null)q.right=rotateRight(q.right);
                q = rotateLeft(q);
            }
            q.left = balance(q.left); q.right = balance(q.right); return q;
        }

        while(size(q.left) * 2 + 1 < size(q.right)) {
            while (size(q.left) + size(q.right.left) + 1 > size(q.right.right)) {
                if (q.right.left != null) q.right = rotateRight(q.right);
                else q.right.left = rotateLeft(q.right.left);
            }
            q = rotateLeft(q);
        }
        while(size(q.right) * 2 + 1 < size(q.left)) {
            while (size(q.right) + size(q.left.right) + 1 > size(q.left.left)) {
                if (q.left.right != null) q.left = rotateLeft(q.left);
                else q.left.right = rotateRight(q.left.right);
            }
            q = rotateRight(q);
        }

        q.left = balance(q.left); q.right = balance(q.right); return q;
    }



    public Node rotateLeft(Node x){
        Node y = new Node (x.key, x.item, x.parent);
        y.right = x.right;
        y.left = x.left;
        x.right.parent = x.parent;
        y.right = x.right.left;
        if(x.right.left!=null) x.right.left.parent=y;
        x.right.left = y;
        y.parent = x.right;
        x = null;
        return y.parent;
    }

    public Node rotateRight(Node x){
        Node y = new Node (x.key, x.item, x.parent);
        y.right = x.right;
        y.left = x.left;
        x.left.parent = x.parent;
        y.left = x.left.right;
        if(x.right.left!=null) x.right.left.parent=y;
        x.left.right = y;
        y.parent = x.left;
        x = null;
        return y.parent;
    }

    public Node get(String key){
        if(root.key.equals(key)){/*parentGetter = null;*/ return root;}
        else return get(key, root);
    }

    public Node get(String key, Node x){
        if(x.key.equals(key))return x;
        if(x == null)return null;
        int cmp = x.key.compareTo(key);
        if(cmp > 0) return get(key, x.left);
        else return get(key, x.right);
    }

    public void output(boolean selector, FileWriter fw) throws IOException{
        if(selector)preOrder(fw);
        else inOrder(fw);
    }

    public void preOrder (FileWriter fw) throws IOException{
        if(root!=null) preOrder(root, fw);
    }

    public void preOrder(Node current, FileWriter fw) throws IOException {
        if(current==null)return;
        fw.write(current.item.toString() +"\n");
        preOrder(current.left, fw);
        preOrder(current.right, fw);
    }

    public void inOrder (FileWriter fw) throws IOException{
        if(root!=null) preOrder(root, fw);
    }

    public void inOrder(Node current, FileWriter fw) throws IOException {
        if(current==null)return;
        preOrder(current.left, fw);
        fw.write(current.item.toString() + "\n");
        preOrder(current.right, fw);
    }

    /*public void delete(String key){
        boolean direction;
        Node temp = get(key); if(temp == null)return;
        Node parent = temp;
        int cmp = temp.key.compareTo(temp.parent.key);
        if(cmp < 0)direction = left;
        else direction = !left;
        if(temp.left==null){
            if (temp.right == null) temp = null;
            else{lr(direction, parent) = rotateLeft(temp); temp = null;}
        }
        else if(temp.right==null){lr(direction, parent) = rotateRight(temp); temp = null;}
    }*/


    public void read(String fileName, boolean selector) throws IOException{
        File file = new File(fileName);
        Scanner scn = new Scanner(file);
        CityOb temp;
        scn.nextLine();
        while(scn.hasNextLine()){
            temp = new CityOb(scn.nextLine(), selector);
            add(temp);
        }
        scn.close();
    }
}
