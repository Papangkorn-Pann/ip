public class Task {

    private String name;
    private Boolean status;

    public Task(String name) {
        this.name = name;
        this.status = false;
    }

    public String getName() {
        return this.name;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public String getTypeIcon() {
        return " ";
    }

    public String getStatusIcon() {
        return (this.status ? "X" : " ");
    }

    public void markDone() {
        this.status = true;
    }

    public void unmarkDone() {
        this.status = false;
    }

    public String getExtraInfo() {
        return "";
    }

    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + getName() + getExtraInfo();
    }

    public String toSaveFormat() {
        return getTypeIcon() + " | " + (getStatus() ? "1" : "0") + " | " + getName();
    }
}
