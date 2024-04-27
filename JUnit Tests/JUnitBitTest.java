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
        Assert.assertEquals(testBit.getValue(),true);
    }
    @Test
    public void JUnitBitSetValueFalseTest(){
        testBit = new Bit();
        testBit.set(false);
        Assert.assertEquals(testBit.getValue(),false);
    }


    // Tests toggle()
    @Test
    public void JUnitBitToggleTrueTest(){
        testBit = new Bit(true);
        testBit.toggle();
        Assert.assertEquals(testBit.getValue(),false);
    }
    @Test
    public void JUnitBitToggleFalseTest(){
        testBit = new Bit(false);
        testBit.toggle();
        Assert.assertEquals(testBit.getValue(),true);
    }


    // Tests set()
    @Test
    public void JUnitBitSetTest(){
        testBit = new Bit();
        testBit.set();
        Assert.assertEquals(testBit.getValue(),true);
    }
    @Test
    public void JUnitBitSetTrueTest(){
        testBit = new Bit(true);
        testBit.set();
        Assert.assertEquals(testBit.getValue(),true);
    }
    @Test
    public void JUnitBitSetFalseTest(){
        testBit = new Bit(false);
        testBit.set();
        Assert.assertEquals(testBit.getValue(),true);
    }


    // Tests clear()
    @Test
    public void JUnitBitClearTest(){
        testBit = new Bit();
        testBit.clear();
        Assert.assertEquals(testBit.getValue(),false);
    }
    @Test
    public void JUnitBitClearTrueTest(){
        testBit = new Bit(true);
        testBit.clear();
        Assert.assertEquals(testBit.getValue(),false);
    }
    @Test
    public void JUnitBitClearFalseTest(){
        testBit = new Bit(false);
        testBit.clear();
        Assert.assertEquals(testBit.getValue(),false);
    }   


    // Tests getValue()
    @Test
    public void JUnitBitGetValueTrueTest(){
        testBit = new Bit(true);
        Assert.assertEquals(testBit.getValue(),true);
    }
    @Test
    public void JUnitBitGetValueFalseTest(){
        testBit = new Bit(false);
        Assert.assertEquals(testBit.getValue(),false);
    }


    // Tests and(Bit other)
    @Test
    public void JUnitBitAndTrueTest(){
        testBit1 = new Bit(true);
        testBit2 = new Bit(true);
        Assert.assertEquals(testBit1.and(testBit2).getValue(),true);
    }
    @Test
    public void JUnitBitAndFalseTest(){
        testBit1 = new Bit(false);
        testBit2 = new Bit(false);
        Assert.assertEquals(testBit1.and(testBit2).getValue(),false);
    }
    @Test
    public void JUnitBitAndMix1Test(){
        testBit1 = new Bit(true);
        testBit2 = new Bit(false);
        Assert.assertEquals(testBit1.and(testBit2).getValue(),false);
    }
    @Test
    public void JUnitBitAndMix2Test(){
        testBit1 = new Bit(false);
        testBit2 = new Bit(true);
        Assert.assertEquals(testBit1.and(testBit2).getValue(),false);
    }


    // Tests or(Bit other)
    @Test
    public void JUnitBitOrTrueTest(){
        testBit1 = new Bit(true);
        testBit2 = new Bit(true);
        Assert.assertEquals(testBit1.or(testBit2).getValue(),true);
    }
    @Test
    public void JUnitBitOrFalseTest(){
        testBit1 = new Bit(false);
        testBit2 = new Bit(false);
        Assert.assertEquals(testBit1.or(testBit2).getValue(),false);
    }
    @Test
    public void JUnitBitOrMix1Test(){
        testBit1 = new Bit(true);
        testBit2 = new Bit(false);
        Assert.assertEquals(testBit1.or(testBit2).getValue(),true);
    }
    @Test
    public void JUnitBitOrMix2Test(){
        testBit1 = new Bit(false);
        testBit2 = new Bit(true);
        Assert.assertEquals(testBit1.or(testBit2).getValue(),true);
    }


    // Tests xor(Bit other)
    @Test
    public void JUnitBitXorTrueTest(){
        testBit1 = new Bit(true);
        testBit2 = new Bit(true);
        Assert.assertEquals(testBit1.xor(testBit2).getValue(),false);
    }
    @Test
    public void JUnitBitXorFalseTest(){
        testBit1 = new Bit(false);
        testBit2 = new Bit(false);
        Assert.assertEquals(testBit1.xor(testBit2).getValue(),false);
    }
    @Test
    public void JUnitBitXorMix1Test(){
        testBit1 = new Bit(true);
        testBit2 = new Bit(false);
        Assert.assertEquals(testBit1.xor(testBit2).getValue(),true);
    }
    @Test
    public void JUnitBitXorMix2Test(){
        testBit1 = new Bit(false);
        testBit2 = new Bit(true);
        Assert.assertEquals(testBit1.xor(testBit2).getValue(),true);
    }


    // Tests not()
    @Test
    public void JUnitBitNotTrueTest(){
        testBit = new Bit(true);
        Assert.assertEquals(testBit.not().getValue(),false);
    }
    @Test
    public void JUnitBitNotFalseTest(){
        testBit = new Bit(false);
        Assert.assertEquals(testBit.not().getValue(),true);
    }


    // Tests toString()
    @Test
    public void JUnitToStringTrueTest(){
        testBit = new Bit(true);
        Assert.assertEquals(testBit.toString(),"t");
    }
    @Test
    public void JUnitToStringFalseTest(){
        testBit = new Bit(false);
        Assert.assertEquals(testBit.toString(),"f");
    }
}