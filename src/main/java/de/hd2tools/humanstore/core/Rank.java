package de.hd2tools.humanstore.core;

public enum Rank {

	PRIVATE(0, "Private"), 
	CORPORAL(1, "Corporal"), 
	SERGEANT(2, "Sergeant"), 
	STAFF_SERGEANT(3,"Staff Sergeant"), 
	SECOND_LIEUTNANT(4, "Second Lieutnant"),
	LIEUTNANT(5, "Lieutnant"),
	CAPTAIN(6, "Captain"),
	MAJOR(7, "Major");

	public static Rank forId(int id) throws Exception {
		for (Rank rank : Rank.values()) {
			if (rank.getRankId() == id) {
				return rank;
			}
		}

		throw new Exception("no ranl found for id " + id);
	}

	private String name;

	private int rankId;

	Rank(int rankId, String name) {
		this.rankId = rankId;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getRankId() {
		return rankId;
	}
}
