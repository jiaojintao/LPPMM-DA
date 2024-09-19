import java.security.NoSuchAlgorithmException;

public class Test {
	
	public static void executeSM(int n,int w, int repeat, double[][][][][] data,int phasepos,int kpos, int npos, int wpos) throws NoSuchAlgorithmException {
		long ms;//timestamp
		double cost;

		int rBit=160;
		int qBit=512;
		
		int paillierBitLength=512;
		int elGamalbitLength=512;
		int schemepos;

		//time cost of SM
		System.out.println("time cost of SM");
		//our scheme
		schemepos=0;
		Multiplication multiplication=new Multiplication(rBit,rBit,w);//aLen,bLen,repeat
		PointMultiplicationECC pointMultiplication=new PointMultiplicationECC(rBit,qBit,rBit/10,n+8);//rBit,qBit,bglen,repeat
    	MappingToPointHash mappingToPointHash=new MappingToPointHash(rBit,qBit,3*n+6);//rBit,qBit,repeat
    	
    	ms=System.currentTimeMillis();
    	for(int i=0;i<repeat;i++) {
        	multiplication.executeMultiplication();
        	pointMultiplication.executePointMultiplication();
        	mappingToPointHash.executemapToPoint();
    	}
    	
    	cost=(System.currentTimeMillis()-ms)*1.0/repeat;
		System.out.println("	our:"+cost);
		data[phasepos][schemepos][kpos][npos][wpos]=cost;
		
		
		//[7]
		schemepos++;
    	mappingToPointHash=new MappingToPointHash(rBit,qBit,2*n+2);//rBit,qBit,repeat
		pointMultiplication=new PointMultiplicationECC(rBit,qBit,rBit/10,2);//rBit,qBit,bglen,repeat

    	ms=System.currentTimeMillis();
    	for(int i=0;i<repeat;i++) {
	    	mappingToPointHash.executemapToPoint();   		
	     	pointMultiplication.executePointMultiplication();
    	}
    	
    	cost=(System.currentTimeMillis()-ms)*1.0/repeat;
		System.out.println("	[7]:"+cost);
		data[phasepos][schemepos][kpos][npos][wpos]=cost;
		
		//[20]
		schemepos++;
		Exponentiation exponentiation = new Exponentiation(rBit/10,rBit/10,3*w+2);//baseLen,exponentLen,repeat
    	mappingToPointHash=new MappingToPointHash(rBit,qBit,1);//rBit,qBit,repeat
		multiplication=new Multiplication(rBit,rBit,3*w-1);//aLen,bLen,repeat

    	ms=System.currentTimeMillis();
    	for(int i=0;i<repeat;i++) {
	    	exponentiation.executeExponentiation();
	    	mappingToPointHash.executemapToPoint();
	    	multiplication.executeMultiplication();    		
    	}
    	
    	cost=(System.currentTimeMillis()-ms)*1.0/repeat;
		System.out.println("	[20]:"+cost);
		data[phasepos][schemepos][kpos][npos][wpos]=cost;

		//[21]
		schemepos++;
		multiplication=new Multiplication(rBit,rBit,w);//aLen,bLen,repeat
		exponentiation = new Exponentiation(rBit/10,rBit/10,w+1);//baseLen,exponentLen,repeat
		BilinearParing bp=new BilinearParing(rBit,qBit,1);//rBit,qBit,repeat
    	mappingToPointHash=new MappingToPointHash(rBit,qBit,2);//rBit,qBit,repeat
    	
    	ms=System.currentTimeMillis();
    	for(int i=0;i<repeat;i++) {
	    	multiplication.executeMultiplication();
	    	exponentiation.executeExponentiation();
	    	bp.executePairing();
	    	mappingToPointHash.executemapToPoint();    		
    	}
    	
    	cost=(System.currentTimeMillis()-ms)*1.0/repeat;
		System.out.println("	[21]:"+cost);
		data[phasepos][schemepos][kpos][npos][wpos]=cost;
		
		//[19]
		schemepos++;
		multiplication=new Multiplication(rBit,rBit,w);//aLen,bLen,repeat
		exponentiation = new Exponentiation(rBit/10,rBit/10,2);//baseLen,exponentLen,repeat
		ElGamal elGamal = new ElGamal(elGamalbitLength,rBit/10,1);//bitLength,bglen,repeat
		mappingToPointHash=new MappingToPointHash(rBit,qBit,1);//rBit,qBit,repeat
    	
    	ms=System.currentTimeMillis();
    	for(int i=0;i<repeat;i++) {
	     	multiplication.executeMultiplication();
	    	exponentiation.executeExponentiation();
	    	elGamal.executeEncrypt();
	    	mappingToPointHash.executemapToPoint();   		
    	}
    	
    	cost=(System.currentTimeMillis()-ms)*1.0/repeat;
		System.out.println("	[19]:"+cost);
		data[phasepos][schemepos][kpos][npos][wpos]=cost;
		
	}
	
