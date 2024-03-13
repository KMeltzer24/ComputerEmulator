import org.junit.Assert;
import org.junit.Test;

/*
 * Unit tests all cases in the ALU class
 * @author Kevin Meltzer
 * @version 1.5
 */
public class JUnitALUTest {

    // Creates an instance of ALU that will be used for testing
    private ALU testALU = new ALU();
    // True and false bits used for testing
    private Bit trueBit = new Bit(true);
    private Bit falseBit = new Bit(false);
    
    // Tests 1000 – and
    @Test
    public void JUnitAluAndPosTest() throws Exception {
        testALU.op1.set(10);        // 00000000 00000000 00000000 00001010
        testALU.op2.set(1);         // 00000000 00000000 00000000 00000001
        Bit and[] = new Bit[]{trueBit,falseBit,falseBit,falseBit};
        testALU.doOperation(and);
        Assert.assertEquals(testALU.result.getSigned(),0);  // 00000000 00000000 00000000 00000000
    }
    @Test
    public void JUnitAluAndPosNegTest() throws Exception {
        testALU.op1.set(-10);             // 11111111 11111111 11111111 11110110
        testALU.op2.set(6);         // 00000000 00000000 00000000 00000110
        Bit and[] = new Bit[]{trueBit,falseBit,falseBit,falseBit};
        testALU.doOperation(and);
        Assert.assertEquals(testALU.result.getSigned(),6);  // 00000000 00000000 00000000 00000110
    }
    @Test
    public void JUnitAluAndNegTest() throws Exception {
        testALU.op1.set(-10);           // 11111111 11111111 11111111 11110110
        testALU.op2.set(-6);            // 11111111 11111111 11111111 11111010
        Bit and[] = new Bit[]{trueBit,falseBit,falseBit,falseBit};
        testALU.doOperation(and);
        Assert.assertEquals(testALU.result.getSigned(),-14);    // 11111111 11111111 11111111 11110010
    }


    // Tests 1001 – or
    @Test
    public void JUnitAluOrPosTest() throws Exception {
        testALU.op1.set(10);        // 00000000 00000000 00000000 00001010
        testALU.op2.set(1);         // 00000000 00000000 00000000 00000001
        Bit or[] = new Bit[]{trueBit,falseBit,falseBit,trueBit};
        testALU.doOperation(or);
        Assert.assertEquals(testALU.result.getSigned(),11);  // 00000000 00000000 00000000 00001011
    }
    @Test
    public void JUnitAluOrPosNegTest() throws Exception {
        testALU.op1.set(-10);              // 11111111 11111111 11111111 11110110
        testALU.op2.set(6);         // 00000000 00000000 00000000 00000110
        Bit or[] = new Bit[]{trueBit,falseBit,falseBit,trueBit};
        testALU.doOperation(or);
        Assert.assertEquals(testALU.result.getSigned(),-10);  // 11111111 11111111 11111111 11110110
    }
    @Test
    public void JUnitAluOrNegTest() throws Exception {
        testALU.op1.set(-10);           // 11111111 11111111 11111111 11110110
        testALU.op2.set(-6);            // 11111111 11111111 11111111 11111010
        Bit or[] = new Bit[]{trueBit,falseBit,falseBit,trueBit};
        testALU.doOperation(or);
        Assert.assertEquals(testALU.result.getSigned(),-2);    // 11111111 11111111 11111111 11111110
    }


    // Tests 1010 – xor
    @Test
    public void JUnitAluXorPosTest() throws Exception {
        testALU.op1.set(10);        // 00000000 00000000 00000000 00001010
        testALU.op2.set(1);         // 00000000 00000000 00000000 00000001
        Bit xor[] = new Bit[]{trueBit,falseBit,trueBit,falseBit};
        testALU.doOperation(xor);
        Assert.assertEquals(testALU.result.getSigned(),11);  // 00000000 00000000 00000000 00001011
    }
    @Test
    public void JUnitAluXorPosNegTest() throws Exception {
        testALU.op1.set(-10);              // 11111111 11111111 11111111 11110110
        testALU.op2.set(6);         // 00000000 00000000 00000000 00000110
        Bit xor[] = new Bit[]{trueBit,falseBit,trueBit,falseBit};
        testALU.doOperation(xor);
        Assert.assertEquals(testALU.result.getSigned(),-16);  // 11111111 11111111 11111111 11110000
    }
    @Test
    public void JUnitAluXorNegTest() throws Exception {
        testALU.op1.set(-10);           // 11111111 11111111 11111111 11110110
        testALU.op2.set(-6);            // 11111111 11111111 11111111 11111010
        Bit xor[] = new Bit[]{trueBit,falseBit,trueBit,falseBit};
        testALU.doOperation(xor);
        Assert.assertEquals(testALU.result.getSigned(),12);    // 00000000 00000000 00000000 00001100
    }


