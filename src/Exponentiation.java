import java.math.BigInteger;
import java.util.Random;


public class Exponentiation {
	
	int baseLen;
	int exponentLen;
	int repeat;
	BigInteger[] base,exp;
	
	
	public Exponentiation(int baseLen, int exponentLen,int repeat) {
		this.baseLen=baseLen;
		this.exponentLen=exponentLen;
		this.repeat=repeat;		
		
        base=new BigInteger[repeat];
        exp=new BigInteger[repeat];
		Random random=new Random();

        for(int i=0;i<repeat;i++) {
        	base[i]=new BigInteger(baseLen,random);
        	exp[i]=new BigInteger(exponentLen,random);
        }
		
	}
	
    public void executeExponentiation() {
		for(int i=0;i<repeat;i++) {
	        BigInteger resultWithoutModulus = base[i].pow(exp[i].intValue());
		}
    }
	

	public static void main(String[] args) {
		long ms;
		Exponentiation exponentiation = new Exponentiation(20,10,100);
    	ms=System.currentTimeMillis();
    	exponentiation.executeExponentiation();
		System.out.println("Exponentiation time cost:"+(System.currentTimeMillis()-ms)*1.0/exponentiation.repeat);

	}

}
