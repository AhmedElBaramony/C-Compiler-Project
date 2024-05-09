import org.abego.treelayout.demo.TextInBox;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

import java.util.ArrayList;
import java.util.List;

class Parser {
    private List<String> tokens;
    private int currentTokenIndex;
    TextInBox Root = new TextInBox("Top el Top", 40, 20);
    DefaultTreeForTreeLayout<TextInBox> tree = new DefaultTreeForTreeLayout<TextInBox>(Root);

    public List<String> getTokens() {
        return tokens;
    }

    public int getCurrentTokenIndex() {
        return currentTokenIndex;
    }

    public DefaultTreeForTreeLayout<TextInBox> getTree() {
        return tree;
    }

    public TextInBox getRoot() {
        return Root;
    }

    public Parser(List<String> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
    }

    public void parse() {
        program();
    }

    private TextInBox program() {
        TextInBox root = new TextInBox("program", 40, 20);
        tree.addChild(Root,root );
        TextInBox decl =declaration();
        tree.addChild(root,decl);
        return root;
    }

    private TextInBox declaration() {
        TextInBox root = new TextInBox("declaration", 40, 20);
        if (tokens.get(currentTokenIndex).equals("int") || tokens.get(currentTokenIndex).equals("float")
                || tokens.get(currentTokenIndex).equals("double") || tokens.get(currentTokenIndex).equals("char")) {
            varDeclaration();
        } else if (tokens.get(currentTokenIndex).equals("void") || tokens.get(currentTokenIndex).equals("int")
                || tokens.get(currentTokenIndex).equals("float") || tokens.get(currentTokenIndex).equals("double")
                || tokens.get(currentTokenIndex).equals("char")) {
            funcDeclaration();
        } else {
            // Handle error or return
        }
        return root;
    }

    private TextInBox varDeclaration() {
        TextInBox root = new TextInBox("varDeclaration", 40, 20);
        typeSpecifier();
        match("ID");
        match(";");
        return root;
    }

    private TextInBox typeSpecifier() {
        TextInBox root = new TextInBox("typeSpecifier", 40, 20);
        String token = tokens.get(currentTokenIndex);
        if (token.equals("int") || token.equals("float") || token.equals("double") || token.equals("char")) {
            match(token);
        } else {
            // Handle error or return
        }
        return root;
    }

    private TextInBox funcDeclaration() {
        TextInBox root = new TextInBox("funcDeclaration", 40, 20);
        typeSpecifier();
        match("ID");
        match("(");
        params();
        match(")");
        compoundStmt();
        return root;
    }

    private TextInBox params() {
        TextInBox root = new TextInBox("params", 40, 20);
        param();
        while (tokens.get(currentTokenIndex).equals(",")) {
            match(",");
            param();
        }
        return root;
    }

    private TextInBox param() {
        TextInBox root = new TextInBox("param", 40, 20);
        typeSpecifier();
        match("ID");
        return root;
    }

    private TextInBox compoundStmt() {
        TextInBox root = new TextInBox("compoundStmt", 40, 20);
        match("{");
        while (!tokens.get(currentTokenIndex).equals("}")) {
            if (tokens.get(currentTokenIndex).equals("int") || tokens.get(currentTokenIndex).equals("float")
                    || tokens.get(currentTokenIndex).equals("double") || tokens.get(currentTokenIndex).equals("char")) {
                varDeclaration();
            } else {
                statement();
            }
        }
        match("}");
        return root;
    }

    private TextInBox statement() {
        TextInBox root = new TextInBox("statement", 40, 20);
        String token = tokens.get(currentTokenIndex);
        if (token.equals("if")) {
            selectionStmt();
        } else if (token.equals("while")) {
            iterationStmt();

        } else if (token.equals("return")) {
            returnStmt();
        } else {
            expressionStmt();
        }
        return root;
    }

    private TextInBox expressionStmt() {
        TextInBox root = new TextInBox("expressionStmt", 40, 20);
        expression();
        match(";");
        return root;
    }

    private TextInBox expression() {
        TextInBox root = new TextInBox("expression", 40, 20);
        if (tokens.get(currentTokenIndex).equals("ID")) {
            match("ID");
            match("=");
            expression();
        } else {
            simpleExpression();
        }
        return root;
    }

    private TextInBox simpleExpression() {
        TextInBox root = new TextInBox("simpleExpression", 40, 20);
        String token = tokens.get(currentTokenIndex);
        if (token.equals("ID") || token.equals("number")) {
            match(token);
        } else {
            match("(");
            expression();
            match(")");
        }
        return root;
    }

    private TextInBox selectionStmt() {
        TextInBox root = new TextInBox("selectionStmt", 40, 20);
        match("if");
        match("(");
        expression();
        match(")");
        statement();
        if (tokens.get(currentTokenIndex).equals("else")) {
            match("else");
            statement();
        }
        return root;
    }

    private TextInBox iterationStmt() {
        TextInBox root = new TextInBox("iterationStmt", 40, 20);
        match("while");
        match("(");
        expression();
        match(")");
        statement();

        return root;
    }

    private TextInBox returnStmt() {
        TextInBox root = new TextInBox("returnStmt", 40, 20);
        match("return");

        if (!tokens.get(currentTokenIndex).equals(";")) {
            expression();

        }
        match(";");
        return root;
    }

    private void match(String expectedToken) {
        if (currentTokenIndex < tokens.size() && tokens.get(currentTokenIndex).equals(expectedToken)) {
            currentTokenIndex++;
        } else {
            // Handle error or return
        }
    }
}
