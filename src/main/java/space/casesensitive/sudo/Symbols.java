package space.casesensitive.sudo;

import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.Set;

public class Symbols
{

	private final Set<Symbol> symbols;
	private final Symbol empty;
	final Rank rank;

	public Symbols(Symbol empty, Symbol... symbols) throws InvalidSudoku
	{
		this.empty = empty;
		this.symbols = Arrays.stream(symbols).filter(symbol -> !symbol.equals(empty)).collect(toSet());
		this.rank = Rank.forSymbolsCount(this.symbols.size());
	}

	boolean accept(Symbol candidate)
	{
		return empty.equals(candidate) || symbols.contains(candidate);
	}

}
