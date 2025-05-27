package com.ComputerEmulator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * An Assembler which takes an input file of assembly instructions
 * and converts them to machine langauge
 * @author Kevin Meltzer
 * @version 1.6
 */
public class Assembler { 
    
    // List of tokens to be parsed                                    
    private ArrayList<Token> tokens;

    /** 
     * Assembles an input file of assembly instructions and converts them to machine langauge
     * @param args an input file of assembly instructions and a output file of machine langauge
     */
    public void assemble(String[] args) throws Exception {
        // Checks if there is only 2 args
        if(args.length != 2) {
            throw new Exception("Error: Only two arguments must be used");
        }

		// Puts all lines from an input file into a arraylist
        File input = new File(args[0]);
        File output = new File(args[1]);
        
        ArrayList<String> instructions = new ArrayList<>();
		try {		
			instructions = new ArrayList<>(Files.readAllLines(input.toPath()));
		} catch (IOException e) {											
		    throw new Exception("Error reading input file: " + e.getMessage());
		}
        // If the input file is empty, throws an empty file exception
		if(instructions.isEmpty()) {
			throw new Exception("Empty file inputed");
		}

		lex(instructions);
        System.out.println(tokens + "\n");
        ArrayList<String> parseResult = parse();
        System.out.println(parseResult + "\n");
        
        // Writes parse result output file
        try {
            FileWriter fileWriter = new FileWriter(output);
            try (PrintWriter printWriter = new PrintWriter(fileWriter)) {
                for (int i = 0; i <parseResult.size(); i++) {
                    printWriter.println(parseResult.get(i));
                }
            }
		} catch (IOException e) {											
		    throw new Exception("Error writing to output file: " + e);
		}
    }

    /**
     * Takes Assembly instructions and them separates it into tokens
     * @param instructions List of Assembly instructions
     */
    private void lex(ArrayList<String> instructions) throws Exception {
        tokens = new ArrayList<>();      
        for (int line = 0; line < instructions.size(); line++) {
            // Initializes a ArrayList which will filled with tokens 
            ArrayList<Token> tokList = new ArrayList<>();
            // Splits the input string into a array character by character
            String str = instructions.get(line);
            String[] arr = str.split("");
            
            // If the input string is empty create a token with the value of NEWLINE
            if (str.equals("")) { 
                tokList.add(new Token("NEWLINE"));
                continue;
            }
            // Iterates through the array with the contents of the input string
            for (int i = 0; i < arr.length; i++) {
                // Checks if the character in the array is a valid character and sets the corresponding state
                // if the character is valid.
                if (Character.isLetter(arr[i].charAt(0))) {   // Creates a token for an instruction word
                    if (i+1 == arr.length) {
                        if (arr[i].charAt(0) == 'R') {
                            throw new Exception("Invalid Assembly Instruction: a register must be \"R\" followed by a number.");
                        }
                        throw new Exception("Invalid Assembly Instruction");
                    }
                    int origIndex = i;
                    if (arr[i].charAt(0) == 'R' && Character.isDigit(arr[i+1].charAt(0))) { // Counts number of characters in a register
                        i++;
                        while(Character.isDigit(arr[i].charAt(0))) {
                            i++;
                            if (i == arr.length) {
                                break;
                            }
                        }
                    } else {
                        if (Character.isLetter(arr[i+1].charAt(0))) {           // Counts number of characters in a word
                            while(Character.isLetter(arr[i].charAt(0))) {
                                i++;
                                if (i == arr.length) {
                                    break;
                                }
                            }
                        }
                    }
                    i--;
                    String word = arr[i];
                    arr[i] = "";
                    while (origIndex < i) {         // Based on the count, puts word/register in arr[i]
                        arr[i] += arr[origIndex];
                        origIndex++;
                    }
                    arr[i] += word;
                    tokList.add(new Token(arr[i])); // Creates a token for arr[i]
                } else if (Character.isDigit(arr[i].charAt(0)) || arr[i].charAt(0) == '-') {    // Creates a token for a number
                    String number = "";
                    if (arr[i].charAt(0) == '-') {  // If the number is negative, make sure there is atleast one digit
                        number = "-";
                        i++;
                        if (!Character.isDigit(arr[i].charAt(0))) {
                            throw new Exception("Assembly instruction invalid: negative sign must be followed by number");
                        }
                    }
                    while (Character.isDigit(arr[i].charAt(0))) {  // Counts number of digits in a number
                        number += arr[i];
                        if (i == arr.length - 1) {
                            break;
                        }
                        i++;
                    }
                    tokList.add(new Token(Token.state.NUMBER,number));  // Creates a token for a number
                } else if (arr[i].equals(" ") || arr[i].equals("\t")) {
                    // do nothing if space/tab
                } else {
                    throw new Exception("Invalid character assembly instructions");
                }
                if (i == arr.length-1) {    // if there is no tokens left, at a newline token to list, and add all tokens from tokList to tokens
                    tokList.add(new Token("NEWLINE"));
                    tokens.addAll(tokList);  
                }	
            }
        }
    }

