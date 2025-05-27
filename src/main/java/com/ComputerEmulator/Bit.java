package com.ComputerEmulator;

/*
 * Represents a bit which can be true or false
 * @author Kevin Meltzer
 * @version 1.1
 */
public class Bit {

    // The boolean value of the bit
    private Boolean value;

    /** 
     * Constructor for Bit that sets the value to false
     */
    public Bit() {
        this.value = false;
    }

    /** 
     * Constructor for Bit that sets the value of the bit
     * @param value A boolean value to set the value of the bit
     */
    public Bit(Boolean value) {
        this.value = value;
    }

    /** 
     * Sets the value of the bit
     * @param value A boolean value to set the value of the bit
     */
    public void set(Boolean value) {
        this.value = value;
    }

    /** 
     * Changes the value from true to false or false to true
     */
    public void toggle() {
        this.value = !this.value;
    }

    /** 
     * Sets the value of the bit to true
     */
    public void set() {
        this.value = true;
    }

    /** 
     * Sets the value of the bit to false
     */
    public void clear() {
        this.value = false;
    }

    /** 
     * Returns the current value fo the bit
     * @return Boolean value of the bit
     */
    public Boolean getValue() {
        return this.value;
    }

    /** 
     * Performs "and" on two bits and returns a new bit set to the result
     * @param other A bit to perform "and" on
     * @return A bit set to the result
     */
    public Bit and(Bit other) {
        if (this.value) {
            if (other.getValue()) {
                return new Bit(true);
            }
        }    
        return new Bit(false);
    }

    /** 
     * Performs "or" on two bits and returns a new bit set to the result
     * @param other A bit to perform "or" on
     * @return A bit set to the result
     */
    public Bit or(Bit other) {
        if (this.value) {
            return new Bit(true);
        } else if (other.getValue()) {
            return new Bit(true);
        } else {
            return new Bit(false);
        }
    }

    /** 
     * Performs "xor" on two bits and returns a new bit set to the result
     * @param other A bit to perform "xor" on
     * @return A bit set to the result
     */
    public Bit xor(Bit other) {
        if (this.value) {
            if (other.getValue() == false) {
                return new Bit(true);
            }
        } else {
            if (other.getValue()) {
                return new Bit(true);
            }
        }  
        return new Bit(false);
    }

    /** 
     * Performs "not" on the bit and returns a new bit set to the result
     * @return A bit set to the result
     */
    public Bit not() {
        if (this.value) {
            return new Bit(false);
        } else {
            return new Bit(true);
        }
    }

    /** 
     * Returns “t” if the value is true or “f” if the value is false
     * @return “t” or “f” corresponding to value of the bit
     */
    @Override
    public String toString() {
        if (this.value) { 
            return "t";
        } else {
            return "f";
        }
    }

}
