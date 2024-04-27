/*
 * Represents the Processor which executes assembly commands
 * @author Kevin Meltzer
 * @version 1.7
 */
public class Processor {                                        

    // An array of words representing the registers
    private Word[] registers; 
    // Word representing a program counter
    private Word PC;
    // Word representing a stack pointer
    private Word SP;
    // Word which holds the current instruction
    private Word currentInstruction;
    // Bit which represents whether or not the run method should be halted
    private Bit halted;
    // Word which holds the current opcode
    private Word opcode;
    // Word which holds the destination register
    private Word rd;
    // Word which holds the destination register's data
    private Word rdData;
    // Word which holds the function
    private Word function;
    // Word which holds the first source register's data
    private Word rs1;
    // Word which holds the second source register's data
    private Word rs2;
    // Word which holds the immediate value
    private Word immediate;
    // Word which holds the result of the execute method
    private Word executionResult;
    // An alu to be used by the processor
    private ALU alu;
    // Word which is used to mask to get register values
    private Word mask;

    // Bits used to set the value of other bits inside of the word
    private Bit trueBit = new Bit(true);     
    private Bit falseBit = new Bit(false); 

    // Int to count the number of clock cycles
    public static int currentClockCycle = 0;

    /** 
     * @param i Index of desired register
     * @return Word at the specified register
     */    
    public Word getRegister(int i) {
        return registers[i];
    }

    /** 
     * A constructor for Processor which initializes the required words and bits.
     */
    public Processor() {
        registers = new Word[32];
        for (int i = 0; i<32; i++) {
            registers[i] = new Word();                                      
        }                                                                  
        PC = new Word();                                                    
        PC.set(0);                                                      
        SP = new Word();
        SP.set(1024);
        currentInstruction = new Word();
        halted = new Bit(false);
        opcode = new Word();
        rd = new Word();
        rdData = new Word();
        function = new Word();
        rs1 = new Word();
        rs2 = new Word();
        immediate = new Word();
        executionResult = new Word();
        alu = new ALU();
        mask = new Word();
        for (int i = 27; i < 32; i++) {
            mask.setBit(i, trueBit);
        }
    }

    /** 
     * While not halted, Fetches an instruction, decodes the instruction, executes the instruction
     * and then stores the results
     * @throws Exception 
     */
    public void run() throws Exception {                               
        while (!halted.getValue()) {
            fetch();
            decode();
            execute();
            store();
        }
        System.out.println("Clock cycles: " + currentClockCycle);
    }

    /**
     * Fetchs an instruction from memory and then increments the program counter
     */
    public void fetch() {
        currentInstruction.copy(InstructionCache.read(PC));
        PC.increment();
    }
    
    /** 
     * Gets the opcode, function, registers and immediate value from current instruction
     */
    public void decode() {
        opcode.copy(currentInstruction.and(mask));     // Masks to get opcode
        if (opcode.getBit(30).or(opcode.getBit(31)).not().getValue()) {   // 0R - 00                           
            immediate.copy(currentInstruction.rightShift(5));
            if (immediate.getBit(5).getValue()) {           // If the value is negative, this sign extends into 32 bits
                for (int i = 0; i < 5; i++) {
                    immediate.setBit(i, trueBit);
                }
            }
        } else if (opcode.getBit(30).not().getValue()) {                    // 1R - 01
            rd.copy(currentInstruction.rightShift(5).and(mask));
            rdData.copy(registers[getRegisterInt(rd)]);                                      
            mask.setBit(27, falseBit);                          // Shrinks the mask to 4 to get function
            function.copy(currentInstruction.rightShift(10).and(mask));
            mask.setBit(27, trueBit);                           // Sets the mask back to 5 
            immediate.copy(currentInstruction.rightShift(14));
            if (immediate.getBit(14).getValue()) {
                for (int i = 0; i < 14; i++) {
                    immediate.setBit(i, trueBit);
                }
            }
        } else if (opcode.getBit(31).getValue()) {                    // 2R - 11
            rd.copy(currentInstruction.rightShift(5).and(mask));
            rdData.copy(registers[getRegisterInt(rd)]);                                      
            mask.setBit(27, falseBit);                            
            function.copy(currentInstruction.rightShift(10).and(mask));
            mask.setBit(27, trueBit);                             // Sets the mask back to 5 to get the register
            rs1.copy(registers[getRegisterInt(currentInstruction.rightShift(14).and(mask))]);
            immediate.copy(currentInstruction.rightShift(19));
            if (immediate.getBit(19).getValue()) {
                for (int i = 0; i < 19; i++) {
                    immediate.setBit(i, trueBit);
                }
            }
        } else {                                                              // 3R - 10
            rd.copy(currentInstruction.rightShift(5).and(mask));
            rdData.copy(registers[getRegisterInt(rd)]);                                      
            mask.setBit(27, falseBit);                            
            function.copy(currentInstruction.rightShift(10).and(mask));
            mask.setBit(27, trueBit);                             
            rs2.copy(registers[getRegisterInt(currentInstruction.rightShift(14).and(mask))]);
            rs1.copy(registers[getRegisterInt(currentInstruction.rightShift(19).and(mask))]);
            immediate.copy(currentInstruction.rightShift(24));
            if (immediate.getBit(24).getValue()) {
                for (int i = 0; i < 24; i++) {
                    immediate.setBit(i, trueBit);
                }
            }
        }
    }

