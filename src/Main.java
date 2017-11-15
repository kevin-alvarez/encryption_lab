import models.Encrypter;
import views.MainView;

public class Main{
  public static void main(String[] args){
    Encrypter crypt = new Encrypter();
    MainView mv = new MainView();

    // Se llama a beginEncryption(String plainText, int blockSize, int iterations);
    // crypt.beginEncryption("texto", 64, 16);
  }
}
