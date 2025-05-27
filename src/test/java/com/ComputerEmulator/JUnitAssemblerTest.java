package com.ComputerEmulator;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

/*
 * Unit tests all cases in the Assembler class
 * @author Kevin Meltzer
 * @version 1.6
 */
// TESTS MUST BE RUN SEPARATELY FOR ACCURATE RESULTS
public class JUnitAssemblerTest { 

    // Creates an instance of Processor that will be used for testing
    private final Processor testProcessor = new Processor();
    // Creates an instance of Assembler that will be used for testing
    private final Assembler testAssembler = new Assembler();

    private String getResourcePath(String filename) {
        return new File("src/test/resources", filename).getAbsolutePath();
    }

    // Tests assemble 
    @Test   
    public void JUnitAssemblerTest0() throws Exception{  
        testAssembler.assemble(new String[]{getResourcePath("input.txt"), "output.txt"});
        MainMemory.load("output.txt");                                                                                                                                                                                     
        testProcessor.run();    
        Assert.assertEquals(404,MainMemory.read(new Word(1023)).getSigned());                                                                                                                                                                                                                                                                                          
    }

    @Test   
    public void JUnitAssemblerFibonacciTest() throws Exception{  
        testAssembler.assemble(new String[]{getResourcePath("fibonacciInput.txt"), "fibonacciOutput.txt"});
        MainMemory.load("fibonacciOutput.txt");                                                                                                                                                                                     
        testProcessor.run();    
        Assert.assertEquals(89,testProcessor.getRegister(10).getSigned());  
    }

    @Test   
    public void JUnitAssemblerFactorialTest() throws Exception{                                                                                                                                                                                       
        testAssembler.assemble(new String[]{getResourcePath("factorialInput.txt"), "factorialOutput.txt"});
        MainMemory.load("factorialOutput.txt");           
        testProcessor.run();                                                                                                                                                                                                                                                                                                
        Assert.assertEquals(6,testProcessor.getRegister(2).getSigned());  
    }
}