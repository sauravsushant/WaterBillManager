package WaterBill;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WaterBillManager {
	private static int totalWaterConsumed = 0;
	private static int totalGuestWaterConsumed = 0;
	private static int waterConsumed = 0;
	private static int totalCost = 0;
	private static int corporationWaterRatio = 0;
	private static int borewellWaterRatio = 0;
	private static int guests = 0;
	private static int apartmenttype = 0;

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("input file location not found in command line argument.");
			return;
		}

		String inputFilePath = args[0];
		try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] command = line.split(" ");
				switch (command[0]) {
				case "ALLOT_WATER":
					apartmenttype = Integer.parseInt(command[1]);
					String[] ratios = command[2].split(":");
					corporationWaterRatio = Integer.parseInt(ratios[0]);
					borewellWaterRatio = Integer.parseInt(ratios[1]);
					break;
				case "ADD_GUESTS":
					guests += Integer.parseInt(command[1]);
					break;
				case "BILL":
					calculateWaterConsumed(apartmenttype);
					calculateCost();
					System.out.println(totalWaterConsumed + " " + totalCost);
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("An error occurred while reading the input file.");
			e.printStackTrace();
		}
	}

	private static void calculateWaterConsumed(int apartmenttype) {
		switch (apartmenttype) {
		case 2:
			waterConsumed = 900;
			break;
		case 3:
			waterConsumed = 1500;
			break;
		default:
			break;
		}
		totalGuestWaterConsumed = guests * 10 * 30;
		totalWaterConsumed = waterConsumed + totalGuestWaterConsumed;
	}

	private static void calculateCost() {
		int corporationWaterCost = waterConsumed * corporationWaterRatio / (corporationWaterRatio + borewellWaterRatio);
		int borewellWaterCost = (int) Math
				.round(waterConsumed * borewellWaterRatio / (corporationWaterRatio + borewellWaterRatio) * 1.5);
		int tankerWaterCost = calculateTankerWaterCost(totalGuestWaterConsumed);

		totalCost = corporationWaterCost + borewellWaterCost + tankerWaterCost;
	}

	private static int calculateTankerWaterCost(int totalGuestWaterConsumed) {
		int cost = 0;

		if (totalGuestWaterConsumed > 0) {
			if (totalGuestWaterConsumed <= 500) {
				cost = totalGuestWaterConsumed * 2;
			} else if (totalGuestWaterConsumed <= 1500) {
				cost = 500 * 2 + (totalGuestWaterConsumed - 500) * 3;
			} else if (totalGuestWaterConsumed <= 3000) {
				cost = 500 * 2 + 1000 * 3 + (totalGuestWaterConsumed - 1500) * 5;
			} else {
				cost = 500 * 2 + 1000 * 3 + 1500 * 5 + (totalGuestWaterConsumed - 3000) * 8;
			}
		}
		return cost;
	}
}
