    //TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    // Define token types
    public enum TokenType {
        KEYWORD, IDENTIFIER, NUMBER, OPERATOR, DELIMITER, COMMENT, WHITESPACE, ERROR
    }

    // Define a token class
    public static class Token {
        public TokenType type;
        public String value;

        public Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
        }
    }

    // Define patterns for token recognition
    private static final Pattern[] patterns = {
            Pattern.compile("\\b(if|else|while|for|int|float|return|Alignas|Alignof|auto|Bool|break|case|char|const|continue|default|do|double|enum|extern|false|goto|inline|long|nullptr|register|restrict|short|signed|sizeof|static|struct|switch|True|typedef|union|unsigned|void|volatile)\\b"), // Keywords
            Pattern.compile("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b"),                    // Identifiers
            Pattern.compile("\\b\\d+(\\.\\d+)?\\b"),                            // Numbers
            Pattern.compile("[+\\-*/%<>=]"),                                    // Operators
            Pattern.compile("[(){};,]"),                                        // Delimiters
            Pattern.compile("//.*|/\\*(?:.|\\R)*?\\*/"),                        // Comments
            Pattern.compile("\\s+"),                                            // Whitespace
            Pattern.compile(".")                                                // Error
    };

    // Tokenize input source code
    public static List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();
        int pos = 0;
        int len = input.length();

        while (pos < len) {
            Matcher matcher = null;
            TokenType type = null;

            for (int i = 0; i < patterns.length; i++) {
                matcher = patterns[i].matcher(input.substring(pos));
                if (matcher.lookingAt()) {
                    type = TokenType.values()[i];
                    break;
                }
            }

            if (type == null) {
                // No matching pattern found, treat it as an error
                tokens.add(new Token(TokenType.ERROR, input.substring(pos, pos + 1)));
                pos++;
            } else {
                // Add token to the list
                tokens.add(new Token(type, matcher.group()));
                pos += matcher.end();
            }
        }

        return tokens;
    }

    // Test the Tokenizer
    public static void main(String[] args) {
        String code = "/* This is a multi-line comment */\n" +
                "#include <stdio.h>\n" +
                "int factorial(int n) {\n" +
                "    if (n <= 1) {\n" +
                "        return 1;\n" +
                "    } else {\n" +
                "        return n * factorial(n - 1);\n" +
                "    }\n" +
                "}\n" +
                "int main() {\n" +
                "    int num = 5;\n" +
                "    printf(\"Factorial of %d is %d\\n\", num, factorial(num));\n" +
                "    return 0;\n" +
                "}\n";



        List<Token> tokens = tokenize(code);

        for (Token token : tokens) {
            if (token.type== TokenType.WHITESPACE){continue;}
            System.out.println("<"+token.type + "   ,   " + token.value+">");
        }
    }
}