    /** 
     * Executes the instruction
     * @throws Exception 
     */
    public void execute() throws Exception {
        if (opcode.getBit(27).or(opcode.getBit(28)).or(opcode.getBit(29)).not().getValue()) {    // Math - 000
            executeMath();
        } else if (opcode.getBit(27).not().and(opcode.getBit(28).not()).and(opcode.getBit(29)).getValue()) {    // Branch - 001
            executeBranch();
        } else if (opcode.getBit(27).not().and(opcode.getBit(28)).and(opcode.getBit(29).not()).getValue()) {    // Call - 010
            executeCall();
        } else if (opcode.getBit(27).not().and(opcode.getBit(28)).and(opcode.getBit(29)).getValue()) {    // Push - 011
            executePush();
        } else if (opcode.getBit(27).and(opcode.getBit(28).not()).and(opcode.getBit(29).not()).getValue()) {  // Load - 100
            executeLoad();
        } else if (opcode.getBit(27).and(opcode.getBit(28).not()).and(opcode.getBit(29)).getValue()) {    // Store - 101
            executeStore();
        } else if (opcode.getBit(27).and(opcode.getBit(28)).and(opcode.getBit(29).not()).getValue()) {    // Pop/Peek - 110
            executePop();
        } else {
            long oldPC = PC.getUnsigned()-1;
            throw new Exception("Invalid opcode at PC = " + oldPC);
        }
    }
    
    /**
     * Stores the results
     * @throws Exception 
     */
    public void store() throws Exception {
        if (halted.getValue()) {
            System.out.println("HALT");
        } else if (opcode.getBit(27).or(opcode.getBit(28)).or(opcode.getBit(29)).not().getValue()) {            // Math - 000
            if (getRegisterInt(rd) != 0) {              // Ensures r0 always reads as 0 and cannot be overwritten
                registers[getRegisterInt(rd)].copy(executionResult); 
            }
            printResultsMath();
        } else if (opcode.getBit(27).not().and(opcode.getBit(28).not()).and(opcode.getBit(29)).getValue()) {    // Branch - 001
            if (executionResult.getSigned() < 0 || executionResult.getSigned() > 1023) {
                long oldPC = PC.getUnsigned()-1;
                throw new Exception("Invalid Branch at PC = " + oldPC + ", PC must be 0-1023");
            }
            PC.copy(executionResult);
            printResultsBranch();
        } else if (opcode.getBit(27).not().and(opcode.getBit(28)).and(opcode.getBit(29).not()).getValue()) {    // Call - 010
            if (executionResult.getSigned() < 0 || executionResult.getSigned() > 1023) {
                long oldPC = PC.getUnsigned()-1;
                throw new Exception("Invalid Call at PC = " + oldPC + ", PC must be 0-1023");
            }
            PC.copy(executionResult);
            printResultsCall();
        } else if (opcode.getBit(27).not().and(opcode.getBit(28)).and(opcode.getBit(29)).getValue()) {          // Push - 011
            SP.decrement();
            L2Cache.write(SP, executionResult);
            printResultsPush();
        } else if (opcode.getBit(27).and(opcode.getBit(28).not()).and(opcode.getBit(29).not()).getValue()) {    // Load - 100
            if (opcode.getBit(30).or(opcode.getBit(31)).not().getValue()) { // 0R - 00
                PC.copy(executionResult);
            } else if (getRegisterInt(rd) != 0) {              // Ensures r0 always reads as 0 and cannot be overwritten
                registers[getRegisterInt(rd)].copy(executionResult); 
            }
            printResultsLoad();
        } else if (opcode.getBit(27).and(opcode.getBit(28).not()).and(opcode.getBit(29)).getValue()) {          // Store - 101
            if (executionResult.getSigned() < 0 || executionResult.getSigned() > 1023) {
                long oldPC = PC.getUnsigned()-1;
                throw new Exception("Invalid Store at PC = " + oldPC + " Can only store in addresses 0-1023");
            }
            if (opcode.getBit(30).not().getValue()) {                                  // 1R - 01
                L2Cache.write(executionResult, immediate);
            } else if (opcode.getBit(31).getValue()) {                                 // 2R - 11
                L2Cache.write(executionResult, rs1);
            } else {                                                                     // 3R - 10
                L2Cache.write(executionResult, rs2);
            }
            printResultsStore();
        } else {                                                                                                      // Pop/Peek - 110
            if (getRegisterInt(rd) != 0) {              // Ensures r0 always reads as 0 and cannot be overwritten
                registers[getRegisterInt(rd)].copy(executionResult); 
            }
            printResultsPop();
        } 
    }
    
    
    /** 
     * Executes a math instruction
     * @throws Exception
     */
    private void executeMath() throws Exception {
        if (opcode.getBit(30).or(opcode.getBit(31)).not().getValue()) {         // 0R - 00
            halted.set(true);
            return;
        } else if (opcode.getBit(30).not().getValue()) {                          // 1R - 01
            executionResult.copy(immediate);
            return;
        } 

        // Checks if the function is valid for Math, if not throws an exception
        if (function.getBit(28).not().and(function.getBit(28).not().and(function.getBit(29)).and(function.getBit(30)).and(function.getBit(31)).not()).getValue()) {
            long oldPC = PC.getUnsigned()-1;
            throw new Exception("Invalid function for Math at PC = " + oldPC);
        }

        if (opcode.getBit(31).getValue()) {                                 // 2R - 11
            alu.op1.copy(rdData);
            alu.op2.copy(rs1);
            alu.doOperation(new Bit[]{function.getBit(28),function.getBit(29),function.getBit(30),function.getBit(31)});    // Preforms math operation
            aluClockAdd();
            executionResult.copy(alu.result);
        } else {                                                                    // 3R - 10
            alu.op1.copy(rs1);                              
            alu.op2.copy(rs2);
            alu.doOperation(new Bit[]{function.getBit(28),function.getBit(29),function.getBit(30),function.getBit(31)});    // Preforms math operation
            aluClockAdd();
            executionResult.copy(alu.result);
        }
    }

