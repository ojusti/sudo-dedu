package space.casesensitive.dedu;

import org.junit.Assert;
import org.junit.Test;

import space.casesensitive.sudo.InvalidSudoku;
import space.casesensitive.sudo.OneLine;
import space.casesensitive.sudo.Rank;

public class MultipleSolutionsTest
{

	@Test
	public void solve_board_with_two_solutions() throws InvalidSudoku
	{
		String board = "..343412..434321";
		String solution = "1234341221434321";
		DeduSolver testee = new DeduSolver();
		OneLine sudoku = new OneLine(board, new Rank(2).predefinedSymbols());
		String actual = new OneLine(testee.solve(sudoku)).toString();
		Assert.assertEquals(solution, actual);
	}
}
