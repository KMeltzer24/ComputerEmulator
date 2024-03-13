import java.nio.file.Files;
import java.nio.file.Paths;

/*
 * Represents the Main Memory which data will be stored in
 * @author Kevin Meltzer
 * @version 1.3
 */
public class MainMemory {                                                       
    
    // An array of words which represents the main memory
    private static Word[] mainMem = new Word[1024];
    
    /** 
     * Reads a word from main memory array and returns the result as a new word
     * @param address A word representing the address of desired bit
     * @return A new word which was located at given address
     */
    public static Word read(Word address) {
        if (mainMem[(int)address.getUnsigned()] == null) {
            mainMem[(int)address.getUnsigned()] = new Word();
        }
        Word result = new Word();
        result.copy(mainMem[(int)address.getUnsigned()]);
        return result;
    }

    
    /** 
     * Writes a word into main memory array
     * @param address A word representing the address of desired location which is to be written to
     * @param value A word which gets written into main memory
     */
    public static void write(Word address, Word value) {
        if (mainMem[(int)address.getUnsigned()] == null) {
            mainMem[(int)address.getUnsigned()] = new Word();     
        }
        mainMem[(int)address.getUnsigned()].copy(value);
    }

    
    /** 
     * Processes an array of Strings into main memory starting at 0
     * @param data An array of strings containing 0's and 1's which will be converted to words and inserted into main memory
     * @throws Exception 
     */
    public static void load(String[] data) throws Exception {                                                                
        int index = 0;
        for (String str : data) {
            if (str.length() != 32) {
                throw new Exception("Input String number " + ++index + " must be a 32 bits.");
            }
            mainMem[index] = new Word();
            for (int x = 0; x < 32; x++) {
                if (str.charAt(x) == '0') {
                    mainMem[index].setBit(x, new Bit(false));
                } else if (str.charAt(x) == '1') {
                    mainMem[index].setBit(x, new Bit(true));
                } else {
                    throw new Exception("Input String number " + ++index + " must only contain 0's and 1's.");
                }
            }
            index++;
        }
    }

    /** 
     * Processes an file into main memory starting at 0
     * @param fileName An file of strings containing 0's and 1's which will be converted to words and inserted into main memory
     * @throws Exception 
     */
    public static void load(String fileName) throws Exception {     
        load(Files.readAllLines(Paths.get(fileName)).toArray(new String[0]));                                                        
    }
}