    /**
     * A recursive descent parser which creates a AST out of tokens from a Shank program
     * @return A list of Strings containing binary words representing assembly instructions
     * @throws Exception 
     */
    private ArrayList<String> parse() throws Exception {
        return statements();
    }
    
    /**
     * Parses assembly statements
     * @return A list of Strings containing binary words representing assembly instructions
     * @throws Exception 
     */
    private ArrayList<String> statements() throws Exception {
        ArrayList<String> statments = new ArrayList<>();
        // Remove all NewLine tokens until a not NewLine token is found
        removeNewLines();
        while (true) {
            statments.add(statement());
            matchRemoveNullTest("NEWLINE");
            if (tokens.isEmpty()) {
                break;
            }
        }
        return statments;
    }

    /**
     * Parses an assembly statement
     * @return A binary word in a string representing a assembly instruction
     * @throws Exception 
     */
    private String statement() throws Exception {
        HashMap<String,String> mathList = new HashMap<>();
        mathList.put("MULT", "0111");mathList.put("AND", "1000");mathList.put("OR", "1001");mathList.put("XOR", "1010");mathList.put("NOT", "1011");
        mathList.put("LSHIFT", "1100");mathList.put("RSHIFT", "1101");mathList.put("ADD", "1110");mathList.put("SUB", "1111");
        HashMap<String,String> branchList = new HashMap<>();
        branchList.put("BRANCHEQ", "0000");branchList.put("BRANCHNEQ", "0001");branchList.put("BRANCHLT", "0010");
		branchList.put("BRANCHGE", "0011");branchList.put("BRANCHGT", "0100");branchList.put("BRANCHLE", "0101");
        HashMap<String,String> callList = new HashMap<>();
        callList.put("CALL", "");callList.put("CALLEQ", "0000");callList.put("CALLNEQ", "0001");callList.put("CALLLT", "0010");
		callList.put("CALLGE", "0011");callList.put("CALLHGT", "0100");callList.put("CALLLE", "0101");
        HashMap<String,String> pushList = new HashMap<>();
        pushList.put("PUSHMULT", "0111");pushList.put("PUSHAND", "1000");pushList.put("PUSHOR", "1001");pushList.put("PUSHXOR", "1010");pushList.put("PUSHNOT", "1011");
        pushList.put("PUSHLSHIFT", "1100");pushList.put("PUSHRSHIFT", "1101");pushList.put("PUSHADD", "1110");pushList.put("PUSHSUB", "1111");

        if (mathList.containsKey(tokens.get(0).getState().toString())) {
            return math(mathList);
        } else if (branchList.containsKey(tokens.get(0).getState().toString())) { 
            return branch(branchList);
        } else if (callList.containsKey(tokens.get(0).getState().toString())) {
            return call(callList);
        } else if (pushList.containsKey(tokens.get(0).getState().toString())) {
            return push(pushList);
        } else if (matchAndRemove("LOAD") != null) {
            return load();
        } else if (matchAndRemove("STORE") != null) {
            return store();
        } else if (matchAndRemove("PEEK") != null) {
            return peek();
        } else if (matchAndRemove("HALT") != null) {
            return halt();
        } else if (matchAndRemove("COPY") != null) {
            return copy();
        } else if (matchAndRemove("JUMPTO") != null) {
            return jumpTo();
        } else if (matchAndRemove("JUMP") != null) {
            return jump();
        } else if (matchAndRemove("RETURN") != null) {
            return Return();
        } else {
            return pop();
        }
    }

    
    /** 
     * Parses a math instruction
     * @param mathList A hashmap of operations and operations in binary
     * @return A binary string representing the instruction
     * @throws Exception
     */
    private String math(HashMap<String,String> mathList) throws Exception {
        String op = mathList.get(matchAndRemove(tokens.get(0).getState().toString()).toString());
        String[] params;
        if (peek(2).getState().equals(Token.state.valueOf("REGISTER"))) {   // 3R - 10
            params = threeR(0);
            return "00000000" + params[0] + params[1] + op + params[2] + "00010";
        }
        params = twoR(2);
        return "0000000000000" + params[1] + op + params[0] + "00011";          // 2R - 11
    }

