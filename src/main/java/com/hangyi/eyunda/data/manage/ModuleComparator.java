package com.hangyi.eyunda.data.manage;

import java.util.Comparator;

public class ModuleComparator implements Comparator<ModuleInfoData> {
	public int compare(ModuleInfoData arg0, ModuleInfoData arg1) {
		int flag = arg0.getModuleLayer().compareTo(arg1.getModuleLayer());
		return flag;
	}
}
