import org.junit.Assert;
import org.junit.Test;

/*
 * Unit tests some cases in the InstructionCache and L2Cache classes
 * @author Kevin Meltzer
 * @version 1.7
 */
// TESTS MUST BE RUN SEPARATELY FOR ACCURATE RESULTS
public class JUnitCacheTest { 

    // Creates an instance of Processor that will be used for testing
    private Processor testProcessor = new Processor();
    // Creates an instance of Assembler that will be used for testing
    private Assembler testAssembler = new Assembler();

    // Tests to make sure InstructionCache and L2Cache are being filled and accessed correctly
    @Test
    public void JUnitCacheTest0() throws Exception {
        int[] L2Cache0 = new int[]{1638433,65601,30787,540743,30787,1079394,1079426,1653922};
        int[] L2Cache1 = new int[]{2653378,1146919,30915,-49147,211910679,216055831,6619377,0};
        int[] L2Cache2 = new int[]{100,0,0,0,0,0,0,0};
        int[] L2Cache3 = new int[]{304,0,0,0,0,0,0,0};
        testAssembler.assemble(new String[]{"cacheTest0Input.txt", "cacheTest0Output.txt"});
        MainMemory.load("cacheTest0Output.txt");                                                                                                                                                                                     
        testProcessor.run();    
        Boolean success = true;
        if (!InstructionCache.cacheEqualTest(L2Cache1)) {
            success = false;
        }
        if (!L2Cache.cacheEqualTest(L2Cache0, 0)) {
            success = false;
        }
        if (!L2Cache.cacheEqualTest(L2Cache1, 1)) {
            success = false;
        }
        if (!L2Cache.cacheEqualTest(L2Cache2, 2)) {
            success = false;
        }
        if (!L2Cache.cacheEqualTest(L2Cache3, 3)) {
            success = false;
        }
        Assert.assertEquals(success,true);                                                                                                                                                                                                                                                                                              
    }          

    // Tests a few sample programs to measure clock times
    @Test   
    public void JUnitClockTest0() throws Exception {
        for (int i = 100; i < 120; i++) {
            if (i%2 == 0) {
                MainMemory.write(new Word(i), new Word(1));
            } else {
                MainMemory.write(new Word(i), new Word(10));
            }
        }  
        testAssembler.assemble(new String[]{"clockTest0Input.txt", "clockTest0Output.txt"});
        MainMemory.load("clockTest0Output.txt");                                                                                                                                                                                     
        testProcessor.run();    
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),110);           // Clock cycles no cache:                                 36798                                                                                                                                                                                                                                                                                      
    }                                                                                         // Clock cycles instructionCache:                         7898
                                                                                              // Clock cycles instructionCache + L2(instructions only): 7998
                                                                                              // Clock cycles instructionCache + L2:                    3448
    @Test  
    public void JUnitClockTest1() throws Exception {
        for (int i = 100; i < 140; i+=4) {
            if (i%8 == 0) {
                MainMemory.write(new Word(i), new Word(1));
                MainMemory.write(new Word(i+1), new Word(i+4));
            } else {
                MainMemory.write(new Word(i), new Word(10));
                MainMemory.write(new Word(i+1), new Word(i+4));
            }
        }
        MainMemory.write(new Word(137), new Word(500));
        for (int i = 500; i < 540; i+=4) {
            if (i%8 == 0) {
                MainMemory.write(new Word(i), new Word(1));
                MainMemory.write(new Word(i+1), new Word(i+4));
            } else {
                MainMemory.write(new Word(i), new Word(10));
                MainMemory.write(new Word(i+1), new Word(i+4));
            }
        } 
        MainMemory.write(new Word(537), new Word(0));
        testAssembler.assemble(new String[]{"clockTest1Input.txt", "clockTest1Output.txt"});
        MainMemory.load("clockTest1Output.txt");                                                                                                                                                                                     
        testProcessor.run();    
        Assert.assertEquals(testProcessor.getRegister(4).getSigned(),110);  // Clock cycles:                                          48238                                                                                                                                                                                                                                                                                      
    }                                                                                // Clock cycles instructionCache:                         13778
                                                                                     // Clock cycles instructionCache + L2(instructions only): 13828
                                                                                     // Clock cycles instructionCache + L2:                    6128
    @Test   
    public void JUnitClockTest2() throws Exception {
        for (int i = 100; i < 120; i++) {
            if (i%2 == 0) {
                MainMemory.write(new Word(i), new Word(1));
            } else {
                MainMemory.write(new Word(i), new Word(10));
            }
        }  
        testAssembler.assemble(new String[]{"clockTest2Input.txt", "clockTest2Output.txt"});
        MainMemory.load("clockTest2Output.txt");   
        testProcessor.run();    
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),110);  // Clock cycles:                                          36798                                                                                                                                                                                                                                                                                      
    }                                                                                // Clock cycles instructionCache:                         7898
                                                                                     // Clock cycles instructionCache + L2(instructions only): 7998
                                                                                     // Clock cycles instructionCache + L2:                    9398
}