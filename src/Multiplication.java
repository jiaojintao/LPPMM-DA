import java.math.BigInteger;
import java.util.Random;

public class Multiplication {
	int aLen;
	int bLen;
	int repeat;
	BigInteger[] a,b;


	public Multiplication(int aLen, int bLen,int repeat) {
		this.aLen=aLen;
		this.bLen=bLen;
		this.repeat=repeat;

        a=new BigInteger[repeat];
        b=new BigInteger[repeat];
		Random random=new Random();

        for(int i=0;i<repeat;i++) {
        	a[i]=new BigInteger(aLen,random);
        	b[i]=new BigInteger(bLen,random);
        }

	}

    public void executeMultiplication() {
		for(int i=0;i<repeat;i++) {
	        BigInteger resultWithoutModulus = a[i].multiply(b[i]);
		}
    }


	public static void main(String[] args) {
		long ms;
		Multiplication multiplication = new Multiplication(160,160,100);
    	ms=System.currentTimeMillis();
    	multiplication.executeMultiplication();
		System.out.println("Multiplication time cost:"+(System.currentTimeMillis()-ms)*1.0/multiplication.repeat);

	}

}
