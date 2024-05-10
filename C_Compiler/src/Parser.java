import org.abego.treelayout.demo.TextInBox;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

import java.util.ArrayList;
import java.util.List;

class Parser {
    private List<Token> tokens;
    private int currentTokenIndex;
    TextInBox Root = new TextInBox("Top el Top", 40, 20);
    DefaultTreeForTreeLayout<TextInBox> tree = new DefaultTreeForTreeLayout<TextInBox>(Root);

    public List<Token> getTokens() {
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

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
    }

    public void parse() {
        program();
    }

    private TextInBox program() {
        TextInBox root = new TextInBox("program", 40, 20);
        tree.addChild(Root,root);

        declaration(root);
        return root;
    }

    private void declaration(TextInBox parent) {
        TextInBox root = new TextInBox("declaration", 40, 20);
        tree.addChild(parent, root);

        if (tokens.get(currentTokenIndex+1).getType().equals(Lexer.TokenType.VAR_IDENTIFIER)) {
            varDeclaration(root);
        } else if (tokens.get(currentTokenIndex+1).getType().equals(Lexer.TokenType.FUN_IDENTIFIER)) {
            funcDeclaration(root);
        } else {
            // Handle error or return
        }
    }

    private void varDeclaration(TextInBox parent) {
        TextInBox root = new TextInBox("varDeclaration", 40, 20);
        tree.addChild(parent, root);

        typeSpecifier(root);

        String token=tokens.get(currentTokenIndex).getValue();
        match(token);
        TextInBox y = new TextInBox(token, 40, 20);

        match(";");
        TextInBox x = new TextInBox(";", 40, 20);
        tree.addChild(root, x);

    }

    private void typeSpecifier(TextInBox parent) {
        TextInBox root = new TextInBox("typeSpecifier", 40, 20);
        tree.addChild(parent, root);

        String token = tokens.get(currentTokenIndex).getValue();
        if (token.equals("int") || token.equals("float") || token.equals("double") || token.equals("char")) {

            match(token);
            TextInBox y = new TextInBox(token, 40, 20);
            tree.addChild(root, y);

        } else {
            // Handle error or return
        }
    }

    private void funcDeclaration(TextInBox parent) {
        TextInBox root = new TextInBox("funcDeclaration", 40, 20);
        tree.addChild(parent, root);

        typeSpecifier(root);

        String token = tokens.get(currentTokenIndex).getValue();
        match(token);
        TextInBox y = new TextInBox(token, 40, 20);
        tree.addChild(root, y);


        match("(");
        TextInBox x = new TextInBox("(", 40, 20);
        tree.addChild(root, x);

        params(root);

        match(")");
        TextInBox t = new TextInBox(")", 40, 20);
        tree.addChild(root, t);

        compoundStmt(root);
        if(currentTokenIndex+1<tokens.size()){
            declaration(root);
        }
    }

    private void params(TextInBox parent) {
        TextInBox root = new TextInBox("params", 40, 20);
        tree.addChild(parent, root);

        param(root);
        while (tokens.get(currentTokenIndex).getValue().equals(",")) {
            match(",");
            TextInBox y = new TextInBox(",", 40, 20);
            tree.addChild(root, y);

            param(root);
        }
    }

    private void param(TextInBox parent) {
        TextInBox root = new TextInBox("param", 40, 20);
        tree.addChild(parent, root);
        if(tokens.get(currentTokenIndex).getType().equals(Lexer.TokenType.KEYWORD)){
            typeSpecifier(root);
        }


        String token = tokens.get(currentTokenIndex).getValue();

        match(token);
        TextInBox y = new TextInBox(token, 40, 20);
        tree.addChild(root, y);
    }

    private void compoundStmt(TextInBox parent) {
        TextInBox root = new TextInBox("compoundStmt", 40, 20);
        tree.addChild(parent, root);

        match("{");
        TextInBox y = new TextInBox("{", 40, 20);
        tree.addChild(root, y);


        while (!tokens.get(currentTokenIndex).getValue().equals("}")) {
            if (tokens.get(currentTokenIndex).getValue().equals("int") || tokens.get(currentTokenIndex).getValue().equals("float")
                    || tokens.get(currentTokenIndex).getValue().equals("double") || tokens.get(currentTokenIndex).getValue().equals("char")) {
                varDeclaration(root);
            } else {
                statement(root);
            }
        }
        match("}");
        TextInBox x = new TextInBox("}", 40, 20);
        tree.addChild(root, x);
    }

    private void statement(TextInBox parent) {
        TextInBox root = new TextInBox("statement", 40, 20);
        tree.addChild(parent, root);

        String token = tokens.get(currentTokenIndex).getValue();
        if (token.equals("if")) {
            selectionStmt(root);
        } else if (token.equals("while")) {
            iterationStmt(root);

        } else if (token.equals("return")) {
            returnStmt(root);
        }

        /*
        else if(token.equals("for")) {
            forStmt(root);
        }*/

        else {

            expressionStmt(root);
        }
    }

    private void expressionStmt(TextInBox parent) {
        TextInBox root = new TextInBox("expressionStmt", 40, 20);
        tree.addChild(parent, root);

        expression(root);

        match(";");
        TextInBox x = new TextInBox(";", 40, 20);
        tree.addChild(root, x);

    }


    /*

    private void forStmt(TextInBox parent) {
        TextInBox root = new TextInBox("forStmt", 40, 20);
        tree.addChild(parent, root);


    }
    */


