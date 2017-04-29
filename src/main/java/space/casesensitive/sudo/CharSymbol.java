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
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		CharSymbol other = (CharSymbol) obj;
		return symbol == other.symbol;
	}

}
