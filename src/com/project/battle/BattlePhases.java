package com.project.battle;

public enum BattlePhases {
	WeaponsButton,WeaponsClick,WeaponActions,Engine,GeneratorActions,Cockpit,CockpitActions,Final,Wait,Go;
	public static BattlePhases[] phases = new BattlePhases[] 

			{GeneratorActions,WeaponsButton,WeaponActions,WeaponsClick,Cockpit,CockpitActions,Go,Final};

}