    /** 
     * Parses a branch instruction
     * @param mathList A hashmap of operations and operations in binary
     * @return A binary string representing the instruction
     * @throws Exception
     */
    private String branch(HashMap<String,String> branchList) throws Exception {
        String op = branchList.get(matchAndRemove(tokens.get(0).getState().toString()).toString());
        String[] params = twoR(0);
        return params[2] + params[0] + op + params[1] + "00111";
    }

    /** 
     * Parses a call instruction
     * @param mathList A hashmap of operations and operations in binary
     * @return A binary string representing the instruction
     * @throws Exception
     */
    private String call(HashMap<String,String> callList) throws Exception {
        String op = callList.get(matchAndRemove(tokens.get(0).getState().toString()).toString());
        String[] params;
        if (peek(0).getState().equals(Token.state.valueOf("NUMBER"))) {                 // 0R - 00
            return noR() + "01000";
        } else if (peek(1).getState().equals(Token.state.valueOf("NUMBER"))) {         // 1R - 01
            params = destOnly(1);
            return params[1] + "0000" + params[0] + "01001";
        } else if (peek(2).getState().equals(Token.state.valueOf("NUMBER"))) {  // 2R - 11
            params = twoR(0);
            return params[2] + params[0] + op + params[1] + "01011";
        }
        params = threeR(1);
        return params[3] + params[0] + params[1] + op + params[2] + "01010";              // 3R - 10
    }

    /** 
     * Parses a push instruction
     * @param mathList A hashmap of operations and operations in binary
     * @return A binary string representing the instruction
     * @throws Exception
     */
    private String push(HashMap<String,String> pushList) throws Exception {
        String op = pushList.get(matchAndRemove(tokens.get(0).getState().toString()).toString());
        String[] params;
        if (peek(1).getState().equals(Token.state.valueOf("NUMBER"))) {   // 1R - 01
            params = destOnly(1);
            return params[1] + op + params[0] + "01101";
        }
        params = twoR(2);
        return "0000000000000" + params[1] + op + params[0] + "01111";          // 2R - 11
    }

    /** 
     * Parses a load instruction
     * @param mathList A hashmap of operations and operations in binary
     * @return A binary string representing the instruction
     * @throws Exception
     */
    private String load() throws Exception {
        String[] params;
        if (peek(0).getState().equals(Token.state.valueOf("NUMBER"))) {         // 1R - 01
            params = destOnly(0);
            return params[0] + "0000" + params[1] + "10001";
        } else if (peek(1).getState().equals(Token.state.valueOf("NUMBER"))) {  // 2R - 11
            params = twoR(1);
            return params[1] + params[0] + "0000" + params[2] + "10011";
        } else {                                                                   // 3R - 10
            params = threeR(0);
            return "00000000" + params[0] + params[1] + "0000" + params[2] + "10010";
        }
    }

