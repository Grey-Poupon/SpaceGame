package com.project.battle;

public enum BattlePhases {
	GeneratorActions,WeaponActions,WeaponsClick,Final,Wait;
	public static BattlePhases[] phases = new BattlePhases[] 
			{GeneratorActions,WeaponActions,WeaponsClick,Final};
	public static BattlePhases getFinalPlayableStage(){
		return phases[phases.length-1];
	}

}
