package space.casesensitive.dedu;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static space.casesensitive.Booleans.logicalOr;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

import space.casesensitive.sudo.Board;
import space.casesensitive.sudo.InvalidSudoku;
import space.casesensitive.sudo.OneLine;
import space.casesensitive.sudo.Rank;
import space.casesensitive.sudo.Symbols;

class Blocks
{

	private final Rank rank;
	private final Cell[] cells;
	private final Block[] blocks;
	private final Symbols symbols;

	Blocks(Board board)
	{
		this.rank = board.rank();
		this.symbols = board.symbols();
		this.cells = board.stream().map(symbol -> new Cell(symbol, symbols)).toArray(Cell[]::new);
		this.blocks = makeBlocks();
	}

	private Block[] makeBlocks()
	{
		Block[] lines = makeBlocks(this::lineClassifier);
		Block[] columns = makeBlocks(this::columnClassifier);
		Block[] squares = makeBlocks(this::squareClassifier);
		Block[] all = new Block[lines.length + columns.length + squares.length];
		System.arraycopy(lines, 0, all, 0, lines.length);
		System.arraycopy(columns, 0, all, lines.length, columns.length);
		System.arraycopy(squares, 0, all, lines.length + columns.length, squares.length);
		return all;
	}

	private Block[] makeBlocks(Function<Integer, Integer> classifier)
	{
		return range(0, cells.length).boxed().collect(groupingBy(classifier, mapping(i -> cells[i], toList())))
				.values().stream().map(list -> list.toArray(new Cell[0])).map(Block::new)
				.toArray(Block[]::new);
	}

	private int lineClassifier(int cellIndex)
	{
		return cellIndex / rank.symbolsCount();
	}

	private int columnClassifier(int cellIndex)
	{
		return cellIndex % rank.symbolsCount();
	}

	private int squareClassifier(int cellIndex)
	{
		int n = rank.get();
		return (lineClassifier(cellIndex) / n) * n + columnClassifier(cellIndex) / n;
	}

	/**
	 * @return true if at least a symbol is removed from at least a cell
	 */
	boolean pruneSolvedSymbols()
	{
		return stream(blocks).map(block -> block.pruneSolvedSymbols()).collect(logicalOr());
	}

	/**
	 * @return true if at least a new cell is solved
	 */
	boolean solveUniquesSymbols()
	{
		return stream(blocks).map(block -> block.solveUniquesSymbols()).collect(logicalOr());
	}

	@Override
	public String toString()
	{
		return Arrays.toString(cells);
	}

	private boolean isSolved()
	{
		return stream(blocks).allMatch(block -> block.isSolved());
	}

	private Optional<Board> solvedBoard()
	{
		try
		{
			if (isSolved())
			{
				String solution = stream(cells).map(cell -> cell.toString()).collect(joining());
				return Optional.of(new OneLine(solution, symbols));
			}
		}
		catch (InvalidSudoku e)
		{// ignore
		}
		return Optional.empty();

	}

	Optional<Board> solve()
	{
		solveRecursively();
		return solvedBoard();
	}

	private boolean solveRecursively()
	{
		System.out.println("+++++++++++++++++");
		do
		{
			System.out.println(this);
			while (pruneSolvedSymbols())
			{
				System.out.println(this);
			}
		}
		while (solveUniquesSymbols());

		Optional<Cell> unsolved = stream(cells).filter(cell -> !cell.isSolved()).findAny();
		if (!unsolved.isPresent())
		{
			return true;
		}
		while (unsolved.get().pickASymbol())
		{
			if (solveRecursively())
			{
				return true;
			}
		}
		return false;
	}

}
