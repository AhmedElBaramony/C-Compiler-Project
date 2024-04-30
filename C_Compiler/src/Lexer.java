import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    public enum TokenType {
        COMMENT, MACRO, KEYWORD, IDENTIFIER, LITERAL, NUMBER, OPERATOR, DELIMITER, WHITESPACE, ERROR
    }

    static String signPart = "(((\\+-)*\\+?)|((-\\+)*-?))";
    static String integerPart = "(0|([1-9][0-9]*))";
    static String floatPart = "(\\.(0|[1-9][0-9]*))";
    static String exponentialPart = "(e|E\\+|-?[0-9]+)";

    static String integerRegex = signPart + integerPart;
    static String floatRegex = signPart + integerPart + floatPart;
    static String exponentialRegex = signPart + integerPart + floatPart + exponentialPart;

    static String base2 = "(\\+|-)?0b|B[0-1]+";
    static String base8 = "(\\+|-)?0[0-7]*";
    static String base16 = "(\\+|-)?0x|X[0-9a-fA-F]+";

    static String numberRegex =  base2 + "|" + base16 + "|"+ base8 + "|"+ exponentialRegex
            + "|" + floatRegex + "|" + integerRegex;

    private static final Pattern[] patterns = {
            Pattern.compile("\\\\\\*.*\\*\\\\|\\\\\\\\.*"),                // Comments
            Pattern.compile("#.*"),                                        //Macros
            Pattern.compile("\\b(if|else|while|for|int|float|return|Alignas|Alignof|auto|Bool|break|case|char|const|continue|default|do|double|enum|extern|false|goto|inline|long|nullptr|register|restrict|short|signed|sizeof|static|struct|switch|True|typedef|union|unsigned|void|volatile)\\b"), // Keywords
            Pattern.compile("\\b\\*?[a-zA-Z_][a-zA-Z0-9_]*\\b"),     // Identifiers
            Pattern.compile("\"(.)*\""),                                   //Literal
            Pattern.compile("\\b"+numberRegex+"\\b"),                      // Numbers
            Pattern.compile("\\+=|-=|\\*=|/=|%=|&=|\\|=|\\^=|>>=|<<=|\\+\\+|\\+|--|-|\\*|/|%|<<|>>|<=|>=|>|<|=|&|\\||\\^"),  // Operators
            Pattern.compile("[(){};,]"),                                   // Delimiters
            Pattern.compile("\\s+"),                                       // Whitespace -------------------------> Not token !!!!!!!!!!!!!!!!!!
            Pattern.compile(".*")                                           // Error
    };

    // Tokenize input source code
    public List<Token> tokenize(String input) {
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
            }
            else if(type == TokenType.COMMENT || type == TokenType.WHITESPACE || type == TokenType.MACRO){
                pos += matcher.end();
            }
            else {
                // Add token to the list
                tokens.add(new Token(type, matcher.group()));
                pos += matcher.end();
            }
        }
        return tokens;
    }

}
