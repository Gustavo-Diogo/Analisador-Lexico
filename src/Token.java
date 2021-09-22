/**
 * Token
 */
public class Token {
    //public static final int TK_IDENT = 0;
    //public static final int TK_NUMERO = 1;
    public static final int TK_OPERADOR = 2;
    //public static final int TK_PONTUACAO = 3;
    //public static final int TK_ASSIGN = 4;
    public static final int TK_DECLARACAOREAL = 5;
    public static final int TK_DECLARACAOINTEIRO = 6;
    public static final int TK_ATRIBUICAO = 7;
    public static final int TK_CONDICIONAL = 8;
    public static final int TK_OUTPUT = 9;
    public static final int TK_ARITMETICO = 10;

    private int type;
    private String text;
    
    public Token(int type, String text){
        super();
        this.type = type;
        this.text = text;
    }

    public Token(){
        super();
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString(){
        return "Token [type=" + type + ", text=" + text + "]";
    }
}