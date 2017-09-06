package com.project.battle;

public enum BattlePhases {
	WeaponsButton,WeaponsClick,WeaponActions,Engine,EngineActions,Cockpit,Final,Wait;
	public static BattlePhases[] phases = new BattlePhases[] 
			{Engine,EngineActions,WeaponsButton,WeaponActions,WeaponsClick,Cockpit,Final};
}
