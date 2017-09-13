package com.project.battle;

public enum BattlePhases {
	WeaponsButton,WeaponsClick,WeaponActions,Engine,GeneratorActions,Cockpit,CockpitActions,Final,Wait;
	public static BattlePhases[] phases = new BattlePhases[] 
			{GeneratorActions,WeaponActions,WeaponsClick,Final};
		//	{GeneratorActions,WeaponsButton,WeaponActions,WeaponsClick,Cockpit,CockpitActions,Final};
}
