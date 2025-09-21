package Assignment;

public class Cell {
    private boolean alive;
    private int age;
    public Cell(boolean alive) {
        this.alive = alive;
        if(alive)
            this.age = 1;
        else
            this.age = 0;
    }
    public boolean isAlive() {
        // Return whether the cell is alive
        return alive;
    }
    public void setAlive() {
        // Set the cell state to Alive
        // Reset age to 1 when a cell becomes alive
        if (!alive) {
            alive = true;
            age = 1;
        }
    }
    public int getAge() {
        // Return the age of the cell
        return age;
    }
    public void setAge(int age) {
        // set the age of the cell
        if (age >= 0) {
            this.age = age;
        }
    }
    public void reset() {
        // set alive to false and age to 0
        alive = false;
        age = 0;
    }
}
