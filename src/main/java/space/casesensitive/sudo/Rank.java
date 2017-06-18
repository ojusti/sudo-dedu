package space.casesensitive.sudo;

import static java.lang.Character.forDigit;
import static java.util.stream.IntStream.range;

public class Rank
{
	static Rank forBoardCellsCount(int cellsCount) throws InvalidSudoku
	{
		Rank rank = new Rank((int) Math.round(Math.sqrt(Math.sqrt(cellsCount))));
		if (rank.boardCellsCount() == cellsCount)
		{
			return rank;
		}
		throw InvalidSudoku.cellCount(cellsCount);
	}

	static Rank forSymbolsCount(int symbolsCount) throws InvalidSudoku
	{
		Rank rank = new Rank((int) Math.round(Math.sqrt(symbolsCount)));
		if (rank.symbolsCount() == symbolsCount)
		{
			return rank;
		}
		throw InvalidSudoku.symbolCount(symbolsCount);
	}

	private final int rank;

	public Rank(int rank) throws InvalidSudoku
	{
		if (rank < 2)
		{
			throw InvalidSudoku.invalidRank(rank);
		}
		this.rank = rank;
	}

	@Override
	public String toString()
	{
		return String.valueOf(rank);
	}

	public int symbolsCount()
	{
		return rank * rank;
	}

	public Symbols predefinedSymbols()
	{
		try
		{
			int radix = symbolsCount() + 1;
			Symbol[] digits = range(1, radix).map(i -> forDigit(i, radix)).mapToObj(CharSymbol::new)
					.toArray(Symbol[]::new);
			return new Symbols(CharSymbol.DOT, digits);
		}
		catch (InvalidSudoku e)
		{
			throw new RuntimeException("bug", e);
		}
	}

	int boardCellsCount()
	{
		int symbolsCount = symbolsCount();
		return symbolsCount * symbolsCount;
	}

	@Override
	public int hashCode()
	{
		return Integer.hashCode(rank);
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
		Rank other = (Rank) obj;
		return rank == other.rank;
	}

	public int get()
	{
		return rank;
	}

}
