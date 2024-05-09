import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

import java.util.List;

public class SemanticAnalyzer {

    public TreeNode buildTree(List<Token> tokens) {
        TreeNode root = new TreeNode(new Token(Lexer.TokenType.PROGRAM, "Program"));
        TreeNode currentFunction = null; // Track the current function being defined
        boolean isFunctionNameExpected = false; // Flag to indicate if the next token is expected to be a function name


        for (Token token : tokens) {
            switch (token.getType()) {
                case KEYWORD:
                    if (token.getValue().equals("int")) {
                        // Start of a function definition
                        currentFunction = new TreeNode(token);
                        root.addChild(currentFunction);
                        isFunctionNameExpected = true;
                    }
                    break;
                case VAR_IDENTIFIER:
                    if (currentFunction != null && isFunctionNameExpected) {
                        // Function name
                        currentFunction.addChild(new TreeNode(token));
                        isFunctionNameExpected = false;
                    }
                    break;
                case FUN_IDENTIFIER:
                    if (currentFunction != null && isFunctionNameExpected) {
                        currentFunction.addChild(new TreeNode(token));
                        isFunctionNameExpected = false;
                    }
                case DELIMITER:
                    if (isTokenStartOfStatement(token)) {
                        // Start of a statement
                        TreeNode statementNode = new TreeNode(token);
                        if (currentFunction != null) {
                            currentFunction.addChild(statementNode);
                        } else {
                            root.addChild(statementNode);
                        }
                        currentFunction = null; // Reset current function after the function body
                    }
                    break;
                case OPERATOR:

                case MACRO:

                case NUMBER:

                case LITERAL:

                case COMMENT:

                case WHITESPACE:

                case ERROR:

                case PROGRAM :


                default:
                    // Other tokens (e.g., expressions, operators, etc.)
                    TreeNode newNode = new TreeNode(token);
                    if (currentFunction != null) {
                        currentFunction.addChild(newNode);
                    } else {
                        root.addChild(newNode);
                    }
                    break;
            }
        }

        return root;
    }

    private boolean isTokenStartOfStatement(Token token) {
        // Implement logic to check if the token starts a statement (e.g., ";" for simple statements)
        return token.getType() == Lexer.TokenType.DELIMITER && token.getValue().equals(";");
    }
}

