public class Token {
    public Lexer.TokenType type;
    public String value;

    public Token(Lexer.TokenType type, String value) {
        this.type = type;
        this.value = value;
    }
}