    // Tests 1011 – not 
    @Test
    public void JUnitAluNotZeroTest() throws Exception {                  
        testALU.op1.set(0);        // 00000000 00000000 00000000 00000000
        Bit not[] = new Bit[]{trueBit,falseBit,trueBit,trueBit};
        testALU.doOperation(not);
        Assert.assertEquals(testALU.result.getSigned(),-1);    // 11111111 11111111 11111111 11111111
    }
    @Test
    public void JUnitAluNotPosLowTest() throws Exception {                  
        testALU.op1.set(10);        // 00000000 00000000 00000000 00001010
        Bit not[] = new Bit[]{trueBit,falseBit,trueBit,trueBit};
        testALU.doOperation(not);
        Assert.assertEquals(testALU.result.getSigned(),-11);    // 11111111 11111111 11111111 11110101
    }
    @Test
    public void JUnitAluNotPosHighTest() throws Exception {                  
        testALU.op1.set(268435455);        // 00001111 11111111 11111111 11111111
        Bit not[] = new Bit[]{trueBit,falseBit,trueBit,trueBit};
        testALU.doOperation(not);
        Assert.assertEquals(testALU.result.getSigned(),-268435456);    // 11110000 00000000 00000000 00000000
    }
    @Test
    public void JUnitAluNotNegLowTest() throws Exception {                  
        testALU.op1.set(-10);        // 11111111 11111111 11111111 11110110
        Bit not[] = new Bit[]{trueBit,falseBit,trueBit,trueBit};
        testALU.doOperation(not);
        Assert.assertEquals(testALU.result.getSigned(),9);    // 00000000 00000000 00000000 00001001
    }
    @Test
    public void JUnitAluNotNegHighTest() throws Exception {                  
        testALU.op1.set(-268435455);        // 11110000 00000000 00000000 00000001
        Bit not[] = new Bit[]{trueBit,falseBit,trueBit,trueBit};
        testALU.doOperation(not);
        Assert.assertEquals(testALU.result.getSigned(),268435454);    // 00001111 11111111 11111111 11111110
    }


    // Tests 1100 – left shift
    @Test
    public void JUnitAluLShiftZeroTest() throws Exception {                  
        testALU.op1.set(0);        // 00000000 00000000 00000000 00000000
        testALU.op2.set(1);
        Bit LShift[] = new Bit[]{trueBit,trueBit,falseBit,falseBit};
        testALU.doOperation(LShift);
        Assert.assertEquals(testALU.result.getSigned(),0);    // 00000000 00000000 00000000 00000000
    }
    @Test
    public void JUnitAluLShiftPosLowTest() throws Exception {                  
        testALU.op1.set(10);        // 00000000 00000000 00000000 00001010
        testALU.op2.set(5);
        Bit LShift[] = new Bit[]{trueBit,trueBit,falseBit,falseBit};
        testALU.doOperation(LShift);
        Assert.assertEquals(testALU.result.getSigned(),320);    // 00000000 00000000 00000001 01000000
    }
    @Test
    public void JUnitAluLShiftPosHighTest() throws Exception {                  
        testALU.op1.set(268435455);        // 00001111 11111111 11111111 11111111
        testALU.op2.set(3);
        Bit LShift[] = new Bit[]{trueBit,trueBit,falseBit,falseBit};
        testALU.doOperation(LShift);
        Assert.assertEquals(testALU.result.getSigned(),2147483640);    // 01111111 11111111 11111111 11111000
    }
    @Test
    public void JUnitAluLShiftNegLowTest() throws Exception {                  
        testALU.op1.set(-10);        // 11111111 11111111 11111111 11110110
        testALU.op2.set(8);
        Bit LShift[] = new Bit[]{trueBit,trueBit,falseBit,falseBit};
        testALU.doOperation(LShift);
        Assert.assertEquals(testALU.result.getSigned(),-2560);    // 11111111 11111111 11110110 00000000
    }
    @Test
    public void JUnitAluLShiftNegHighTest() throws Exception {                  
        testALU.op1.set(-268435455);        // 11110000 00000000 00000000 00000001
        testALU.op2.set(4);
        Bit LShift[] = new Bit[]{trueBit,trueBit,falseBit,falseBit};
        testALU.doOperation(LShift);
        Assert.assertEquals(testALU.result.getSigned(),16);    // 00000000 00000000 00000000 00010000
    }


