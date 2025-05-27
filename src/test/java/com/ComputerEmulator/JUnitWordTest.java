package com.ComputerEmulator;

import org.junit.Assert;
import org.junit.Test;

/*
 * Unit tests all cases in the Word class
 * @author Kevin Meltzer
 * @version 1.5
 */
public class JUnitWordTest {                                                       

    // Helper functions which create words for testing
    private Word wordAllTrue() {     // 11111111 11111111 11111111 11111111
        return new Word(-1);
    }
    private Word wordAllFalse() {    // 00000000 00000000 00000000 00000000
        return new Word(0);
    }
    private Word wordMix1() {        // 11111111 11111111 00000000 00000000
        return new Word(-65536);
    }
    private Word wordMix2() {        // 00000000 00000000 11111111 11111111
        return new Word(65535);
    }


    // Tests getBit(int i)
    @Test
    public void JUnitWordGetBitTrueTest(){
        Assert.assertTrue(wordAllTrue().getBit(0).getValue());
    }
    @Test
    public void JUnitWordGetBitFalseTest(){
        Assert.assertFalse(wordAllFalse().getBit(0).getValue());
    }
    @Test
    public void JUnitWordGetBitMixTest(){
        Assert.assertTrue(wordMix1().getBit(0).getValue());
        Assert.assertFalse(wordMix1().getBit(31).getValue());
    }


