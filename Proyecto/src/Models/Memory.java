/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;


import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author jimon
 */
public class Memory {
    private int memorySize=0;
    private int actualMemoryPosition=0;
    private int availableInstruction=0;
    
    private ArrayList<Integer> stack = new ArrayList<>();
    private  HashMap<String, Integer> memoryRegister = new HashMap<String, Integer>(){{
        put("AC",0);
        put("AX",0);
        put("BX",0);
        put("CX",0);
        put("DX",0);
    }};
    
    
     public Memory(int pMemorySize){
         setMemorySize(pMemorySize);
         setInitialMemoryPosition();
         setAvailableInstruction();
         
    }
     
     public int[] executeLoad(Instruction pInstruction){
         memoryRegister.replace("AC",   memoryRegister.get(pInstruction.getInstructionRegister()));
         return getMemoryValues();
     }
     
     public int[] executeStore(Instruction pInstruction){
         memoryRegister.replace(pInstruction.getInstructionRegister(), memoryRegister.get("AC"));
         return getMemoryValues();
     }
     
     public int[] executeMov(Instruction pInstruction){
        if (pInstruction.getSecondaryRegister() != null) {
            memoryRegister.replace(pInstruction.getInstructionRegister(), memoryRegister.get(pInstruction.getSecondaryRegister()));
            System.out.println("asdasdasd"+memoryRegister.get(pInstruction.getInstructionRegister()));
        } else {
            System.out.println("registro que viene MOV:"+pInstruction.getInstructionRegister());
            memoryRegister.replace(pInstruction.getInstructionRegister(), pInstruction.getSecondaryValue());
            System.out.println("Instruccion MOV con nuevo valor"+memoryRegister.get(pInstruction.getInstructionRegister()));
        }
        return getMemoryValues();
    }
     
    public int[] executeSwap(Instruction pInstruction){
        String register1 = pInstruction.getInstructionRegister();
        String register2 = pInstruction.getSecondaryRegister();
        int temp = memoryRegister.get(register1);
        memoryRegister.replace(register1, memoryRegister.get(register2));
        memoryRegister.replace(register2, temp);
        return getMemoryValues();
    }


     
     public int[] executeSub(Instruction pInstruction){
         int acRegisterValue = memoryRegister.get("AC");
         memoryRegister.replace("AC",   acRegisterValue-memoryRegister.get(pInstruction.getInstructionRegister()));
         return getMemoryValues();
     }
     
     public int[] executeAdd(Instruction pInstruction){
         int acRegisterValue = memoryRegister.get("AC");
         memoryRegister.replace("AC",   acRegisterValue+memoryRegister.get(pInstruction.getInstructionRegister()));
         return getMemoryValues();
     }
     
     
     
     public int[] executeInc(){
        int acRegisterValue = memoryRegister.get("AC");
        memoryRegister.replace("AC",   acRegisterValue+1);
        return getMemoryValues();
    }

    public int[] executeInc(Instruction pInstruction){
        int registerValue = memoryRegister.get(pInstruction.getInstructionRegister());
        memoryRegister.replace(pInstruction.getInstructionRegister(),   registerValue+1);
        return getMemoryValues();
    }

     
     
     
     public int[] executeDec(Instruction pInstruction){
         int acRegisterValue = memoryRegister.get("AC");
         memoryRegister.replace("AC",   acRegisterValue-1);
         return getMemoryValues();
     }
     
     public int[] executePush(Instruction pInstruction){
         int saveValue = memoryRegister.get(pInstruction.getInstructionRegister());
         stack.add(0, saveValue);
         return getMemoryValues();
    }
     
    public int[] executePop(Instruction pInstruction){
         memoryRegister.replace(pInstruction.getInstructionRegister(),getStackValue());
         return getMemoryValues();
     }
     
     
     
     public int[] executeInt09H (int value){
         memoryRegister.replace("DX", value);
         return getMemoryValues();
     }
     public int[] executeDec() {
        int acRegisterValue = memoryRegister.get("AC");
        memoryRegister.replace("AC", acRegisterValue - 1);
        return getMemoryValues();
    }

    public int[] executeDecRegister(Instruction pInstruction) {
        int registerValue = memoryRegister.get(pInstruction.getInstructionRegister());
        memoryRegister.replace(pInstruction.getInstructionRegister(), registerValue - 1);
        return getMemoryValues();
    }
     
     
     public int[] getMemoryValues(){
         int[] intArray = new int[] {this.getMemoryPosition(),memoryRegister.get("AC"),memoryRegister.get("AX"),memoryRegister.get("BX"),
         memoryRegister.get("CX"),memoryRegister.get("DX")};
         this.actualMemoryPosition++;
         this.availableInstruction--;
         return   intArray;
     }
     
     public int getValue(String register){
        return   memoryRegister.get(register);
     }
     
    
    public int getAvailableInstruction(){
        return availableInstruction;
    }
    
    
    public int getMemoryPosition() {
        return this.actualMemoryPosition;
    }
    
    public int getStackValue(){
         if (stack.isEmpty()){return -1;}
         else {return stack.get(0);}
     }
   
    
    public int getMemorySize() {
        return memorySize-9;
    }
    
    public void setAvailableInstruction(){
        this.availableInstruction=getMemorySize();
    }
    
    
    public void setMemorySize(int pMemorySize) {
        this.memorySize = pMemorySize;
    }
    
     public void setInitialMemoryPosition() {
         if(this.memorySize==10){
             this.actualMemoryPosition=10;
         }else{
             int position=(int)(Math.random()*this.memorySize);
             while(position<10){
                 position=(int)(Math.random()*this.memorySize);
             }
             this.actualMemoryPosition=position;
             
         }
         
    }
     
     public void updateInicialPC(int necesaryMemory) {
         int totalFreeSpace = this.memorySize - necesaryMemory;
         this.actualMemoryPosition = (int) ((Math.random() * (totalFreeSpace - 1)) + 1);
     }
     
     public void resetMemoryRegister(){
         memoryRegister.replace("AC",0);
         memoryRegister.replace("AX", 0);
         memoryRegister.replace("BX",   0);
         memoryRegister.replace("CX",  0);
         memoryRegister.replace("DX",  0);
         actualMemoryPosition=0;
     
    }
     
     public int getMemoryDX(){
        int valorDX =  memoryRegister.get("DX");
        return valorDX;
     }
}
