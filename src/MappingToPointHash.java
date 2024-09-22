
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.ElementPowPreProcessing;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;

public class MappingToPointHash {
	int rBit;
	int qBit;
	int repeat;
	TypeACurveGenerator pg;
	PairingParameters typeAParams;
	Pairing pairing;
	Field<Element> G1;
	Element g1;
	ElementPowPreProcessing ppp;
	String[] str;

	public MappingToPointHash(int rBit, int qBit, int repeat) {
		this.rBit=rBit;
		this.qBit=qBit;
		this.repeat=repeat;
		pg = new TypeACurveGenerator(rBit, qBit);
		typeAParams = pg.generate();
		pairing = PairingFactory.getPairing(typeAParams);
		G1 = pairing.getG1();
		str=new String[repeat];
		for(int i=0;i<repeat;i++) {
			str[i]=i+"";
		}
		g1=G1.newRandomElement();
		ppp=g1.getElementPowPreProcessing();

		ppp = g1.getElementPowPreProcessing();

		//pairing.getPairingPreProcessingFromElement(g1);
	}

    // Maps a message to a point on the elliptic curve
    public Element mapToPoint(String message) throws NoSuchAlgorithmException {
        // Compute SHA-256 hash of the message
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] hash = sha256.digest(message.getBytes());

		Element mappedElement = pairing.getZr().newRandomElement();


        // Map the hash value to a point in the G1 group of the elliptic curve
        //Element point = g1.setFromHash(hash, 0, hash.length);
		Element point = mappedElement.setFromHash(hash, 0, hash.length);

		ppp.powZn(point);

        // Ensure the point is in its standard (normalized) form
        point = point.getImmutable();
        return point;
    }

    public void executemapToPoint() throws NoSuchAlgorithmException {

		for(int i=0;i<repeat;i++) {
			mapToPoint(str[i]);
		}
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
		long ms;
    	MappingToPointHash mappingToPointHash=new MappingToPointHash(160,512,100);
    	ms=System.currentTimeMillis();
    	mappingToPointHash.executemapToPoint();
		System.out.println("Mapping To Point Hash Function time cost:"+(System.currentTimeMillis()-ms)*1.0/mappingToPointHash.repeat);

    }
}

