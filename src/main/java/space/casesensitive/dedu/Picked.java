package space.casesensitive.dedu;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import space.casesensitive.sudo.Symbol;

class Picked
{

	private final Set<Symbol> originalSet;
	private final Set<Symbol> symbols;
	private final Iterator<Symbol> iterator;

	Picked(Set<Symbol> symbols)
	{
		this.originalSet = symbols;
		this.symbols = new HashSet<>(symbols);
		this.iterator = this.symbols.iterator();
	}

	Optional<Symbol> next()
	{
		if (hasNext())
		{
			return Optional.of(iterator.next());
		}
		else
		{
			originalSet.addAll(symbols);
			return Optional.empty();
		}
	}

	boolean hasNext()
	{
		return iterator.hasNext();
	}

}
