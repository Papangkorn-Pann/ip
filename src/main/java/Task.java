public class Task {

    private static int count = 0;

    private String name;
    private Integer id;
    private Boolean status; //true is done, false is not done

    public Task(String name) {
        count++;
        this.name = name;
        this.id = count;
        this.status = false; //default task completion to false
    }

    public String getName() {
        return this.name;
    }

    public Integer getId() {
        return this.id;
    }

    public String getStatusIcon() {
        return (this.status ? "X" : " "); // mark done task with X
    }

    public void markDone() {
        this.status = true;
    }

    public void unmarkDone() {
        this.status = false;
    }

    public void printTask() {
        System.out.print(this.id);
        System.out.print(". [");
        System.out.print(this.getStatusIcon());
        System.out.print("] ");
        System.out.println(this.name);
    }
}
