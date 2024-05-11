import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.demo.TextInBox;
import org.abego.treelayout.demo.TextInBoxNodeExtentProvider;
import org.abego.treelayout.demo.swing.TextInBoxTreePane;
import org.abego.treelayout.util.DefaultConfiguration;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

import java.util.List;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;



public class Main {
    public static int findLine(String code, String targetLine) {
        String[] lines = code.split("\n");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].contains(targetLine)) {
                return i + 1; // Adding 1 to make line numbers human-readable (starting from 1)
            }
        }
        return -1; // Return -1 if the line is not found
    }

    public static void main(String[] args) {
        String code = """
                \\* This is a multi-line comment *\\\s
                #include <stdio.h>
                int factorial(int n) {
                    if (n <= 1)
                        return 1;
                    else
                        return factorial(n - 1) * factorial(n - 2);
                }
                int main() {
                    num = 10;
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
        Parser parser = new Parser(tokens);
        try{
            parser.parse();
        }catch(Exception e){}
        finally {
            if(parser.getError().equals(""))
                printTree(parser.getRoot(),parser.getTree(),0);
            else{
                System.out.println("Error at Line: " + findLine(code, parser.getError())
                        + ", Token: " + parser.getError());
            }
        }
        // get the sample tree
        String treeName = (args.length > 0) ? args[0] : "";
        boolean boxVisible = true;

        for (String s: args) {
            if (s.equalsIgnoreCase("--nobox")) {
                boxVisible = false;
                break;
            }
        }
        TreeForTreeLayout<TextInBox> tree = parser.getTree();

        // setup the tree layout configuration
        double gapBetweenLevels = treeName.startsWith("semtab") ? 15 : 20;
        double gapBetweenNodes = 10;
        DefaultConfiguration<TextInBox> configuration = new DefaultConfiguration<TextInBox>(
                gapBetweenLevels, gapBetweenNodes);

        // create the NodeExtentProvider for TextInBox nodes
        TextInBoxNodeExtentProvider nodeExtentProvider = new TextInBoxNodeExtentProvider();

        // create the layout
        TreeLayout<TextInBox> treeLayout = new TreeLayout<TextInBox>(tree,
                nodeExtentProvider, configuration);

        // Create a panel that draws the nodes and edges and show the panel
        TextInBoxTreePane panel = new TextInBoxTreePane(treeLayout);
        panel.setVisible(boxVisible);
        showInDialog(panel);
    }

    private static void showInDialog(JComponent panel) {
        JDialog dialog = new JDialog();
        Container contentPane = dialog.getContentPane();
        ((JComponent) contentPane).setBorder(BorderFactory.createEmptyBorder(
                10, 10, 10, 10));
        contentPane.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
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

