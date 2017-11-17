package views;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import models.Encrypter;

public class MainView extends JFrame implements ActionListener{
  //Encrypter atributes
  private Encrypter encrypter;
  private int[][] bytesPlainText;  
  private int[][] bytesEncryptedText;  
  private int[][] bytesOriginalText;
  private int iterations;
  private int blockSize;
  //View atributes
  private JTextField plainTextField;
  private JTextField iterationTextField;
  private JComboBox blockSizeCB;
  private JButton encryptButton;
  private JButton decryptButton;
  private JLabel encryptedText;
  private JLabel decryptedText;
  private String[] blockSizes = {"16", "32", "64", "128", "256", "512"};

  public MainView(Encrypter encrypter){
    this.encrypter = encrypter;
    this.setWindow();
    this.setButtons();
    this.render();
  }

  private void setWindow(){
    this.setTitle("Text Encrypter");
    this.setSize(800, 600);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void render(){
    JLabel lblPlainText = new JLabel("Insert Plain text: ");
    JLabel lblKey = new JLabel("Insert iterations: ");
    JLabel lblBlockSize = new JLabel("Select Block Size: ");
    JLabel lblEncrypt = new JLabel("Encrypted text: ");
    JLabel lblDecrypt = new JLabel("Decrypted text: ");
    this.plainTextField = new JTextField();
    this.iterationTextField = new JTextField("16");
    this.blockSizeCB = new JComboBox(this.blockSizes);
    this.encryptedText = new JLabel();
    this.encryptedText.setBorder(new LineBorder(Color.GRAY));
    this.decryptedText = new JLabel();
    this.decryptedText.setBorder(new LineBorder(Color.GRAY));

    GroupLayout layout = new GroupLayout(this.getContentPane());
    this.getContentPane().setLayout(layout);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);
    layout.setHorizontalGroup(layout.createSequentialGroup()
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(lblPlainText)
        .addComponent(lblKey)
        .addComponent(lblEncrypt)
        .addComponent(this.encryptButton)
        .addComponent(lblDecrypt)
        .addComponent(this.decryptButton))
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(this.plainTextField)
        .addGroup(layout.createSequentialGroup()
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(this.iterationTextField))
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(lblBlockSize))
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(this.blockSizeCB)))
        .addComponent(this.encryptedText)
        .addComponent(this.decryptedText))
    );
    layout.setVerticalGroup(layout.createSequentialGroup()
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(lblPlainText)
        .addComponent(plainTextField))
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(lblKey)
        .addGroup(layout.createSequentialGroup()
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(this.iterationTextField)
            .addComponent(lblBlockSize)
            .addComponent(this.blockSizeCB))))
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(lblEncrypt)
        .addComponent(this.encryptedText))
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(this.encryptButton))
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(lblDecrypt)
        .addComponent(this.decryptedText))
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(this.decryptButton))
    );
    this.setVisible(true);
  }

  private void setButtons(){
    this.encryptButton = new JButton("Encrypt");
    this.encryptButton.addActionListener(this);

    this.decryptButton = new JButton("Decrypt");
    this.decryptButton.addActionListener(this);
  }

  public void showView(boolean show){
    this.setVisible(show);
  }

  @Override
  public void actionPerformed(ActionEvent ae){
    if (ae.getSource().equals(this.encryptButton)){
      try {
        this.iterations = Integer.parseInt(this.iterationTextField.getText());
        this.blockSize = Integer.parseInt(this.blockSizeCB.getSelectedItem().toString());
  
        this.bytesPlainText = this.encrypter.begin(this.plainTextField.getText(), this.blockSize, this.iterations);
        this.bytesEncryptedText = this.encrypter.encrypt(this.bytesPlainText);
        this.encryptedText.setText(this.encrypter.createString(this.bytesEncryptedText, this.blockSize));
      } catch (Exception e) {
        JOptionPane.showConfirmDialog(this, "Error ocurred on parameters ingresed", "Something went wrong", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
      }
    }else if(ae.getSource().equals(this.decryptButton)){
      this.bytesOriginalText = this.encrypter.decrypt(this.bytesEncryptedText);
      this.decryptedText.setText(this.encrypter.createString(this.bytesOriginalText, this.blockSize));
    }
  }
}
