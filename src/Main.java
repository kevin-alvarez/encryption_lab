import models.Encrypter;
import views.MainView;

public class Main{
  public static void main(String[] args) {
    Encrypter crypt = new Encrypter();
    MainView mv = new MainView(crypt);
  }
}
