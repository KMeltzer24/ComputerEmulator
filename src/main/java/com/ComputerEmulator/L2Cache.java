package com.ComputerEmulator;

/* 
 * Represents a L2 Cache with FIFO replacement
 * @author Kevin Meltzer
 * @version 1.7
 */
public class L2Cache {
    // An array of words representing an L2 cache
    private final static Word[] cache0 = new Word[8];
    private final static Word[] cache1 = new Word[8];
    private final static Word[] cache2 = new Word[8];
    private final static Word[] cache3 = new Word[8];

    // Word array representing the addresses of first item in each of the caches 
    private final static Word[] firstAddress = new Word[4];

    // Holds the index of the oldest cache
    private static int oldestCache = 0;

    /** 
     * Reads a instruction word from L2 cache arrays and returns the result as a new word
     * @param address A word representing the address of desired bit
     * @return A new word which was located at given address
     */
    public static Word readInstruction(Word address) {
        Word result = new Word();

        // distances from start of cache addresses
        long distance0 = -1;
        long distance1 = -1;
        long distance2 = -1;
        long distance3 = -1;
        

        if (firstAddress[0] != null) {                                              // Finds the distance between the address and the first
            distance0 = address.getUnsigned() - firstAddress[0].getUnsigned();      // addresses of the caches, if they are not empty
            if (firstAddress[1] != null) {
                distance1 = address.getUnsigned() - firstAddress[1].getUnsigned();
                if (firstAddress[2] != null) {
                    distance2 = address.getUnsigned() - firstAddress[2].getUnsigned();
                    if (firstAddress[3] != null) {
                        distance3 = address.getUnsigned() - firstAddress[3].getUnsigned();
                    }
                }
            }
        }

        // If address is in a L2 cache, set result to data and copy cache up to InstructionCache
        if (distance0 >= 0 && distance0 < 8) {
            result.copy(cache0[(int)distance0]);
            InstructionCache.copyFromL2(cache0, firstAddress[0]);
        } else if (distance1 >= 0 && distance1 < 8) {
            result.copy(cache1[(int)distance1]);
            InstructionCache.copyFromL2(cache1, firstAddress[1]);
        } else if (distance2 >= 0 && distance2 < 8) {
            result.copy(cache2[(int)distance2]);
            InstructionCache.copyFromL2(cache2, firstAddress[2]);
        } else if (distance3 >= 0 && distance3 < 8) {
            result.copy(cache3[(int)distance3]);
            InstructionCache.copyFromL2(cache3, firstAddress[3]);
        } else {                                                    // If address is not in a L2 cache, fill cache from main memory for 350 clock cycles,
            result.copy(copyBlock(address));                        // set result to data, and copy cache up to InstructionCache
            switch (oldestCache) {
                case 0 -> InstructionCache.copyFromL2(cache3, firstAddress[3]);
                case 1 -> InstructionCache.copyFromL2(cache0, firstAddress[0]);
                case 2 -> InstructionCache.copyFromL2(cache1, firstAddress[1]);
                case 3 -> InstructionCache.copyFromL2(cache2, firstAddress[2]);
            }
        } 
        return result;
    }

    /** 
     * Reads a non instruction word from L2 cache arrays and returns the result as a new word
     * @param address A word representing the address of desired bit
     * @return A new word which was located at given address
     */
    public static Word read(Word address) {
        Word result = new Word();

        // distances from start of cache addresses
        long distance0 = -1;
        long distance1 = -1;
        long distance2 = -1;
        long distance3 = -1;

        if (firstAddress[0] != null) {                                              // Finds the distance between the address and the first
            distance0 = address.getUnsigned() - firstAddress[0].getUnsigned();      // addresses of the caches, if they are not empty
            if (firstAddress[1] != null) {
                distance1 = address.getUnsigned() - firstAddress[1].getUnsigned();
                if (firstAddress[2] != null) {
                    distance2 = address.getUnsigned() - firstAddress[2].getUnsigned();
                    if (firstAddress[3] != null) {
                        distance3 = address.getUnsigned() - firstAddress[3].getUnsigned();
                    }
                }
            }
        }

        // If address is in a L2 cache, set result to data and add 20 cycles
        if (distance0 >= 0 && distance0 < 8) {
            result.copy(cache0[(int)distance0]);
            Processor.currentClockCycle += 20;
        } else if (distance1 >= 0 && distance1 < 8) {
            result.copy(cache1[(int)distance1]);
            Processor.currentClockCycle += 20;
        } else if (distance2 >= 0 && distance2 < 8) {
            result.copy(cache2[(int)distance2]);
            Processor.currentClockCycle += 20;
        } else if (distance3 >= 0 && distance3 < 8) {
            result.copy(cache3[(int)distance3]);
            Processor.currentClockCycle += 20;
        } else {                                                    // If address is not in a L2 cache, fill cache from main memory, set result to data, and add 20 cycles
            result.copy(copyBlock(address));                        
            Processor.currentClockCycle += 20;

        } 
        return result;
    }