    /** 
     * Parses a store instruction
     * @param mathList A hashmap of operations and operations in binary
     * @return A binary string representing the instruction
     * @throws Exception
     */
    private String store() throws Exception {
        String[] params;
        if (peek(2).getState().equals(Token.state.valueOf("REGISTER"))) {         // 3R - 10
            params = threeR(0);
            return "00000000" + params[2] + params[0] + "0000" + params[1] + "10110";
        } else if (peek(0).getState().equals(Token.state.valueOf("REGISTER"))) {  // 2R - 11
            params = twoR(0);
            return params[2] + params[0] + "0000" + params[1] + "10111";
        } else {                                                                      // 1R - 01
            params = destOnly(0);
            return params[0] + "0000" + params[1] + "10101";
        }
    }

    /** 
     * Parses a peek instruction
     * @param mathList A hashmap of operations and operations in binary
     * @return A binary string representing the instruction
     * @throws Exception
     */
    private String peek() throws Exception {
        String[] params;
        if (peek(1).getState().equals(Token.state.valueOf("NUMBER"))) {       // 2R - 11
            params = twoR(1);
            return params[1] + params[0] + "0000" + params[2] + "11011";
        }                                                                       // 3R - 10
        params = threeR(0);
        return "00000000" + params[0] + params[1] + "0000" + params[2] + "11010";
    }

    /** 
     * Parses a halt instruction
     * @return A binary string representing the instruction
     * @throws Exception
     */
    private String halt() throws Exception {
        return "00000000000000000000000000000000";
    }

    /** 
     * Parses a copy instruction
     * @return A binary string representing the instruction
     * @throws Exception
     */
    private String copy() throws Exception {
        String[] params = destOnly(0);
        return params[0] + "0000" + params[1] + "00001";
    } 

    /** 
     * Parses a jumpTo instruction
     * @return A binary string representing the instruction
     * @throws Exception
     */
    private String jumpTo() throws Exception {
        return noR() + "00100";
    } 

    /** 
     * Parses a jump instruction
     * @return A binary string representing the instruction
     * @throws Exception
     */
    private String jump() throws Exception {
        return toBinary(matchRemoveNullTest("NUMBER").getValue(), 18) + "00000000000101";
    } 
    
    /** 
     * Parses a return instruction
     * @return A binary string representing the instruction
     * @throws Exception
     */
    private String Return() throws Exception {
        return "00000000000000000000000000010000";
    } 

    /** 
     * Parses a pop instruction
     * @return A binary string representing the instruction
     * @throws Exception
     */
    private String pop() throws Exception {
        matchRemoveNullTest("POP");
        return "0000000000000000000000" + toBinary(matchRemoveNullTest("REGISTER").getValue(), 5) + "11001";
    }

    /** 
     * Parses immediate value for noR - 00
     * @return A binary string representing the immediate value
     * @throws Exception
     */
    private String noR() throws Exception {
        return toBinary(matchRemoveNullTest("NUMBER").getValue(), 27);
    }

    /** 
     * Parses immediate value and register for destOnly - 01
     * @return A binary string array containing immediate value and register
     * @throws Exception
     */
    private String[] destOnly(int flag) throws Exception {
        if (flag == 0) {
            return new String[]{toBinary(matchRemoveNullTest("NUMBER").getValue(), 18), toBinary(matchRemoveNullTest("REGISTER").getValue(), 5)};
        }
        return new String[]{toBinary(matchRemoveNullTest("REGISTER").getValue(), 5), toBinary(matchRemoveNullTest("NUMBER").getValue(), 18)};
    }

    /** 
     * Parses immediate value and registers for 2R - 11
     * @return A binary string array containing immediate value and registers
     * @throws Exception
     */
    private String[] twoR(int flag) throws Exception {
        if (flag == 0) {
            return new String[]{toBinary(matchRemoveNullTest("REGISTER").getValue(), 5), toBinary(matchRemoveNullTest("REGISTER").getValue(), 5), toBinary(matchRemoveNullTest("NUMBER").getValue(), 13)};
        } else if (flag == 1) {
            return new String[]{toBinary(matchRemoveNullTest("REGISTER").getValue(), 5), toBinary(matchRemoveNullTest("NUMBER").getValue(), 13), toBinary(matchRemoveNullTest("REGISTER").getValue(), 5)};
        }   
        return new String[]{toBinary(matchRemoveNullTest("REGISTER").getValue(), 5), toBinary(matchRemoveNullTest("REGISTER").getValue(), 5)};
    }

