/* 
 * Represents a Instruction Cache 
 * @author Kevin Meltzer
 * @version 1.7
 */
public class InstructionCache {
    // An array of words representing a cache
    private static Word[] cache = new Word[8];
    // Word representing the address of first item in cache 
    private static Word firstAddress;

    /** 
     * Reads a word from cache array and returns the result as a new word
     * @param address A word representing the address of desired bit
     * @return A new word which was located at given address
     * @throws Exception
     */
    public static Word read(Word address) {
        Word result = new Word();
        long distance = -1;
        if (firstAddress != null) {
            distance = address.getUnsigned() - firstAddress.getUnsigned();
        }
        if (distance >= 0 && distance < 8) {                       // If address is in cache, copy data from cache and add 10 cycles
            result.copy(cache[(int)distance]);
            Processor.currentClockCycle += 10;
        } else {                                                   
            result.copy(L2Cache.readInstruction(address));                   
        }
        return result;
    }

    /** 
     * Copies a cache from L2 into InstructionCache for a cost of 50 clock cycles
     * @param L2Cache An L2 cache which will fill InstructionCache
     */
    public static void copyFromL2(Word[] L2Cache, Word L2firstAddress) { 
        for (int i = 0; i < 8; i++) {
            if (cache[i] == null) {
                cache[i] = new Word();
            }
            cache[i].copy(L2Cache[i]);
        }
        firstAddress = new Word();
        firstAddress.copy(L2firstAddress);
        Processor.currentClockCycle += 50;
    }

    /**
     * Tests if an array of numbers is equal to the cache's values for testing.
     * @param arr An array of numbers to be compared to the cache
     * @return true if equal and false if not equal
     */
   public static Boolean cacheEqualTest(int[] arr) {
       for (int i = 0; i < 8; i++) {
            if (arr[i] != cache[i].getSigned()) {
                return false;
            }
       }
       return true;
   }

}