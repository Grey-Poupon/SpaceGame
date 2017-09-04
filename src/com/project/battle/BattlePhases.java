package com.project.battle;

public enum BattlePhases {
	WeaponsButton,WeaponsClick,WeaponActions,Engine,EngineActions,Final,Wait;
	public static BattlePhases[] phases = new BattlePhases[] 
			{WeaponsButton,WeaponActions,WeaponsClick,Engine,EngineActions,Final};
}
