package space.casesensitive.dedu;

import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static space.casesensitive.Booleans.logicalOr;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import space.casesensitive.sudo.Symbol;

class Block
{

	private final Cell[] cells;

	Block(Cell[] cells)
	{
		this.cells = cells;
		Arrays.stream(cells).forEach(cell -> cell.partOf(this));
	}

	/**
	 * @return true if at least a symbol is removed from at least a cell
	 */
	boolean pruneSolvedSymbols()
	{
		return solvedSymbols().map(symbol -> pruneSymbol(symbol)).collect(logicalOr());
	}

	private Stream<Symbol> solvedSymbols()
	{
		return stream(cells).filter(cell -> cell.isSolved()).map(cell -> cell.solved().get());
	}

	boolean pruneSymbol(Symbol symbol)
	{
		String before = toString();
		try
		{
			return stream(cells).map(cell -> cell.pruneSymbol(symbol)).collect(logicalOr());
		}
		finally
		{
			oneRule(before);
		}
	}

	boolean solveUniquesSymbols()
	{
		Collection<List<Symbol>> tallies = stream(cells).flatMap(cell -> cell.symbols())
				.collect(groupingBy(identity())).values();
		// tally.size == 1 if symbol appears in a single cell
		return tallies.stream().filter(tally -> tally.size() == 1).flatMap(List::stream)
				.map(symbol -> solveSymbol(symbol)).collect(logicalOr());
	}

	private boolean solveSymbol(Symbol symbol)
	{
		String before = toString();
		try
		{
			return stream(cells).map(cell -> cell.solveSymbol(symbol)).collect(logicalOr());
		}
		finally
		{
			oneRule(before);
		}
	}

	private void oneRule(String before)
	{
		boolean respected = solvedSymbols().collect(groupingBy(identity())).values().stream()
				.allMatch(tally -> tally.size() == 1);
		if (!respected)
		{
			throw new RuntimeException(before + ":" + toString());
		}
	}

	@Override
	public String toString()
	{
		return Arrays.toString(cells);
	}

	boolean isSolved()
	{
		return stream(cells).allMatch(cell -> cell.isSolved());
	}

}