    // Tests setBit(int i)
    @Test
    public void JUnitWordSetBitTrueTest(){
        Word testWord = wordAllTrue();
        testWord.setBit(0, new Bit(false));
        Assert.assertFalse(testWord.getBit(0).getValue());
    }
    @Test
    public void JUnitWordSetBitFalseTest(){
        Word testWord = wordAllFalse();
        testWord.setBit(0, new Bit(true));
        Assert.assertTrue(testWord.getBit(0).getValue());
    }
    @Test
    public void JUnitWordSetBitMixTest(){
        Word testWord = wordMix1();
        testWord.setBit(0, new Bit(false));
        testWord.setBit(31, new Bit(true));
        Assert.assertFalse(testWord.getBit(0).getValue());
        Assert.assertTrue(testWord.getBit(31).getValue());
    }
    
    
    // Tests and(Word other)
    @Test
    public void JUnitWordAndTrueTest(){
        Assert.assertEquals(wordAllTrue().and(wordAllTrue()).toString(),wordAllTrue().toString());
    }
    @Test
    public void JUnitWordAndFalseTest(){
        Assert.assertEquals(wordAllFalse().and(wordAllFalse()).toString(),wordAllFalse().toString());
    }
    @Test
    public void JUnitWordAndTrueFalseTest(){
        Assert.assertEquals(wordAllTrue().and(wordAllFalse()).toString(),wordAllFalse().toString());
    }
    @Test
    public void JUnitWordAndFalseTrueTest(){
        Assert.assertEquals(wordAllFalse().and(wordAllTrue()).toString(),wordAllFalse().toString());
    }
    @Test
    public void JUnitWordAndMix1Test(){
        Assert.assertEquals(wordMix1().and(wordMix1()).toString(),wordMix1().toString());
    }
    @Test
    public void JUnitWordAndMix2Test(){
        Assert.assertEquals(wordMix2().and(wordMix2()).toString(),wordMix2().toString());
    }
    @Test
    public void JUnitWordAndMix3Test(){
        Assert.assertEquals(wordMix1().and(wordMix2()).toString(),wordAllFalse().toString());
    }
    @Test
    public void JUnitWordAndMix4Test(){
        Assert.assertEquals(wordMix2().and(wordMix1()).toString(),wordAllFalse().toString());
    }
    
    
    // Tests or(Word other)
    @Test
    public void JUnitWordOrTrueTest(){
        Assert.assertEquals(wordAllTrue().or(wordAllTrue()).toString(),wordAllTrue().toString());
    }
    @Test
    public void JUnitWordOrFalseTest(){
        Assert.assertEquals(wordAllFalse().or(wordAllFalse()).toString(),wordAllFalse().toString());
    }
    @Test
    public void JUnitWordOrTrueFalseTest(){
        Assert.assertEquals(wordAllTrue().or(wordAllFalse()).toString(),wordAllTrue().toString());
    }
    @Test
    public void JUnitWordOrFalseTrueTest(){
        Assert.assertEquals(wordAllFalse().or(wordAllTrue()).toString(),wordAllTrue().toString());
    }
    @Test
    public void JUnitWordOrMix1Test(){
        Assert.assertEquals(wordMix1().or(wordMix1()).toString(),wordMix1().toString());
    }
    @Test
    public void JUnitWordOrMix2Test(){
        Assert.assertEquals(wordMix2().or(wordMix2()).toString(),wordMix2().toString());
    }
    @Test
    public void JUnitWordOrMix3Test(){
        Assert.assertEquals(wordMix1().or(wordMix2()).toString(),wordAllTrue().toString());
    }
    @Test
    public void JUnitWordOrMix4Test(){
        Assert.assertEquals(wordMix2().or(wordMix1()).toString(),wordAllTrue().toString());
    }
    
    
    // Tests xor(Word other);
    @Test
    public void JUnitWordXorTrueTest(){
        Assert.assertEquals(wordAllTrue().xor(wordAllTrue()).toString(),wordAllFalse().toString());
    }
    @Test
    public void JUnitWordXorFalseTest(){
        Assert.assertEquals(wordAllFalse().xor(wordAllFalse()).toString(),wordAllFalse().toString());
    }
    @Test
    public void JUnitWordXorTrueFalseTest(){
        Assert.assertEquals(wordAllTrue().xor(wordAllFalse()).toString(),wordAllTrue().toString());
    }
    @Test
    public void JUnitWordXorFalseTrueTest(){
        Assert.assertEquals(wordAllFalse().xor(wordAllTrue()).toString(),wordAllTrue().toString());
    }
    @Test
    public void JUnitWordXorMix1Test(){
        Assert.assertEquals(wordMix1().xor(wordMix1()).toString(),wordAllFalse().toString());
    }
    @Test
    public void JUnitWordXorMix2Test(){
        Assert.assertEquals(wordMix2().xor(wordMix2()).toString(),wordAllFalse().toString());
    }
    @Test
    public void JUnitWordXorMix3Test(){
        Assert.assertEquals(wordMix1().xor(wordMix2()).toString(),wordAllTrue().toString());
    }
    @Test
    public void JUnitWordXorMix4Test(){
        Assert.assertEquals(wordMix2().xor(wordMix1()).toString(),wordAllTrue().toString());
    }
    
    
    // Tests not();
    @Test
    public void JUnitWordNotTrueTest(){
        Word testWord = wordAllTrue().not();
        Assert.assertEquals(testWord.toString(),wordAllFalse().toString());
    }
    @Test
    public void JUnitWordNotFalseTest(){
        Word testWord = wordAllFalse().not();
        Assert.assertEquals(testWord.toString(),wordAllTrue().toString());
    }
    @Test
    public void JUnitWordNotMix1Test(){
        Word testWord = wordMix1().not();
        Assert.assertEquals(testWord.toString(),wordMix2().toString());
    } 
    @Test
    public void JUnitWordNotMix2Test(){
        Word testWord = wordMix2().not();
        Assert.assertEquals(testWord.toString(),wordMix1().toString());
    } 
    
    
    // Tests rightShift(int amount);
    @Test
    public void JUnitWordRightShiftMix1Test(){
        Word testWord1 = wordMix1().rightShift(1);
        Word testWord2 = new Word(2147450880);                     // 01111111 11111111 10000000 00000000
        Assert.assertEquals(testWord1.toString(),testWord2.toString());
    }
    @Test
    public void JUnitWordRightShiftMix2Test(){
        Word testWord1 = wordMix2().rightShift(10);
        Word testWord2 = new Word(63);                            // 00000000 00000000 00000000 00111111
        Assert.assertEquals(testWord1.toString(),testWord2.toString());
    }
    
