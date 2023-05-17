/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gaming
 */
public class SongSuggester {

    /**
     * @param args the command line arguments
     */
    static Scanner input = new Scanner(System.in);
    static TreeNode root, temp;
    static ArrayList<TreeNode> Tree = new ArrayList<TreeNode>();

    public static TreeNode fromPreorder( BufferedReader br) throws IOException {
        int count = 0;
        String line= br.readLine();
        if (line.contains("?")) {
            Tree.add(new TreeNode(line));
            count = Tree.size()-1;
            System.out.println("Count==" + count);
            Tree.get(count).left = fromPreorder(br);
            System.out.println("Count==" + count + " Left==" + Tree.get(count).left.value);
            Tree.get(count).right = fromPreorder(br);
            System.out.println("Count==" + count + "Right==" + Tree.get(count).right.value);
            return Tree.get(count);
        }
        else
        {
            Tree.add(new TreeNode(line));
            System.out.println("size==" + (Tree.size()-1));
            return Tree.get(Tree.size()-1);
        }
        /*while ((line = br.readLine()) != null) {
            if (line.contains("?")) {
                
                    node = new TreeNode(line);
                    if (Tree.get(count).left == null) {
                        Tree.get(count).left = node;
                    } else {
                        Tree.get(count).right = node;
                    }
                    count++;

            } else {
                node = new TreeNode(line);
                 if (Tree.get(count).left == null) {
                        Tree.get(count).left = node;
                    } else {
                        Tree.get(count).right = node;
                    }
            }
        }*/
    }

    public static boolean askYesNo(String question) {
        System.out.println(question + "[y/n]");
        if (input.nextLine().equalsIgnoreCase("y")) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        // TODO code application logic here
        //Scanner input = new Scanner(System.in);
        File file = new File("suggestions.txt");
        BufferedWriter bw = null;
        PrintWriter pw = null;
        String strings = "";
        boolean flag = false;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            fromPreorder(br);
        } catch (IOException ex) {
            Logger.getLogger(SongSuggester.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
        ArrayList<TreeNode> Tree = new ArrayList<>();
        Tree.add(new TreeNode("Eine Kleine Nachtmusik"));
        Tree.add(new TreeNode("\"3 Romances\" by Clara Schumann"));
        Tree.add(new TreeNode("Are you a fan of Mozart?", Tree.get(0), Tree.get(1)));
        Tree.add(new TreeNode("\"Take Five\" by Dave Brubeck"));
        Tree.add(new TreeNode("Do you like classical music?", Tree.get(2), Tree.get(3)));*/
        TreeNode current;
        String question, song;
        for(TreeNode n : Tree)
        {
            System.out.println("-----------------------");
            System.out.println(n.value);
            if(!(n.left==null))
            {
                System.out.println(n.left.value);
            }
            if(!(n.right==null))
            {
                System.out.println(n.right.value);
            }
        }
        System.out.println("Welcome! Let's help you find a song.");
        do {
            current = Tree.get(0);
            while (current != null) {
                if (current.isLeaf()) {
                    if (askYesNo("Perhaps you would like " + current.getValue() + "\nIs this satisfactory?")) {
                        break;
                    } else {
                        System.out.println("What would you prefer instead?");
                        song = input.nextLine();
                        System.out.println("Tell me a question that distinguishes " + current.getValue() + " from " + song);
                        question = input.nextLine();
                        current.right = new TreeNode(current.getValue());
                        current.left = new TreeNode(song);
                        current.value = question;
                        break;
                    }
                } else {
                    if (askYesNo(current.getValue())) {
                        current = current.getLeft();
                    } else {
                        current = current.getRight();
                    }
                }
            }
            if (!askYesNo("Do you want to play again?")) {
                if (askYesNo("Do you want to save the current tree?")) {
                    flag = true;
                }
                break;
            }
        } while (true);
        /*if(file.delete())
        {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(SongSuggester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
        if (flag) {
            try {
                bw = new BufferedWriter(new FileWriter(file));
                pw = new PrintWriter(bw);
                strings = root.preOrder(root);
                // System.out.println(strings);
                String[] data = strings.split(",");
                for (int i = 0; i < data.length; i++) {
                    pw.println(data[i]);
                }
                pw.flush();
                pw.close();
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(SongSuggester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
