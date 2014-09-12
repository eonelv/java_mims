package com.mims.app.aim;

public class AimUtils
{

	public static boolean isNull(String value)
	{
		if (value == null || value.trim().length() == 0)
		{
			return true;
		}
		return false;
	}
}