    /** 
     * Parses immediate value and registers for 3R - 10
     * @return A binary string array containing immediate value and registers
     * @throws Exception
     */
    private String[] threeR(int flag) throws Exception {
        if (flag == 0) {
            return new String[]{toBinary(matchRemoveNullTest("REGISTER").getValue(), 5), toBinary(matchRemoveNullTest("REGISTER").getValue(), 5), toBinary(matchRemoveNullTest("REGISTER").getValue(), 5)};
        } else {
            return new String[]{toBinary(matchRemoveNullTest("REGISTER").getValue(), 5), toBinary(matchRemoveNullTest("REGISTER").getValue(), 5),toBinary(matchRemoveNullTest("REGISTER").getValue(), 5),toBinary(matchRemoveNullTest("NUMBER").getValue(), 8)};
        }   
    }
    
    /** 
     * Converts a number into binary given the number and number of bits
     * @param numString Number represented as a string to be converted into binary
     * @param bits Number of bits for the number to be stored in
     * @return String Number in binary represented as a string
     * @throws Exception
     */
    private String toBinary(String numString,int bits) throws Exception {
        int num = Integer.parseInt(numString);
        String result = "";
        if (num == 0) {                         // When num is 0, fills string with 0's
            for (int i = 0; i < bits; i++) {
                result += "0";
            }
        } else if (bits == 5) {                 // If the number is a register
            if (num < 0 || num > 31) {
                throw new Exception("Register value must be in range 0-31.");
            }
            while (num > 0) {
                if (num % 2 == 0) {
                    result = "0" + result;
                } else {
                    result = "1" + result;
                }
                num /= 2;
            }
            while (result.length() < 5) {       // Pads the result with 0's
                result = "0" + result;
            }
        } else {
            if (num < -Math.pow(2, bits - 1) || num > Math.pow(2, bits - 1) - 1) {
                throw new Exception("Immediate value must be in range.");
            }
            int absValue = Math.abs(num);
            while (absValue > 0) {
                if (absValue % 2 == 0) {
                    result = "0" + result;
                } else {
                    result = "1" + result;
                }
                absValue /= 2;
            }
            while (result.length() < bits) {  // Pads the result with 0's
                result = "0" + result;
            }
            if (num < 0) {                  // If num is negative, preform two's compliment
                String invertedResult = "";
                for (int i = 0; i < bits; i++) {      // Inverts the result
                    if (result.charAt(i) == '0') {                      
                        invertedResult += '1';
                    } else {
                        invertedResult += '0';
                    }
                }
                result = "";
                int z = bits-1;
                while(invertedResult.charAt(z) == '1') {     // Increments the word
                    result = "0" + result;
                    z--;
                }
                result = "1" + result;
                result = invertedResult.substring(0, z) + result; // Copies the unchanged bits from inverted result into result
            }
        }
        return result;
    }

    /**
	 * if the next token matches the inputed type then return it
	 * or return null
	 * @param state Desired state of the token
	 * @return A token or null
	 */
	private Token matchAndRemove(String state) {
		// Checks if the state of the token equals the desired state
		if (tokens.get(0).getState().equals(Token.state.valueOf(state))) {
			Token token = tokens.get(0);
			tokens.remove(0);
			return token;
		} else {
			return null;
		}
	}

    /**
     * If the next token matches the inputed type then return it
	 * If the next token is null throws an exception
	 * @param token A token which is tested
	 * @throws Exception
	 */
	private Token matchRemoveNullTest(String state) throws Exception {
		// Checks if the state of the token equals the desired state
        Token token = matchAndRemove(state); 
		if (token == null) {
            throw new Exception("Instruction format not valid.");
		}
        return token;
	}

    /**
	 * Gets the next token from the list
     * @param num Number of tokens to look ahead
	 * @return The desired token from the list
	 */
	private Token peek(int num) {
		return tokens.get(num);
	}
	
	/**
	 * Removes a newline token and checks for empty lines in the file
	 */
    @SuppressWarnings("empty-statement")
	private void removeNewLines() {
		while (!(matchAndRemove("NEWLINE") == null));
	}
}