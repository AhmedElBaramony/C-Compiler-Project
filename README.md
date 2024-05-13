
# C-Compiler Project

## Project Overview

This C-Compiler is designed to efficiently compile C code into an abstract syntax tree, demonstrating the inner workings of compilers through a comprehensive GUI. It facilitates deep interaction with core compiler components, including lexical analysis, parsing, and symbol table management, effectively highlighting each stage of the compilation process.

## Key Features

- **Lexical Analysis:** Breaks down the input C code into a sequence of tokens.
- **Parsing:** Constructs a syntax tree based on the sequence of tokens.
- **Syntax Tree Visualization:** Visualizes the structure of the program as a tree, helping in understanding the grammatical relationships between different components.
- **Symbol Table Management:** Displays the symbol table, which includes information like variable names, types, and scopes.
- **Graphical User Interface:** A user-friendly GUI that provides easy navigation and interaction with the compiler's functionalities.

## Components

### Source Code Components

1. **Lexer.java**
   - **Purpose:** Responsible for breaking down the source code into a series of tokens. It handles the initial stage of the compilation process by identifying patterns that match keywords, identifiers, literals, and operators.

2. **Parser.java**
   - **Purpose:** Takes the tokens produced by the lexer and builds a syntax tree. This component checks the grammatical structure of the code and is crucial for detecting syntax errors.

3. **Token.java**
   - **Purpose:** Defines the properties and types of tokens that the lexer can return. This includes data about the token's type, value, and position in the input stream.

4. **TreeNode.java**
   - **Purpose:** Represents a node in the syntax tree. Each node corresponds to a grammatical construct such as an expression or a statement.

5. **SymbolTable.java**
   - **Purpose:** Manages a table that stores information about identifiers declared in the code, such as their types and scopes. This is essential for semantic analysis and for supporting features like scope resolution and type checking.

6. **Main.java**
   - **Purpose:** The entry point of the compiler. It coordinates the flow of processes from lexical analysis to parsing, and finally to the display of results.

7. **GUI Components (MainGUI.java, SwingDemo.java, SymbolTableWindow.java)**
   - **Purpose:** These files collectively define the graphical user interface. `MainGUI.java` is the main frame of the application, `SwingDemo.java` serves as a demonstration of how GUI components can be organized, and `SymbolTableWindow.java` is dedicated to displaying the symbol table.


Ensure you have Java installed on your system to compile and run the project. This project has been tested with Java 8.

## Usage

- **Starting the Compiler:** Run the `Main` class to start the application. This will launch the GUI from which you can interact with the compiler.
- **Input C Code:** Through the GUI, input the C code you wish to compile.
- **View Results:** After compilation, the lexer and parser results, including symbol tables and syntax trees, can be viewed directly through the GUI.