	public static void executeGW(int n, int repeat, double[][][][][] data,int phasepos, int kpos, int npos, int wpos) throws NoSuchAlgorithmException {
		long ms;//timestamp
		double cost;

		int rBit=160;
		int qBit=512;
		
		int paillierBitLength=512;
		int elGamalbitLength=512;
		int schemepos;


		
		//time cost of GW
		System.out.println("time cost of GW");
		
		//our
		schemepos=0;
		PointMultiplicationECC pointMultiplication=new PointMultiplicationECC(rBit,qBit,rBit/10,2*n+1);//rBit,qBit,bglen,repeat
		MappingToPointHash mappingToPointHash=new MappingToPointHash(rBit,qBit,n+1);//rBit,qBit,repeat
    	
    	ms=System.currentTimeMillis();
    	for(int i=0;i<repeat;i++) {
    		pointMultiplication.executePointMultiplication();
    		mappingToPointHash.executemapToPoint();
    	}
    	
    	cost=(System.currentTimeMillis()-ms)*1.0/repeat;
		System.out.println("	our:"+cost);	
		data[phasepos][schemepos][kpos][npos][wpos]=cost;
		
		//[7]
		schemepos++;
		BilinearParing bp=new BilinearParing(rBit,qBit,2*n);//rBit,qBit,repeat
    	mappingToPointHash=new MappingToPointHash(rBit,qBit,n);//rBit,qBit,repeat
		pointMultiplication=new PointMultiplicationECC(rBit,qBit,rBit/10,n);//rBit,qBit,bglen,repeat
		
    	ms=System.currentTimeMillis();
    	for(int i=0;i<repeat;i++) {
	     	bp.executePairing();
	    	pointMultiplication.executePointMultiplication();
	    	mappingToPointHash.executemapToPoint();   		
    	}
    	
    	cost=(System.currentTimeMillis()-ms)*1.0/repeat;
		System.out.println("	[7]:"+cost);
		data[phasepos][schemepos][kpos][npos][wpos]=cost;

		//[20]
		schemepos++;
		bp=new BilinearParing(rBit,qBit,n+2);//rBit,qBit,repeat
		Exponentiation exponentiation = new Exponentiation(rBit/10,rBit/10,3*n);//baseLen,exponentLen,repeat
    	mappingToPointHash=new MappingToPointHash(rBit,qBit,2*n);//rBit,qBit,repeat
    	Multiplication multiplication=new Multiplication(rBit,rBit,3*n-2);//aLen,bLen,repeat

    	ms=System.currentTimeMillis();
    	for(int i=0;i<repeat;i++) {
	    	bp.executePairing();
	    	exponentiation.executeExponentiation();
	    	mappingToPointHash.executemapToPoint();
	    	multiplication.executeMultiplication();    		
    	}
    	
    	cost=(System.currentTimeMillis()-ms)*1.0/repeat;
		System.out.println("	[20]:"+cost);
		data[phasepos][schemepos][kpos][npos][wpos]=cost;
		
		//[21]
		schemepos++;
		multiplication=new Multiplication(rBit,rBit,n-1);//aLen,bLen,repeat
		bp=new BilinearParing(rBit,qBit,2*n+1);//rBit,qBit,repeat
    	mappingToPointHash=new MappingToPointHash(rBit,qBit,2*n+1);//rBit,qBit,repeat
    	
    	ms=System.currentTimeMillis();
    	for(int i=0;i<repeat;i++) {
	     	multiplication.executeMultiplication();
	    	bp.executePairing();
	    	mappingToPointHash.executemapToPoint();   		
    	}
    	
    	cost=(System.currentTimeMillis()-ms)*1.0/repeat;
		System.out.println("	[21]:"+cost);
		data[phasepos][schemepos][kpos][npos][wpos]=cost;
		
		//[19]
		schemepos++;
		bp=new BilinearParing(rBit,qBit,n+1);//rBit,qBit,repeat
    	mappingToPointHash=new MappingToPointHash(rBit,qBit,n+1);//rBit,qBit,repeat
		exponentiation = new Exponentiation(rBit/10,rBit/10,1);//baseLen,exponentLen,repeat
		multiplication=new Multiplication(rBit,rBit,4*(n-1));//aLen,bLen,repeat

    	ms=System.currentTimeMillis();
    	for(int i=0;i<repeat;i++) {
	     	bp.executePairing();
	    	mappingToPointHash.executemapToPoint();
	    	exponentiation.executeExponentiation();
	    	multiplication.executeMultiplication();   		
    	}

    	cost=(System.currentTimeMillis()-ms)*1.0/repeat;
		System.out.println("	[19]:"+cost);
		data[phasepos][schemepos][kpos][npos][wpos]=cost;
		
	}	
	
	
	
	
	public static void executeDC(int k, int n, int w, int repeat, double[][][][][] data,int phasepos,int kpos, int npos, int wpos) throws NoSuchAlgorithmException {
		long ms;//timestamp
		double cost;

		int rBit=160;
		int qBit=512;
		
		int paillierBitLength=512;
		int elGamalbitLength=512;
		int schemepos;

		
		
		//time cost of DC
		System.out.println("time cost of DC");
		//our
		schemepos=0;
		PointMultiplicationECC pointMultiplication=new PointMultiplicationECC(rBit,qBit,rBit/10,2*n+3);//rBit,qBit,bglen,repeat
		MappingToPointHash mappingToPointHash=new MappingToPointHash(rBit,qBit,2*n+1);//rBit,qBit,repeat
		Multiplication multiplication=new Multiplication(rBit,rBit,k+w);//aLen,bLen,repeat
		
    	ms=System.currentTimeMillis();
    	for(int i=0;i<repeat;i++) {
	     	pointMultiplication.executePointMultiplication();
	    	mappingToPointHash.executemapToPoint();
	    	multiplication.executeMultiplication();   		
    	}
    	
    	cost=(System.currentTimeMillis()-ms)*1.0/repeat;
		System.out.println("	our:"+cost);	
		data[phasepos][0][kpos][npos][wpos]=cost;		
		
		//[7]
		schemepos++;
		BilinearParing bp=new BilinearParing(rBit,qBit,n+1);//rBit,qBit,repeat
		pointMultiplication=new PointMultiplicationECC(rBit,qBit,rBit/10,n);//rBit,qBit,bglen,repeat
    	mappingToPointHash=new MappingToPointHash(rBit,qBit,n);//rBit,qBit,repeat
    	
    	ms=System.currentTimeMillis();
    	for(int i=0;i<repeat;i++) {
	     	bp.executePairing();
	    	mappingToPointHash.executemapToPoint();
	    	pointMultiplication.executePointMultiplication();   		
    	}

    	cost=(System.currentTimeMillis()-ms)*1.0/repeat;
		System.out.println("	[7]:"+cost);
		data[phasepos][1][kpos][npos][wpos]=cost;
		
		//[20]
		schemepos++;
		bp=new BilinearParing(rBit,qBit,4);//rBit,qBit,repeat
    	mappingToPointHash=new MappingToPointHash(rBit,qBit,3);//rBit,qBit,repeat
    	Exponentiation exponentiation = new Exponentiation(rBit/10,rBit/10,1);//baseLen,exponentLen,repeat
		multiplication=new Multiplication(rBit,rBit,w);//aLen,bLen,repeat

    	ms=System.currentTimeMillis();
    	for(int i=0;i<repeat;i++) {
	    	bp.executePairing();
	    	mappingToPointHash.executemapToPoint();
	    	PollardsLambdaMethod.executePollardsLambda(1);
	    	exponentiation.executeExponentiation();
	    	multiplication.executeMultiplication();    		
    	}
    	
    	cost=(System.currentTimeMillis()-ms)*1.0/repeat;
		System.out.println("	[20]:"+cost);
		data[phasepos][2][kpos][npos][wpos]=cost;
		
		//[21]
		schemepos++;
		bp=new BilinearParing(rBit,qBit,1);//rBit,qBit,repeat
    	mappingToPointHash=new MappingToPointHash(rBit,qBit,2);//rBit,qBit,repeat
        Paillier paillier = new Paillier(paillierBitLength,rBit/10,1);//bitLength,bglen,repeat
        paillier.executeEncrypt();//precompute the cyphertext
		multiplication=new Multiplication(rBit,rBit,w);//aLen,bLen,repeat
    	
    	ms=System.currentTimeMillis();
    	for(int i=0;i<repeat;i++) {
	      	bp.executePairing();
	    	mappingToPointHash.executemapToPoint();
	        paillier.executeDecrypt();
	    	multiplication.executeMultiplication();  		
    	}
    	
    	cost=(System.currentTimeMillis()-ms)*1.0/repeat;
		System.out.println("	[21]:"+cost);
		data[phasepos][3][kpos][npos][wpos]=cost;
		
		//[19]
		schemepos++;
		bp=new BilinearParing(rBit,qBit,2*(n+2));//rBit,qBit,repeat
    	mappingToPointHash=new MappingToPointHash(rBit,qBit,n+1);//rBit,qBit,repeat
		ElGamal elGamal = new ElGamal(elGamalbitLength,rBit/10,1);//bitLength,bglen,repeat
		elGamal.executeEncrypt();
		exponentiation = new Exponentiation(rBit/10,rBit/10,1);//baseLen,exponentLen,repeat
		multiplication=new Multiplication(rBit,rBit,k+w);//aLen,bLen,repeat

    	ms=System.currentTimeMillis();
    	for(int i=0;i<repeat;i++) {
	    	bp.executePairing();
	    	mappingToPointHash.executemapToPoint();
	    	elGamal.executeDecrypt();
	    	PollardsLambdaMethod.executePollardsLambda(1);
	    	exponentiation.executeExponentiation();
	    	multiplication.executeMultiplication();    		
    	}
    	
    	cost=(System.currentTimeMillis()-ms)*1.0/repeat;
		System.out.println("	[19]:"+cost);
		data[phasepos][4][kpos][npos][wpos]=cost;

	}