    // Tests 1101 – right shift
    @Test
    public void JUnitAluRShiftZeroTest() throws Exception {                  
        testALU.op1.set(0);        // 00000000 00000000 00000000 00000000
        testALU.op2.set(1);
        Bit RShift[] = new Bit[]{trueBit,trueBit,falseBit,trueBit};
        testALU.doOperation(RShift);
        Assert.assertEquals(testALU.result.getSigned(),0);    // 00000000 00000000 00000000 00000000
    }
    @Test
    public void JUnitAluRShiftPosLowTest() throws Exception {                  
        testALU.op1.set(10);        // 00000000 00000000 00000000 00001010
        testALU.op2.set(3);
        Bit RShift[] = new Bit[]{trueBit,trueBit,falseBit,trueBit};
        testALU.doOperation(RShift);
        Assert.assertEquals(testALU.result.getSigned(),1);    // 00000000 00000000 00000000 00000001
    }
    @Test
    public void JUnitAluRShiftPosHighTest() throws Exception {                  
        testALU.op1.set(268435455);        // 00001111 11111111 11111111 11111111
        testALU.op2.set(4);
        Bit RShift[] = new Bit[]{trueBit,trueBit,falseBit,trueBit};
        testALU.doOperation(RShift);
        Assert.assertEquals(testALU.result.getSigned(),16777215);    // 00000000 11111111 11111111 11111111
    }
    @Test
    public void JUnitAluRShiftNegLowTest() throws Exception {                  
        testALU.op1.set(-10);        // 11111111 11111111 11111111 11110110
        testALU.op2.set(3);
        Bit RShift[] = new Bit[]{trueBit,trueBit,falseBit,trueBit};
        testALU.doOperation(RShift);
        Assert.assertEquals(testALU.result.getSigned(),536870910);    // 00011111 11111111 11111111 11111110
    }
    @Test
    public void JUnitAluRShiftNegHighTest() throws Exception {                  
        testALU.op1.set(-268435455);        // 11110000 00000000 00000000 00000001
        testALU.op2.set(8);
        Bit RShift[] = new Bit[]{trueBit,trueBit,falseBit,trueBit};
        testALU.doOperation(RShift);
        Assert.assertEquals(testALU.result.getSigned(),15728640);    // 00000000 11110000 00000000 00000000
    }


    // Tests add2
    @Test
    public void JUnitAluAdd2ZeroTest() throws Exception {
        Assert.assertEquals(testALU.add2(new Word(1), new Word(0)).getSigned(),1);
    }
    @Test
    public void JUnitAluAdd2PosLowTest() throws Exception {
        Assert.assertEquals(testALU.add2(new Word(9), new Word(1)).getSigned(),10);
    }
    @Test
    public void JUnitAluAdd2PosHighTest() throws Exception {
        Assert.assertEquals(testALU.add2(new Word(268435455), new Word(2000000)).getSigned(),270435455);
    }
    @Test
    public void JUnitAluAdd2NegLowTest() throws Exception {
        Assert.assertEquals(testALU.add2(new Word(-9), new Word(-1)).getSigned(),-10);
    }
    @Test
    public void JUnitAluAdd2NegHighTest() throws Exception {
        Assert.assertEquals(testALU.add2(new Word(-268435455), new Word(-2000000)).getSigned(),-270435455);
    }
    @Test
    public void JUnitAluAdd2MixLowTest() throws Exception {
        Assert.assertEquals(testALU.add2(new Word(-9), new Word(1)).getSigned(),-8);
    }
    @Test
    public void JUnitAluAdd2MixHighTest() throws Exception {
        Assert.assertEquals(testALU.add2(new Word(268435455), new Word(-2000000)).getSigned(),266435455);
    }


    // Tests add4
    @Test
    public void JUnitAluAdd4ZeroTest() throws Exception {
        Assert.assertEquals(testALU.add4(new Word(1), new Word(0), new Word(0), new Word(0)).getSigned(),1);
    }
    @Test
    public void JUnitAluAdd4PosLowTest() throws Exception {
        Assert.assertEquals(testALU.add4(new Word(1), new Word(2), new Word(3), new Word(4)).getSigned(),10);
    }
    @Test
    public void JUnitAluAdd4PosHighTest() throws Exception {
        Assert.assertEquals(testALU.add4(new Word(268435455), new Word(500000), new Word(500000), new Word(1000000)).getSigned(),270435455);
    }
    @Test
    public void JUnitAluAdd4NegLowTest() throws Exception {
        Assert.assertEquals(testALU.add4(new Word(-1), new Word(-2), new Word(-3), new Word(-4)).getSigned(),-10);
    }
    @Test
    public void JUnitAluAdd4NegHighTest() throws Exception {
        Assert.assertEquals(testALU.add4(new Word(-268435455), new Word(-500000), new Word(-500000), new Word(-1000000)).getSigned(),-270435455);
    }
    @Test
    public void JUnitAluAdd4MixLowTest() throws Exception {
        Assert.assertEquals(testALU.add4(new Word(-11), new Word(-1), new Word(1), new Word(3)).getSigned(),-8);
    }
    @Test
    public void JUnitAluAdd4MixHighTest() throws Exception {
        Assert.assertEquals(testALU.add4(new Word(268435455), new Word(-8000000), new Word(2000000), new Word(4000000)).getSigned(),266435455);
    }


