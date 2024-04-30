import java.util.List;
public class SymbolTable {
    private List<Token> tokens;

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void printTable(){
        for (Token token : tokens) {
            System.out.println("<"+token.type + "   ,   " + token.value+">");
        }
    }

}
