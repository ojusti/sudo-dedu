package space.casesensitive.sudo;

import java.util.stream.Stream;

public interface Board
{

	Rank rank();

	Symbols symbols();

	Stream<Symbol> board();

}
