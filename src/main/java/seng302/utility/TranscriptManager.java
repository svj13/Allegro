package seng302.utility;


import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Joseph on 3/03/2016.
 */
public class TranscriptManager {

    private String output;
    private ArrayList<String> transcriptContent = new ArrayList<String>();


    public void addText(String text){
        transcriptContent.add(text);
    }

    public ArrayList<String> getText(){
        return transcriptContent;
    }


    public void Save(String path, ArrayList<String> info){

        try {
            FileWriter writer =  new FileWriter(path, true); // the true variable means that if the file exists it will append to it not 100% sure thats what we want yet
            for (int i = 0; i < info.size(); i++) {
                writer.write(info.get(i));
            }
            writer.close();

        }catch(IOException ex) {
            System.out.println("problem writing to the selected file");
        }
    }



    public void Open(String path){

        transcriptContent.clear(); // they want it so that when a file is opened the transcript gets replaced with the new content

        try {
            FileReader reader =  new FileReader(path);
            BufferedReader input = new BufferedReader(reader);
            String str;
            while ((str = input.readLine()) != null) {
               transcriptContent.add(str);
            }


        }catch(IOException ex) {
            System.out.println("problem Reading from file");
        }
    }

    /**
     * sets the current command output with a provided command string.
     * @param s
     */
    public void setCommand(String s){
        output = s;
    }

    /**
     * Adds the command result to the current command output string.
     * @param s
     */
    public void setResult(String s){
        output += s + "\n";
    }

    public String getLastCommand(){
        return output;
    }


//    public static void main(String args[]){
//        TranscriptManager manager = new TranscriptManager();
//        ArrayList<String> words1 = new ArrayList<String>();
//        words1.add("command : response");
//        words1.add("command2 : response");
//        words1.add("command3 : response");
//        manager.Save("C:\\Users\\Joseph\\Desktop\\Tester123.txt",words1);
//
//        manager.Open("C:\\Users\\Joseph\\Desktop\\Tester123.txt");
//    }

}


