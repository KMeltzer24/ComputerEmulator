/*
 * Represents an ALU which preforms operations on words
 * @author Kevin Meltzer
 * @version 1.5
 */
public class ALU {
    
    // A Word representing operand 1
    public Word op1;
    // A Word representing operand 2
    public Word op2;
    // A Word representing the result of the operation
    public Word result;

    /** 
     * Constructor for ALU that initializes the member words
     */
    public ALU() {
        this.op1 = new Word();
        this.op2 = new Word();
        this.result = new Word();
    }

    /** 
     * Preforms an operation represented by a 4 bit array 
     * and then sets result to the result of the operation 
     * @param operation A 4 Bit array representing the desired operation
     * @throws Exception 
     */
    public void doOperation(Bit[] operation) throws Exception {
        Bit trueBit = new Bit(true);
        Bit falseBit = new Bit(false);
        Word mask = new Word();         
        mask.setBit(27, trueBit);
        mask.setBit(28, trueBit);
        mask.setBit(29, trueBit);
        mask.setBit(30, trueBit);
        mask.setBit(31, trueBit);
        Word one = new Word();              
        one.setBit(31, new Bit(true));
        Word zero = new Word();              
        Word tempResult = new Word();
        if (operation[0].and(operation[1].not()).and(operation[2].not()).and(operation[3].not()).getValue()) {   // and - 1000
            tempResult = op1.and(op2);          
            result.copy(tempResult);

        } else if (operation[0].and(operation[1].not()).and(operation[2].not()).and(operation[3]).getValue()) { // or - 1001
            tempResult = op1.or(op2);          
            result.copy(tempResult);

        } else if (operation[0].and(operation[1].not()).and(operation[2]).and(operation[3].not()).getValue()) { // xor - 1010
            tempResult = op1.xor(op2);          
            result.copy(tempResult); 

        } else if (operation[0].and(operation[1].not()).and(operation[2]).and(operation[3]).getValue()) {   // not - 1011
            tempResult = op1.not();             
            result.copy(tempResult);

        } else if (operation[0].and(operation[1]).and(operation[2].not()).and(operation[3].not()).getValue()) { // Left Shift - 1100
            tempResult = op1.leftShift((int)op2.and(mask).getUnsigned()); 
            result.copy(tempResult);

        } else if (operation[0].and(operation[1]).and(operation[2].not()).and(operation[3]).getValue()) {   // Right Shift - 1101
            tempResult = op1.rightShift((int)op2.and(mask).getUnsigned());  
            result.copy(tempResult);

        } else if (operation[0].and(operation[1]).and(operation[2]).and(operation[3].not()).getValue()) {   // Add - 1110
            tempResult = add2(op1, op2);        
            result.copy(tempResult);

        }  else if (operation[0].and(operation[1]).and(operation[2]).and(operation[3]).getValue()) {    // Subtract - 1111
            tempResult = add2(op1, add2(op2.not(), one));
            result.copy(tempResult);

        } else if (operation[0].not().and(operation[1]).and(operation[2]).and(operation[3]).getValue()) {   // Multiply - 0111
            Word multArr[] = new Word[32];      
            for (int i = 31; i >= 0; i--) {
                multArr[i] = new Word();
                if (op2.getBit(i).getValue()) {             // if ith bit in op2 is a true,
                    multArr[i].copy(op1.leftShift(31-i));   // entire op1 is left shifted and put into multArr to be added
                } else {
                    multArr[i].copy(new Word());            // if ith bit in op2 is a false,
                }                                           // a word filled with 0's is put into multArr
            }
            int x = 0;
            for (int i = 0; i < 32; i+=4) {                 // Adds every word in multArr together in groups of 4
                multArr[x++] = add4(multArr[i], multArr[i+1], multArr[i+2], multArr[i+3]);
            }
            x = 0;
            for (int i = 0; i < 8; i+=4) {                  // Adds the resulting 8 words in groups of 4
                multArr[x++] = add4(multArr[i], multArr[i+1], multArr[i+2], multArr[i+3]);
            }
            tempResult = add2(multArr[0], multArr[1]);      // Adds the final 2 words
            result.copy(tempResult);

        } else if (operation[0].or(operation[1]).or(operation[2]).or(operation[3]).not().getValue()) {   // Equals - 0000
            operation[0].toggle();
            operation[1].toggle();
            operation[2].toggle();
            operation[3].toggle();      
            doOperation(operation);                             // Subtracts op1-op2
            int x = 31;
            while (result.getBit(x).not().getValue()) {         // Checks if the result is 0
                if (x == 0) {                                   // If 0, sets result as one and returns
                    result.copy(one);
                    return;
                }
                x--;
            }
            result.copy(zero);                                  // If not 0, sets result as zero

        } else if (operation[0].not().and(operation[1].not()).and(operation[2].not()).and(operation[3]).getValue()) {   // Not Equal - 0001
            operation[3].toggle();   
            doOperation(operation);
            if (result.getBit(31).getValue()) {               // Checks if ops are equal
                result.setBit(31, falseBit);                  // if equal, sets result to zero
            } else {
                result.setBit(31, trueBit);                   // if not equal, sets result to one
            }

        } else if (operation[0].not().and(operation[1].not()).and(operation[2]).and(operation[3].not()).getValue()) {   // Less than - 0010 
            operation[0].toggle();
            operation[1].toggle();
            operation[3].toggle();       
            doOperation(operation);                         // Subtracts op1-op2
            if (result.getBit(0).getValue()) {            // If the result is negative, sets result to one
                result.copy(one);                               
            } else {
                result.copy(zero);         // If the result is postive, sets result to zero
            }

        } else if (operation[0].not().and(operation[1].not()).and(operation[2]).and(operation[3]).getValue()) {   // Greater than or equal - 0011 
            operation[3].toggle();       
            doOperation(operation);                             // Checks if op1 < op2
            if (result.getBit(31).getValue()) {             
                result.setBit(31, falseBit);                  // If true, sets result to zero         
            } else {
                result.setBit(31, trueBit);                  // If false, sets result to one  
            }

        } else if (operation[0].not().and(operation[1]).and(operation[2].not()).and(operation[3].not()).getValue()) {   // Greater than - 0100
            operation[3].toggle();       
            doOperation(operation);                             // Checks if op1 <= op2
            if (result.getBit(31).getValue()) {             
                result.setBit(31, falseBit);                  // If true, sets result to zero         
            } else {
                result.setBit(31, trueBit);                   // If false, sets result to one  
            }

        } else if (operation[0].not().and(operation[1]).and(operation[2].not()).and(operation[3]).getValue()) {   // Less than or equal - 0101 
            operation[1].toggle();
            operation[3].toggle();      
            doOperation(operation);
            if (result.getBit(31).not().getValue()) {             // Checks if the ops are equal
                operation[0].toggle();
                operation[1].toggle();
                operation[3].toggle();
                doOperation(operation);                             // Checks if op1 is less than op2, and sets result as one or zero
            }

        } else {
            throw new Exception("Invalid ALU operation");
        }
    }