    /** 
     * Executes a branch instruction
     * @throws Exception
     */
    private void executeBranch() throws Exception {
        if (opcode.getBit(30).or(opcode.getBit(31)).not().getValue()) {         // 0R - 00
            executionResult.copy(immediate);
            return;
        } else if (opcode.getBit(30).not().getValue()) {                          // 1R - 01
            alu.op1.copy(PC);                              
            alu.op2.copy(immediate);
            alu.doOperation(new Bit[]{trueBit,trueBit,trueBit,falseBit});   // Adds PC and immediate value
            currentClockCycle += 2;
            executionResult.copy(alu.result);
            return;
        } 

        // Checks if the function is valid for Branch, if not throws an exception
        if (function.getBit(28).or(function.getBit(28).not().and(function.getBit(29)).and(function.getBit(30))).getValue()) {
            long oldPC = PC.getUnsigned()-1;
            throw new Exception("Invalid function for Branch at PC = " + oldPC);
        }
        
        if (opcode.getBit(31).getValue()) {                                 // 2R - 11
            alu.op1.copy(rs1);                              
            alu.op2.copy(rdData);
            alu.doOperation(new Bit[]{function.getBit(28),function.getBit(29),function.getBit(30),function.getBit(31)});   // Preforms boolean operation
            currentClockCycle += 2;
            if (alu.result.getBit(31).getValue()) {
                alu.op1.copy(PC);                              
                alu.op2.copy(immediate);
                alu.doOperation(new Bit[]{trueBit,trueBit,trueBit,falseBit});   // Adds PC and immediate value
                currentClockCycle += 2;
                executionResult.copy(alu.result);
            } else {
                executionResult.copy(PC);
            }
        } else {                                                                    // 3R - 10
            alu.op1.copy(rs1);                              
            alu.op2.copy(rs2);
            alu.doOperation(new Bit[]{function.getBit(28),function.getBit(29),function.getBit(30),function.getBit(31)});   // Preforms boolean operation
            currentClockCycle += 2;
            if (alu.result.getBit(31).getValue()) {
                alu.op1.copy(PC);                              
                alu.op2.copy(immediate);
                alu.doOperation(new Bit[]{trueBit,trueBit,trueBit,falseBit});   // Adds PC and immediate value
                currentClockCycle += 2;
                executionResult.copy(alu.result);
            } else {
                executionResult.copy(PC);
            }
        }
    }

