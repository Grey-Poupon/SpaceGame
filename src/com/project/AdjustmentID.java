package com.project;

public enum AdjustmentID {
Up_Left     ,Up_MidLeft     ,Up     ,Up_MidRight     ,Up_Right,
MidUp_Left  ,MidUp_MidLeft  ,MidUp  ,MidUp_MidRight  ,MidUp_Right,
Left        ,MidLeft        ,None   ,MidRight		 ,Right, 
MidDown_Left,MidDown_MidLeft,MidDown,MidDown_MidRight,MidDown_Right, 
Down_Left   ,Down_MidLeft   ,Down   ,Down_MidRight   ,Down_Right; 


	public static float getXAdjustment(AdjustmentID adjust) {
		if(adjust == Up_Left     || adjust == MidUp_Left 	 || adjust == Left 	   || adjust == MidDown_Left     || adjust == Down_Left) {
			return 1;
			}
		if(adjust == Up_MidLeft  || adjust == MidUp_MidLeft  || adjust == MidLeft  || adjust == MidDown_MidLeft  || adjust == Down_MidLeft) {
			return 0.5f;
			}
		if(adjust == Up          || adjust == MidUp 		 || adjust == None 	   || adjust == MidDown 		 || adjust == Down) {
			return 0;
			}
		if(adjust == Up_MidRight || adjust == MidUp_MidRight || adjust == MidRight || adjust == MidDown_MidRight || adjust == Down_MidRight) {
			return -0.5f;
			}
		else {return -1;}
	}
	public static float getYAdjustment(AdjustmentID adjust) {
		if(adjust == Down_Left    || adjust == Down_MidLeft    || adjust == Down    || adjust == Down_MidRight    || adjust == Down_Right) {
			return 1;
			}
		if(adjust == MidDown_Left || adjust == MidDown_MidLeft || adjust == MidDown || adjust == MidDown_MidRight || adjust == MidDown_Right) {
			return 0.5f;
			}
		if(adjust == Left         || adjust == MidLeft         || adjust == None    || adjust == MidRight         || adjust == Right) {
			return 0; 
			}
		if(adjust == MidUp_Left   || adjust == MidUp_MidLeft   || adjust == MidUp   || adjust == MidUp_MidRight   || adjust == MidUp_Right) {
			return -0.5f;
			}
		else {return -1;}
	}

}

