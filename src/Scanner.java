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
            System.out.println("------LEITURA-------");
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

        while (true) {
            currentChar = nextChar();
            if (isEOF()) {
                return null;
            }
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

                    if (isI(currentChar)) {
                        estado = 10;
                        term += currentChar;
                    } else if (isChar(currentChar)) {
                        estado = 9;
                        term += currentChar;
                    } // else if(!isChar(currentChar)){
                      // throw new ScannerException("AQ"); SEMPRE CAI AQ POR ALGUM MOTIVO
                      // }
                    break;
                case 9:
                    if (isChar(currentChar) || isDigit(currentChar)) {
                        estado = 9;
                        term += currentChar;
                    }else if (isAtribuicao(currentChar)){
                        term+= currentChar;
                        estado = 14;
                    }
                    // } else if (isAtribuicao(currentChar)) {
                    //     // estado = 2;
                    //     estado = 14;
                    //     term += currentChar;
                    //     System.out.println("Texto do Token: =");
                    //     System.out.println("Tipo do Token: 7\n");
                    // } 
                    else if (isF(currentChar)) {
                        int substate = 0;
                        term += currentChar;
                    }
                    break;
                case 2:
                    if (isDigit(currentChar)) {
                        estado = 7;
                        term += currentChar;
                    } // else{
                      // throw new ScannerException("Unrecognized SYMBOL");
                      // }
                    break;
                case 7:
                    if (isDigit(currentChar)) {
                        estado = 7;
                        term += currentChar;
                    } else if (isPonto(currentChar)) {
                        estado = 4;
                        term += currentChar;
                    } else if (isSpace(currentChar)) {
                        estado = 6;
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
                        estado = 8;
                        term += currentChar;
                    }
                    break;
                case 8:
                    if (isDigit(currentChar)) {
                        estado = 8;
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
                // ---DECLARAÇÃO INTEIRO E REAL E ATRIBUIÇÃO

                case 10:
                    if (isF(currentChar)) {
                        estado = 11;
                        term += currentChar;
                    } else if (isChar(currentChar) || isDigit(currentChar)) {
                        estado = 9;
                        term += currentChar;
                    }
                    break;
                case 11:
                    if (isAbreParen(currentChar)) {
                        estado = 12;
                        term += currentChar;
                    } else if (isChar(currentChar)) {
                        estado = 9;
                    }
                    // else is numero
                    break;
                case 12:
                    if (isExclamation(currentChar)) {
                        estado = 1;
                        term += currentChar;
                    } else if (isF(currentChar)) {
                        estado = 1;
                        term += currentChar;
                    }
                    // else is numero
                    break;
                case 13:
                    if (isExclamation(currentChar) || isChar(currentChar)) {
                        estado = 1;
                        term += currentChar;
                    } else if (isDigit(currentChar)) {

                    }
                case 14:
                if(isDigit(currentChar)){
                        term += currentChar;
                        estado = 7;
                        System.out.println("Texto do Token: =");
                        System.out.println("Tipo do Token: 7\n");

                 }else if(isEqual(currentChar)){
                     
                }
                break;

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
        return c == '>' || c == '<';
    }

    private boolean isExclamation(char c) {
        return c == '!';
    }

    private boolean isEqual(char c) {
        return c == '=';
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

    private boolean isF(char c) {
        return c == 'f';
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
