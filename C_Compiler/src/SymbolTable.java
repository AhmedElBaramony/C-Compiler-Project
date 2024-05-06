import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SymbolTable {
    private List<Token> tokens;
    private List<Token> symbols;

    public SymbolTable() {
        symbols = new ArrayList<>();
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void setSymbol(Token symbol) {
        symbols.add(symbol);
    }

    public String searchSymbol(String symbol) {
        for (Token sym : symbols) {
            if (Objects.equals(sym.getValue(), symbol)){
                return symbol;
            }
        }
        return null;
    }

    public void printTokenTable(){
        for (Token token : tokens) {
            System.out.println("<"+token.getType() + "   ,   " + token.getValue()+">");
        }
    }

    public void printSymbolTable(){
        for (Token token : tokens) {
            if (token.getType() == Lexer.TokenType.IDENTIFIER && searchSymbol(token.getValue()) == null){
                setSymbol(token);
                System.out.println(token.getValue());
            }
        }
    }
}
