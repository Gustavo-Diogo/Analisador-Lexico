import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

/**
 * Scanner
 */
public class Scanner {

    private char[] content;
    private int estado;
    private int pos;

    public Scanner(String filename) {
        try {

            String txtConteudo;
            txtConteudo = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
            System.out.println("------TESTE-------");
            System.out.println(txtConteudo);
            System.out.println("------------------");

            content = txtConteudo.toCharArray();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public Token nextToken() throws Exception {

        char currentChar;
        Token token;
        String term = "";
        estado = 0;

        if (isEOF()) {
            return null;
        }
        while (true) {
            currentChar = nextChar();
            // term += currentChar;
            switch (estado) {
                case 0:
                    int subEstado;
                    if (isP(currentChar)) {
                        subEstado = 0;
                        term += currentChar;
                        currentChar = nextChar();
                        switch (subEstado) {
                            case 0:
                                if (isR(currentChar)) {
                                    term += currentChar;
                                    currentChar = nextChar();

                                    int subSubEstado = 0;
                                    switch (subSubEstado) {
                                        case 0:
                                            if (isI(currentChar)) {
                                                term += currentChar;
                                                currentChar = nextChar();

                                                int subSubSubEstado = 0;
                                                switch (subSubSubEstado) {
                                                    case 0:
                                                        if (isN(currentChar)) {
                                                            term += currentChar;
                                                            currentChar = nextChar();

                                                            int subSubSubSubEstado = 0;
                                                            switch (subSubSubSubEstado) {
                                                                case 0:
                                                                    if (isT(currentChar)) {
                                                                        term += currentChar;
                                                                        currentChar = nextChar();

                                                                        int subSubSubSubSubEstado = 0;
                                                                        switch (subSubSubSubSubEstado) {
                                                                            case 0:
                                                                                if (isAbreParen(currentChar)) {
                                                                                    int subState = 0;
                                                                                    boolean text = true;
                                                                                    term += currentChar;
                                                                                    currentChar = nextChar();
                                                                                    do {
                                                                                        switch (subState) {
                                                                                            case 0:
                                                                                                if (isAspast(
                                                                                                        currentChar)) {
                                                                                                    term += currentChar;
                                                                                                    currentChar = nextChar();
                                                                                                    subState = 1;
                                                                                                }
                                                                                                break;

                                                                                            case 1:
                                                                                                if (!isAspast(
                                                                                                        currentChar)) {
                                                                                                    term += currentChar;
                                                                                                    currentChar = nextChar();
                                                                                                    subState = 1;
                                                                                                } else if (isAspast(
                                                                                                        currentChar)) {
                                                                                                    term += currentChar;
                                                                                                    currentChar = nextChar();
                                                                                                    subState = 2;
                                                                                                }
                                                                                                break;
                                                                                            case 2:
                                                                                                if (isFechaParen(
                                                                                                        currentChar)) {
                                                                                                    term += currentChar;
                                                                                                    currentChar = nextChar();
                                                                                                    subState = 2;
                                                                                                }
                                                                                                if (isSpace(
                                                                                                        currentChar)) {
                                                                                                    token = new Token();
                                                                                                    token.setType(
                                                                                                            Token.TK_ASSIGN);
                                                                                                    token.setText(term);
                                                                                                    back();
                                                                                                    return token;
                                                                                                }
                                                                                                break;
                                                                                            default:
                                                                                                break;
                                                                                        }
                                                                                    } while (text == true);
                                                                                }

                                                                                break;
                                                                        }
                                                                    }
                                                                    break;
                                                            }
                                                        }
                                                        break;
                                                    default:
                                                        break;
                                                }
                                            }
                                            break;

                                        default:
                                            break;
                                    }
                                }

                                break;

                            default:
                                if (isChar(currentChar) || isDigit(currentChar)) {
                                    term += currentChar;
                                    estado = 0;
                                } else {

                                }
                                break;
                        }
                    } else {
                        estado = 1;
                    }
                case 1:
                    if (isChar(currentChar)) {
                        estado = 1;
                        term += currentChar;

                    } else if (isSpace(currentChar)) {
                        estado = 3;
                        term += currentChar;
                    } else if (isAtribuicao(currentChar)) {
                        estado = 2;
                        term += currentChar;
                    }
                    break;
                case 3:
                    if (isAtribuicao(currentChar)) {
                        estado = 2;
                        term += currentChar;
                    }
                    break;
                case 2:
                    if (isDigit(currentChar)) {
                        estado = 2;
                        term += currentChar;
                    } else if (isSpace(currentChar)) {
                        estado = 2;
                        term += currentChar;
                    } else if (isPonto(currentChar)) {
                        estado = 4;
                        term += currentChar;
                    }
                    break;

                case 6:
                    token = new Token();
                    token.setType(Token.TK_DECLARACAOINTEIRO);
                    token.setText(term);
                    back();
                    return token;

                case 4:
                    if (isDigit(currentChar)) {
                        estado = 4;
                        term += currentChar;
                    } else if (isSpace(currentChar)) {
                        estado = 5;
                        term += currentChar;
                    }
                case 5:
                    token = new Token();
                    token.setType(Token.TK_DECLARACAOREAL);
                    token.setText(term);
                    back();
                    return token;
            }
        }

    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isChar(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private boolean isOperator(char c) {
        return c == '>' || c == '<' || c == '=' || c == '!';
    }

    private boolean isSpace(char c) {
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    private boolean isDoisPontos(char c) {
        return c == ':';
    }

    private boolean isAtribuicao(char c) {
        return c == '=';
    }

    private boolean isP(char c) {
        return c == 'p';
    }

    private boolean isR(char c) {
        return c == 'r';
    }

    private boolean isI(char c) {
        return c == 'i';
    }

    private boolean isN(char c) {
        return c == 'n';
    }

    private boolean isT(char c) {
        return c == 't';
    }

    private boolean isPonto(char c) {
        return c == '.';
    }

    private boolean isAbreParen(char c) {
        return c == '(';
    }

    private boolean isFechaParen(char c) {
        return c == ')';
    }

    private boolean isAspast(char c) {
        return c == '\"';
    }

    private char nextChar() {
        return content[pos++];
    }

    private boolean isEOF() {
        return pos == content.length;
    }

    private void back() {
        pos--;
    }

}
