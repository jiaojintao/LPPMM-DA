import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class ElGamal {
    
    private static final SecureRandom random = new SecureRandom();
    private BigInteger p, g, y, x;
    int bitLength;
    int repeat;
    int bglen;
    BigInteger[] msg;
    BigInteger[][] c;

    // Constructor to generate keys
    public ElGamal(int bitLength, int bglen, int repeat) {
        this.bitLength = bitLength;

        this.repeat=repeat;
        this.bglen=bglen;
    	
        // Generate large prime number p
        p = BigInteger.probablePrime(bitLength, random);
        // Select a generator g
        g = new BigInteger(bitLength - 1, random).mod(p);
        // Private key x, a random integer < p
        x = new BigInteger(bitLength - 1, random).mod(p.subtract(BigInteger.ONE));
        // Public key y = g^x mod p
        y = g.modPow(x, p);
        
        msg=new BigInteger[repeat];
        c=new BigInteger[repeat][];
		Random random=new Random();

        for(int i=0;i<repeat;i++) {
        	msg[i]=new BigInteger(bglen,random);
        }
    }

    // Encryption: Given public key (p, g, y) and plaintext message m
    public BigInteger[] encrypt(BigInteger m) {
        BigInteger k = new BigInteger(p.bitLength() - 1, random).mod(p.subtract(BigInteger.ONE));
        BigInteger c1 = g.modPow(k, p);
        BigInteger c2 = m.multiply(y.modPow(k, p)).mod(p);
        return new BigInteger[] {c1, c2};
    }

    // Decryption: Given private key x and ciphertext (c1, c2)
    public BigInteger decrypt(BigInteger[] cipher) {
        BigInteger c1 = cipher[0];
        BigInteger c2 = cipher[1];
        BigInteger s = c1.modPow(x, p);
        return c2.multiply(s.modInverse(p)).mod(p);
    }

    // Getters for the public key
    public BigInteger getP() {
        return p;
    }

    public BigInteger getG() {
        return g;
    }

    public BigInteger getY() {
        return y;
    }

    public void executeEncrypt() {
		for(int i=0;i<repeat;i++) {
			c[i]=encrypt(msg[i]);
		}
    }
    
    public void executeDecrypt() {
		for(int i=0;i<repeat;i++) {
			decrypt(c[i]);
		}
    }
    
    
    public static void main(String[] args) {
		long ms;
		ElGamal elGamal = new ElGamal(1024,100,10);
    	ms=System.currentTimeMillis();
    	elGamal.executeEncrypt();
		System.out.println("Paillier Encrypt time cost:"+(System.currentTimeMillis()-ms)*1.0/elGamal.repeat);
		
    	ms=System.currentTimeMillis();
    	elGamal.executeDecrypt();
		System.out.println("Paillier Decrypt time cost:"+(System.currentTimeMillis()-ms)*1.0/elGamal.repeat);

    }
}
