import java.util.ArrayList;

public class Editable {

	public String path;
	public float zPerLayer;
	public float z;
	public float[] layerZ;
	
	
	
	public Editable() {
		this.path = "matron";
		this.zPerLayer = 0.05f;
		this.z = 16f;
		this.layerZ = new float[]{
				16f,18f,20f,22f,24f,26f,28f,30f,32f,34f,36f,38f,40f,42f,44f,46f,48f,50f,52f,54f,56f,58f,60f,62f,64f,66f,68f,70f};
	}

}
