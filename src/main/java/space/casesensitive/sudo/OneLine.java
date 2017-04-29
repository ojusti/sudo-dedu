package space.casesensitive.sudo;

import static java.util.stream.Collectors.joining;

import java.util.Optional;
import java.util.stream.Stream;

public class OneLine implements Board
{

	private final String board;
	private final Rank rank;
	private final Symbols symbols;

	public OneLine(String board, Symbols symbols) throws InvalidSudoku
	{
		this.board = board;
		this.rank = Rank.forBoardCellsCount(board.length());
		if (!this.rank.equals(symbols.rank))
		{
			throw InvalidSudoku.differentRanks(this.rank, symbols.rank);
		}
		Optional<Symbol> undefinedSymbol = board().filter(symbol -> !symbols.accept(symbol)).findAny();
		if (undefinedSymbol.isPresent())
		{
			throw undefinedSymbol.map(InvalidSudoku::undefinedSymbol).get();
		}
		this.symbols = symbols;
	}

	/**
	 * @throws ClassCastException if the other board's symbols are not CharSymbol
	 */
	public OneLine(Board other)
	{
		this.symbols = other.symbols();
		this.rank = other.rank();
		this.board = other.board().map(CharSymbol.class::cast).map(CharSymbol::toString).collect(joining());
	}

	@Override
	public Stream<Symbol> board()
	{
		return this.board.chars().mapToObj(i -> new CharSymbol((char) i));
	}

	@Override
	public String toString()
	{
		return board;
	}

	@Override
	public Symbols symbols()
	{
		return symbols;
	}

	@Override
	public Rank rank()
	{
		return rank;
	}
}
