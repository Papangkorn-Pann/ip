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

    public Boolean getStatus() {
        return this.status;
    }

    public String getTypeIcon() {
        return " ";
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
        System.out.println(getId() + ". [" + getTypeIcon() + "]["
                + getStatusIcon() + "] " + getName()
                + getExtraInfo());
    }

    public String getExtraInfo(){
        return "";
    }

    public String toSaveFormat() {
        return getTypeIcon() + " | " + (getStatus() ? "1" : "0") + " | " + getName();
    }

}
