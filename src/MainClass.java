/**
 * MainClass
 */
public class MainClass {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner("arquivo1.txt");
            Token token = null;

            do {
                token = sc.le_token();
                if (token != null) {
                    System.out.println("Texto do Token: "+ token.getText() +"\nTipo do Token: "+token.getType()+"\n");
                }
                else{
                    break;
                }
            } while (true);
        } catch (ScannerException ex) {
            System.out.println("Lexical ERROR " + ex.getLocalizedMessage());
        } catch (Exception ex) {
            //System.out.println(token);
            System.out.println("Generic Error!!");
            ex.printStackTrace();
        }
    }
}
