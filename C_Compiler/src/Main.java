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
        symbolTable.setTokens(lexer.tokenize(code));

        symbolTable.printTokenTable();
        System.out.println("The symbol table contains the following tokens:");
        symbolTable.printSymbolTable();
    }
}