    /** 
     * Executes a call instruction
     * @throws Exception
     */
    private void executeCall() throws Exception {
        if (opcode.getBit(30).or(opcode.getBit(31)).not().getValue()) {         // 0R - 00
            SP.decrement();
            L2Cache.write(SP, PC);
            executionResult.copy(immediate);
            return;
        } else if (opcode.getBit(30).not().getValue()) {                          // 1R - 01
            SP.decrement();
            L2Cache.write(SP, PC);
            alu.op1.copy(rdData);                              
            alu.op2.copy(immediate);
            alu.doOperation(new Bit[]{trueBit,trueBit,trueBit,falseBit});   // Adds rd and immediate value
            currentClockCycle += 2;
            executionResult.copy(alu.result);
            return;
        } 

        // Checks if the function is valid for Call, if not throws an exception
        if (function.getBit(28).or(function.getBit(28).not().and(function.getBit(29)).and(function.getBit(30))).getValue()) {
            long oldPC = PC.getUnsigned()-1;
            throw new Exception("Invalid function for Call at PC = " + oldPC);
        }

        if (opcode.getBit(31).getValue()) {                                 // 2R - 11
            alu.op1.copy(rs1);                              
            alu.op2.copy(rdData);
            alu.doOperation(new Bit[]{function.getBit(28),function.getBit(29),function.getBit(30),function.getBit(31)});   // Preforms boolean operation
            currentClockCycle += 2;
            if (alu.result.getBit(31).getValue()) {
                SP.decrement();
                L2Cache.write(SP, PC);  
                PC.decrement();                                             
                alu.op1.copy(PC);                              
                alu.op2.copy(immediate);
                alu.doOperation(new Bit[]{trueBit,trueBit,trueBit,falseBit});   // Adds PC and immediate value
                currentClockCycle += 2;
                executionResult.copy(alu.result);
            } else {
                executionResult.copy(PC);   
            }
        } else {                                                                    // 3R - 10
            alu.op1.copy(rs1);                              
            alu.op2.copy(rs2);
            alu.doOperation(new Bit[]{function.getBit(28),function.getBit(29),function.getBit(30),function.getBit(31)});   // Preforms boolean operation
            currentClockCycle += 2;
            if (alu.result.getBit(31).getValue()) {
                SP.decrement();
                L2Cache.write(SP, PC);
                alu.op1.copy(rdData);                              
                alu.op2.copy(immediate);
                alu.doOperation(new Bit[]{trueBit,trueBit,trueBit,falseBit});   // Adds rd and immediate value
                currentClockCycle += 2;
                executionResult.copy(alu.result);
            } else {
                executionResult.copy(PC);
            }
        } 
    }

    /** 
     * Executes a push instruction
     * @throws Exception
     */
    private void executePush() throws Exception {
        if (opcode.getBit(30).or(opcode.getBit(31)).not().getValue()) {         // 0R - 00
            long oldPC = PC.getUnsigned()-1;
            throw new Exception("Invalid opcode at PC = " + oldPC);
        }

        // Checks if the function is valid for Push, if not throws an exception
        if (function.getBit(28).not().and(function.getBit(28).not().and(function.getBit(29)).and(function.getBit(30)).and(function.getBit(31)).not()).getValue()) {
            long oldPC = PC.getUnsigned()-1;
            throw new Exception("Invalid function for Push at PC = " + oldPC);
        }

        if (opcode.getBit(30).not().getValue()) {                                  // 1R - 01
            alu.op1.copy(rdData);                              
            alu.op2.copy(immediate);
            alu.doOperation(new Bit[]{function.getBit(28),function.getBit(29),function.getBit(30),function.getBit(31)});   // Preforms math operation
            aluClockAdd();
            executionResult.copy(alu.result);
        } else if (opcode.getBit(31).getValue()) {                           // 2R - 11
            alu.op1.copy(rdData);                              
            alu.op2.copy(rs1);
            alu.doOperation(new Bit[]{function.getBit(28),function.getBit(29),function.getBit(30),function.getBit(31)});   // Preforms math operation
            aluClockAdd();
            executionResult.copy(alu.result);
        } else {                                                                     // 3R - 10
            alu.op1.copy(rs1);                              
            alu.op2.copy(rs2);
            alu.doOperation(new Bit[]{function.getBit(28),function.getBit(29),function.getBit(30),function.getBit(31)});   // Preforms math operation
            aluClockAdd();
            executionResult.copy(alu.result);
        }
    }