    /** 
     * Writes a word into an L2 cache
     * @param address A word representing the address of desired location which is to be written to
     * @param value A word which gets written into main memory
     */
    public static void write(Word address, Word value) {                               

        // distances from start of cache addresses
        long distance0 = -1;
        long distance1 = -1;
        long distance2 = -1;
        long distance3 = -1;
        

        if (firstAddress[0] != null) {                                              // Finds the distance between the address and the first
            distance0 = address.getUnsigned() - firstAddress[0].getUnsigned();      // addresses of the caches, if they are not empty
            if (firstAddress[1] != null) {
                distance1 = address.getUnsigned() - firstAddress[1].getUnsigned();
                if (firstAddress[2] != null) {
                    distance2 = address.getUnsigned() - firstAddress[2].getUnsigned();
                    if (firstAddress[3] != null) {
                        distance3 = address.getUnsigned() - firstAddress[3].getUnsigned();
                    }
                }
            }
        }

        // If address is in a L2 cache, write to cache for 50 cycles, and then write to main memory 
        if (distance0 >= 0 && distance0 < 8) {
            cache0[(int)distance0].copy(value);
            Processor.currentClockCycle += 50;
        } else if (distance1 >= 0 && distance1 < 8) {
            cache1[(int)distance1].copy(value);
            Processor.currentClockCycle += 50;
        } else if (distance2 >= 0 && distance2 < 8) {
            cache2[(int)distance2].copy(value);
            Processor.currentClockCycle += 50;
        } else if (distance3 >= 0 && distance3 < 8) {
            cache3[(int)distance3].copy(value);
            Processor.currentClockCycle += 50;
        } else {                                // If address is not in L2, write to main memory, then copy block up to L2 for 50 cycles
            MainMemory.write(address, value);
            copyBlock(address);
            Processor.currentClockCycle += 50;
            return;
        }
        MainMemory.write(address, value);
    }


    
    /** 
     * Copies a block from main memory into L2 for 350 cycles
     * @param address desired address for the block to contain
     * @return A word representing the data at address
     */
    private static Word copyBlock(Word address) {
        int i = 0;                                             
        int location = 0;
        Word tempAddr = new Word();
        Word result = new Word();
        if (address.getUnsigned() < 1016) {     // If address is at least 8 slots away from end of main memory,
            tempAddr.copy(address);             // set tempAddr to address 
        } else {                                // else set tempAddr to 1016, and save distance of address from 1016 by incrementing location
            tempAddr.copy(address);
            while (tempAddr.getUnsigned() > 1016) {
                tempAddr.decrement();
                location++;
            }
        }
        switch (oldestCache) {                  // Reads block of main memory into oldest cache, adds 350 clock cycles, and changes the oldest cache.
            case 0 -> {
                firstAddress[0] = new Word();
                firstAddress[0].copy(tempAddr);
                while (i < 8) {
                    if (cache0[i] == null) {
                        cache0[i] = new Word();
                    }
                    cache0[i].copy(MainMemory.read(tempAddr));
                    tempAddr.increment();
                    i++;
                }
                result.copy(cache0[location]);
                Processor.currentClockCycle += 350;
                oldestCache++;
            }

            case 1 -> {
                firstAddress[1] = new Word();
                firstAddress[1].copy(tempAddr);
                while (i < 8) {
                    if (cache1[i] == null) {
                        cache1[i] = new Word();
                    }
                    cache1[i].copy(MainMemory.read(tempAddr));
                    tempAddr.increment();
                    i++;
                }
                result.copy(cache1[location]);
                Processor.currentClockCycle += 350;
                oldestCache++;
            }

            case 2 -> {
                firstAddress[2] = new Word();
                firstAddress[2].copy(tempAddr);
                while (i < 8) {
                    if (cache2[i] == null) {
                        cache2[i] = new Word();
                    }
                    cache2[i].copy(MainMemory.read(tempAddr));
                    tempAddr.increment();
                    i++;
                }
                result.copy(cache2[location]);
                Processor.currentClockCycle += 350;
                oldestCache++;
            }

            case 3 -> {
                firstAddress[3] = new Word();
                firstAddress[3].copy(tempAddr);
                while (i < 8) {
                    if (cache3[i] == null) {
                        cache3[i] = new Word();
                    }
                    cache3[i].copy(MainMemory.read(tempAddr));
                    tempAddr.increment();
                    i++;
                }
                result.copy(cache3[location]);
                Processor.currentClockCycle += 350;
                oldestCache = 0;
            }
        }
        // Reads block of main memory into oldest cache, adds 350 clock cycles, and changes the oldest cache.
        return result;
    }

    /**
     * Tests if an array of numbers is equal to a cache's values for testing.
     * @param arr An array of numbers to be compared to a cache
     * @param cacheNum The number of the cache to be compared to
     * @return true if equal and false if not equal
     */
    public static Boolean cacheEqualTest(int[] arr, int cacheNum) {
        for (int i = 0; i < 8; i++) {
            switch (cacheNum) {                  
                case 0 -> {
                    if (arr[i] != cache0[i].getSigned()) {
                        return false;
                    }
                }
    
                case 1 -> {
                    if (arr[i] != cache1[i].getSigned()) {
                        return false;
                    }
                }
    
                case 2 -> {
                    if (arr[i] != cache2[i].getSigned()) {
                        return false;
                    }
                }
    
                case 3 -> {
                    if (arr[i] != cache3[i].getSigned()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}