    // Tests 1110 – add
    @Test
    public void JUnitAluAddZeroTest() throws Exception {
        testALU.op1.set(1);
        testALU.op2.set(0);
        Bit add[] = new Bit[]{trueBit,trueBit,trueBit,falseBit};
        testALU.doOperation(add);
        Assert.assertEquals(testALU.result.getSigned(),1);
    }
    @Test
    public void JUnitAluAddPosLowTest() throws Exception {
        testALU.op1.set(9);
        testALU.op2.set(1);
        Bit add[] = new Bit[]{trueBit,trueBit,trueBit,falseBit};
        testALU.doOperation(add);
        Assert.assertEquals(testALU.result.getSigned(),10);
    }
    @Test
    public void JUnitAluAddPosHighTest() throws Exception {
        testALU.op1.set(268435455);
        testALU.op2.set(2000000);
        Bit add[] = new Bit[]{trueBit,trueBit,trueBit,falseBit};
        testALU.doOperation(add);
        Assert.assertEquals(testALU.result.getSigned(),270435455);
    }
    @Test
    public void JUnitAluAddNegLowTest() throws Exception {
        testALU.op1.set(-9);
        testALU.op2.set(-1);
        Bit add[] = new Bit[]{trueBit,trueBit,trueBit,falseBit};
        testALU.doOperation(add);
        Assert.assertEquals(testALU.result.getSigned(),-10);
    }
    @Test
    public void JUnitAluAddNegHighTest() throws Exception {
        testALU.op1.set(-268435455);
        testALU.op2.set(-2000000);
        Bit add[] = new Bit[]{trueBit,trueBit,trueBit,falseBit};
        testALU.doOperation(add);
        Assert.assertEquals(testALU.result.getSigned(),-270435455);
    }
    @Test
    public void JUnitAluAddMixLowTest() throws Exception {
        testALU.op1.set(-9);
        testALU.op2.set(1);
        Bit add[] = new Bit[]{trueBit,trueBit,trueBit,falseBit};
        testALU.doOperation(add);
        Assert.assertEquals(testALU.result.getSigned(),-8);
    }
    @Test
    public void JUnitAluAddMixHighTest() throws Exception {
        testALU.op1.set(268435455);
        testALU.op2.set(-2000000);
        Bit add[] = new Bit[]{trueBit,trueBit,trueBit,falseBit};
        testALU.doOperation(add);
        Assert.assertEquals(testALU.result.getSigned(),266435455);
    }


    // Tests 1111 – subtract
    @Test
    public void JUnitAluSubZeroTest() throws Exception {
        testALU.op1.set(1);
        testALU.op2.set(0);
        Bit Sub[] = new Bit[]{trueBit,trueBit,trueBit,trueBit};
        testALU.doOperation(Sub);
        Assert.assertEquals(testALU.result.getSigned(),1);
    }
    @Test
    public void JUnitAluSubPosLowTest() throws Exception {
        testALU.op1.set(9);
        testALU.op2.set(1);
        Bit Sub[] = new Bit[]{trueBit,trueBit,trueBit,trueBit};
        testALU.doOperation(Sub);
        Assert.assertEquals(testALU.result.getSigned(),8);
    }
    @Test
    public void JUnitAluSubPosHighTest() throws Exception {
        testALU.op1.set(268435455);
        testALU.op2.set(2000000);
        Bit Sub[] = new Bit[]{trueBit,trueBit,trueBit,trueBit};
        testALU.doOperation(Sub);
        Assert.assertEquals(testALU.result.getSigned(),266435455);
    }
    @Test
    public void JUnitAluSubNegLowTest() throws Exception {
        testALU.op1.set(-9);
        testALU.op2.set(-1);
        Bit Sub[] = new Bit[]{trueBit,trueBit,trueBit,trueBit};
        testALU.doOperation(Sub);
        Assert.assertEquals(testALU.result.getSigned(),-8);
    }
    @Test
    public void JUnitAluSubNegHighTest() throws Exception {
        testALU.op1.set(-268435455);
        testALU.op2.set(-2000000);
        Bit Sub[] = new Bit[]{trueBit,trueBit,trueBit,trueBit};
        testALU.doOperation(Sub);
        Assert.assertEquals(testALU.result.getSigned(),-266435455);
    }
    @Test
    public void JUnitAluSubMixLowTest() throws Exception {
        testALU.op1.set(-9);
        testALU.op2.set(1);
        Bit Sub[] = new Bit[]{trueBit,trueBit,trueBit,trueBit};
        testALU.doOperation(Sub);
        Assert.assertEquals(testALU.result.getSigned(),-10);
    }
    @Test
    public void JUnitAluSubMixHighTest() throws Exception {
        testALU.op1.set(268435455);
        testALU.op2.set(-2000000);
        Bit Sub[] = new Bit[]{trueBit,trueBit,trueBit,trueBit};
        testALU.doOperation(Sub);
        Assert.assertEquals(testALU.result.getSigned(),270435455);
    }


