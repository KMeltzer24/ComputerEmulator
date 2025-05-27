# Computer Emulator


A Java-based implementation of a computer system emulator that simulates a processor, memory hierarchy, and assembly language processing. This project demonstrates fundamental computer architecture concepts including ALU operations, caching, and assembly language interpretation.

## Getting Started

1. Ensure you have JDK 17+ and Maven 3.6.0+ installed
2. Clone this repository
3. Navigate to the project directory
4. Build the project:
   ```bash
   mvn clean compile
   ```
5. Follow the Testing Instructions section below to run tests

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

**Running Tests:**
1. For most test files:
   ```bash
   mvn test -Dtest=\!JUnitAssemblerTest,\!JUnitProcessorTest,\!JUnitCacheTest
   ```

2. For multithreaded tests (must be run individually):
   - Using command line:
     ```bash
     # Run specific test methods
     mvn test -Dtest=JUnitAssemblerTest#testMethodName
     mvn test -Dtest=JUnitProcessorTest#testMethodName
     mvn test -Dtest=JUnitCacheTest#testMethodName
     ```
   - Using IDE (recommended):
     1. Open the project in your IDE
     2. Navigate to the test file
     3. Click the green "run" button next to individual test methods

   Examples of specific test methods:
   ```bash
   # Assembler tests
   mvn test "-Dtest=JUnitAssemblerTest#JUnitAssemblerFibonacciTest"
   
   # Cache tests
   mvn test "-Dtest=JUnitCacheTest#JUnitCacheTest0"
   mvn test "-Dtest=JUnitCacheTest#JUnitClockTest0"
   
   # Processor tests
   mvn test "-Dtest=JUnitProcessorTest#JUnitProcessorAddTest"
   mvn test "-Dtest=JUnitProcessorTest#JUnitProcessorMultTest"
   ```

**Important:** Do not run all tests in parallel or all at once for the multithreaded test files.

## Documentation

For detailed architecture specifications and instruction set details, refer to `docs/SIA32.pdf`.


