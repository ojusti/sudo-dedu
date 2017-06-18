package space.casesensitive.dedu;

import space.casesensitive.sudo.Board;

public class DeduSolver implements Solver
{

	@Override
	public Board solve(Board board)
	{
		return new Blocks(board).solve().get();
	}
}
