import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;


public class Paillier {
    private final BigInteger p, q, lambda, n, nsquare, g;
    private final int bitLength;
    private final SecureRandom random = new SecureRandom();
    int repeat;
    int bglen;
    BigInteger[] msg,c;


    // Constructor to generate keys
    public Paillier(int bitLength, int bglen, int repeat) {
        this.bitLength = bitLength;
        this.bglen=bglen;
        this.repeat=repeat;

        // Generate two large primes p and q
        p = new BigInteger(bitLength / 2, 100, random);
        q = new BigInteger(bitLength / 2, 100, random);

        // Calculate n = p * q
        n = p.multiply(q);
        nsquare = n.multiply(n);

        // Choose g where g = n + 1
        g = n.add(BigInteger.ONE);

        // Calculate lambda = lcm(p-1, q-1)
        lambda = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE))
                .divide(p.subtract(BigInteger.ONE).gcd(q.subtract(BigInteger.ONE)));

        msg=new BigInteger[repeat];
        c=new BigInteger[repeat];
		Random random=new Random();

        for(int i=0;i<repeat;i++) {
        	msg[i]=new BigInteger(bglen,random);
        }

    }

    // Encryption: Given public key (n, g) and plaintext message m
    public BigInteger encrypt(BigInteger m) {
        // Random r where r < n and gcd(r, n) = 1
        BigInteger r = new BigInteger(bitLength, random);
        while (r.compareTo(n) >= 0 || !r.gcd(n).equals(BigInteger.ONE)) {
            r = new BigInteger(bitLength, random);
        }

        // c = (g^m * r^n) mod n^2
        return g.modPow(m, nsquare).multiply(r.modPow(n, nsquare)).mod(nsquare);
    }

    // Decryption: Given private key lambda and ciphertext c
    public BigInteger decrypt(BigInteger c) {
        // L function: L(u) = (u - 1) / n
        BigInteger u = c.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n);
        BigInteger l = g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n);

        // m = L(c^lambda mod n^2) * L(g^lambda mod n^2)^-1 mod n
        return u.multiply(l.modInverse(n)).mod(n);
    }

    // Getters for the public key
    public BigInteger getN() {
        return n;
    }

    public BigInteger getG() {
        return g;
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
        Paillier paillier = new Paillier(512,10,100);
    	ms=System.currentTimeMillis();
    	paillier.executeEncrypt();
		System.out.println("Paillier Encrypt time cost:"+(System.currentTimeMillis()-ms)*1.0/paillier.repeat);

    	ms=System.currentTimeMillis();
    	paillier.executeDecrypt();
		System.out.println("Paillier Decrypt time cost:"+(System.currentTimeMillis()-ms)*1.0/paillier.repeat);


    }
}