    /** 
     * Executes a load instruction
     * @throws Exception
     */
    private void executeLoad() throws Exception {
        if (opcode.getBit(30).or(opcode.getBit(31)).not().getValue()) {          // 0R - 00
            executionResult.copy(L2Cache.read(SP));
            SP.increment();
        } else if (opcode.getBit(30).not().getValue()) {                           // 1R - 01
            alu.op1.copy(rdData);                              
            alu.op2.copy(immediate);
            alu.doOperation(new Bit[]{trueBit,trueBit,trueBit,falseBit});   // Adds rd and immediate value   
            currentClockCycle += 2;
            executionResult.copy(L2Cache.read(alu.result));
        } else if (opcode.getBit(31).getValue()) {                           // 2R - 11
            alu.op1.copy(rs1);                              
            alu.op2.copy(immediate);
            alu.doOperation(new Bit[]{trueBit,trueBit,trueBit,falseBit});   // Adds rs1 and immediate value   
            currentClockCycle += 2;
            executionResult.copy(L2Cache.read(alu.result));
        } else {                                                                     // 3R - 10
            alu.op1.copy(rs1);                              
            alu.op2.copy(rs2);
            alu.doOperation(new Bit[]{trueBit,trueBit,trueBit,falseBit});   // Adds rs1 and rs2 
            currentClockCycle += 2;
            executionResult.copy(L2Cache.read(alu.result));
        }
    }

    /** 
     * Executes a store instruction
     * @throws Exception
     */
    private void executeStore() throws Exception {
        if (opcode.getBit(30).or(opcode.getBit(31)).not().getValue()) {          // 0R - 00
            long oldPC = PC.getUnsigned()-1;
            throw new Exception("Invalid opcode at PC = " + oldPC); 
        } else if (opcode.getBit(30).not().getValue()) {                           // 1R - 01
            executionResult.copy(rdData);
        } else if (opcode.getBit(31).getValue()) {                           // 2R - 11
            alu.op1.copy(rdData);                              
            alu.op2.copy(immediate);
            alu.doOperation(new Bit[]{trueBit,trueBit,trueBit,falseBit});   // Adds rd and immediate value 
            currentClockCycle += 2; 
            executionResult.copy(alu.result);
        } else {                                                                     // 3R - 10
            alu.op1.copy(rdData);                              
            alu.op2.copy(rs1);
            alu.doOperation(new Bit[]{trueBit,trueBit,trueBit,falseBit});   // Adds rd and rs1
            currentClockCycle += 2;
            executionResult.copy(alu.result);
        }
    }

    /** 
     * Executes a pop/peek instruction
     * @throws Exception
     */
    private void executePop() throws Exception {
        if (opcode.getBit(30).or(opcode.getBit(31)).not().getValue()) {          // 0R - 00
            long oldPC = PC.getUnsigned()-1;
            throw new Exception("Invalid opcode at PC = " + oldPC);
        } else if (opcode.getBit(30).not().getValue()) {                           // 1R - 01
            executionResult.copy(L2Cache.read(SP));
            SP.increment();
        } else if (opcode.getBit(31).getValue()) {                           // 2R - 11
            alu.op1.copy(rs1);                              
            alu.op2.copy(immediate);
            alu.doOperation(new Bit[]{trueBit,trueBit,trueBit,falseBit});   // Adds rs1 and immediate value 
            currentClockCycle += 2;
            alu.op1.copy(SP);
            alu.op2.copy(alu.result);
            alu.doOperation(new Bit[]{trueBit,trueBit,trueBit,trueBit});   // Subtracts SP – (rs1 + immediate value) 
            currentClockCycle += 2;
            executionResult.copy(L2Cache.read(alu.result));

        } else {                                                                     // 3R - 10
            alu.op1.copy(rs1);                              
            alu.op2.copy(rs2);
            alu.doOperation(new Bit[]{trueBit,trueBit,trueBit,falseBit});   // Adds rs1 and rs2
            currentClockCycle += 2;
            alu.op1.copy(SP);
            alu.op2.copy(alu.result);
            alu.doOperation(new Bit[]{trueBit,trueBit,trueBit,trueBit});   // Subtracts SP – (rs1 + rs2) 
            currentClockCycle += 2;
            executionResult.copy(L2Cache.read(alu.result));
        }
    }

    /** 
     * Converts a register number represented in the word into an int
     * @param register A word which will be converted into an int
     * @return int representing the register number
     */
    private int getRegisterInt(Word register) {
        int sum = 0;
        int x = 0;
        for (int i = 31; i>26; i--) {
            if (register.getBit(i).getValue()) {
                sum += Math.pow(2, x);
            }
            x++;
        }
        return sum;
    }