	public static void main(String[] args) throws NoSuchAlgorithmException {
		
		int[] k= {3,5,7};//number of subsets 
		int[] n= {8,10,12,14,16};//the number of signers in ring signature
		int[] n_prime= {100,200,300,400,500,600,700,800,900,1000};//the number of users in the system
		int[] w= {5,10,15,20,25,30,35,40,45,50}; //the dimension of data
		int repeat=100;//The time cost is calculated based on the statistical results of 100 repeated runs
		
		String[] phase=new String[] {"SM","GW","DC"};
		String[] scheme=new String[] {"our","[7]","[20]","[21]","[19]"};
		
		int phasepos,schemepos,kpos,npos,wpos;
		double[][][][][] data=new double[phase.length][scheme.length][k.length][n.length][w.length];

		
		//SM
		phasepos=0;
		kpos=0;
		for(npos=0;npos<n.length;npos++) {
			for(wpos=0;wpos<w.length;wpos++) {
				System.out.println("n="+n[npos]+", w="+w[wpos]);
				executeSM(n[npos], w[wpos],repeat,data,phasepos,kpos,npos,wpos);
			}
		}

		System.out.println("-------SM----------");

		for(int j=0;j<data[0].length;j++) {
			System.out.println(scheme[j]);
			System.out.print("n,w\t");					
			for(int m=0;m<data[0][0][0][0].length;m++) {
				System.out.print(w[m]+"\t");//ouput w
			}
			System.out.println("");					
			for(int l=0;l<data[0][0][0].length;l++) {
				System.out.print(n[l]+"\t");//output n
				for(int m=0;m<data[0][0][0][0].length;m++) {
					System.out.print(data[phasepos][j][kpos][l][m]+"\t");
				}
				System.out.println(";");
			}
		}

		//GW
		phasepos=1;
		kpos=0;
		wpos=0;
		
		for(npos=0;npos<n.length;npos++) {
			System.out.println("n="+n[npos]);
			executeGW(n_prime[npos],repeat,data,phasepos,kpos,npos,wpos);
		}
		System.out.println("-------GW----------");

		for(int j=0;j<data[0].length;j++) {
			System.out.println(scheme[j]);
			System.out.print("n_prime\t");
			for(int l=0;l<data[0][0][0].length;l++) {
				System.out.print(n_prime[l]+"\t");//output n
			}
			System.out.println("");
			System.out.print("\t");
			for(int l=0;l<data[0][0][0].length;l++) {
				System.out.print(data[phasepos][j][kpos][l][wpos]+"\t");
			}
			System.out.println(";");
		}
		
		//DC
		phasepos=2;
		
		for(kpos=0;kpos<k.length;kpos++) {
			for(npos=0;npos<n.length;npos++) {
				for(wpos=0;wpos<w.length;wpos++) {
					System.out.println("k="+k[kpos]+", n="+n[npos]+", w="+w[wpos]);
					executeDC(k[kpos], n_prime[npos], w[wpos],repeat,data,phasepos,kpos,npos,wpos);
				}
			}
		}
		System.out.println("-------DC---------");

		for(int j=0;j<data[0].length;j++) {
			System.out.println(scheme[j]);
			for(int k1=0;k1<data[0][0].length;k1++) {
				System.out.println("k="+k[k1]);
				
				System.out.print("n,w\t");					
				for(int m=0;m<data[0][0][0][0].length;m++) {
					System.out.print(w[m]+"\t");//output w
				}
				System.out.println("");					
				for(int l=0;l<data[0][0][0].length;l++) {
					System.out.print(n_prime[l]+"\t");//output n
					for(int m=0;m<data[0][0][0][0].length;m++) {
						System.out.print(data[phasepos][j][k1][l][m]+"\t");
					}
					System.out.println(";");
				}
			}
		}
	}	
}
