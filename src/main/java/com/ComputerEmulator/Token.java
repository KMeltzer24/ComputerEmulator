package com.ComputerEmulator;

/**
 * A token object which has a enum state and a value which holds a number represented as a string
 * @author Kevin Meltzer
 * @version 1.6
 */
@SuppressWarnings("static-access")
public class Token {
	
	/**
	 * Makes a enum with all of the possible valid input states
	 */
	public enum state {
		NEWLINE,
		NUMBER,
		REGISTER,
		HALT,
		MULT,AND,OR,XOR,NOT,LSHIFT,RSHIFT,ADD,SUB,
		COPY,
		JUMPTO,
		JUMP,
		BRANCHEQ,BRANCHNEQ,BRANCHLT,BRANCHGE,BRANCHGT,BRANCHLE,
		CALL,CALLEQ,CALLNEQ,CALLLT,CALLGE,CALLGT,CALLLE,
		PUSHMULT,PUSHAND,PUSHOR,PUSHXOR,PUSHNOT,PUSHLSHIFT,PUSHRSHIFT,PUSHADD,PUSHSUB,
		RETURN,
		LOAD,
		STORE,
		POP,
		PEEK
	}
	/**
	 * Holds a number represented as a string which is stored in a token
	 */ 
	private String value;
	
	/**
	 * Holds the state of the token
	 */ 
	private state state;

	/**
	 * A token constructor that checks if string is a valid state.
	 * If not, throws an exception
	 * @param str String to be checked if it is a valid state
	 */ 
	public Token(String str) throws Exception {
		for (state i : state.values()) {
			if (i.toString().equals(str)) {
				this.state = state.valueOf(str);
				return;
			} else if (str.charAt(0) == 'R' && Character.isDigit(str.charAt(1))) {
				this.state = state.REGISTER;
				this.value = str.substring(1);
				return;
			}
		}
		throw new Exception("Invalid Instruction Type.");
	}
	
	/**
	 * A token constructor that takes a string and a enum state as input
	 * @param state Current state of token
	 * @param value Numbers, operator or reserved word
	 */
	public Token(state state, String value) {
		this.state = state;
		this.value = value;
	}
	
	/**
	 * Retrieves the state of a token
	 * @return The state of a token
	 */
	public state getState() {
		return this.state;
	}
	
	/**
	 * Returns the value of a token
	 * @return Value of the token
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Outputs the value of the token in a string
	 * @output The value of the token in a string
	 */
	@Override
	public String toString() {
		if (this.state == state.NUMBER || this.state == state.REGISTER ) {
			return state + "(" + value + ")";
		} else {
			return state.toString();
		}
	}
}
