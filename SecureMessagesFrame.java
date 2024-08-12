package za.ac.tut.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import za.ac.tut.encryption.MessageEncryptor;

/**
 *
 * @author kayte
 */
public class SecureMessagesFrame extends JFrame {
    //menu
    private JMenuBar menuBar;
    private JMenu fileMenu;
    
    private JMenuItem openFileMenuItem;
    private JMenuItem encryptMessageMenuItem;
    private JMenuItem saveEncryptedMessageMenuItem;
    private JMenuItem clearMenuItem;
    private JMenuItem exitMenuItem;
    
    //panels
    private JPanel headingPnl;
    private JPanel plainMessagePnl;
    private JPanel encryptedMessagePnl;
    private JPanel messageCombinedPnl;
    private JPanel mainPnl;
    
    //text area
    private JTextArea plainMessageTxtArea;
    private JTextArea encryptedMessageTxtArea;
    
    //scrollpane
    private JScrollPane plainMessageSclPn;
    private JScrollPane encryptedMessageSclPn;
    
    //label
    private JLabel headingLbl;
    
    MessageEncryptor encryptor;
    
    //create gui
    public SecureMessagesFrame() {
        setTitle("Secure Messages");
        setSize(680, 390);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        //create a menu bar
        menuBar = new JMenuBar();
        
        fileMenu = new JMenu("File");
        
        //file menu items
        openFileMenuItem = new JMenuItem("Open file...");
        openFileMenuItem.addActionListener(new OpenFileMenuItemListener());
        
        encryptMessageMenuItem = new JMenuItem("Encrypt message...");
        encryptMessageMenuItem.addActionListener(new EncryptMessageFileItemListener());
        
        saveEncryptedMessageMenuItem = new JMenuItem("Save encrypted message...");
        saveEncryptedMessageMenuItem.addActionListener(new SaveEncryptedMessageMenuItemListener());
        
        clearMenuItem = new JMenuItem("Clear");
        clearMenuItem.addActionListener(new ClearMenuItemListener());
        
        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ExitMenuItemListener());
        
        //add file items
        fileMenu.add(openFileMenuItem);
        fileMenu.add(encryptMessageMenuItem);    
        fileMenu.add(saveEncryptedMessageMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(clearMenuItem);    
        fileMenu.add(exitMenuItem);
        
        //add menu to menu bar
        menuBar.add(fileMenu);
        
        setJMenuBar(menuBar);
        
        //create panels
        headingPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        plainMessagePnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        encryptedMessagePnl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        messageCombinedPnl = new JPanel(new GridLayout(1, 2));
        mainPnl = new JPanel(new BorderLayout());
        
        //create label
        headingLbl = new JLabel("Message Encryptor");
        headingLbl.setFont(new Font(Font.SANS_SERIF, Font.ITALIC + Font.BOLD, 20));
        headingLbl.setForeground(Color.BLUE);
        headingLbl.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        
        //create text areas
        plainMessageTxtArea = new JTextArea(15, 30);
        plainMessageTxtArea.setEditable(false);
        
        plainMessageSclPn = new JScrollPane(plainMessageTxtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        plainMessageSclPn.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1), "Plain message"));
        
        
        
        encryptedMessageTxtArea = new JTextArea(15,30);
        encryptedMessageTxtArea.setEditable(false);
        
        encryptedMessageSclPn = new JScrollPane(encryptedMessageTxtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        encryptedMessageSclPn.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1), "Encrypted message"));
        
        
        //add components to panels
        headingPnl.add(headingLbl);
        
        plainMessagePnl.add(plainMessageSclPn);
        encryptedMessagePnl.add(encryptedMessageSclPn);
        
        messageCombinedPnl.add(plainMessagePnl);
        messageCombinedPnl.add(encryptedMessagePnl);
        
        mainPnl.add(headingPnl, BorderLayout.NORTH);
        mainPnl.add(messageCombinedPnl, BorderLayout.CENTER);
        
        add(mainPnl);
        
        setVisible(true);
    }
    
    public class OpenFileMenuItemListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int results = fileChooser.showOpenDialog(SecureMessagesFrame.this);
            if(results == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                try(FileReader reader = new FileReader(file)){
                    StringBuilder sb = new StringBuilder();
                    int c;
                    while((c = reader.read()) != -1){
                        sb.append((char) c);
                    }
                    plainMessageTxtArea.setText(sb.toString());
                    
                    reader.close();
                }
                catch (IOException ioe){
                    JOptionPane.showMessageDialog(SecureMessagesFrame.this, "Error reading file: " + ioe.getMessage());
                }
            }
        }
        
    }
    
    public class EncryptMessageFileItemListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                encryptor = new MessageEncryptor();
                String plainMessage = plainMessageTxtArea.getText();
                String encryptedMessage = encryptor.encrypt(plainMessage);
                encryptedMessageTxtArea.setText(encryptedMessage);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(rootPane, "Error encrypting message: " + ex.getMessage());
            }
        }
        
    }
    
    public class SaveEncryptedMessageMenuItemListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int results = fileChooser.showSaveDialog(SecureMessagesFrame.this);
            if(results == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                try(FileWriter writer = new FileWriter(file)) {
                    BufferedWriter bW = new BufferedWriter(writer);
                   bW.write(encryptedMessageTxtArea.getText());
                   JOptionPane.showMessageDialog(SecureMessagesFrame.this, "Encrypted message saved successfully");
                   
                   bW.close();
                } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(SecureMessagesFrame.this, "Error saving encrypted message: " + ioe.getMessage());
                }
            }
        }
        
    }
    
    public class ClearMenuItemListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            plainMessageTxtArea.setText("");
            encryptedMessageTxtArea.setText("");
        }
        
    }
    
    public class ExitMenuItemListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
        
    }
}
