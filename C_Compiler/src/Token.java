public class Token {
    public Lexer.TokenType type;
    public String value;
    public int pointer;

    public Token(Lexer.TokenType type, String value) {
        this.type = type;
        this.value = value;
        this.pointer = -1;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Lexer.TokenType getType() {
        return type;
    }

    public void setType(Lexer.TokenType type) {
        this.type = type;
    }

    public int getPointer() { return pointer; }

    public void setPointer(int pointer) { this.pointer = pointer; }
}
