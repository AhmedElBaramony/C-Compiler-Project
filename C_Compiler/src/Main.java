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

        TextInBox root = new TextInBox("root", 40, 20);
        TextInBox n1 = new TextInBox("n1", 30, 20);
        TextInBox n1_1 = new TextInBox("n1.1\n(first node)", 80, 36);
        TextInBox n1_2 = new TextInBox("n1.2", 40, 20);
        TextInBox n1_3 = new TextInBox("n1.3\n(last node)", 80, 36);
        TextInBox n2 = new TextInBox("n2", 30, 20);
        TextInBox n2_1 = new TextInBox("n2", 30, 20);

        DefaultTreeForTreeLayout<TextInBox> tree = new DefaultTreeForTreeLayout<TextInBox>(
                root);
        tree.addChild(root, n1);
        tree.addChild(n1, n1_1);
        tree.addChild(n1, n1_2);
        tree.addChild(n1, n1_3);
        tree.addChild(root, n2);
        tree.addChild(n2, n2_1);
        //printTree(root, tree, 0);

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