    /** 
     * Adds cycles to clock depending on alu operation
     */
    private void aluClockAdd() {
        if (function.getBit(28).not().and(function.getBit(29)).and(function.getBit(30)).and(function.getBit(31)).getValue()) { 
            currentClockCycle += 10;        // If function is multiply, add 10 to clock,
        } else {                            // else add 2 to the clock
            currentClockCycle += 2;
        }
    }

    /** 
     * Prints the results from Math - 000
     */
    private void printResultsMath() {
        if (getRegisterInt(rd) == 0) {              // Ensures r0 always reads as 0 and cannot be overwritten
            executionResult.set(0);
        }
        String op = "";                 // Gets function and sets op string to prepare for printing
        switch (function.getSigned()) {
            case 7:op = "MULT ";break;case 8:op = "AND ";break;case 9:op = "OR" ;break;case 10:op = "XOR ";break;case 11:op = "NOT ";break;
            case 12:op = "LSHIFT ";break;case 13:op = "RSHIFT ";break;case 14:op = "ADD ";break;case 15:op = "SUB ";break;
        }
        if (opcode.getBit(30).not().getValue()) {                                  // 1R - 01
            System.out.println("COPY " + immediate.getSigned() + " R" + rd.getUnsigned()
                + "    input: " + immediate.getUnsigned() + " output: R" + rd.getUnsigned() + " = " + executionResult.getSigned());
        } else if (opcode.getBit(31).getValue()) {                           // 2R - 11
            System.out.println(op + "R" + rd.getUnsigned() + " R" + getRegisterInt(currentInstruction.rightShift(14).and(mask))
                + "    inputs: " + rdData.getSigned() + ", " + rs1.getSigned() + " output: R" + rd.getUnsigned() + " = " + executionResult.getSigned());
        } else {                                                                     // 3R - 10
            System.out.println(op + "R" + getRegisterInt(currentInstruction.rightShift(19).and(mask)) + " R" + getRegisterInt(currentInstruction.rightShift(14).and(mask)) + " R" + rd.getUnsigned()
                + "    inputs: " + rs1.getSigned()+ ", " + rs2.getSigned() + " output: R" + rd.getUnsigned() + " = " + executionResult.getSigned());
        }
    }

    /** 
     * Prints the results from Branch - 001
     */
    private void printResultsBranch() {
        String op = "";                 // Gets function and sets op string to prepare for printing
        switch (function.getSigned()) {
            case 0:op = "EQ ";break;case 1:op = "NEQ ";break;case 2:op = "LT ";break;case 3:op = "GE ";break;case 4:op = "GT ";break;case 5:op = "LE ";break;
        }
        if (opcode.getBit(30).or(opcode.getBit(31)).not().getValue()) {            // 0R - 00
            System.out.println("JUMPTO " + immediate.getSigned()                   
                + "    input: " + immediate.getSigned() + " output: PC = " + PC.getUnsigned());
        } else if (opcode.getBit(30).not().getValue()) {                            // 1R - 01
            System.out.println("JUMP " + immediate.getSigned()
                + "    input: " + immediate.getSigned() + " output: PC = " + PC.getUnsigned());
        } else if (opcode.getBit(31).getValue()) {                           // 2R - 11
            System.out.println("BRANCH" + op + "R" + getRegisterInt(currentInstruction.rightShift(14).and(mask)) + " R" + rd.getUnsigned() + " " + immediate.getSigned()
                + "    inputs: " + rs1.getSigned() + ", " + rdData.getSigned() + ", " + immediate.getSigned() + " output: PC = " + PC.getUnsigned());
        } else {                                                                     // 3R - 10
            System.out.println("BRANCH" + op + "R" + getRegisterInt(currentInstruction.rightShift(19).and(mask)) + " R" + getRegisterInt(currentInstruction.rightShift(14).and(mask)) + " " + immediate.getSigned()
                + "    inputs: " + rs1.getSigned() + ", " + rs2.getSigned() + ", " + immediate.getSigned() + " output: PC = " + PC.getUnsigned());
        }
    }

