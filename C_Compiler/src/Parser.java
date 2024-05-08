import java.util.ArrayList;
import java.util.List;

class Parser {
    private List<String> tokens;
    private int currentTokenIndex;

    public Parser(List<String> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
    }

    public void parse() {
        program();
    }

    private void program() {
        match("{");
        declaration();
        match("}");
    }

    private void declaration() {
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
    }

    private void varDeclaration() {
        typeSpecifier();
        match("ID");
        match(";");
    }

    private void typeSpecifier() {
        String token = tokens.get(currentTokenIndex);
        if (token.equals("int") || token.equals("float") || token.equals("double") || token.equals("char")) {
            match(token);
        } else {
            // Handle error or return
        }
    }

    private void funcDeclaration() {
        typeSpecifier();
        match("ID");
        match("(");
        params();
        match(")");
        compoundStmt();
    }

    private void params() {
        param();
        while (tokens.get(currentTokenIndex).equals(",")) {
            match(",");
            param();
        }
    }

    private void param() {
        typeSpecifier();
        match("ID");
    }

    private void compoundStmt() {
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
    }

    private void statement() {
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
    }

    private void expressionStmt() {
        expression();
        match(";");
    }

    private void expression() {
        if (tokens.get(currentTokenIndex).equals("ID")) {
            match("ID");
            match("=");
            expression();
        } else {
            simpleExpression();
        }
    }

    private void simpleExpression() {
        String token = tokens.get(currentTokenIndex);
        if (token.equals("ID") || token.equals("number")) {
            match(token);
        } else {
            match("(");
            expression();
            match(")");
        }
    }

    private void selectionStmt() {
        match("if");
        match("(");
        expression();
        match(")");
        statement();
        if (tokens.get(currentTokenIndex).equals("else")) {
            match("else");
            statement();
        }
    }

    private void iterationStmt() {
        match("while");
        match("(");
        expression();
        match(")");
        statement();
    }

    private void returnStmt() {
        match("return");
        if (!tokens.get(currentTokenIndex).equals(";")) {
            expression();
        }
        match(";");
    }

    private void match(String expectedToken) {
        if (currentTokenIndex < tokens.size() && tokens.get(currentTokenIndex).equals(expectedToken)) {
            currentTokenIndex++;
        } else {
            // Handle error or return
        }
    }
}
