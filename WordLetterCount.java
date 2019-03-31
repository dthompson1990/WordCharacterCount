
//Darian Thompson
//Program counts the number of words and unique characters within a given text file.
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

import java.util.ArrayList;

public class WordLetterCount implements Runnable
{
   public static String filename;
   public static void setFile(String file)
   {
      filename = file;
   }
   //method to determine word count
   public int wordCount(String filename)
   {
      
      int wordCount = 0;
      try
      {
         String line = "";
         String [] wordArray;
         BufferedReader fileIn = new BufferedReader(new FileReader (filename));
         while ((line = fileIn.readLine()) != null)
         {
            wordArray = line.split(" ");
            wordCount = wordArray.length + wordCount;
         }
      }
      catch (FileNotFoundException er)
      {
         System.out.println("File not found.");
      }
      catch (IOException er)
      {
         System.out.println("Error found in input."); 
      }
      return wordCount;
   }
   //method to determine number of unique characters
   public static int uniqueCharacters (String filename)
   {
      int unique = 0;
      try
      {
         BufferedReader fileIn = new BufferedReader(new FileReader (filename));
         String line;
         String multiLine = "";
         while ((line = fileIn.readLine()) != null)
         {
            multiLine = multiLine + line;
         }
         multiLine.toLowerCase();
         line = "";
         for (int i = 0; i < multiLine.length(); i++)
         {
            if (line.indexOf(multiLine.charAt(i)) == -1)
               line = line + multiLine.charAt(i);
         }
         unique = line.length();
      }                              
      catch (FileNotFoundException er)
      {
         System.out.println("File not found.");
      }
      catch (IOException er)
      {
         System.out.println("Error found in input."); 
      }
      unique = unique - 1;
      if (unique > 26)
         unique = 26;   
      return unique;
   }
   public String fileContent (String filename)
   {
      String content = "";
      try
      {
         content = new Scanner(new File(filename)).useDelimiter("\\Z").next();
      }
      catch (FileNotFoundException e)
      {
         System.out.println("File not found.");
      }
      return content;
   }
   //output window for found files.
   public void outputWindow (String filename)
   {
      JFrame output = new JFrame("Results");
      int wordcount = wordCount(filename);
      int uniqueChar = uniqueCharacters(filename);
      String filetext = fileContent(filename);
      JLabel wordLabel = new JLabel("<html>Filename: " + filename + "\n   Word Count:" + wordcount + "\n   UniqueCharacters: " + uniqueChar + "</html>");
      JLabel contentLabel = new JLabel(filetext);
      JPanel panel = new JPanel();
      panel.add(wordLabel);
      panel.add(contentLabel);
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      output.add(panel);
      output.pack();
      output.setVisible(true);
   }
   public void run()
   {
      outputWindow(filename);
   }
   public static void main (String []args)
   {
      JFrame window = new JFrame("Please enter file names with add. Then use submit when all files are added.");
      JPanel panel = new JPanel();
      JTextField inputField = new JTextField();
      JButton addButton = new JButton("Add");
      JButton submitButton = new JButton("Submit");
      inputField.setColumns(40);
      window.add(panel);
      panel.add(inputField);
      panel.add(addButton);
      panel.add(submitButton);
      window.pack();
      window.setVisible(true);
      
      JFrame outputWindow = new JFrame("Results");
      JTextArea results = new JTextArea("Word Count: ");
      
      int wordCount = 0;
      String location = "";
      ArrayList <String> locationPaths = new ArrayList <String> ();
      addButton.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            locationPaths.add(inputField.getText());
            System.out.println("File Added");
         }
      });
      int n = 0;
      Runnable r = new WordLetterCount();
      Thread t = new Thread(r);
      submitButton.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            for (int i = 0; i < locationPaths.size(); i++)
            {
               setFile(locationPaths.get(i));
               t.run();
            }
         }
         });
      }
     }
