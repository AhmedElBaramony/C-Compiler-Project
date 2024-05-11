import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.demo.TextInBox;
import org.abego.treelayout.demo.TextInBoxNodeExtentProvider;
import org.abego.treelayout.demo.swing.TextInBoxTreePane;
import org.abego.treelayout.util.DefaultConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class MainGUI extends JFrame {
    private JTextArea codeTextArea;
    private JButton runButton;
    private JButton getSymbolTableButton;
    private JButton getParseTreeButton;

    private Lexer lexer;
    private SymbolTable symbolTable;
    private Parser parser;
    String[] args;

    public MainGUI(String[] args) {
        setTitle("Code Analyzer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        initializeComponents();
        setLayout(new BorderLayout());
        add(createMainPanel(), BorderLayout.CENTER);
        this.args = args;
    }

    private void initializeComponents() {
        codeTextArea = new JTextArea();
        JScrollPane codeScrollPane = new JScrollPane(codeTextArea);

        runButton = new JButton("Run");
        getSymbolTableButton = new JButton("Get Symbol Table");
        getParseTreeButton = new JButton("Get Parse Tree");

        runButton.addActionListener(e ->{
            runCodeAnalyzer();
        });

        getSymbolTableButton.addActionListener(e ->{
            showSymbolTable();
        });

        getParseTreeButton.addActionListener(e -> {
            showParseTree();
        });
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(runButton);
        buttonPanel.add(getSymbolTableButton);
        buttonPanel.add(getParseTreeButton);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(codeTextArea), BorderLayout.CENTER);

        return mainPanel;
    }

    private void runCodeAnalyzer() {
        String code = codeTextArea.getText();

        lexer = new Lexer();
        symbolTable = new SymbolTable();
        List<Token> tokens = lexer.tokenize(code);
        symbolTable.setTokens(tokens);

        symbolTable.makeSymbolTable();

        parser = new Parser(tokens);
        try {
            parser.parse();
            JOptionPane.showMessageDialog(this, "Code analysis completed.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error at Line: " + findLine(code, parser.getError())
                    + ", Token: " + parser.getError());
        }
    }

    public static int findLine(String code, String targetLine) {
        String[] lines = code.split("\n");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].contains(targetLine)) {
                return i + 1; // Adding 1 to make line numbers human-readable (starting from 1)
            }
        }
        return -1; // Return -1 if the line is not found
    }
    private void showSymbolTable() {
        if (symbolTable == null) {
            JOptionPane.showMessageDialog(this, "Symbol table not available.");
            return;
        }

        SymbolTableWindow symbolTableWindow = new SymbolTableWindow(symbolTable);
        symbolTableWindow.setVisible(true);
    }

    public void showParseTree() {
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

    private void showInDialog(TextInBoxTreePane panel) {
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainGUI mainGUI = new MainGUI(args);
                mainGUI.setVisible(true);
            }
        });
    }
}
