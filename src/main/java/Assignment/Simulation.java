package Assignment;
import java.io.*;

/**
 *  The simulator. This tracks the cells in a grid
 *  and coordinates that with the display.
 *  @author Abul Hasnat Mohammad Istiaqullah
 */
public class Simulation {

      // The grid that holds the cell data.

    private DynamicArray<DynamicArray<Cell>> grid;
//      The number of rows the grid has.
    private int rows;
     // The number of cols the grid has.
    private int cols;
    // The  number of generations this sim has gone through.
    private int generations;
    /**
     *  Main constructor.
     *  Initialize the instance variables.
     *  @param rows the number of rows in the grid
     *  @param cols the number of columns in the grid
     */
    public Simulation(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.generations = 0;
        initializeGrid();
    }
    /**
     *  Helper method to initialize the grid of Cells.
     */
    private void initializeGrid() {
        // the grid is DynamicArray OF DynamicArrays OF Cells
        grid = new DynamicArray<>();
        for (int r = 0; r < rows; r++) {
            DynamicArray<Cell> row = new DynamicArray<>();
            for (int c = 0; c < cols; c++) {
                row.add(new Cell(false));
            }
            grid.add(row);}

    }
    /**
     *  DO NOT CHANGE THIS, FOR GRADING PURPOSE ONLY.
     *  @return grid for automatic testing
     */
    public DynamicArray<DynamicArray<Cell>> getGrid() {
        return grid;
    }

    /**
     *  This is called when the user interacts with the grid.
     *  Sets the cell at row/col to Alive
     *  Does NOT change cell from Alive to Dead
     *  @param row the row where the action happened
     *  @param col the column where the action happened
     */
    public void toggleCell(int row, int col) {

        if(row < 0 || row >= rows || col < 0 || col >= cols)
            return;//for invalid input
        Cell cell = grid.get(row).get(col);
        if(!cell.isAlive()) {
            cell.setAlive();
        }
    }

    /**
     *  Method to handle the evolution of the grid by ONE generation.
     */
    public void evolve() {
        // Rules:
        // A live cell with fewer than 2 live neighbors dies, as if by underpopulation.
        // A live cell with 2 or 3 live neighbors survives to the next generation.
        // A live cell with more than 3 live neighbors dies, as if by overpopulation.
        // A dead cell with exactly 3 live neighbors becomes a live cell, as if by reproduction.
        // if a cell is on the border or corner of the grid, it will naturally have less total possible neighbors
        // e.g. a corner cell has only 3 total neighbors

        DynamicArray<DynamicArray<Cell>> newGrid = new DynamicArray<>();
        for (int r = 0; r < rows; r++) {
            DynamicArray<Cell> newRow = new DynamicArray<>();

            for (int c = 0; c < cols; c++) {
                Cell oldCell = grid.get(r).get(c);
                int liveNeighbors = countLiveNeighbors(r, c);
                Cell newCell = new Cell( false);
                if (oldCell.isAlive()) {
                    if (liveNeighbors == 2 || liveNeighbors == 3) {
                        newCell.setAlive();
                        newCell.setAge(oldCell.getAge() + 1);
                    }
                } else {
                    if (liveNeighbors == 3) {
                        newCell.setAlive();
                        newCell.setAge(1);
                    }
                }
                newRow.add(newCell);
            }
            newGrid.add(newRow);
        }
        grid = newGrid;
        generations++;
    }

    /**
     *  Helper method to count the live neighbors of a specific cell at row/col
     *
     *  @param row the row of the cell in question
     *  @param col the col of the cell in question
     */
    public int countLiveNeighbors(int row, int col) {
        // Neighbors include the eight surrounding cells:
        //   Directly adjacent cells (north, south, east, west).
        //   Diagonal cells (northeast, northwest, southeast, southwest).

        int count = 0;
        // Check north
        if (row > 0 && grid.get(row - 1).get(col).isAlive()) count++;
// Check south
        if (row < rows - 1 && grid.get(row + 1).get(col).isAlive()) count++;
// Check west
        if (col > 0 && grid.get(row).get(col - 1).isAlive()) count++;
// Check east
        if (col < cols - 1 && grid.get(row).get(col + 1).isAlive()) count++;
// Check northwest
        if (row > 0 && col > 0 && grid.get(row - 1).get(col - 1).isAlive()) count++;
// Check northeast
        if (row > 0 && col < cols - 1 && grid.get(row - 1).get(col + 1).isAlive()) count++;
// Check southwest
        if (row < rows - 1 && col > 0 && grid.get(row + 1).get(col - 1).isAlive()) count++;
// Check southeast
        if (row < rows - 1 && col < cols - 1 && grid.get(row + 1).get(col + 1).isAlive()) count++;

        return count;

    }