    /** 
     * Prints the results from Call - 010
     */
    private void printResultsCall() {
        String op = "";                 // Gets function and sets op string to prepare for printing
        switch (function.getSigned()) {
            case 0:op = "EQ ";break;case 1:op = "NEQ ";break;case 2:op = "LT ";break;case 3:op = "GE ";break;case 4:op = "GT ";break;case 5:op = "LE ";break;
        }
        if (opcode.getBit(30).or(opcode.getBit(31)).not().getValue()) {            // 0R - 00
            System.out.println("CALL " + immediate.getSigned()
                + "    input: " + immediate.getSigned() + " outputs: mem[" + SP.getUnsigned() + "] = " + MainMemory.read(SP).getSigned() + ", PC = " + PC.getUnsigned());
        } else if (opcode.getBit(30).not().getValue()) {                            // 1R - 01
            System.out.println("CALL R" + rd.getUnsigned() + " " + immediate.getSigned()
                + "    inputs: " + rdData.getSigned() + ", " + immediate.getSigned() + " outputs: mem[" + SP.getUnsigned() + "] = " + MainMemory.read(SP).getSigned() + ", PC = " + PC.getUnsigned());;
        } else if (opcode.getBit(31).getValue()) {                           // 2R - 11
            if (alu.result.getSigned() != 0) {
                System.out.println("CALL" + op + "R" + getRegisterInt(currentInstruction.rightShift(14).and(mask)) + " R" + rd.getUnsigned() + " " + immediate.getSigned()
                    + "    inputs: " + rs1.getSigned() + ", " + rdData.getSigned() + ", " + immediate.getSigned() + " outputs: mem[" + SP.getUnsigned() + "] = " + MainMemory.read(SP).getSigned() + ", PC = " + PC.getUnsigned());
            } else {
                System.out.println("CALL" + op + "R" + getRegisterInt(currentInstruction.rightShift(14).and(mask)) + " R" + rd.getUnsigned() + " " + immediate.getSigned()
                    + "    inputs: " + rs1.getSigned() + ", " + rdData.getSigned() + ", " + immediate.getSigned() + " output: PC = " + PC.getUnsigned());
            }
        } else {    
            if (alu.result.getSigned() != 0) {                                       // 3R - 10
                System.out.println("CALL" + op + "R" + getRegisterInt(currentInstruction.rightShift(19).and(mask)) + " R" + getRegisterInt(currentInstruction.rightShift(14).and(mask)) + " R" + rd.getUnsigned() + " " + immediate.getSigned()
                    + "    inputs: " + rs1.getSigned() + ", " + rs2.getSigned() + ", " + rdData.getSigned() + ", " + immediate.getSigned() + " outputs: mem[" + SP.getUnsigned() + "] = " + MainMemory.read(SP).getSigned() + ", PC = " + PC.getUnsigned());
            } else {
                System.out.println("CALL" + op + "R" + getRegisterInt(currentInstruction.rightShift(19).and(mask)) + " R" + getRegisterInt(currentInstruction.rightShift(14).and(mask)) + " R" + rd.getUnsigned() + " " + immediate.getSigned()
                    + "    inputs: " + rs1.getSigned() + ", " + rs2.getSigned() + ", " + rdData.getSigned() + ", " + immediate.getSigned() + " output: PC = " + PC.getUnsigned());
            }
        }                                                                 
    }

    /** 
     * Prints the results from Push - 011
     */
    private void printResultsPush() {
        String op = "";                 // Gets function and sets op string to prepare for printing
        switch (function.getSigned()) {
            case 7:op = "MULT ";break;case 8:op = "AND ";break;case 9:op = "OR" ;break;case 10:op = "XOR ";break;case 11:op = "NOT ";break;
            case 12:op = "LSHIFT ";break;case 13:op = "RSHIFT ";break;case 14:op = "ADD ";break;case 15:op = "SUB ";break;
        }
        if (opcode.getBit(30).not().getValue()) {                            // 1R - 01
            System.out.println("PUSH" + op + "R" + rd.getUnsigned() + " " + immediate.getSigned()
                + "    inputs: " + rdData.getSigned() + ", " + immediate.getSigned() + " output: mem[" + SP.getUnsigned() + "] = " + executionResult.getSigned());
        } else if (opcode.getBit(31).getValue()) {                           // 2R - 11
            System.out.println("PUSH" + op + "R" + rd.getUnsigned() + " R" + getRegisterInt(currentInstruction.rightShift(14).and(mask)) 
                + "    inputs: " + rdData.getSigned() + ", " + rs1.getSigned() + " output: mem[" + SP.getUnsigned() + "] = " + executionResult.getSigned());
        } else {                                                                     // 3R - 10
            System.out.println("PUSH" + op + "R" + getRegisterInt(currentInstruction.rightShift(19).and(mask)) + " R" + getRegisterInt(currentInstruction.rightShift(14).and(mask))
                + "    inputs: " + rs1.getSigned() + ", " + rs2.getSigned() + " output: mem[" + SP.getUnsigned() + "] = " + executionResult.getSigned());
        }
    }

