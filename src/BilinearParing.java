import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;

public class BilinearParing {
	int rBit;
	int qBit;
	int repeat;
	TypeACurveGenerator pg;
	PairingParameters typeAParams;
	Pairing pairing;
	Field G1,G2,GT,Zr;
	Element[] g1,g2;

	public BilinearParing(int rBit,int qBit, int repeat) {
		this.rBit=rBit;
		this.qBit=qBit;
		this.repeat=repeat;
		pg = new TypeACurveGenerator(rBit, qBit);

		typeAParams = pg.generate();
		pairing = PairingFactory.getPairing(typeAParams);
		G1 = pairing.getG1();
		G2 = pairing.getG2();
		GT = pairing.getGT();
		Zr = pairing.getZr();

		g1=new Element[repeat];
		g2=new Element[repeat];
		for(int i=0;i<repeat;i++) {
			g1[i]=G1.newRandomElement().getImmutable();
			g2[i]=G2.newRandomElement().getImmutable();

		}

	}

	//execute bilinear pairing operation (repeat) times.
	public void executePairing() {
		for(int i=0;i<repeat;i++) {
			Element gt = pairing.pairing(g1[i], g2[i]);
		}
	}


	public static void main(String[] args) {
		long ms;
		BilinearParing bp=new BilinearParing(160,512,100);

		ms=System.currentTimeMillis();
		bp.executePairing();
		System.out.println("Bilinear Paring time cost:"+(System.currentTimeMillis()-ms)*1.0/bp.repeat);
		//----------------------------------------------------



	}

}
