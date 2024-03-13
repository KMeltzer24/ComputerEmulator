/*
 * Represents a word which holds an array of 32 bits
 * @author Kevin Meltzer
 * @version 1.5
 */
public class Word {             
    
    // An array which holds 32 bits
    private Bit[] bits = new Bit[32];

    // Bits used to set the value of other bits inside of the word
    private Bit trueBit = new Bit(true);     
    private Bit falseBit = new Bit(false); 

    /** 
     * Constructor for Word that sets each bit to false
     */
    public Word() {
        for (int i = 0; i < 32; i++) {
            this.bits[i] = new Bit(false);
        }
    }

    /** 
     * Constructor for Word that sets the array of bits
     * @param bits An array of bits used to set the array of the word
     */
    public Word(Bit[] bits) {
        this.bits = bits;
    }

    /** 
     * Constructor for Word that uses set to set the value of the word **(Used for testing)**
     * @param num An integer to set the value of the word
     */
    public Word(int num) {
        this.copy(new Word());
        this.set(num);
    }

    /** 
     * Get a new Bit that has the same value as bit i
     * @param i An integer value for the desired index in the array
     * @return A new bit that has the same value as bit i
     */
    public Bit getBit(int i) {
        return new Bit(this.bits[i].getValue());
    }

    /** 
     * Sets bit i's value
     * @param i An integer value for the desired index in the array
     * @param value A Bit to set the value of desired index in the array
     */
    public void setBit(int i, Bit value) {
        this.bits[i] = new Bit(value.getValue());
    }

    /** 
     * Preforms "and" on two words and returns the result as a new Word
     * @param other A word to perform "and" on
     * @return A new word that is the result of the "and" operation
     */
    public Word and(Word other) {
        Word result = new Word();
        result.copy(this);
        for (int i=0; i<32; i++) {
            result.setBit(i, result.getBit(i).and(other.getBit(i)));
        }
        return result;
    }

    /** 
     * Preforms "or" on two words and returns the result as a new Word
     * @param other A word to perform "or" on
     * @return A new word that is the result of the "or" operation
     */
    public Word or(Word other) {
        Word result = new Word();
        result.copy(this);
        for (int i=0; i<32; i++) {
            result.setBit(i, result.getBit(i).or(other.getBit(i)));
        }
        return result;
    }

    /** 
     * Preforms "xor" on two words and returns the result as a new Word
     * @param other A word to perform "xor" on
     * @return A new word that is the result of the "xor" operation
     */
    public Word xor(Word other) {
        Word result = new Word();
        result.copy(this);
        for (int i=0; i<32; i++) {
            result.setBit(i, result.getBit(i).xor(other.getBit(i)));
        }
        return result;
    }

    /** 
     * Preforms "not" on this words and returns the result as a new Word
     * @return A new word that is the result of the "not" operation
     */
    public Word not() {
        Word result = new Word();
        result.copy(this);
        for (int i=0; i<32; i++) {
            result.setBit(i, result.getBit(i).not());
        }
        return result;
    }

    /** 
     * Preforms a right shift on this word by amount bits and returns the result as a new Word
     * @param amount The amount of bits to right shift the word by
     * @return A new word that is the result of the right shift operation
     */
    public Word rightShift(int amount) {
        Word result = new Word();
        result.copy(this);
        for (int i=31; i != -1; i--) {
            if (i < amount) {
                result.setBit(i, falseBit);
            } else {
                result.setBit(i, result.getBit(i-amount));
            }
        }
        return result;
    }

    /** 
     * Preforms a left shift on this word by amount bits and returns the result as a new Word
     * @param amount The amount of bits to left shift the word by
     * @return A new word that is the result of the left shift operation
     */
    public Word leftShift(int amount) {
        Word result = new Word();
        result.copy(this);
        for (int i=0; i<32; i++) {
            if (i > 31-amount) {
                result.setBit(i, falseBit);
            } else {
                result.setBit(i, result.getBit(i+amount));
            }
        }
        return result;
    }

    /** 
     * Returns a comma separated string of t’s and f’s
     * @return A comma separated string of t’s and f’s
     */
    public String toString() {
        String result = "";
        for (int i=0; i<32; i++) {
            if (i < 31) {
                result += this.getBit(i).toString() + ", ";
            } else {
                result += this.getBit(i).toString();
            }
        }
        return result;
    }


    /** 
     * Two's complement inverts a word
     * @return The resulting inverted word
     */
    private Word twosComplementInvert() {       
        Word result = new Word();
        result.copy(this.not());         // Inverts every bit
        int x = 31;
        while(result.getBit(x).and(trueBit).getValue()) {     // Increments the word
            result.setBit(x--, falseBit);
        }
        result.setBit(x, trueBit);
        return result;
    }


    /** 
     * Returns the value of this word as a unsigned long
     * @return The value of this word as a unsigned long
     */
    public long getUnsigned() {                
        long result = 0;
        Word tempWord = new Word();
        tempWord.copy(this);
        if (tempWord.getBit(0).getValue()) {            // If the number is negative,
            tempWord = twosComplementInvert();            // Inverts every bit and adds 1
        }
        for (int i=0; i<32; i++) { 
            if (tempWord.getBit(i).getValue()) {
                result += Math.pow(2,31-i);
            }
        }
        return result;
    }

    /** 
     * Returns the value of this word as a signed int
     * @return The value of this word as a signed int
     */
    public int getSigned() {     
        Boolean negative = false;
        Word tempWord = new Word();
        tempWord.copy(this);
        if (tempWord.getBit(0).getValue()) {            // If the number is negative,
            negative = true;                              // Inverts every bit and adds 1
            tempWord = twosComplementInvert();
        }  
        int result = 0;
        for (int i=0; i<32; i++) { 
            if (tempWord.getBit(i).getValue()) {
                result += Math.pow(2,31-i);
            }
        }
        if (negative) {
            return -result;
        } else {
            return result;
        }
    }

    /** 
     * Copies the values of the bits from another Word into this one
     * @param other A word to copy the bits over from
     */
    public void copy(Word other) {
        for (int i=0; i<32; i++) {
            this.setBit(i, other.getBit(i));
        }
    }

    /** 
     * Set the value of the bits of this Word 
     * @param value A int to set the value of the Word
     */
    public void set(int value) {
        this.copy(new Word());
        if (value == 0) {
            return;
        }
        int absValue = Math.abs(value);
        int x = 31;
        while (absValue > 0) {
            if (absValue % 2 == 0) {
                this.setBit(x, falseBit);
            } else {
                this.setBit(x, trueBit);
            }
            absValue /= 2;
            x--;
        }
        if (value < 0) {
            this.copy(twosComplementInvert());
        }
    }

    /**
     * Increments the word by 1 by using "and"
     */
    public void increment() {                                         
        int x = 31;
        while(this.getBit(x).and(trueBit).getValue()) {
            this.setBit(x--, falseBit);
            if (x == -1) { // In case word was -1, This returns once all bits flipped to avoid out of bounds error
                return;
            }
        }
        this.setBit(x, trueBit);
    }
    
    /**
     * Decrements the word by 1 by using "and"
     */
    public void decrement() {                                         
        int x = 31;
        while(this.getBit(x).not().and(trueBit).getValue()) {
            this.setBit(x--, trueBit);
            if (x == -1) { // In case word was 0, This returns once all bits flipped to avoid out of bounds error
                return;
            }
        }
        this.setBit(x, falseBit);
    }
}