    /** 
     * Adds two words and returns the result (Public For testing)
     * @param paramOp1 First Operand to add 
     * @param paramOp2 Second Operand to add 
     * @return The result word after addition is done
     */
    public Word add2(Word paramOp1, Word paramOp2) {
        Word tempResult = new Word();
        Bit carry = new Bit();
        for (int i = 31; i >= 0; i--) {
            Bit sum = paramOp1.getBit(i).xor(paramOp2.getBit(i)).xor(carry);
            carry = paramOp1.getBit(i).and(paramOp2.getBit(i)).or(paramOp1.getBit(i).xor(paramOp2.getBit(i)).and(carry));
            tempResult.setBit(i, sum);
        }
        return tempResult;
    }

    /** 
     * Adds four words and returns the result (Public For testing)
     * @param paramOp1 First Operand to add 
     * @param paramOp2 Second Operand to add 
     * @param paramOp3 Third Operand to add 
     * @param paramOp4 Fourth Operand to add 
     * @return The result word after addition is done
     */
    public Word add4(Word paramOp1, Word paramOp2, Word paramOp3, Word paramOp4) {     
        Word tempResult1 = new Word();
        Bit carry = new Bit();
        for (int i = 31; i >= 0; i--) {
            Bit sum = paramOp1.getBit(i).xor(paramOp2.getBit(i)).xor(carry);
            carry = paramOp1.getBit(i).and(paramOp2.getBit(i)).or(paramOp1.getBit(i).xor(paramOp2.getBit(i)).and(carry));
            tempResult1.setBit(i, sum);
        }
        Word tempResult2 = new Word();
        carry = new Bit();
        for (int i = 31; i >= 0; i--) {
            Bit sum = paramOp3.getBit(i).xor(paramOp4.getBit(i)).xor(carry);
            carry = paramOp3.getBit(i).and(paramOp4.getBit(i)).or(paramOp3.getBit(i).xor(paramOp4.getBit(i)).and(carry));
            tempResult2.setBit(i, sum);
        }
        Word finalResult = new Word();
        carry = new Bit();
        for (int i = 31; i >= 0; i--) {
            Bit sum = tempResult1.getBit(i).xor(tempResult2.getBit(i)).xor(carry);
            carry = tempResult1.getBit(i).and(tempResult2.getBit(i)).or(tempResult1.getBit(i).xor(tempResult2.getBit(i)).and(carry));
            finalResult.setBit(i, sum);
        }
        return finalResult;
    }

}
