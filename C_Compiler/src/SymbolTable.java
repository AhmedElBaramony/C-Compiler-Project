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

    public int searchSymbolIndex(String symbol) {
        for (Token sym : symbols) {
            if (Objects.equals(sym.getValue(), symbol)){
                return symbols.indexOf(sym);
            }
        }
        return -1;
    }

    public void printTokenTable(){

        System.out.println("\n********************************Token Table******************************\n");

        for (Token token : tokens) {
            if (token.getPointer() != -1) {
                System.out.println("<"+token.getType() + "   ,   " + token.getValue() + "   ,   Loc: " + token.getPointer() + ">");
            }
            else {
                System.out.println("<"+token.getType() + "   ,   " + token.getValue()+">");
            }
        }
    }

    public void makeSymbolTable(){

        System.out.println("\n********************************Symbol Table******************************\n");

        for (Token token : tokens) {
            if (token.getType() == Lexer.TokenType.VAR_IDENTIFIER && searchSymbolIndex(token.getValue()) == -1){
                setSymbol(token);
                System.out.println(token.getValue());
            }
            else if (token.getType() == Lexer.TokenType.VAR_IDENTIFIER && searchSymbolIndex(token.getValue()) != -1){
                token.setPointer(searchSymbolIndex(token.getValue()));
            }
        }
    }
}
