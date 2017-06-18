package space.casesensitive.dedu;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import space.casesensitive.sudo.InvalidSudoku;
import space.casesensitive.sudo.OneLine;
import space.casesensitive.sudo.Rank;

@RunWith(Parameterized.class)
public class Sudoku3Test
{
	@Parameters
	public static String[][] boardsAndSolutions()
	{
		return new String[][] {
				{ "2.7....38......71..6.1....5...7....2......5.4.5.3...7...5...4.6.7..8..5...3.6.8..",
						"217459638548623719369178245694715382731892564852346971185937426976284153423561897" },
				{ "...7..3.....4..951.......4..21..9..645..3..799..6..42..4.......893..1.....2..4...",
						"514796382267483951389125647721549836456832179938617425645978213893251764172364598" },
				{ ".......1......2..3...4...........5..4.16.......71......5....2......8..4..3.91....",
						"745368912819572463362491857693824571421657398587139624158746239976283145234915786" } };
	}

	@Parameter(0)
	public String board;
	@Parameter(1)
	public String solution;

	@Test
	public void solve_well_formed_sudoku() throws InvalidSudoku
	{
		DeduSolver testee = new DeduSolver();
		OneLine sudoku = new OneLine(board, new Rank(3).predefinedSymbols());
		String actual = new OneLine(testee.solve(sudoku)).toString();
		Assert.assertEquals(solution, actual);
	}

}