    /** 
     * Prints the results from Load - 100
     */
    private void printResultsLoad() {
        if (getRegisterInt(rd) == 0) {              // Ensures r0 always reads as 0 and cannot be overwritten
            executionResult.set(0);
        }
        if (opcode.getBit(30).or(opcode.getBit(31)).not().getValue()) {            // 0R - 00
            System.out.println("RETURN outputs: PC = " + PC.getUnsigned() + ", SP = " + SP.getUnsigned());
        } else if (opcode.getBit(30).not().getValue()) {                            // 1R - 01
            System.out.println("LOAD " + immediate.getSigned() + " R" + rd.getUnsigned()
                + "    inputs: " + immediate.getSigned() + ", " + rdData.getSigned() + " output: R" + rd.getUnsigned() + " = " + executionResult.getSigned());
        } else if (opcode.getBit(31).getValue()) {                           // 2R - 11
            System.out.println("LOAD R" + getRegisterInt(currentInstruction.rightShift(14).and(mask)) + " " + immediate.getSigned() + " R" + rd.getUnsigned()
                + "    inputs: " + rs1.getSigned() + ", " + immediate.getSigned() + " output: R" + rd.getUnsigned() + " = " + executionResult.getSigned());
        } else {                                                                     // 3R - 10
            System.out.println("LOAD R" + getRegisterInt(currentInstruction.rightShift(19).and(mask)) + " R" + getRegisterInt(currentInstruction.rightShift(14).and(mask)) + " R" + rd.getUnsigned()
                + "    inputs: " + rs1.getSigned() + ", " + rs2.getSigned() + " output: R" + rd.getUnsigned() + " = " + executionResult.getSigned());
        }
    }

    /** 
     * Prints the results from Store - 101
     */
    private void printResultsStore() {
        if (opcode.getBit(30).not().getValue()) {                            // 1R - 01
            System.out.println("STORE " + immediate.getSigned() + " R" + rd.getUnsigned()
                + "    input: " + immediate.getSigned() + ", " + rdData.getSigned() + " output: mem[" + executionResult.getUnsigned() + "] = " + immediate.getSigned());
        } else if (opcode.getBit(31).getValue()) {                           // 2R - 11
            System.out.println("STORE R" + getRegisterInt(currentInstruction.rightShift(14).and(mask)) + " R" + rd.getUnsigned() + " " + immediate.getSigned()
                + "    input: " + rs1.getSigned() + ", " + rdData.getSigned() + ", " + immediate.getSigned() + " output: mem[" + executionResult.getUnsigned() + "] = " + rs1.getSigned());
        } else {                                                                     // 3R - 10
            System.out.println("STORE R" + getRegisterInt(currentInstruction.rightShift(14).and(mask)) + " R" + rd.getUnsigned() + " R" + getRegisterInt(currentInstruction.rightShift(19).and(mask))
                + "    input: " + rs2.getSigned() + ", " + rdData.getSigned() + ", " + rs1.getSigned() + " output: mem[" + executionResult.getUnsigned() + "] = " + rs2.getSigned());
        }
    }

    /** 
     * Prints the results from Pop/Peek - 110
     */
    private void printResultsPop() {
        if (getRegisterInt(rd) == 0) {              // Ensures r0 always reads as 0 and cannot be overwritten
            executionResult.set(0);
        }
        if (opcode.getBit(30).not().getValue()) {                            // 1R - 01
            long oldSP = SP.getUnsigned()-1;
            System.out.println("POP R" + rd.getUnsigned() 
            + "    input: SP = " + oldSP + " outputs: R" + rd.getUnsigned() + " = " + executionResult.getSigned() + ", SP = " + SP.getUnsigned());
        } else if (opcode.getBit(31).getValue()) {                           // 2R - 11
            System.out.println("PEEK R" + getRegisterInt(currentInstruction.rightShift(14).and(mask)) + " " + immediate.getSigned() + " R" + rd.getUnsigned()
                + "    inputs: " + rs1.getSigned() + ", " + immediate.getSigned() + ", " + rdData.getSigned() + ", SP=" + SP.getUnsigned() + " output: R" + rd.getUnsigned() + " = " + executionResult.getSigned());
        } else {                                                                     // 3R - 10
            System.out.println("PEEK R" + getRegisterInt(currentInstruction.rightShift(19).and(mask)) + " R" + getRegisterInt(currentInstruction.rightShift(14).and(mask)) + " R" + rd.getUnsigned()
                + "    inputs: " + rs1.getSigned() + ", " + rs2.getSigned() + ", " + rdData.getSigned() + ", SP=" + SP.getUnsigned() + " output: R" + rd.getUnsigned() + " = " + executionResult.getSigned());
        }
    }

}