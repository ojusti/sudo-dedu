package space.casesensitive;

import static java.util.stream.Collectors.reducing;

import java.util.stream.Collector;

public class Booleans
{
	public static Collector<Boolean, ?, Boolean> logicalOr()
	{
		return reducing(false, Boolean::logicalOr);
	}
}
