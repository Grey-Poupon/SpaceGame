package com.project.battle;

public enum BattlePhases {
	WeaponsButton,WeaponsClick,WeaponActions,Engine,Final,Wait;
	public static BattlePhases[] phases = new BattlePhases[] 
			{WeaponsButton,WeaponActions,WeaponsClick,Engine,Final};
}
