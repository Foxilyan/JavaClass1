package org.spbstu.morozovnikita;

public class TicTacToeField {
    private final int fieldSize;
    private final char[][] cells;
    private static final char symbol1 = 'x';
    private static final char symbol2 = 'o';
    private static final char emptyCell = 'n';

    public TicTacToeField(int fieldSize){
        this.fieldSize = fieldSize;
        if (fieldSize < 1) {throw new IllegalArgumentException();}
        this.cells = new char[this.fieldSize][this.fieldSize + 2]; // +1 для проверки направления диагонали справа-сверху влево-вниз
        for (int i = 0; i < this.fieldSize; i++){                      // иначе вылет за границы
            for (int j = 0; j < this.fieldSize + 2; j++){              // из-за того, что сам fieldSize не изменяется, на другие методы не повлияет
                this.cells[i][j] = emptyCell;
            }
        }
    }

    private boolean isEmpty(char cell) {
        return cell == emptyCell;
    }

    public char[][] getCells() {
        return this.cells;
    }

    public boolean setCell(char symbol, int x, int y){
        if (symbol == symbol1 || symbol == symbol2){
            if (isEmpty(this.cells[y][x])) {
                this.cells[y][x] = symbol;
                return true;
            }
        }
        return false;
    }

    public boolean removeCell(int x, int y){
        if (this.cells[y][x] == symbol1 || this.cells[y][x] == symbol2) {
            this.cells[y][x] = emptyCell;
            return true;
        }
        return false;
    }

    private int findLongestDirection(int x, int y, int dx, int dy, int[][] cellsCount){
        int length;
        if (cellsCount[x + dx][y + dy] == 1) length = 2;
        else if (x > 1 && cellsCount[x + dx][y + dy] == cellsCount[x + 2*dx][y + 2*dy] + 1)
            length = cellsCount[x + dx][y + dy] + 1;
        else length = 1;
        return length;
    }

    public int findLongest(char symbol){
        if (symbol != symbol1 && symbol != symbol2) {
            return -1;
        } else{
            int maxCountSymbol = 0;
            int[][] cellsCount = new int[this.fieldSize][this.fieldSize + 2];

            if (this.cells[0][0] == symbol) cellsCount[0][0] = 1;
            for (int i = 1; i < this.fieldSize; i++) if (this.cells[i][0] == symbol) cellsCount[i][0] = cellsCount[i-1][0] + 1;
            for (int i = 1; i < this.fieldSize; i++) if (this.cells[0][i] == symbol) cellsCount[0][i] = cellsCount[0][i-1] + 1;

            /*Подряд может идти либо по вертикали, либо по горизонтали, либо по вертикали
             * Проверяем для каждой клетки, с какой из этих возможных сторон идет
             * наибольшая цепочка*/

            for (int i = 1; i < this.fieldSize; i++){
                for (int j = 1; j < this.fieldSize; j++){
                    if (this.cells[i][j] == symbol) {
                        int up = findLongestDirection(i, j, -1, 0, cellsCount);             //сверху вниз
                        int left = findLongestDirection(i, j, 0, -1, cellsCount);           //слева направо
                        int diaLeftDown = findLongestDirection(i, j, -1, -1, cellsCount);   // по диагонали слева-сверху вниз-направо
                        int diaRightDown = findLongestDirection(i, j, -1, +1, cellsCount);  // по диагонали справа-сверху вниз-налево

                        cellsCount[i][j] = Math.max(Math.max(up, left), Math.max(diaLeftDown, diaRightDown));
                        maxCountSymbol = Math.max(maxCountSymbol, cellsCount[i][j]);
                    }
                }
            }

            return maxCountSymbol;
        }
    }
}
