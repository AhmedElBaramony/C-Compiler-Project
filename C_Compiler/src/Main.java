import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String code = """
                \\* This is a multi-line comment *\\\s
                #include <stdio.h>
                int factorial(int n) {
                    if (n <= 1) {
                        return 1;
                    } else {
                        return n * factorial(n - 1);
                    }
                }
                int main() {
                    int num = 6;
                    printf("Factorial of %d is %d\\n", num, factorial(num));
                    return 0;
                }
                """;

        Lexer lexer = new Lexer();
        SymbolTable symbolTable = new SymbolTable();
        List<Token> tokens = lexer.tokenize(code);
        symbolTable.setTokens(tokens);



        symbolTable.makeSymbolTable();
        symbolTable.printTokenTable();
        // Add your tokens here
        Parser parser = new Parser(lexer.tokenize_str(tokens));
        parser.parse();
    }
}

