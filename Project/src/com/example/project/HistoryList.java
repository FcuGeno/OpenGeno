package com.example.project;

import java.util.LinkedList;

public class HistoryList {
	LinkedList<History> LLU;
	LinkedList<History> LLR;
	boolean isChange = false;
	boolean needSave = false;

	public HistoryList() {
		LLU = new LinkedList<History>();
		LLR = new LinkedList<History>();
	}

	public void add(History history) {
		isChange = true;
		needSave = true;
		LLU.push(history);
		if (!LLR.isEmpty())
			LLR.clear();
	}

	public void reAdd(History history) {
		isChange = true;
		needSave = true;
		LLU.push(history);
	}

	public LinkedList<History> getLLU() {
		return LLU;
	}

	public LinkedList<History> getLLR() {
		return LLR;
	}

	public void clear() {
		isChange = false;
		needSave = false;
		LLU.clear();
		LLR.clear();
	}

	public boolean isChange() {
		return isChange;
	}

	public boolean needSave() {
		return needSave;
	}

	public void setChange() {
		isChange = true;
	}

	public void setSave() {
		needSave = true;
	}

	public void resetChange() {
		isChange = false;
	}

	public void resetSave() {
		needSave = false;
	}
}