    private void expression(TextInBox parent) {
        TextInBox root = new TextInBox("expression", 40, 20);
        tree.addChild(parent, root);

        if (tokens.get(currentTokenIndex).getType().equals(Lexer.TokenType.VAR_IDENTIFIER)) {

            Token token = tokens.get(currentTokenIndex);
            match(token.getValue());
            TextInBox y = new TextInBox(token.getValue(), 40, 20);
            tree.addChild(root, y);

            //HARD CODED
            if (token.getType().equals(Lexer.TokenType.OPERATOR)){
                match(token.getValue());
                TextInBox t= new TextInBox(token.getValue(), 40, 20);
                tree.addChild(root, t);
            }
            expression(root);

        } else {
            simpleExpression(root);
        }
    }

    private void simpleExpression(TextInBox parent) {
        TextInBox root = new TextInBox("simpleExpression", 40, 20);
        tree.addChild(parent, root);

        Token token = tokens.get(currentTokenIndex);
        if ((token.getType()).equals(Lexer.TokenType.VAR_IDENTIFIER) || token.getType().equals(Lexer.TokenType.NUMBER)) {

            match(token.getValue());
            TextInBox t = new TextInBox(token.getValue(), 40, 20);
            tree.addChild(root, t);
        }else if((token.getType()).equals(Lexer.TokenType.FUN_IDENTIFIER)){
            funcCall(root);
        } else {
            match("(");
            TextInBox x = new TextInBox("(", 40, 20);
            tree.addChild(root, x);

            expression(root);

            match(")");
            TextInBox y = new TextInBox(")", 40, 20);
            tree.addChild(root, y);
        }
    }

    private void funcCall(TextInBox parent) {
        TextInBox root = new TextInBox("funcCall", 40, 20);
        tree.addChild(parent, root);

        String token = tokens.get(currentTokenIndex).getValue();

        match(token);
        TextInBox y = new TextInBox(token, 40, 20);
        tree.addChild(root, y);


        match("(");
        TextInBox x = new TextInBox("(", 40, 20);
        tree.addChild(root, x);

        params(root);

        match(")");
        TextInBox t = new TextInBox(")", 40, 20);
        tree.addChild(root, t);

    }

    private void selectionStmt(TextInBox parent) {
        TextInBox root = new TextInBox("selectionStmt", 40, 20);
        tree.addChild(parent, root);

        match("if");
        TextInBox x = new TextInBox("if", 40, 20);
        tree.addChild(root, x);

        match("(");
        TextInBox y = new TextInBox("(", 40, 20);
        tree.addChild(root, y);

        expression(root);

        match(")");
        TextInBox z = new TextInBox(")", 40, 20);
        tree.addChild(root, z);

        match("{");
        TextInBox a = new TextInBox("{", 40, 20);
        tree.addChild(root, a);

        statement(root);

        match("}");
        TextInBox b = new TextInBox("}", 40, 20);
        tree.addChild(root, b);

        if (tokens.get(currentTokenIndex).getValue().equals("else")) {
            match("else");
            TextInBox q = new TextInBox("else", 40, 20);
            tree.addChild(root, q);

            match("{");
            TextInBox c = new TextInBox("{", 40, 20);
            tree.addChild(root, c);

            statement(root);

            match("}");
            TextInBox d = new TextInBox("}", 40, 20);
            tree.addChild(root, d);
        }
    }

    private void iterationStmt(TextInBox parent) {
        TextInBox root = new TextInBox("iterationStmt", 40, 20);
        tree.addChild(parent, root);

        match("while");
        TextInBox x = new TextInBox("while", 40, 20);
        tree.addChild(root, x);

        match("(");
        TextInBox y = new TextInBox("(", 40, 20);
        tree.addChild(root, y);

        expression(root);

        match(")");
        TextInBox z = new TextInBox(")", 40, 20);
        tree.addChild(root, z);

        statement(root);

    }

    private void returnStmt(TextInBox parent) {
        TextInBox root = new TextInBox("returnStmt", 40, 20);
        tree.addChild(parent, root);

        match("return");
        TextInBox x = new TextInBox("return", 40, 20);
        tree.addChild(root, x);

        if (!tokens.get(currentTokenIndex).getValue().equals(";")) {
            expression(root);
        }

        match(";");
        TextInBox y = new TextInBox(";", 40, 20);
        tree.addChild(root, y);

    }




    /////////////////////////////// Not Finished /////////////////////////////
    private void additiveExpression(TextInBox parent) {
        TextInBox root = new TextInBox("additiveExpression", 40, 20);
        tree.addChild(parent, root);


            additiveExpression(root);



    }



    /////////////////////////////// Not Finished /////////////////////////////
    private void addOp(TextInBox parent) {
        TextInBox root = new TextInBox("addOp", 40, 20);
        tree.addChild(parent, root);

        String token = tokens.get(currentTokenIndex).getValue();
        if (token.equals("+")) {
            match(token);
            TextInBox x = new TextInBox("+", 40, 20);
            tree.addChild(root, x);
        }
        else if (token.equals("-")) {
            match(token);
            TextInBox y = new TextInBox("-", 40, 20);
            tree.addChild(root, y);
        }
    }


    /////////////////////////////// Not Finished /////////////////////////////

    private void relOp(TextInBox parent) {
        TextInBox root = new TextInBox("relOp", 40, 20);
        tree.addChild(parent, root);

        String token = tokens.get(currentTokenIndex).getValue();
        if (token.equals("+")) {
            match(token);
            TextInBox x = new TextInBox("+", 40, 20);
            tree.addChild(root, x);
        }
        else if (token.equals("-")) {
            match(token);
            TextInBox y = new TextInBox("-", 40, 20);
            tree.addChild(root, y);
        }
    }


    private void match(String expectedToken) {
        if (currentTokenIndex < tokens.size() && tokens.get(currentTokenIndex).getValue().equals(expectedToken)) {
            currentTokenIndex++;
        }else {
            currentTokenIndex++;
        }
    }
}
