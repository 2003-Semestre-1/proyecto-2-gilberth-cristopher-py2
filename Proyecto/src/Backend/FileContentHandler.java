/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;

import Models.Instruction;
import javax.swing.JTable;
/**
 *
 * @author jimon,Cristopher
 */
public class FileContentHandler {
    
    public FileContentHandler(){
    }
    /*
    @SuppressWarnings("unchecked") //In this case that warinig is shown because I am parsing directly the result of new FileReader(pSelectedFileRoute)
    public ArrayList<Instruction> getFileContent(String pSelectedFileRoute) throws FileNotFoundException, IOException{
         ArrayList<Instruction> instructionList= new ArrayList<Instruction>();
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(pSelectedFileRoute));
            String currentLine;
           
            while((currentLine = objReader.readLine() ) !=null){
                if(!currentLine.equals("")){
                    String [] instructionArray = currentLine.split(" ");
                    String operationName = instructionArray[0];
                    String operationRegister = instructionArray[1].substring(0, 2);
                    int operationValue = (operationName.equals("MOV")) ?  Integer.parseInt(instructionArray[2]) : 0;
                    int weight = detectWeight(operationName);
                    Instruction test = new Instruction(currentLine,operationName,
                            operationRegister,operationValue,
                            weight);
                    instructionList.add(test);   
                }
            }
            objReader.close();
            return instructionList; 
        } catch (IOException e) {
            System.out.println("Error: "+ e.getMessage()); //Aqui va a un mensaje de error
        }
        return instructionList;
    }
*/
    public ArrayList<Instruction> getFileContent(String fileName) throws IOException {
        ArrayList<Instruction> instructions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                Instruction instruction = parseInstruction(line);
                instructions.add(instruction);
            }
        }
        return instructions;
    }
    
   public Instruction parseInstruction(String instructionLine) {
    String[] parts = instructionLine.split(" ");
    for (int i = 0; i < parts.length; i++) {
        parts[i] = parts[i].replace(",", "");
    }
    Instruction instruction = null;
    if (parts.length == 2) {
        instruction = new Instruction(parts[0], parts[0], parts[1], 0, 1);
    }
    if (parts.length > 2) {
        try {
            int value = Integer.parseInt(parts[2]);
            instruction = new Instruction(parts[0], parts[0], parts[1], value);
        } catch (NumberFormatException e) {
            instruction = new Instruction(parts[0], parts[0], parts[1], parts[2]);
        }
    }
    
    return instruction;
    }


    
    
    
    public int detectWeight(String OperationName){
        switch (OperationName) {
            case "MOV":
            case "INC":
            case "DEC":
            case "SWAP":
            case "INT":    
            case "PUSH":    
            case "POP":{ return 1;}
            
            case "LOAD":
            case "STORE":
            case "JMP":
            case "JNE":{ return 2;}
            
            case "ADD":
            case "SUB":
            case "PARAM":{ return 3;}
            default: return 0;
        }
    }
}
