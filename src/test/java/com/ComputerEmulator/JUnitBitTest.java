package com.ComputerEmulator;

import org.junit.Assert;
import org.junit.Test;

/*
 * Unit tests all cases in the Bit class
 * @author Kevin Meltzer
 * @version 1.1
 */
public class JUnitBitTest {

    // Bits used for testing
    private Bit testBit;
    private Bit testBit1;
    private Bit testBit2;
    
    // Tests set(Boolean value)
    @Test
    public void JUnitBitSetValueTrueTest(){                 
        testBit = new Bit();
        testBit.set(true);
        Assert.assertTrue(testBit.getValue());
    }
    @Test
    public void JUnitBitSetValueFalseTest(){
        testBit = new Bit();
        testBit.set(false);
        Assert.assertFalse(testBit.getValue());
    }


    // Tests toggle()
    @Test
    public void JUnitBitToggleTrueTest(){
        testBit = new Bit(true);
        testBit.toggle();
        Assert.assertFalse(testBit.getValue());
    }
    @Test
    public void JUnitBitToggleFalseTest(){
        testBit = new Bit(false);
        testBit.toggle();
        Assert.assertTrue(testBit.getValue());
    }


    // Tests set()
    @Test
    public void JUnitBitSetTest(){
        testBit = new Bit();
        testBit.set();
        Assert.assertTrue(testBit.getValue());
    }
    @Test
    public void JUnitBitSetTrueTest(){
        testBit = new Bit(true);
        testBit.set();
        Assert.assertTrue(testBit.getValue());
    }
    @Test
    public void JUnitBitSetFalseTest(){
        testBit = new Bit(false);
        testBit.set();
        Assert.assertTrue(testBit.getValue());
    }


    // Tests clear()
    @Test
    public void JUnitBitClearTest(){
        testBit = new Bit();
        testBit.clear();
        Assert.assertFalse(testBit.getValue());
    }
    @Test
    public void JUnitBitClearTrueTest(){
        testBit = new Bit(true);
        testBit.clear();
        Assert.assertFalse(testBit.getValue());
    }
    @Test
    public void JUnitBitClearFalseTest(){
        testBit = new Bit(false);
        testBit.clear();
        Assert.assertFalse(testBit.getValue());
    }   


    // Tests getValue()
    @Test
    public void JUnitBitGetValueTrueTest(){
        testBit = new Bit(true);
        Assert.assertTrue(testBit.getValue());
    }
    @Test
    public void JUnitBitGetValueFalseTest(){
        testBit = new Bit(false);
        Assert.assertFalse(testBit.getValue());
    }


    // Tests and(Bit other)
    @Test
    public void JUnitBitAndTrueTest(){
        testBit1 = new Bit(true);
        testBit2 = new Bit(true);
        Assert.assertTrue(testBit1.and(testBit2).getValue());
    }
    @Test
    public void JUnitBitAndFalseTest(){
        testBit1 = new Bit(false);
        testBit2 = new Bit(false);
        Assert.assertFalse(testBit1.and(testBit2).getValue());
    }
    @Test
    public void JUnitBitAndMix1Test(){
        testBit1 = new Bit(true);
        testBit2 = new Bit(false);
        Assert.assertFalse(testBit1.and(testBit2).getValue());
    }
    @Test
    public void JUnitBitAndMix2Test(){
        testBit1 = new Bit(false);
        testBit2 = new Bit(true);
        Assert.assertFalse(testBit1.and(testBit2).getValue());
    }


    // Tests or(Bit other)
    @Test
    public void JUnitBitOrTrueTest(){
        testBit1 = new Bit(true);
        testBit2 = new Bit(true);
        Assert.assertTrue(testBit1.or(testBit2).getValue());
    }
    @Test
    public void JUnitBitOrFalseTest(){
        testBit1 = new Bit(false);
        testBit2 = new Bit(false);
        Assert.assertFalse(testBit1.or(testBit2).getValue());
    }
    @Test
    public void JUnitBitOrMix1Test(){
        testBit1 = new Bit(true);
        testBit2 = new Bit(false);
        Assert.assertTrue(testBit1.or(testBit2).getValue());
    }
    @Test
    public void JUnitBitOrMix2Test(){
        testBit1 = new Bit(false);
        testBit2 = new Bit(true);
        Assert.assertTrue(testBit1.or(testBit2).getValue());
    }


    // Tests xor(Bit other)
    @Test
    public void JUnitBitXorTrueTest(){
        testBit1 = new Bit(true);
        testBit2 = new Bit(true);
        Assert.assertFalse(testBit1.xor(testBit2).getValue());
    }
    @Test
    public void JUnitBitXorFalseTest(){
        testBit1 = new Bit(false);
        testBit2 = new Bit(false);
        Assert.assertFalse(testBit1.xor(testBit2).getValue());
    }
    @Test
    public void JUnitBitXorMix1Test(){
        testBit1 = new Bit(true);
        testBit2 = new Bit(false);
        Assert.assertTrue(testBit1.xor(testBit2).getValue());
    }
    @Test
    public void JUnitBitXorMix2Test(){
        testBit1 = new Bit(false);
        testBit2 = new Bit(true);
        Assert.assertTrue(testBit1.xor(testBit2).getValue());
    }


    // Tests not()
    @Test
    public void JUnitBitNotTrueTest(){
        testBit = new Bit(true);
        Assert.assertFalse(testBit.not().getValue());
    }
    @Test
    public void JUnitBitNotFalseTest(){
        testBit = new Bit(false);
        Assert.assertTrue(testBit.not().getValue());
    }


    // Tests toString()
    @Test
    public void JUnitToStringTrueTest(){
        testBit = new Bit(true);
        Assert.assertEquals("t",testBit.toString());
    }
    @Test
    public void JUnitToStringFalseTest(){
        testBit = new Bit(false);
        Assert.assertEquals("f",testBit.toString());
    }
}