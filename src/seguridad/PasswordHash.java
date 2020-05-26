package seguridad;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.codec.binary.Hex;

public class PasswordHash {
	
	private String hashedString;
	
	public PasswordHash(String contrasena) {
		char[] passwordChars = contrasena.toCharArray();
        byte[] saltBytes = "Cambiar".getBytes(); //cada usuario debe tener su propia sal aleatoria
        byte[] hashedBytes = hashPassword(passwordChars, saltBytes);
        this.hashedString = Hex.encodeHexString(hashedBytes);
	}
	
	private byte[] hashPassword(final char[] password, final byte[] salt) {
		
		final int iterations = 10000;
	    final int keyLength = 512; 
	    
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512"); 
            PBEKeySpec spec = new PBEKeySpec( password, salt, iterations, keyLength );
            SecretKey key = skf.generateSecret(spec);
            byte[] res = key.getEncoded();
            return res;
        } catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            throw new RuntimeException( e );
        }
    }
	
	public String getHash() {
		return this.hashedString;
	}
}
