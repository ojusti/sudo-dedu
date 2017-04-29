package space.casesensitive.dedu;

import org.junit.Assert;
import org.junit.Test;

import space.casesensitive.sudo.InvalidSudoku;
import space.casesensitive.sudo.OneLine;
import space.casesensitive.sudo.Rank;

public class SudokuTest
{
	@Test
	public void solveOneLineBoard() throws InvalidSudoku
	{
		String board = "2.7....38......71..6.1....5...7....2......5.4.5.3...7...5...4.6.7..8..5...3.6.8..";
		String solution = "217459638548623719369178245694715382731892564852346971185937426976284153423561897";
		DeduSolver testee = new DeduSolver();
		OneLine sudoku = new OneLine(board, new Rank(3).predefinedSymbols());
		String actual = new OneLine(testee.solve(sudoku)).toString();
		Assert.assertEquals(solution, actual);
	}
}
