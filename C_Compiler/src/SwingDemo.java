import java.awt.Container;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;

import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.demo.SampleTreeFactory;
import org.abego.treelayout.demo.TextInBox;
import org.abego.treelayout.demo.TextInBoxNodeExtentProvider;
import org.abego.treelayout.demo.swing.TextInBoxTreePane;
import org.abego.treelayout.util.DefaultConfiguration;

public class SwingDemo {

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
                    num = 1 + 1 * 10;
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
        parser.parse();

        // get the sample tree
        String treeName = (args.length > 0) ? args[0] : "";
        boolean boxVisible = true;

        for (String s: args) {
            if (s.equalsIgnoreCase("--nobox")) {
                boxVisible = false;
            }
        }
        TreeForTreeLayout<TextInBox> tree = parser.getTree();

        // setup the tree layout configuration
        double gapBetweenLevels = treeName.startsWith("semtab") ? 15 : 50;
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
}
