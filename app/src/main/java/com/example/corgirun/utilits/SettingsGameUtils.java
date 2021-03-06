package com.example.corgirun.utilits;

import android.content.SharedPreferences;
import com.example.puzzle.CorePuz;

public class SettingsGameUtils {

	public static int distance = 0;
	public static int coins = 0;
	public static boolean training = true;
	public static boolean settings = false;

	public static boolean vita = true;
	public static boolean santa = false;
	public static boolean mort = true;
	public static boolean doux = false;
	public static boolean tard = false;
	public static boolean neg = false;
	public static boolean crocy = false;


	public static void saveSettings(CorePuz corePuz) {
		SharedPreferences.Editor editor = corePuz.getSharedPreferences().edit();
		editor.clear(); //очищаем файл
		editor.putInt("passedDistance", distance);
		editor.putInt("gameCoins", coins);
		editor.putBoolean("isTraining", training);
		editor.putBoolean("isSettings", settings);

		editor.putBoolean("dinoSanta", santa);
		editor.putBoolean("dinoDoux", doux);
		editor.putBoolean("dinoTard", tard);
		editor.putBoolean("dinoNeg", neg);
		editor.putBoolean("dinoCrocy", crocy);
		editor.apply(); //сохраняем в файл
	}

	public static void loadSettings(CorePuz corePuz) {
		distance = corePuz.getSharedPreferences().getInt("passedDistance", distance);
		coins = corePuz.getSharedPreferences().getInt("gameCoins", coins);
		training = corePuz.getSharedPreferences().getBoolean("isTraining", training);
		settings = corePuz.getSharedPreferences().getBoolean("isSettings", settings);

		santa = corePuz.getSharedPreferences().getBoolean("dinoSanta", santa);
		doux = corePuz.getSharedPreferences().getBoolean("dinoDoux", doux);
		tard = corePuz.getSharedPreferences().getBoolean("dinoTard", tard);
		neg = corePuz.getSharedPreferences().getBoolean("dinoNeg", neg);
		crocy = corePuz.getSharedPreferences().getBoolean("dinoCrocy", crocy);
	}

	public static void addDistance(int values) {
		if (distance < values) {
			distance = values;
		}
	}

	public static void setTraining(boolean training) {
		SettingsGameUtils.training = training;
	}

	public static boolean isTraining() {
		return training;
	}

	public static void setSettings(boolean settings) {
		SettingsGameUtils.settings = settings;
	}

	public static boolean isSettings() {
		return settings;
	}

	public static void setCoins(int values) {
		coins += values;
	}

	public static void setIsBought(int dino, boolean isBought) {
		if (dino == 1) {
			santa = isBought;
		}
		if (dino == 3) {
			doux = isBought;
		}
		if (dino == 4) {
			tard = isBought;
		}
		if (dino == 5) {
			neg = isBought;
		}
		if (dino == 6) {
			crocy = isBought;
		}
	}

	public static boolean getIsBought(int dino) {
		if (dino == 1) {
			return santa;
		}
		if (dino == 3) {
			return doux;
		}
		if (dino == 4) {
			return tard;
		}
		if (dino == 5) {
			return neg;
		}
		if (dino == 6) {
			return crocy;
		}
		return true;
	}

}
