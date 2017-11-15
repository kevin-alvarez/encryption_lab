package views;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame implements ActionListener{
  private JTextField plainTextField;
  private JTextField keyTextField;
  private JComboBox blockSizeCB;
  private JButton encryptButton;
  private JLabel encryptedText;
  private String[] blockSizes = {"8", "16", "32", "64", "128", "256", "512"};

  public MainView(){
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
    JLabel lblKey = new JLabel("Insert Key value: ");
    JLabel lblBlockSize = new JLabel("Select Block Size: ");
    JLabel lblEncrypt = new JLabel("Encrypted text: ");
    this.plainTextField = new JTextField();
    this.keyTextField = new JTextField();
    this.blockSizeCB = new JComboBox(this.blockSizes);
    this.encryptedText = new JLabel("Encrypted text here");

    GroupLayout layout = new GroupLayout(this.getContentPane());
    this.getContentPane().setLayout(layout);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);
    layout.setHorizontalGroup(layout.createSequentialGroup()
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(lblPlainText)
        .addComponent(lblKey)
        .addComponent(lblEncrypt)
        .addComponent(this.encryptButton))
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(this.plainTextField)
        .addGroup(layout.createSequentialGroup()
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(this.keyTextField))
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(lblBlockSize))
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(this.blockSizeCB)))
        .addComponent(this.encryptedText))
    );
    layout.setVerticalGroup(layout.createSequentialGroup()
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(lblPlainText)
        .addComponent(plainTextField))
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(lblKey)
        .addGroup(layout.createSequentialGroup()
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(this.keyTextField)
            .addComponent(lblBlockSize)
            .addComponent(this.blockSizeCB))))
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(lblEncrypt)
        .addComponent(this.encryptedText))
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(this.encryptButton))
    );
    this.setVisible(true);
  }

  private void setButtons(){
    this.encryptButton = new JButton("Encrypt");
    this.encryptButton.addActionListener(this);
  }

  public void showView(boolean show){
    this.setVisible(show);
  }

  @Override
  public void actionPerformed(ActionEvent e){
    this.encryptedText.setText("I Changed...");
  }
}