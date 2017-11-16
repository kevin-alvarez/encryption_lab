import models.Encrypter;
import views.MainView;

public class Main{
  public static void main(String[] args) {
    Encrypter crypt = new Encrypter();
    MainView mv = new MainView();

    String plainText = "Catching Multiple Exception Types and Rethrowing Exceptions with Improved Type Checking";
    int blockSize = 64;
    int iterations = 16;
    int[][] bytesPlainText;

    int[][] bytesEncryptedText;
    String encryptedText;

    int[][] bytesOriginalText;
    String originalText;


    bytesPlainText = crypt.begin(plainText, blockSize, iterations);
    bytesEncryptedText = crypt.encrypt(bytesPlainText);
    encryptedText = crypt.createString(bytesEncryptedText, blockSize);
    System.out.println(encryptedText); //
    bytesOriginalText = crypt.decrypt(bytesEncryptedText);
    originalText = crypt.createString(bytesOriginalText, blockSize);
    System.out.println(originalText); //
  }
}
