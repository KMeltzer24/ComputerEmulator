# Computer Emulator


A Java-based implementation of a computer system emulator that simulates a processor, memory hierarchy, and assembly language processing. This project demonstrates fundamental computer architecture concepts including ALU operations, caching, and assembly language interpretation.


## Project Structure


```
ComputerEmulator/
├── pom.xml                  # Maven configuration
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── ComputerEmulator/
│   │               ├── Processor.java   # CPU implementation
│   │               ├── ALU.java        # Arithmetic Logic Unit
│   │               ├── Assembler.java  # Assembly language processor
│   │               ├── Bit.java        # Bit-level operations
│   │               ├── Word.java       # Word-level operations
│   │               ├── InstructionCache.java  # L1 instruction cache
│   │               ├── L2Cache.java    # L2 cache implementation
│   │               └── MainMemory.java # Main memory implementation
│   └── test/
│       └── java/
│           └── com/
│               └── ComputerEmulator/
├── docs/
│   └── SIA32.pdf              # Architecture specification
└── .gitignore
```

## Features

1. **Processor Implementation**
   - Complete CPU emulation
   - Instruction execution pipeline
   - Register management
   - ALU operations


2. **Memory Hierarchy**
   - L1 Instruction Cache
   - L2 Cache
   - Main Memory
   - Cache coherency protocols


3. **Assembly Language Processing**
   - Assembly code parsing
   - Instruction tokenization
   - Machine code generation
   - Program execution


4. **Bit-Level Operations**
   - Bit manipulation and word-level operations
   - Memory addressing


## Building and Running

1. Build the project:
   ```bash
   mvn clean compile
   ```

2. Run tests:
   ```bash
   mvn test
   ```

3. Run the main program:
   ```bash
   mvn exec:java -Dexec.mainClass="com.ComputerEmulator.Main"
   ```

## Documentation

For detailed architecture specifications and instruction set details, refer to `docs/SIA32.pdf`.


