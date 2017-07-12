
public class Button {
	private int xCoordinate,yCoordinate,width,height;
	public enum ButtonID{
		
	}
	public Button(int x,int y,int width,int height){
		this.xCoordinate = x;
		this.yCoordinate =y;
		this.width = width;
		this.height = height;
	}
	public boolean isInside(int x, int y){
		if(x<this.xCoordinate+this.width&&x>this.xCoordinate){
			if(y<this.yCoordinate+this.height&&y>this.yCoordinate){
				return true;
			}
			
		}
		return false;
		
	}

}
