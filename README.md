# Computer Emulator


A Java-based implementation of a computer system emulator that simulates a processor, memory hierarchy, and assembly language processing. This project demonstrates fundamental computer architecture concepts including ALU operations, caching, and assembly language interpretation.

## Getting Started

1. Ensure you have JDK 17+ and Maven 3.6.0+ installed
2. Clone this repository
3. Navigate to the project directory
4. Follow the Testing Instructions and Building and Running sections below

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
│       ├── java/ # JUnit tests
│       └── resources/      # Test input files
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


## Testing Instructions

**Required Development Environment:**
- JDK 17 or later (required for compilation)
- Maven 3.6.0 or later (required for building and testing)

**Recommended Tools:**
- An IDE with Java support (e.g., VSCode, IntelliJ IDEA, Eclipse) for easier development

**Important Testing Information:**

Some test files must be run individually due to their multithreaded nature. These files are:
- `JUnitAssemblerTest.java`
- `JUnitProcessorTest.java`
- `JUnitCacheTest.java`

For these specific test files, use the green "go" button next to each test in your IDE to run them one at a time. Running all tests in these files at once will cause failures and unreliable results.

All other test files can be run together using standard test runners or `mvn test`.

**Do not use any options or tools that run tests in parallel or all at once for the files listed above.**


## Building and Running

1. Build the project:
   ```bash
   mvn clean compile
   ```

2. Run the main program:
   ```bash
   mvn exec:java -Dexec.mainClass="com.ComputerEmulator.Main"
   ```

**Note:** For running tests, please refer to the Testing Instructions section above. Tests must be run individually for certain test files to ensure accurate results.

## Documentation

For detailed architecture specifications and instruction set details, refer to `docs/SIA32.pdf`.


