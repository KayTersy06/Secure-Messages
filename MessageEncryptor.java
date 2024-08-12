package za.ac.tut.encryption;

/**
 *
 * @author kayte
 */
public class MessageEncryptor {
    private static final int SHIFT = 3;
    
    public String encrypt(String message){
        StringBuilder encryptedMessage = new StringBuilder();
        for (char c : message.toCharArray()) {
            if(Character.isLetter(c))
            {
                char base = Character.isAlphabetic(c) ? 'A': 'a';
                encryptedMessage.append((char) ((c - base + SHIFT) % 26 + base));
            }
            else
            {
                encryptedMessage.append(c);
            }
        }
        
        return encryptedMessage.toString();
    }
}
