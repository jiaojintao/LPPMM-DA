import java.math.BigInteger;
import java.util.Random;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.ElementPowPreProcessing;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;


public class PointMultiplicationECC {

	int rBit;
	int qBit;
	int repeat;
	TypeACurveGenerator pg;
	PairingParameters typeAParams;
	Pairing pairing;
	Field<Element> G1;
	Element g1;
	ElementPowPreProcessing ppp;
	int bglen;
	BigInteger[] bigInt;

	public PointMultiplicationECC(int rBit, int qBit, int bglen, int repeat) {
		this.rBit=rBit;
		this.qBit=qBit;
		this.bglen=bglen;
		this.repeat=repeat;
		pg = new TypeACurveGenerator(rBit, qBit);
		typeAParams = pg.generate();
		pairing = PairingFactory.getPairing(typeAParams);
		G1 = pairing.getG1();
		g1=G1.newRandomElement().getImmutable();
		ppp=g1.getElementPowPreProcessing();
		pairing.getPairingPreProcessingFromElement(g1);
		bigInt=new BigInteger[repeat];
		Random random=new Random();
		for(int i=0;i<repeat;i++) {
			bigInt[i]=new BigInteger(bglen,random);
		}


	}
    //Performs point multiplication on the elliptic curve

    public Element pointMultiplication(ElementPowPreProcessing point, BigInteger scalar) {
        // Perform point multiplication
    	Element result=ppp.pow(scalar);
        //Element result = point.duplicate().mul(scalar);

        // Ensure the result is in its standard (normalized) form
        result = result.getImmutable();
        return result;
    }

    public void executePointMultiplication() {
		for(int i=0;i<repeat;i++) {
			pointMultiplication(ppp,bigInt[i]);
		}

    }


    public static void main(String[] args) {
		long ms;
		PointMultiplicationECC pointMultiplication=new PointMultiplicationECC(160,512,16,100);
    	ms=System.currentTimeMillis();
    	pointMultiplication.executePointMultiplication();
		System.out.println("Point Multiplication in ECC time cost:"+(System.currentTimeMillis()-ms)*1.0/pointMultiplication.repeat);
    }


}