    // Tests 0111 - multiply
    @Test
    public void JUnitAluMultZeroTest() throws Exception {
        testALU.op1.set(1);
        testALU.op2.set(0);
        Bit Mult[] = new Bit[]{falseBit,trueBit,trueBit,trueBit};
        testALU.doOperation(Mult);
        Assert.assertEquals(testALU.result.getSigned(),0);
    }
    @Test
    public void JUnitAluMultPosLowTest() throws Exception {
        testALU.op1.set(9);
        testALU.op2.set(1);
        Bit Mult[] = new Bit[]{falseBit,trueBit,trueBit,trueBit};
        testALU.doOperation(Mult);
        Assert.assertEquals(testALU.result.getSigned(),9);
    }
    @Test
    public void JUnitAluMultPosHighTest() throws Exception {
        testALU.op1.set(268435455);
        testALU.op2.set(5);
        Bit Mult[] = new Bit[]{falseBit,trueBit,trueBit,trueBit};
        testALU.doOperation(Mult);
        Assert.assertEquals(testALU.result.getSigned(),1342177275);
    }
    @Test
    public void JUnitAluMultNegLowTest() throws Exception {
        testALU.op1.set(-9);
        testALU.op2.set(-1);
        Bit Mult[] = new Bit[]{falseBit,trueBit,trueBit,trueBit};
        testALU.doOperation(Mult);
        Assert.assertEquals(testALU.result.getSigned(),9);
    }
    @Test
    public void JUnitAluMultNegHighTest() throws Exception {
        testALU.op1.set(-268435455);
        testALU.op2.set(-5);
        Bit Mult[] = new Bit[]{falseBit,trueBit,trueBit,trueBit};
        testALU.doOperation(Mult);
        Assert.assertEquals(testALU.result.getSigned(),1342177275);
    }
    @Test
    public void JUnitAluMultMixLowTest() throws Exception {
        testALU.op1.set(-9);
        testALU.op2.set(1);
        Bit Mult[] = new Bit[]{falseBit,trueBit,trueBit,trueBit};
        testALU.doOperation(Mult);
        Assert.assertEquals(testALU.result.getSigned(),-9);
    }
    @Test
    public void JUnitAluMultMixHighTest() throws Exception {
        testALU.op1.set(268435455);
        testALU.op2.set(-5);
        Bit Mult[] = new Bit[]{falseBit,trueBit,trueBit,trueBit};
        testALU.doOperation(Mult);
        Assert.assertEquals(testALU.result.getSigned(),-1342177275);
    }


