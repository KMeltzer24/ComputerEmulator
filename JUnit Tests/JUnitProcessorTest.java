import org.junit.Assert;
import org.junit.Test;

/*
 * Unit tests all cases in the Processor class
 * @author Kevin Meltzer
 * @version 1.5
 */
// TESTS MUST BE RUN SEPARATELY FOR ACCURATE RESULTS
public class JUnitProcessorTest {

    // Creates an instance of Processor that will be used for testing
    private Processor testProcessor = new Processor();
    
    // Tests run (MUST BE RAN INDIVIDUALLY TO SEE PRINTED RESULTS ACCURATELY)
    @Test   
    public void JUnitProcessorFibonacciTest() throws Exception{                                                                                                                                                                                       
        MainMemory.load(new String[]{"00000000000000101100000000100001","00000000000100000100010000000111","00000000000000000000000101000001","00000000000000000000001000000100","00000000000000000000000001000001","00000000000000000100000001100001","00000000000000000100000010100001","00000000000000000100000011000001","00000000000010011011110000100010","00000000001010010101000000100111","00000000000100001111100010000010","00000000000110000011100001000010","00000000001000000011100001100010","00000000000000011011100010100011","11111111111111101000000000000101","00000000000110000011100101000010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(10).getSigned(),89);  
    }
    @Test   
    public void JUnitProcessorFactorialTest() throws Exception{                                                                                                                                                                                       
        MainMemory.load(new String[]{"00000000000000001100000000100001","00000000000000000100000001000001","00000000000000000100000001100001","00000000001010000100100000000111","00000000001010000100000000000111","00000000000000000101110001000011","00000000000010001111110000100010","00000000000100000101010000000111","00000000000000000000000010100100","00000000000000000000000001000001","00000000000000000000000000000000"});
        testProcessor.run();                                                                                                                                                                                                                                                                                                
        Assert.assertEquals(testProcessor.getRegister(2).getSigned(),6);  
    }

    // Tests Math - 000
    @Test
    public void JUnitProcessorR0Test() throws Exception{ 
        MainMemory.load(new String[]{"00000000000000000000000000100001","00000000000000000000000000000000"});          // r0 = 0, because r0 can never be overwritten
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(0).getSigned(),0);  
    }
    @Test   
    public void JUnitProcessorAndTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000100100000000100001","00000000000000100000000001000001","00000000000010001010000001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),8);  
    }
    @Test   
    public void JUnitProcessorOrTest() throws Exception{
        MainMemory.load(new String[]{"00000000000001011100000000100001","00000000000000100000000001000001","00000000000010001010010001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),31);  
    }
    @Test  
    public void JUnitProcessorXorTest() throws Exception{
        MainMemory.load(new String[]{"00000000000001011100000000100001","00000000000000100100000001000001","00000000000010001010100001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),30);  
    }
    @Test   
    public void JUnitProcessorNotTest() throws Exception{
        MainMemory.load(new String[]{"00000000000001011100000000100001","00000000000010001010110001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),-24);  
    }
    @Test   
    public void JUnitProcessorLShiftTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000000100000000100001","00000000000000001100000001000001","00000000000010001011000001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),8);  
    }
    @Test   
    public void JUnitProcessorRShiftTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000010100000000100001","00000000000000001000000001000001","00000000000010001011010001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),1);  
    }
    @Test  
    public void JUnitProcessorAddTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000010101110000100001","00000000000010000111100001000010","00000000000000001011100001000011","00000000000100000111100001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),25);  
    }
    @Test   
    public void JUnitProcessorSubTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000001000000000100001","00000000000100000111110001000010","00000000000000001011110000100011","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(1).getSigned(),4);  
    }
    @Test   
    public void JUnitProcessorMultTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000010000000000100001","00000000000000000101110000100011","00000000000000001100000001000001","00000000000010001001110001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),48);  
    }
    @Test   
    public void JUnitProcessorMultSubTest() throws Exception{
        MainMemory.load(new String[]{"11111111111111111000000000100001","00000000000000000101110000100011","00000000000100000111110001000010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(2).getSigned(),-4);  
    }
    @Test   
    public void JUnitProcessorAndAddTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000100100000000100001","00000000000001111100000001000001","00000000000010001010000001100010","00000000000100001111100010000010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(4).getSigned(),40);  
    }

    // Tests Branch - 001
    @Test   
    public void JUnitProcessorBranch0RTest1() throws Exception{
        MainMemory.load(new String[]{"00000000000000100100000000100001","00000000000000100000000001000001","00000000000000000000000010000100","00000000000010001011110001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),0);  
    }
    @Test   
    public void JUnitProcessorBranch0RTest2() throws Exception{
        MainMemory.load(new String[]{"00000000000000100100000000100001","00000000000000100000000001000001","00000000000000000000000010100100","00000000000010001011110001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),0);  
    }
    @Test   
    public void JUnitProcessorBranch1RTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000100100000000100001","00000000000000100000000001000001","00000000000000001000000000000101","00000000000010001011110001100010","00000000000000000000000000000000"});
        testProcessor.run();                                                                        
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),0);  
    }
    @Test   
    public void JUnitProcessorBranch1R0Test() throws Exception{
        MainMemory.load(new String[]{"00000000000000100100000000100001","00000000000000100000000001000001","00000000000000000000000000000101","00000000000010001011110001100010","00000000000000000000000000000000"});
        testProcessor.run();                                                                        
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),1);  
    }
    @Test   
    public void JUnitProcessorBranch2REQPassTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000100100000000100001","00000000000000100100000001000001","00000000000010000100000001000111","00000000000010001011110001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),0);  
    }
    @Test   
    public void JUnitProcessorBranch2REQFailTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000100100000000100001","00000000000000100000000001000001","00000000000010000100000001000111","00000000000010001011110001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),1);  
    }
    @Test   
    public void JUnitProcessorBranch2RGTPassTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000100100000000100001","00000000000000100000000001000001","00000000000010000101000001000111","00000000000010001011110001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),0);  
    }
    @Test   
    public void JUnitProcessorBranch2RGTFailTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000100000000000100001","00000000000000100100000001000001","00000000000010000101000001000111","00000000000010001011110001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),-1);  
    }
    @Test   
    public void JUnitProcessorBranch2RGEPassGTTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000100100000000100001","00000000000000100000000001000001","00000000000010000100110001000111","00000000000010001011110001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),0);  
    }
    @Test   
    public void JUnitProcessorBranch2RGEPassEQTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000100100000000100001","00000000000000100100000001000001","00000000000010000100110001000111","00000000000010001011110001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),0);  
    }
    @Test   
    public void JUnitProcessorBranch2RGEFailTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000100000000000100001","00000000000000100100000001000001","00000000000010000100110001000111","00000000000010001011110001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),-1);  
    }
    @Test   
    public void JUnitProcessorBranch3RNEQPassTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000100100000000100001","00000000000000100000000001000001","00000001000010001000010000000110","00000000000010001011110001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),0);  
    }
    @Test   
    public void JUnitProcessorBranch3RNEQFailTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000100100000000100001","00000000000000100100000001000001","00000001000010001000010000000110","00000000000010001011100001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),18);  
    }
    @Test   
    public void JUnitProcessorBranch3RLTPassTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000100000000000100001","00000000000000100100000001000001","00000001000010001000100000000110","00000000000010001011110001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),0);  
    }
    @Test   
    public void JUnitProcessorBranch3RLTFailTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000100100000000100001","00000000000000100100000001000001","00000001000010001000100000000110","00000000000010001011100001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),18);  
    }
    @Test   
    public void JUnitProcessorBranch3RLEPassLTTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000100000000000100001","00000000000000100100000001000001","00000001000010001001010000000110","00000000000010001011110001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),0);  
    }
    @Test   
    public void JUnitProcessorBranch3RLEPassEQTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000100100000000100001","00000000000000100100000001000001","00000001000010001001010000000110","00000000000010001011110001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),0);  
    }
    @Test   
    public void JUnitProcessorBranch3RLEFailTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000100100000000100001","00000000000000100000000001000001","00000001000010001001010000000110","00000000000010001011100001100010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(3).getSigned(),17);  
    }
    @Test   
    public void JUnitProcessorBranchTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000100000000000100001","00000000000000000100000001000001","00000000000000001011100001000011","11111110000100000100100000000110","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(2).getSigned(),8);  
    }
 
    // Tests Call - 010
    @Test   
    public void JUnitProcessorCall0RTest1() throws Exception{
        MainMemory.load(new String[]{"00000000000000000000000000100001","00000000000000000000000001101000","00000000000000000100000000100001","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(1).getSigned(),0);  
    }
    @Test   
    public void JUnitProcessorCall0RTest2() throws Exception{
        MainMemory.load(new String[]{"00000000000000000000000000100001","00000000000000000000000001001000","00000000000000000100000000100001","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(1).getSigned(),1); 
    }
    @Test   
    public void JUnitProcessorCall1RTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000000100000000100001","00000000000000001000000000101001","00000000000000001000000000100001","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(1).getSigned(),1);  
    }
    @Test   
    public void JUnitProcessorCall2RTest() throws Exception{
        MainMemory.load(new String[]{"11111111000000000000000000100001","11111111000000000000000001000001","00000000000100000100000001001011","00000000000000001000000000100001","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(1).getSigned(),-1024);  
    }
    @Test   
    public void JUnitProcessorCall3RTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000000100000000100001","00000000000000001100000001000001","00000001000010001000100001001010","00000000000000001000000000100001","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(1).getSigned(),1);  
    }

    // Tests Push - 011
    @Test   
    public void JUnitProcessorPush1RTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000000111100000001101","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(MainMemory.read(new Word(1023)).getSigned(),1);  
    }
    @Test   
    public void JUnitProcessorPush2RTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000000100000000100001","00000000000000001100000001000001","00000000000000001011110000101111","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(MainMemory.read(new Word(1023)).getSigned(),-2);  
    }
    @Test   
    public void JUnitProcessorPush3RTest() throws Exception{
        MainMemory.load(new String[]{"00000000000001111100000000100001","00000000000000010100000001000001","00000000000100000101110000001110","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(MainMemory.read(new Word(1023)).getSigned(),155);  
    }

    // Tests Load - 100
    @Test   
    public void JUnitProcessorLoad0RTest() throws Exception{
        MainMemory.load(new String[]{"00000000000000001111100000001101","00000000000000000000000000010000","00000000000000000100000000100001","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(1).getSigned(),0);  
    }
    @Test   
    public void JUnitProcessorLoad1RTest() throws Exception{
        MainMemory.load(new String[]{"00000011111111111111100000001101","00000000111111111100000000110001","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(1).getSigned(),4095);  
    }
    @Test   
    public void JUnitProcessorLoad2RTest() throws Exception{
        MainMemory.load(new String[]{"11111111111111111100000000100001","00000001111111111111100000001101","00100000000000000100000001010011","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(2).getSigned(),2047);  
    }
    @Test   
    public void JUnitProcessorLoad3RTest() throws Exception{
        MainMemory.load(new String[]{"00000000111111111100000000100001","11111111111111111111100000001101","00000000000010000000000001010010","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(testProcessor.getRegister(2).getSigned(),-1);
    }

    // Tests Store - 101
    @Test   
    public void JUnitProcessorStore1RTest() throws Exception{
        MainMemory.load(new String[]{"00000000000001100000000000100001","00000000000000000100000000110101","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(MainMemory.read(new Word(24)).getSigned(),1);  
    }
    @Test   
    public void JUnitProcessorStore2RTest() throws Exception{
        MainMemory.load(new String[]{"00000000111111100000000000100001","00001111111111111100000001000001","00000000000010001000000000110111","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(MainMemory.read(new Word(1017)).getSigned(),16383);  
    }
    @Test   
    public void JUnitProcessorStore3RTest() throws Exception{
        MainMemory.load(new String[]{"00000000000010000100000000100001","11111111111111111000000001000001","00000000000100000000010000110110","00000000000000000000000000000000"});
        testProcessor.run();
        Assert.assertEquals(MainMemory.read(new Word(31)).getSigned(),0);
    }

        // Tests Pop/Peek - 110
        @Test   
        public void JUnitProcessorPop1RTest() throws Exception{
            MainMemory.load(new String[]{"11111111111111111011100000001101","00000000000000000000000000111001","00000000000000000000000000000000"});
            testProcessor.run();
            Assert.assertEquals(testProcessor.getRegister(1).getSigned(),-2);  
        }
        @Test   
        public void JUnitProcessorPop2RTest() throws Exception{
            MainMemory.load(new String[]{"00000000000000000100000000100001","11111110000000000011100000101101","11111111111110000100000000111011","00000000000000000000000000000000"});
            testProcessor.run();
            Assert.assertEquals(testProcessor.getRegister(1).getSigned(),-2047); 
        }
        @Test   
        public void JUnitProcessorPop3RTest() throws Exception{
            MainMemory.load(new String[]{"11111111111111111100000000100001","00000000000000000111100000001101","00000000000000001011100000001101","00000000000000000100000000111010","00000000000000000000000000000000"});
            testProcessor.run();
            Assert.assertEquals(testProcessor.getRegister(1).getSigned(),1); 
        }

 }