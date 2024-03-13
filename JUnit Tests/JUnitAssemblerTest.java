import org.junit.Assert;
import org.junit.Test;

/*
 * Unit tests all cases in the Assembler class
 * @author Kevin Meltzer
 * @version 1.6
 */
public class JUnitAssemblerTest { 

    // Creates an instance of Processor that will be used for testing
    private Processor testProcessor = new Processor();
    // Creates an instance of Assembler that will be used for testing
    private Assembler testAssembler = new Assembler();

    // Tests assemble 
    @Test   
    public void JUnitAssemblerTest0() throws Exception{  
        testAssembler.assemble(new String[]{"input.txt", "output.txt"});
        MainMemory.load("output.txt");                                                                                                                                                                                     
        testProcessor.run();    
        Assert.assertEquals(MainMemory.read(new Word(1023)).getSigned(),404);                                                                                                                                                                                                                                                                                          
    }

    @Test   
    public void JUnitAssemblerFibonacciTest() throws Exception{  
        testAssembler.assemble(new String[]{"fibonacciInput.txt", "fibonacciOutput.txt"});
        MainMemory.load("fibonacciOutput.txt");                                                                                                                                                                                     
        testProcessor.run();    
        Assert.assertEquals(testProcessor.getRegister(10).getSigned(),89);  
    }

    @Test   
    public void JUnitAssemblerFactorialTest() throws Exception{                                                                                                                                                                                       
        testAssembler.assemble(new String[]{"factorialInput.txt", "factorialOutput.txt"});
        MainMemory.load("factorialOutput.txt");           
        testProcessor.run();                                                                                                                                                                                                                                                                                                
        Assert.assertEquals(testProcessor.getRegister(2).getSigned(),6);  
    }
}