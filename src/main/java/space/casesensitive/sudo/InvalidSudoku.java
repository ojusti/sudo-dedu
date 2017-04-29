package space.casesensitive.sudo;

@SuppressWarnings("serial")
public class InvalidSudoku extends Exception
{

	private InvalidSudoku(String message)
	{
		super(message);
	}

	static InvalidSudoku cellCount(int cellCount)
	{
		return new InvalidSudoku("I cannot accept a Sudoku board with " + cellCount + " cells");
	}

	static InvalidSudoku symbolCount(int symbolCount)
	{
		return new InvalidSudoku("I cannot accept a Sudoku symbol array with " + symbolCount + " symbols");
	}

	static InvalidSudoku undefinedSymbol(Symbol unknown)
	{
		return new InvalidSudoku("I cannot accept a Sudoku board with this symbol " + unknown);
	}

	static InvalidSudoku differentRanks(Rank board, Rank symbols)
	{
		return new InvalidSudoku("I cannot accept a Sudoku board with different ranks for board (" + board
				+ ") and symbols (" + symbols + ")");
	}

	static InvalidSudoku invalidRank(int rank)
	{
		return new InvalidSudoku(
				"I cannot accept a Sudoku board with a rank lower than two. Actual computed rank is " + rank);
	}

}
