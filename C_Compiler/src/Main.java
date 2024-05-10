import org.abego.treelayout.demo.TextInBox;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

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
                        return n;
                    }
                }
                int main(int m) {
                    int num;
                    factorial(num);
                    printf("Factorial of %d is %d\\n", num);
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
        Parser parser = new Parser(tokens);
        parser.parse();

        printTree(parser.getRoot(),parser.getTree(),0);
    }

    // Method to print the whole tree
    private static void printTree(TextInBox node, DefaultTreeForTreeLayout<TextInBox> tree, int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("   "); // Indentation for each level
        }
        System.out.println(node.text);
        List<TextInBox> children = tree.getChildrenList(node);
        for (TextInBox child : children) {
            printTree(child, tree, depth + 1); // Recursively print each subtree
        }
    }


}

