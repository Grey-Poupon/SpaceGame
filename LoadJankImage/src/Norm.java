import java.util.Random;

public class Norm {
	
	Random rand;
	public Norm() {
		this.rand = new Random();
		
	}
	
	public int getNorm(int mean,int pop) {
		int result = 0;
		for(int i=0;i<pop;i++) {
			result+=rand.nextInt(2);
		}
		result=result*2*mean/pop;
		return result;
	}
}
