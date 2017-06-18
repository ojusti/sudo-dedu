package space.casesensitive.dedu;

import static java.util.Collections.singleton;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toCollection;
import static space.casesensitive.dedu.DeduError.bug;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import space.casesensitive.sudo.Symbol;
import space.casesensitive.sudo.Symbols;

class Cell
{
	private static final int BLOCKS = 3;
	private final Set<Symbol> symbols;
	private final Block[] blocks;
	private Picked picked;

	Cell(Symbol given, Symbols symbols)
	{
		if (symbols.isEmpty(given))
		{
			this.symbols = symbols.stream().collect(toCollection(TreeSet::new));
		}
		else
		{
			this.symbols = singleton(given);
		}
		this.blocks = new Block[BLOCKS];
	}

	void partOf(Block block)
	{
		for (int i = 0; i < BLOCKS; i++)
		{
			if (blocks[i] == null)
			{
				blocks[i] = block;
				return;
			}
		}
		throw bug("I should have called this method only " + BLOCKS + " times");
	}

	Optional<Symbol> solved()
	{
		if (isSolved())
		{
			return symbols().findAny();
		}
		else
		{
			return Optional.empty();
		}
	}

	boolean isSolved()
	{
		return symbols.size() == 1;
	}

	Stream<Symbol> symbols()
	{
		return symbols.stream();
	}

	/**
	 * @return true if the symbol is removed from this cell
	 */
	boolean pruneSymbol(Symbol symbol)
	{
		return !isSolved() && symbols.remove(symbol);
	}

	@Override
	public String toString()
	{
		return symbols.stream().map(symbol -> symbol.toString()).collect(joining());
	}

	boolean solveSymbol(Symbol symbol)
	{
		if (!isSolved() && symbols.contains(symbol))
		{
			symbols.retainAll(singleton(symbol));
			return true;
		}
		return false;
	}

	boolean pickASymbol()
	{
		if (picked == null || !picked.hasNext())
		{
			picked = new Picked(symbols);
		}
		return picked.next().map(symbol -> solveSymbol(symbol)).isPresent();
	}
}

//2     , 149  , 7    , 459  , 459 , 459   , 6, 3 , 8,
//3458  , 348  , 48   , 24568, 2345, 234568, 7, 1 , 9,
//389   , 6    , 89   , 1    , 379 , 3789  , 2, 4 , 5,
//
//14689 , 1489 , 14689, 7    , 1459, 145689, 3, 68, 2,
//136789, 12389, 12689, 2689 , 129 , 12689 , 5, 68, 4,
//468   , 5    , 2468 , 3    , 24  , 2468  , 9, 7 , 1,
//
//189   , 1289 , 5    , 29   , 12379, 12379, 4, 29, 6,
//469   , 7    , 2469 , 249  , 8    , 249  , 1, 5 , 3,
//149   , 1249 , 3    , 2459 , 6    , 12459, 8, 29, 7
