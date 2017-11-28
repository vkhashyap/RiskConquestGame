package com.risk.behavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.TreeSet;

import com.risk.model.ReinforcementPhaseModel;
import com.risk.model.StartUpPhaseModel;
import com.risk.ui.ReinforcementsUI;

public class BenevolantBehaviorImpl implements PlayerBehavior {

	@Override

	public void reinforce(String player){

		ReinforcementPhaseModel obj = new ReinforcementPhaseModel();
		ReinforcementsUI objUI = new ReinforcementsUI();
		obj.addObserver(objUI);
		obj.reinforceBenevolent(player);

	}

	@Override
	public void fortify(int player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void attack(String player, HashMap<String, List<String>> territoryMap) {
		System.out.println("--------------------------");
		System.out.println("ATTACK PHASE UNINITIATED DUE TO ADOPTION OF BENEVOLENT STRATEGY ");
		System.out.println("--------------------------");

	}

}
