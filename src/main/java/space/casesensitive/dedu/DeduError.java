package space.casesensitive.dedu;

@SuppressWarnings("serial")
class DeduError extends RuntimeException
{

	DeduError(String message)
	{
		super(message);
	}

	static DeduError bug(String message)
	{
		return new DeduError(message);
	}

}
