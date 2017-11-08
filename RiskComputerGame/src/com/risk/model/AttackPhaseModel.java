package com.risk.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class AttackPhaseModel {

	static int noOfAttackerArmies;
	static int noOfDefenderArmies;
	static String attackerKey;
	static String defenderKey;
	static String attackerPlayer;
	static String defenderPlayer;
	static boolean armiesDepleted = false;
	static String attackAgain = "Yes";
	static String attackSameCountryAgain = "Yes";
	static Scanner input = new Scanner(System.in);

	// for getting data from player info entries for the specific player
	// list for attackers
	static List<String> playerAccToPlayerNo = new ArrayList<String>();
	// get countries of attacker
	static List<String> countryAccToAttacker = new ArrayList<String>();
	// list for attacked candidates
	static List<String> playerNotAccToPlayerNo = new ArrayList<String>();

	static Random random;

	/*
	 * method for checking adjacency of the attacker and defender
	 */
	public static void chooseCountryToBeAttacked(int plyr, HashMap<String, List<String>> territoryMap) {
		attackAgain = "Yes";
		attackSameCountryAgain = "Yes";
		noOfAttackerArmies = 0;
		noOfDefenderArmies = 0;

		// populating the lists for attacker and defender
		populateListsForAttackerAndDefender(plyr);

		// randomly select attacker and defender
		// for generating a stream of pseudo random numbers
		random = new Random();
		String attacker = playerAccToPlayerNo.get(random.nextInt(playerAccToPlayerNo.size()));
		// global key initialized
		attackerKey = attacker;
		// getting number of attacker armies
		noOfAttackerArmies = StartUpPhaseModel.playerInfo.get(attacker);
		while (attackAgain.trim().equalsIgnoreCase("Yes")) {

			String attacked = playerNotAccToPlayerNo.get(random.nextInt(playerNotAccToPlayerNo.size()));
			// global key initialized
			defenderKey = attacked;
			// getting number of attacker armies
			noOfDefenderArmies = StartUpPhaseModel.playerInfo.get(attacked);
			String[] keySplit1 = attacker.split("-");
			attackerPlayer = keySplit1[0];
			String territoryAttacker = keySplit1[1];
			String[] keySplit2 = attacked.split("-");
			defenderPlayer = keySplit2[0];
			String territoryAttacked = keySplit2[1];
			for (Map.Entry<String, List<String>> iterate : territoryMap.entrySet()) {
				//
				String[] keyArray = iterate.getKey().split(",");
				String territory = keyArray[1];
				if (territoryAttacker.equals(territory)) {

					for (String country : iterate.getValue()) {

						if (territoryAttacked.equals(country)) {
							while (attackSameCountryAgain.trim().equalsIgnoreCase("Yes")
									//&& StartUpPhaseModel.playerInfo.get(attacked) != 0
									) {

								// attackSameCountryAgain
								System.out
										.println("Player" + keySplit1[0] + "(Attacker) armies :" + noOfAttackerArmies);
								System.out
										.println("Player" + keySplit2[0] + "(Defender) armies :" + noOfDefenderArmies);
								System.out.println();
								System.out.println();
								System.out.println();
								System.out.println("Player" + keySplit1[0] + "in" + territoryAttacker + "attacks Player"
										+ keySplit2[0] + "in" + territoryAttacked);

								// Dice Rolled
								// country is attacked and armies are deducted
								// based on the dice roll obtained
								try {
									rollDice();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}

								System.out.println("---------------------------------------------");
								System.out.println("Do you want to attack the Same Country again? ");
								System.out.println("Please enter  Yes or No : ");

								attackSameCountryAgain = input.next();

							}

						}

					}
				}

			}
			System.out.println("----------------------------------------");
			System.out.println("Do you want to attack again? ");
			System.out.println("Please enter Yes or No :");
			attackAgain = input.next();

		}
		System.out.println();
		System.out.println();
		System.out.println("Attack Phase Ends");

	}

	private static void populateListsForAttackerAndDefender(int plyr) {
		for (String playerInfoKey : StartUpPhaseModel.playerInfo.keySet()) {
			String[] playerInfoArr = playerInfoKey.split("-");
			if (playerInfoArr[0].equals(String.valueOf(plyr))) {
				playerAccToPlayerNo.add(playerInfoKey);
				countryAccToAttacker.add(playerInfoArr[1]);

			} else {
				playerNotAccToPlayerNo.add(playerInfoKey);

			}
		}
	}

	/*
	 * dice is rolled and numbers obtained are used to compute the result
	 */
	public static void rollDice() throws InterruptedException {

		Thread.sleep(1000);
		int diceRollsForAttacker, diceRollsForDefender;
		int[] diceArrayForAttackers = new int[3];
		int[] diceArrayForDefenders = new int[3];

		if (noOfAttackerArmies > 2) {

			diceRollsForAttacker = 3;
			diceArrayForAttackers = AttackPhaseModel.getDiceNumbers(diceRollsForAttacker);
			System.out.println("Player" + attackerPlayer + "Dice Roll : " + diceArrayForDefenders);
			System.out.println();
			System.out.println();

			diceRollsForDefender = 2;
			diceArrayForDefenders = AttackPhaseModel.getDiceNumbers(diceRollsForDefender);
			System.out.println("Player" + defenderPlayer + "Dice Roll : " + diceArrayForDefenders);
			System.out.println();
			System.out.println();
		} else if (noOfAttackerArmies == 2) {

			diceRollsForAttacker = 2;
			diceArrayForAttackers = AttackPhaseModel.getDiceNumbers(diceRollsForAttacker);
			System.out.println("Player" + attackerPlayer + "Dice Roll : " + diceArrayForDefenders);
			System.out.println();
			System.out.println();
			diceRollsForDefender = 2;
			diceArrayForDefenders = AttackPhaseModel.getDiceNumbers(diceRollsForDefender);
			System.out.println("Player" + defenderPlayer + "Dice Roll : " + diceArrayForDefenders);
			System.out.println();
			System.out.println();
		} else if (noOfAttackerArmies == 1) {

			diceRollsForAttacker = 1;
			diceArrayForAttackers = AttackPhaseModel.getDiceNumbers(diceRollsForAttacker);
			System.out.println("Player" + attackerPlayer + "Dice Roll : " + diceArrayForDefenders);
			System.out.println();
			System.out.println();

			diceRollsForDefender = 1;
			diceArrayForDefenders = AttackPhaseModel.getDiceNumbers(diceRollsForDefender);
			System.out.println("Player" + defenderPlayer + "Dice Roll : " + diceArrayForDefenders);
			System.out.println();
			System.out.println();
		}
		// analyzing the numbers obtained to compute the no of armies
		diceRollAnalysis(diceArrayForAttackers, diceArrayForDefenders);

	}

	/*
	 * analyzing the numbers obtained to compute the no of armies thus making
	 * changes in player data
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void diceRollAnalysis(int[] diceArrayForAttackers, int[] diceArrayForDefenders)
			throws InterruptedException {
		List diceArrayForAttackersList = Arrays.asList(diceArrayForAttackers);
		Collections.sort(diceArrayForAttackersList);
		List diceArrayForDefendersList = Arrays.asList(diceArrayForDefenders);
		Collections.sort(diceArrayForDefendersList);

		int maxAttacker = 0, attacker2ndBest = 0;
		int maxDefender = 0, minDefender2ndRoll = 0;
		int armiesObtainedForAttacker = 0, armiesObtainedForDefender = 0;

		// Case 1 : attacker and defender both are given more than 1 dice rolls
		if (diceArrayForAttackersList.size() >= 2) {
			maxAttacker = Collections.max(diceArrayForAttackersList);
			attacker2ndBest = (int) diceArrayForAttackersList.get(diceArrayForAttackersList.size() - 2);
			maxDefender = Collections.max(diceArrayForDefendersList);
			minDefender2ndRoll = (int) diceArrayForDefendersList.get(diceArrayForDefendersList.size() - 2);
			if (maxAttacker > maxDefender) {
				++armiesObtainedForAttacker;
				--armiesObtainedForDefender;

			} else {
				--armiesObtainedForAttacker;
				++armiesObtainedForDefender;

			}
			if (attacker2ndBest > minDefender2ndRoll) {
				++armiesObtainedForAttacker;
				--armiesObtainedForDefender;
			} else {
				--armiesObtainedForAttacker;
				++armiesObtainedForDefender;
			}

			// Updating the Player Data

			int noOfAttackerArmies = StartUpPhaseModel.playerInfo.get(attackerKey);
			noOfAttackerArmies = noOfAttackerArmies - armiesObtainedForAttacker;
			// Updating Attacker Data
			StartUpPhaseModel.playerInfo.replace(attackerKey, noOfAttackerArmies);

			int noOfDefenderArmies = StartUpPhaseModel.playerInfo.get(defenderKey);
			noOfDefenderArmies = noOfDefenderArmies - armiesObtainedForDefender;
			// Updating Defender Data
			StartUpPhaseModel.playerInfo.replace(defenderKey, noOfAttackerArmies);

			Thread.sleep(2000);
			System.out.println("Attack Completed");
			System.out.println("Player" + attackerPlayer + "(Attacker) armies :" + noOfAttackerArmies);
			System.out.println("Player" + defenderPlayer + "(Defender) armies :" + noOfDefenderArmies);

			if (noOfDefenderArmies == 0) {

				Thread.sleep(1000);
				System.out.println();
				System.out.println();

				System.out.println("Attacker emerges Victorious");

				// assigning defender territory to attacker
				String[] defKeyString = defenderKey.split("-");
				defKeyString[0] = attackerPlayer;
				String modifiedKey = defKeyString[0] + defKeyString[1] + defKeyString[2];
				// attacking player must place a number of armies
				// in the conquered country which is greater or equal than the number of dice
				// that was used in the attack that
				// resulted in conquering the country
				StartUpPhaseModel.playerInfo.put(modifiedKey, diceArrayForAttackersList.size() - 1);
				StartUpPhaseModel.playerInfo.remove(defenderKey);
			}
			if (noOfAttackerArmies == 0) {
				StartUpPhaseModel.playerInfo.put(attackerKey, 1);

			}

		}
		// Case 2 : attacker and defender both are given 1 dice roll
		else if (diceArrayForAttackersList.size() == 1) {
			maxAttacker = 0;
			maxDefender = 0;
			if (maxAttacker > maxDefender) {
				++armiesObtainedForAttacker;
				--armiesObtainedForDefender;

			} else {
				--armiesObtainedForAttacker;
				++armiesObtainedForDefender;
			}

			// Updating the Player Data
			int noOfAttackerArmies = StartUpPhaseModel.playerInfo.get(attackerKey);
			noOfAttackerArmies = noOfAttackerArmies - armiesObtainedForAttacker;
			// Updating Attacker Data
			StartUpPhaseModel.playerInfo.replace(attackerKey, noOfAttackerArmies);

			int noOfDefenderArmies = StartUpPhaseModel.playerInfo.get(defenderKey);
			noOfDefenderArmies = noOfDefenderArmies - armiesObtainedForDefender;
			// Updating Defender Data
			StartUpPhaseModel.playerInfo.replace(defenderKey, noOfAttackerArmies);

			if (noOfDefenderArmies == 0) {

				Thread.sleep(1000);
				System.out.println();
				System.out.println("Attacker emerges Victorious");
				// assigning defender territory to attacker
				String[] defKeyString = defenderKey.split("-");
				defKeyString[0] = attackerPlayer;
				String modifiedKey = defKeyString[0] + "-" + defKeyString[1] + "-" + defKeyString[2];
				StartUpPhaseModel.playerInfo.put(modifiedKey, diceArrayForAttackersList.size() - 1);
				StartUpPhaseModel.playerInfo.remove(defenderKey);
			}
			if (noOfAttackerArmies == 0) {
				StartUpPhaseModel.playerInfo.put(attackerKey, 1);
			}

		}
		// calling function to decide whether the player should or shouldn't attack
		// again

	}

	/*
	 * this method generate and returns the numbers obtained in the dice rolled
	 */
	public static int[] getDiceNumbers(int diceRolls) {
		random = new Random();
		int[] diceArray = new int[3];

		switch (diceRolls) {
		case 1:
			diceArray[0] = random.nextInt(6);
			break;
		case 2:
			diceArray[0] = random.nextInt(6);
			diceArray[1] = random.nextInt(6);

			break;
		case 3:
			diceArray[0] = random.nextInt(6);
			diceArray[1] = random.nextInt(6);
			diceArray[2] = random.nextInt(6);
			break;
		default:
			break;
		}
		return diceArray;
	}

}