        // Tests leftShift(int amount);
        @Test
        public void JUnitWordLeftShiftMix1Test(){
            Word testWord1 = wordMix1().leftShift(1);   
            Word testWord2 = new Word(-131072);                 // 11111111 11111110 00000000 00000000
            Assert.assertEquals(testWord1.toString(),testWord2.toString());
        }
        @Test
        public void JUnitWordLeftShiftMix2Test(){
            Word testWord1 = wordMix2().leftShift(10);
            Word testWord2 = new Word(67107840);                    // 00000011 11111111 11111100 00000000
            Assert.assertEquals(testWord1.toString(),testWord2.toString());
        }
    
    
    // Tests toString();
    @Test
    public void JUnitWordToStringTrueTest(){
        Word testWord = wordAllTrue();
        String result = "";
        for (int i = 0; i < 32; i++) {
            if (i < 31) {
                result += "t, ";
            } else {
                result += "t";
            }
        }
        Assert.assertEquals(testWord.toString(),result);
    } 
    @Test
    public void JUnitWordToStringFalseTest(){
        Word testWord = wordAllFalse();
        String result = "";
        for (int i = 0; i < 32; i++) {
            if (i < 31) {
                result += "f, ";
            } else {
                result += "f";
            }
        }
        Assert.assertEquals(testWord.toString(),result);
    } 
    @Test
    public void JUnitWordToStringMix1Test(){
        Word testWord = wordMix1();
        String result = "";
        for (int i = 0; i < 32; i++) {
            if (i < 16) {
                result += "t, ";
            } else if (i < 31) {
                result += "f, ";
            } else {
                result += "f";
            }
        }
        Assert.assertEquals(testWord.toString(),result);
    } 
    @Test
    public void JUnitWordToStringMix2Test(){
        Word testWord = wordMix2();
        String result = "";
        for (int i = 0; i < 32; i++) {
            if (i < 16) {
                result += "f, ";
            } else if (i < 31) {
                result += "t, ";
            } else {
                result += "t";
            }
        }
        Assert.assertEquals(testWord.toString(),result);
    } 
    

    // Tests getUnsigned();
    @Test
    public void JUnitWordUnsignedZeroTest(){
        Word testWord = new Word(0);
        Assert.assertEquals(0,testWord.getUnsigned());
    } 
    @Test
    public void JUnitWordUnsignedPosLowTest(){
        Word testWord = new Word(10);
        Assert.assertEquals(10,testWord.getUnsigned());
    } 
    @Test
    public void JUnitWordUnsignedPosHighTest(){
        Word testWord = new Word(268435455);
        Assert.assertEquals(268435455,testWord.getUnsigned());
    } 
    @Test
    public void JUnitWordUnsignedNegLowTest(){
        Word testWord = new Word(-10);
        Assert.assertEquals(10,testWord.getUnsigned());
    } 
    @Test
    public void JUnitWordUnsignedNegHighTest(){
        Word testWord = new Word(-268435455);
        Assert.assertEquals(268435455,testWord.getUnsigned());
    } 
    

    // Tests getSigned(); 
    @Test
    public void JUnitWordSignedZeroTest(){
        Word testWord = new Word(0);
        Assert.assertEquals(0,testWord.getSigned());
    } 
    @Test
    public void JUnitWordSignedPosLowTest(){
        Word testWord = new Word(10);
        Assert.assertEquals(10,testWord.getSigned());
    } 
    @Test
    public void JUnitWordSignedPosHighTest(){
        Word testWord = new Word(268435455);
        Assert.assertEquals(268435455,testWord.getSigned());
    } 
    @Test
    public void JUnitWordSignedNegLowTest(){
        Word testWord = new Word(-10);
        Assert.assertEquals(-10,testWord.getSigned());
    } 
    @Test
    public void JUnitWordSignedNegHighTest(){
        Word testWord = new Word(-268435455);
        Assert.assertEquals(-268435455,testWord.getSigned());
    } 