    // Equals - 0000
    @Test
    public void JUnitAluEQ0sTest() throws Exception {
        testALU.op1.set(0);
        testALU.op2.set(0);
        Bit EQ[] = new Bit[]{new Bit(false),new Bit(false),new Bit(false),new Bit(false)};
        testALU.doOperation(EQ);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluEQLowPosTTest() throws Exception {
        testALU.op1.set(1);
        testALU.op2.set(1);
        Bit EQ[] = new Bit[]{new Bit(false),new Bit(false),new Bit(false),new Bit(false)};
        testALU.doOperation(EQ);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluEQLowPosFTest() throws Exception {
        testALU.op1.set(1);
        testALU.op2.set(2);
        Bit EQ[] = new Bit[]{new Bit(false),new Bit(false),new Bit(false),new Bit(false)};
        testALU.doOperation(EQ);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluEQLowNegTTest() throws Exception {
        testALU.op1.set(-9);
        testALU.op2.set(-9);
        Bit EQ[] = new Bit[]{new Bit(false),new Bit(false),new Bit(false),new Bit(false)};
        testALU.doOperation(EQ);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluEQLowNegFTest() throws Exception {
        testALU.op1.set(-9);
        testALU.op2.set(-10);
        Bit EQ[] = new Bit[]{new Bit(false),new Bit(false),new Bit(false),new Bit(false)};
        testALU.doOperation(EQ);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluEQLowMixTest() throws Exception {
        testALU.op1.set(1);
        testALU.op2.set(-1);
        Bit EQ[] = new Bit[]{new Bit(false),new Bit(false),new Bit(false),new Bit(false)};
        testALU.doOperation(EQ);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluEQHighMixTest() throws Exception {
        testALU.op1.set(268435455);
        testALU.op2.set(-268435455);
        Bit EQ[] = new Bit[]{new Bit(false),new Bit(false),new Bit(false),new Bit(false)};
        testALU.doOperation(EQ);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluEQHighPosTTest() throws Exception {
        testALU.op1.set(268435455);
        testALU.op2.set(268435455);
        Bit EQ[] = new Bit[]{new Bit(false),new Bit(false),new Bit(false),new Bit(false)};
        testALU.doOperation(EQ);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluEQHighPosFTest() throws Exception {
        testALU.op1.set(268435455);
        testALU.op2.set(268435454);
        Bit EQ[] = new Bit[]{new Bit(false),new Bit(false),new Bit(false),new Bit(false)};
        testALU.doOperation(EQ);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluEQHighNegTTest() throws Exception {
        testALU.op1.set(-268435455);
        testALU.op2.set(-268435455);
        Bit EQ[] = new Bit[]{new Bit(false),new Bit(false),new Bit(false),new Bit(false)};
        testALU.doOperation(EQ);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluEQHighNegFTest() throws Exception {
        testALU.op1.set(-268435455);
        testALU.op2.set(-268435454);
        Bit EQ[] = new Bit[]{new Bit(false),new Bit(false),new Bit(false),new Bit(false)};
        testALU.doOperation(EQ);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }

    // Not Equal - 0001
    @Test
    public void JUnitAluNEQ0sTest() throws Exception {
        testALU.op1.set(0);
        testALU.op2.set(0);
        Bit NEQ[] = new Bit[]{new Bit(false),new Bit(false),new Bit(false),trueBit};
        testALU.doOperation(NEQ);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluNEQLowPosFTest() throws Exception {
        testALU.op1.set(1);
        testALU.op2.set(1);
        Bit NEQ[] = new Bit[]{new Bit(false),new Bit(false),new Bit(false),trueBit};
        testALU.doOperation(NEQ);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluNEQLowPosTTest() throws Exception {
        testALU.op1.set(1);
        testALU.op2.set(2);
        Bit NEQ[] = new Bit[]{new Bit(false),new Bit(false),new Bit(false),trueBit};
        testALU.doOperation(NEQ);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluNEQLowNegFTest() throws Exception {
        testALU.op1.set(-9);
        testALU.op2.set(-9);
        Bit NEQ[] = new Bit[]{new Bit(false),new Bit(false),new Bit(false),trueBit};
        testALU.doOperation(NEQ);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluNEQLowNegTTest() throws Exception {
        testALU.op1.set(-9);
        testALU.op2.set(-10);
        Bit NEQ[] = new Bit[]{new Bit(false),new Bit(false),new Bit(false),trueBit};
        testALU.doOperation(NEQ);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluNEQLowMixTest() throws Exception {
        testALU.op1.set(1);
        testALU.op2.set(-1);
        Bit NEQ[] = new Bit[]{new Bit(false),new Bit(false),new Bit(false),trueBit};
        testALU.doOperation(NEQ);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluNEQHighMixTest() throws Exception {
        testALU.op1.set(268435455);
        testALU.op2.set(-268435455);
        Bit NEQ[] = new Bit[]{new Bit(false),new Bit(false),new Bit(false),trueBit};
        testALU.doOperation(NEQ);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluNEQHighPosFTest() throws Exception {
        testALU.op1.set(268435455);
        testALU.op2.set(268435455);
        Bit NEQ[] = new Bit[]{new Bit(false),new Bit(false),new Bit(false),trueBit};
        testALU.doOperation(NEQ);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluNEQHighPosTTest() throws Exception {
        testALU.op1.set(268435455);
        testALU.op2.set(268435454);
        Bit NEQ[] = new Bit[]{new Bit(false),new Bit(false),new Bit(false),trueBit};
        testALU.doOperation(NEQ);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluNEQHighNegFTest() throws Exception {
        testALU.op1.set(-268435455);
        testALU.op2.set(-268435455);
        Bit NEQ[] = new Bit[]{new Bit(false),new Bit(false),new Bit(false),trueBit};
        testALU.doOperation(NEQ);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluNEQHighNegTTest() throws Exception {
        testALU.op1.set(-268435455);
        testALU.op2.set(-268435454);
        Bit NEQ[] = new Bit[]{new Bit(false),new Bit(false),new Bit(false),trueBit};
        testALU.doOperation(NEQ);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }


    // Less than - 0010
    @Test
    public void JUnitAluLT0sTest() throws Exception {
        testALU.op1.set(0);
        testALU.op2.set(0);
        Bit LT[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(false)};
        testALU.doOperation(LT);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluLTLowPosTTest() throws Exception {
        testALU.op1.set(1);
        testALU.op2.set(2);
        Bit LT[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(false)};
        testALU.doOperation(LT);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluLTLowPosFTest() throws Exception {
        testALU.op1.set(2);
        testALU.op2.set(1);
        Bit LT[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(false)};
        testALU.doOperation(LT);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluLTLowNegTTest() throws Exception {
        testALU.op1.set(-10);
        testALU.op2.set(-9);
        Bit LT[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(false)};
        testALU.doOperation(LT);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluLTLowNegFTest() throws Exception {
        testALU.op1.set(-9);
        testALU.op2.set(-10);
        Bit LT[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(false)};
        testALU.doOperation(LT);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluLTLowMixTTest() throws Exception {
        testALU.op1.set(-1);
        testALU.op2.set(1);
        Bit LT[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(false)};
        testALU.doOperation(LT);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluLTLowMixFTest() throws Exception {
        testALU.op1.set(1);
        testALU.op2.set(-1);
        Bit LT[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(false)};
        testALU.doOperation(LT);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluLTHighMixTTest() throws Exception {
        testALU.op1.set(-268435455);
        testALU.op2.set(268435455);
        Bit LT[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(false)};
        testALU.doOperation(LT);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluLTHighMixFTest() throws Exception {
        testALU.op1.set(268435455);
        testALU.op2.set(-268435455);
        Bit LT[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(false)};
        testALU.doOperation(LT);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluLTHighPosTTest() throws Exception {
        testALU.op1.set(268435454);
        testALU.op2.set(268435455);
        Bit LT[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(false)};
        testALU.doOperation(LT);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluLTHighPosFTest() throws Exception {
        testALU.op1.set(268435455);
        testALU.op2.set(268435454);
        Bit LT[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(false)};
        testALU.doOperation(LT);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluLTHighNegTTest() throws Exception {
        testALU.op1.set(-268435455);
        testALU.op2.set(-268435454);
        Bit LT[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(false)};
        testALU.doOperation(LT);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluLTHighNegFTest() throws Exception {
        testALU.op1.set(-268435454);
        testALU.op2.set(-268435455);
        Bit LT[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(false)};
        testALU.doOperation(LT);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }


    // Greater than or equal - 0011 
    @Test
    public void JUnitAluGE0sTest() throws Exception {
        testALU.op1.set(0);
        testALU.op2.set(0);
        Bit GE[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(true)};
        testALU.doOperation(GE);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluGELowPosFTest() throws Exception {
        testALU.op1.set(1);
        testALU.op2.set(2);
        Bit GE[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(true)};
        testALU.doOperation(GE);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluGELowPosTTest() throws Exception {
        testALU.op1.set(2);
        testALU.op2.set(1);
        Bit GE[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(true)};
        testALU.doOperation(GE);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluGELowPosEQTest() throws Exception {
        testALU.op1.set(1);
        testALU.op2.set(1);
        Bit GE[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(true)};
        testALU.doOperation(GE);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluGELowNegFTest() throws Exception {
        testALU.op1.set(-10);
        testALU.op2.set(-9);
        Bit GE[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(true)};
        testALU.doOperation(GE);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluGELowNegTTest() throws Exception {
        testALU.op1.set(-9);
        testALU.op2.set(-10);
        Bit GE[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(true)};
        testALU.doOperation(GE);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluGELowNegEQTest() throws Exception {
        testALU.op1.set(-9);
        testALU.op2.set(-9);
        Bit GE[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(true)};
        testALU.doOperation(GE);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluGELowMixFTest() throws Exception {
        testALU.op1.set(-1);
        testALU.op2.set(1);
        Bit GE[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(true)};
        testALU.doOperation(GE);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluGELowMixTTest() throws Exception {
        testALU.op1.set(1);
        testALU.op2.set(-1);
        Bit GE[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(true)};
        testALU.doOperation(GE);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluGEHighMixFTest() throws Exception {
        testALU.op1.set(-268435455);
        testALU.op2.set(268435455);
        Bit GE[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(true)};
        testALU.doOperation(GE);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluGEHighMixTTest() throws Exception {
        testALU.op1.set(268435455);
        testALU.op2.set(-268435455);
        Bit GE[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(true)};
        testALU.doOperation(GE);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluGEHighPosFTest() throws Exception {
        testALU.op1.set(268435454);
        testALU.op2.set(268435455);
        Bit GE[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(true)};
        testALU.doOperation(GE);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluGEHighPosTTest() throws Exception {
        testALU.op1.set(268435455);
        testALU.op2.set(268435454);
        Bit GE[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(true)};
        testALU.doOperation(GE);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluGEHighPosEQTest() throws Exception {
        testALU.op1.set(268435455);
        testALU.op2.set(268435455);
        Bit GE[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(true)};
        testALU.doOperation(GE);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluGEHighNegFTest() throws Exception {
        testALU.op1.set(-268435455);
        testALU.op2.set(-268435454);
        Bit GE[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(true)};
        testALU.doOperation(GE);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluGEHighNegTTest() throws Exception {
        testALU.op1.set(-268435454);
        testALU.op2.set(-268435455);
        Bit GE[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(true)};
        testALU.doOperation(GE);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluGEHighNegEQTest() throws Exception {
        testALU.op1.set(-268435455);
        testALU.op2.set(-268435455);
        Bit GE[] = new Bit[]{new Bit(false),new Bit(false),trueBit,new Bit(true)};
        testALU.doOperation(GE);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    

    // Greater than - 0100
    @Test
    public void JUnitAluGT0sTest() throws Exception {
        testALU.op1.set(0);
        testALU.op2.set(0);
        Bit GT[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(false)};
        testALU.doOperation(GT);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluGTLowPosFTest() throws Exception {
        testALU.op1.set(1);
        testALU.op2.set(2);
        Bit GT[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(false)};
        testALU.doOperation(GT);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluGTLowPosTTest() throws Exception {
        testALU.op1.set(2);
        testALU.op2.set(1);
        Bit GT[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(false)};
        testALU.doOperation(GT);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluGTLowNegFTest() throws Exception {
        testALU.op1.set(-10);
        testALU.op2.set(-9);
        Bit GT[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(false)};
        testALU.doOperation(GT);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluGTLowNegTTest() throws Exception {
        testALU.op1.set(-9);
        testALU.op2.set(-10);
        Bit GT[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(false)};
        testALU.doOperation(GT);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluGTLowMixFTest() throws Exception {
        testALU.op1.set(-1);
        testALU.op2.set(1);
        Bit GT[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(false)};
        testALU.doOperation(GT);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluGTLowMixTTest() throws Exception {
        testALU.op1.set(1);
        testALU.op2.set(-1);
        Bit GT[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(false)};
        testALU.doOperation(GT);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluGTHighMixFTest() throws Exception {
        testALU.op1.set(-268435455);
        testALU.op2.set(268435455);
        Bit GT[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(false)};
        testALU.doOperation(GT);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluGTHighMixTTest() throws Exception {
        testALU.op1.set(268435455);
        testALU.op2.set(-268435455);
        Bit GT[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(false)};
        testALU.doOperation(GT);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluGTHighPosFTest() throws Exception {
        testALU.op1.set(268435454);
        testALU.op2.set(268435455);
        Bit GT[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(false)};
        testALU.doOperation(GT);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluGTHighPosTTest() throws Exception {
        testALU.op1.set(268435455);
        testALU.op2.set(268435454);
        Bit GT[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(false)};
        testALU.doOperation(GT);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluGTHighNegFTest() throws Exception {
        testALU.op1.set(-268435455);
        testALU.op2.set(-268435454);
        Bit GT[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(false)};
        testALU.doOperation(GT);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluGTHighNegTTest() throws Exception {
        testALU.op1.set(-268435454);
        testALU.op2.set(-268435455);
        Bit GT[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(false)};
        testALU.doOperation(GT);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }


    // Less than or equal - 0101 
    @Test
    public void JUnitAluLE0sTest() throws Exception {
        testALU.op1.set(0);
        testALU.op2.set(0);
        Bit LE[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(true)};
        testALU.doOperation(LE);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluLELowPosTTest() throws Exception {
        testALU.op1.set(1);
        testALU.op2.set(2);
        Bit LE[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(true)};
        testALU.doOperation(LE);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluLELowPosFTest() throws Exception {
        testALU.op1.set(2);
        testALU.op2.set(1);
        Bit LE[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(true)};
        testALU.doOperation(LE);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluLELowPosEQTest() throws Exception {
        testALU.op1.set(1);
        testALU.op2.set(1);
        Bit LE[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(true)};
        testALU.doOperation(LE);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluLELowNegTTest() throws Exception {
        testALU.op1.set(-10);
        testALU.op2.set(-9);
        Bit LE[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(true)};
        testALU.doOperation(LE);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluLELowNegFTest() throws Exception {
        testALU.op1.set(-9);
        testALU.op2.set(-10);
        Bit LE[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(true)};
        testALU.doOperation(LE);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluLELowNeLEQTest() throws Exception {
        testALU.op1.set(-9);
        testALU.op2.set(-9);
        Bit LE[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(true)};
        testALU.doOperation(LE);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluLELowMixTTest() throws Exception {
        testALU.op1.set(-1);
        testALU.op2.set(1);
        Bit LE[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(true)};
        testALU.doOperation(LE);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluLELowMixFTest() throws Exception {
        testALU.op1.set(1);
        testALU.op2.set(-1);
        Bit LE[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(true)};
        testALU.doOperation(LE);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluLEHighMixTTest() throws Exception {
        testALU.op1.set(-268435455);
        testALU.op2.set(268435455);
        Bit LE[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(true)};
        testALU.doOperation(LE);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluLEHighMixFTest() throws Exception {
        testALU.op1.set(268435455);
        testALU.op2.set(-268435455);
        Bit LE[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(true)};
        testALU.doOperation(LE);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluLEHighPosTTest() throws Exception {
        testALU.op1.set(268435454);
        testALU.op2.set(268435455);
        Bit LE[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(true)};
        testALU.doOperation(LE);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluLEHighPosFTest() throws Exception {
        testALU.op1.set(268435455);
        testALU.op2.set(268435454);
        Bit LE[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(true)};
        testALU.doOperation(LE);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluLEHighPosEQTest() throws Exception {
        testALU.op1.set(268435455);
        testALU.op2.set(268435455);
        Bit LE[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(true)};
        testALU.doOperation(LE);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluLEHighNegTTest() throws Exception {
        testALU.op1.set(-268435455);
        testALU.op2.set(-268435454);
        Bit LE[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(true)};
        testALU.doOperation(LE);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }
    @Test
    public void JUnitAluLEHighNegFTest() throws Exception {
        testALU.op1.set(-268435454);
        testALU.op2.set(-268435455);
        Bit LE[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(true)};
        testALU.doOperation(LE);
        Assert.assertEquals(testALU.result.getUnsigned(),0);
    }
    @Test
    public void JUnitAluLEHighNeLEQTest() throws Exception {
        testALU.op1.set(-268435455);
        testALU.op2.set(-268435455);
        Bit LE[] = new Bit[]{new Bit(false),trueBit,new Bit(false),new Bit(true)};
        testALU.doOperation(LE);
        Assert.assertEquals(testALU.result.getUnsigned(),1);
    }

}