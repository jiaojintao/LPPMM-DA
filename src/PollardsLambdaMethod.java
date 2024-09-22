import java.math.BigInteger;

public class PollardsLambdaMethod {

    // 随机函数 f(x)
    private static BigInteger[] f(BigInteger x, BigInteger a, BigInteger b, BigInteger g, BigInteger h, BigInteger p) {
        if (x.mod(BigInteger.valueOf(3)).equals(BigInteger.ZERO)) {
            return new BigInteger[]{x.multiply(x).mod(p), a.multiply(BigInteger.TWO).mod(p.subtract(BigInteger.ONE)), b.multiply(BigInteger.TWO).mod(p.subtract(BigInteger.ONE))};
        } else if (x.mod(BigInteger.valueOf(3)).equals(BigInteger.ONE)) {
            return new BigInteger[]{x.multiply(g).mod(p), a.add(BigInteger.ONE).mod(p.subtract(BigInteger.ONE)), b};
        } else {
            return new BigInteger[]{x.multiply(h).mod(p), a, b.add(BigInteger.ONE).mod(p.subtract(BigInteger.ONE))};
        }
    }

    // Pollard's Lambda Method 主函数
    public static BigInteger pollardsLambda(BigInteger g, BigInteger h, BigInteger p) {
        BigInteger x = BigInteger.ONE;
        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ZERO;

        BigInteger X = x;
        BigInteger A = a;
        BigInteger B = b;

        for (int i = 1; i < p.intValue(); i++) {
            BigInteger[] res1 = f(x, a, b, g, h, p);
            x = res1[0];
            a = res1[1];
            b = res1[2];

            BigInteger[] res2 = f(X, A, B, g, h, p);
            res2 = f(res2[0], res2[1], res2[2], g, h, p);
            X = res2[0];
            A = res2[1];
            B = res2[2];

            if (x.equals(X)) {
                BigInteger r = b.subtract(B).mod(p.subtract(BigInteger.ONE));
                if (r.equals(BigInteger.ZERO)) {
                    return null; // 无法找到离散对数
                }
                return (A.subtract(a)).multiply(r.modInverse(p.subtract(BigInteger.ONE))).mod(p.subtract(BigInteger.ONE));
            }
        }
        return null; // 没有找到离散对数
    }


    public static void executePollardsLambda(int repeat) {
        BigInteger g = BigInteger.valueOf(2); // 基数
        BigInteger h = BigInteger.valueOf(22); // h = g^x mod p
        BigInteger p = BigInteger.valueOf(29); // 素数

    	for(int i=0;i<repeat;i++) {
    		 BigInteger log = pollardsLambda(g, h, p);
    	}

    }

    public static void main(String[] args) {
		long ms;
    	ms=System.currentTimeMillis();
    	executePollardsLambda(100);
		System.out.println("Paillier Encrypt time cost:"+(System.currentTimeMillis()-ms)*1.0/100);


    }
}
