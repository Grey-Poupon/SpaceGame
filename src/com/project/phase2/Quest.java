package com.project.phase2;

import com.project.Crew;

public class Quest {



	private String name;
	private Crew givenBy;
	private boolean accepted =false;


	public Quest(String name, Crew givenBy) {
		this.name = name;
		this.givenBy = givenBy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Crew getGivenBy() {
		return givenBy;
	}

	public void setGivenBy(Crew givenBy) {
		this.givenBy = givenBy;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
}
