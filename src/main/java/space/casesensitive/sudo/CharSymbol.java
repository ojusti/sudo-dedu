package space.casesensitive.sudo;

class CharSymbol implements Symbol
{

	static final Symbol DOT = new CharSymbol('.');
	private final char symbol;

	CharSymbol(char symbol)
	{
		this.symbol = symbol;
	}

	CharSymbol(int symbol)
	{
		this((char) symbol);
	}

	@Override
	public String toString()
	{
		return String.valueOf(symbol);
	}

	@Override
	public int hashCode()
	{
		return Character.hashCode(symbol);
	}

	@Override
	public boolean equals(Object other)
	{
		if (this == other)
		{
			return true;
		}
		if (other == null)
		{
			return false;
		}
		if (getClass() != other.getClass())
		{
			return false;
		}
		return symbol == ((CharSymbol) other).symbol;
	}

	@Override
	public int compareTo(Symbol other)
	{
		return symbol - ((CharSymbol) other).symbol;
	}

}