    // Tests copy(Word other);
    @Test
    public void JUnitWordCopyTrueTest(){    
        Word testWord = new Word();
        testWord.copy(wordAllTrue());
        Assert.assertEquals(wordAllTrue().toString(),testWord.toString());
    }
    @Test
    public void JUnitWordCopyFalseTest(){    
        Word testWord = new Word();
        testWord.copy(wordAllFalse());
        Assert.assertEquals(wordAllFalse().toString(),testWord.toString());
    }
    @Test
    public void JUnitWordCopyMix1Test(){    
        Word testWord = new Word();
        testWord.copy(wordMix1());
        Assert.assertEquals(wordMix1().toString(),testWord.toString());
    }
    @Test
    public void JUnitWordCopyMix2Test(){    
        Word testWord = new Word();
        testWord.copy(wordMix2());
        Assert.assertEquals(wordMix2().toString(),testWord.toString());
    }  
    
    
    // Tests set(int value);
    @Test
    public void JUnitWordSet1Test(){    
        Word testWord = new Word();       
        testWord.set(-1);               // 11111111 11111111 11111111 11111111
        String result = "";
        for (int i = 0; i < 32; i++) {    // t,t,t,t,.....
            if (i < 31) {
                result += "t, ";
            } else {
                result += "t";
            }
        }
        Assert.assertEquals(testWord.toString(),result);
    }
    @Test
    public void JUnitWordSet2Test(){   
        Word testWord = new Word();     
        testWord.set(0);          // 00000000 00000000 00000000 00000000
        String result = "";
        for (int i = 0; i < 32; i++) {  // f,f,f,f,.....
            if (i < 31) {
                result += "f, ";
            } else {
                result += "f";
            }
        }
        Assert.assertEquals(testWord.toString(),result);
    }
    @Test
    public void JUnitWordSet3Test(){        
        Word testWord = new Word();   
        testWord.set(-65536);               // 11111111 11111111 00000000 00000000
        String result = "";
        for (int i = 0; i < 32; i++) {      // t,t,t,t,.....f,f,f,f,f....
            if (i < 16) {
                result += "t, ";
            } else if (i < 31) {
                result += "f, ";
            } else {
                result += "f";
            }
        }
        Assert.assertEquals(testWord.toString(),result);
    }
    @Test
    public void JUnitWordSet4Test(){        
        Word testWord = new Word();    
        testWord.set(65535);            // 00000000 00000000 11111111 11111111
        String result = "";
        for (int i = 0; i < 32; i++) {      // f,f,f,f,.....t,t,t,t,t....
            if (i < 16) {
                result += "f, ";
            } else if (i < 31) {
                result += "t, ";
            } else {
                result += "t";
            }
        }
        Assert.assertEquals(testWord.toString(),result);
    } 
    
    
    // Tests increment()
    @Test
    public void JUnitWordIncrementZeroTest(){
        Word testWord = new Word(0);
        testWord.increment();
        Assert.assertEquals(1,testWord.getSigned());
    } 
    @Test
    public void JUnitWordIncrementPosLowTest(){
        Word testWord = new Word(9);
        testWord.increment();
        Assert.assertEquals(10,testWord.getSigned());
    } 
    @Test
    public void JUnitWordIncrementPosHighTest(){
        Word testWord = new Word(268435455);
        testWord.increment();
        Assert.assertEquals(268435456,testWord.getSigned());
    }
    @Test
    public void JUnitWordIncrementNeg1Test(){
        Word testWord = new Word(-1);
        testWord.increment();
        Assert.assertEquals(0,testWord.getSigned());
    } 
    @Test
    public void JUnitWordIncrementNegLowTest(){
        Word testWord = new Word(-9);
        testWord.increment();
        Assert.assertEquals(-8,testWord.getSigned());
    } 
    @Test
    public void JUnitWordIncrementNegHighTest(){
        Word testWord = new Word(-268435455);
        testWord.increment();
        Assert.assertEquals(-268435454,testWord.getSigned());
    } 

    // Tests decrement()
    @Test
    public void JUnitWordDecrementZeroTest(){
        Word testWord = new Word(0);
        testWord.decrement();
        Assert.assertEquals(-1,testWord.getSigned());
    } 
    @Test
    public void JUnitWordDecrementPosLowTest(){
        Word testWord = new Word(9);
        testWord.decrement();
        Assert.assertEquals(8,testWord.getSigned());
    } 
    @Test
    public void JUnitWordDecrementPosHighTest(){
        Word testWord = new Word(268435455);
        testWord.decrement();
        Assert.assertEquals(268435454,testWord.getSigned());
    }
    @Test
    public void JUnitWordDecrementNeg1Test(){
        Word testWord = new Word(-1);
        testWord.decrement();
        Assert.assertEquals(-2,testWord.getSigned());
    } 
    @Test
    public void JUnitWordDecrementNegLowTest(){
        Word testWord = new Word(-9);
        testWord.decrement();
        Assert.assertEquals(-10,testWord.getSigned());
    } 
    @Test
    public void JUnitWordDecrementNegHighTest(){
        Word testWord = new Word(-268435455);
        testWord.decrement();
        Assert.assertEquals(-268435456,testWord.getSigned());
    } 
}