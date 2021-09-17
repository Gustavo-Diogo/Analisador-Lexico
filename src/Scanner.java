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
                        boolean doWhile = true;
                        do {

                            switch (subEstado) {
                                case 0:
                                    if (isR(currentChar)) {
                                        subEstado = 1;
                                        term += currentChar;
                                        currentChar = nextChar();
                                    } else if (isDigit(currentChar) || isChar(currentChar)) {
                                        doWhile = false;
                                        estado = 1;
                                    }
                                    break;
                                case 1:
                                    if (isI(currentChar)) {
                                        subEstado = 2;
                                        term += currentChar;
                                        currentChar = nextChar();

                                    } else if (isDigit(currentChar) || isChar(currentChar)) {
                                        doWhile = false;
                                        estado = 1;
                                    }
                                    break;

                                case 2:
                                    if (isN(currentChar)) {
                                        subEstado = 3;
                                        term += currentChar;
                                        currentChar = nextChar();

                                    } else if (isDigit(currentChar) || isChar(currentChar)) {
                                        doWhile = false;
                                        estado = 1;
                                    }
                                    break;
                                case 3:
                                    if (isT(currentChar)) {
                                        subEstado = 4;
                                        term += currentChar;
                                        currentChar = nextChar();

                                    } else if (isDigit(currentChar) || isChar(currentChar)) {
                                        doWhile = false;
                                        estado = 1;
                                    }
                                    break;
                                case 4:
                                    if (isAbreParen(currentChar)) {
                                        subEstado = 5;
                                        term += currentChar;
                                        currentChar = nextChar();

                                    } else if (isDigit(currentChar) || isChar(currentChar)) {
                                        doWhile = false;
                                        estado = 1;
                                    }
                                    break;

                                case 5:
                                    if (isAspast(currentChar)) {
                                        subEstado = 6;
                                        term += currentChar;
                                        currentChar = nextChar();

                                    } else if (isDigit(currentChar) || isChar(currentChar)) {
                                        doWhile = false;
                                        estado = 1;
                                    }
                                    break;
                                case 6:
                                    if (isDigit(currentChar) || isChar(currentChar)) {
                                        subEstado = 6;
                                        term += currentChar;
                                        currentChar = nextChar();
                                    }
                                    if (isAspast(currentChar)) {
                                        subEstado = 7;
                                        term += currentChar;
                                        currentChar = nextChar();
                                    }
                                    break;

                                case 7:
                                    if (isFechaParen(currentChar)) {
                                        subEstado = 8;
                                        term += currentChar;
                                        currentChar = nextChar();
                                    } else {
                                        throw new Exception();
                                    }
                                    break;
                                case 8:

                                    if (isSpace(currentChar)) {
                                        term += currentChar;
                                        currentChar = nextChar();
                                        subEstado = 9;

                                    } else {
                                        throw new Exception();
                                    }
                                    break;
                                case 9:
                                    token = new Token();
                                    token.setType(Token.TK_IDENT);
                                    token.setText(term);
                                    back();
                                    return token;
                            }
                        } while (doWhile == true);
                    } else if (isChar(currentChar)) {
                        estado = 1;
                        term += currentChar;
                    }
                    break;
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
                    // } else if (isAtribuicao(currentChar)) {
                    //     estado = 14;
                    //     term += currentChar;
                    
                    } else if (isFechaParen(currentChar)) {
                        estado = 18;
                        term += currentChar;
                    }else if (isAtribuicao(currentChar)) {
                    estado = 2;
                    // estado = 14;
                    term += currentChar;
                    System.out.println("Texto do Token: =");
                    System.out.println("Tipo do Token: 7\n");
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
                    } else {
                        throw new ScannerException("Unrecognized SYMBOL");
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
                    break;
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
                        term += currentChar;
                        
                    }
                    // else is numero
                    break;
                case 12:
                // Acho q está errado esse if
                    if (isExclamation(currentChar)) {
                        estado = 1;
                        term += currentChar;
                    } else if (isChar(currentChar)) {
                        estado = 20;
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
                    break;
                case 14:
                    if (isDigit(currentChar)) {
                        estado = 7;
                        term += currentChar;
                        // System.out.println("Texto do Token: =");
                        // System.out.println("Tipo do Token: 7\n");

                    } else if (isEqual(currentChar)) {
                        term += currentChar;
                        estado = 15;
                    } else {
                        throw new ScannerException("Unrecognized SYMBOL");
                    }
                    break;

                case 15:
                    if (isChar(currentChar)) {
                        estado = 16;
                        term += currentChar;
                    } else if (isDigit(currentChar)) {
                        estado = 17;
                        term += currentChar;
                    }
                    break;
                case 16:
                    if (isChar(currentChar) || isDigit(currentChar)) {
                        estado = 16;
                        term += currentChar;
                    }else if (isFechaParen(currentChar)){
                        estado = 18;
                        term += currentChar;
                    }
                    break;
                case 17:
                    if (isDigit(currentChar)) {
                        estado = 17;
                        term += currentChar;
                    } else if (isPonto(currentChar)) {
                        estado = 18;
                        term += currentChar;
                    }
                    break;
                case 18:
                    if (isDigit(currentChar)) {
                        estado = 18;
                        term += currentChar;
                    } else if (isDoisPontos(currentChar)) {
                        estado = 19; // QUALQUER COISA MUDAR PARA CASO 0 DO GUSTAVO
                        term += currentChar;
                    }
                    break;
                case 20:
                if (isChar(currentChar) || isDigit(currentChar)) {
                    estado = 20;
                    term += currentChar;
                } else if (isAtribuicao(currentChar)) {
                    estado = 14;
                    term += currentChar;
                
                } else if (isFechaParen(currentChar)) {
                    estado = 18;
                    term += currentChar;
                }
                break;
                case 19:
                    token = new Token();
                    token.setType(Token.TK_CONDICIONAL);
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