    /**
     *  Reset all cells in the grid. Generation count should also reset to zero.
     */
    public void reset() {
        // note that Cell objects have a reset() method
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid.get(r).get(c).reset();
            }
        }
        generations = 0;

    }

    /**
     *  Returns the count of live cells in the grid.
     */
    public int getAliveCells() {
        int aliveCount = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid.get(r).get(c).isAlive())
                    aliveCount++;
            }
        }
        return aliveCount;
    }

    /**
     *  Returns the average age of all ALIVE cells in the grid.
     */
    public double getAverageAge() {
        // dead cells DO NOT COUNT
        int aliveCount = 0;
        int totalAge = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid.get(r).get(c).isAlive()) {
                    aliveCount++;}
                totalAge += grid.get(r).get(c).getAge();
            }
        }

        if(aliveCount == 0) {
            return 0.0;
        } else {
            return (double) totalAge / aliveCount;
        }
    }

    /**
     *  Returns the maximum age of all ALIVE cells in the grid.
     */
    public int getMaxAge() {
        int maxAge = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = grid.get(r).get(c);
                if (cell.isAlive()) {
                    maxAge = Math.max(maxAge, cell.getAge());
                }
            }
        }
        return maxAge;
    }

    /**
     *  Returns the number of generations the simulation has gone through.
     */
    public int getGenerations() {
        return generations;
    }

    /**
     *  Parses an RLE file into a 2d array of Boolean variables.
     *  This method is key to loading template data from external files.
     *  see https://conwaylife.com/wiki/Run_Length_Encoded for more info on this format.
     */
    public void parseRle(DynamicArray<String> lines) {
        StringBuilder rleData = new StringBuilder();
        int width = 10, height = 10; // default these to 10, update to correct value below based on file

        for (String line : lines) {
            if (line.startsWith("x")) { // Header line
                String[] parts = line.split(",");
                for (String part : parts) {
                    part = part.trim();
                    if (part.startsWith("x")) {
                        width = Integer.parseInt(part.split("=")[1].trim());
                        // extract and assign width value
                    } else if (part.startsWith("y")) {
                        height = Integer.parseInt(part.split("=")[1].trim());
                        // extract and assign height value
                    }
                }
            } else {
                rleData.append(line);
            }
        }

        // declaring boolean array for the pattern data
        boolean[][] pattern = new boolean[height][width];
        String rle = rleData.toString();
        int row = 0, col = 0;
        int count = 0;
        StringBuilder str = new StringBuilder();
        for (int r = 0; r < rle.length(); r++) {
            char ch = rle.charAt(r);
            if (Character.isDigit(ch)) {
                str.append(ch);
            } else if (ch == 'b' || ch == 'o') {
                if (str.length() > 0) {
                    count = Integer.parseInt(str.toString());
                } else {
                    count = 1;
                }
                str.setLength(0);
                for (int i = 0; i < count; i++) {
                    if (col < width && row < height) {
                        pattern[row][col] = (ch == 'o');
                        col++;
                    }
                }
            } else if (ch == '$') {
                if (str.length() > 0) {
                    count = Integer.parseInt(str.toString());
                } else {
                    count = 1;
                }
                str.setLength(0);
                row += count;
                col = 0;
            } else if (ch == '!') {
                break;
            }
        }
        // Apply the pattern to the grid
        applyPatternToGrid(pattern);
    }

    /**
     *  Translates boolean 2d array into actual cell data in our grid.
     */
    public void applyPatternToGrid(boolean[][] pattern) {
        // grid gets reset first
        reset();

        // startRow and startCol are the offsets into the grid.
        int startRow = rows / 2 - pattern.length / 2;
        int startCol = cols / 2 - pattern[0].length / 2;

        for (int r = 0; r < pattern.length; r++) {
            for (int c = 0; c < pattern[0].length; c++) {
                if (pattern[r][c]) {
                    int gridRow = startRow + r;
                    int gridCol = startCol + c;
                    if (gridRow >= 0 && gridRow < rows && gridCol >= 0 && gridCol < cols) {
                        grid.get(gridRow).get(gridCol).setAlive();
                    }
                }
            }
        }

    }

    /**
     * This is provided as an optional way to load RLE files if you want to do testing in main.
     * This is basically a copy of the loadRleFile method in GameOfLife class, but without any GUI stuff.
     *
     * @param filename relative path to the file (/sample-rle-files/blah.rle)
     */
    private void loadRleFile(String filename) {
        File file = new File(filename);
        try {
            DynamicArray<String> lines = new DynamicArray<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.startsWith("#")) { // Ignore comments
                        lines.add(line);
                    }
                }
                parseRle(lines);
            }
        } catch (IOException e) {
        }
    }
    // For detecting repeating states
    private DynamicArray<String> previousStates = new DynamicArray<>();

    private String serializeGrid() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if(grid.get(r).get(c).isAlive()) {
                    sb.append('1');
                } else {
                    sb.append('0');
                }
            }
        }
        return sb.toString();
    }
// Returns true if the current state has been seen before
    public boolean isRepeatingState() {
        String current = serializeGrid();
        if (previousStates.contains(current)) {
            return true;
        }
        previousStates.add(current);
        return false;
    }


    /**
     * Gets the number of rows in the simulation grid.
     *
     * @return the number of rows in the grid
     */
    public int getRows() {
        return rows;
    }

    /**
     * Sets the number of rows in the simulation grid.
     * @param rows the new number of rows
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * Gets the number of columns in the simulation grid.
     *
     * @return the number of columns in the grid
     */
    public int getCols() {
        return cols;
    }

    /**
     * Sets the number of columns in the simulation grid.
     *
     * @param cols the new number of columns
     */
    public void setCols(int cols) {
        this.cols = cols;
    }

    public static void main(String args[]) {
        // simple test code
        Simulation sim = new Simulation(50, 50);
        sim.toggleCell(1,1);

        // if the cell at 1,1 is alive, you did good
        if(sim.grid.get(1).get(1).isAlive() == true) {
            System.out.println("Yay 1");
        }
        // the number of alive cells should be 1
        if(sim.getAliveCells() == 1) {
            System.out.println("Yay 2");
        }

        // progress the sim by one generation
        sim.evolve();

        // the cell at 1,1 should now be dead (starvation rule)
        if(sim.grid.get(1).get(1).isAlive() == false) {
            System.out.println("Yay 3");
        }
        // the number of alive cells should be zero
        if(sim.getAliveCells() == 0) {
            System.out.println("Yay 4");
        }
        // generation counter should have advanced
        if(sim.getGenerations() == 1) {
            System.out.println("Yay 5");
        }
    }
}
