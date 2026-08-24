public class Deadline extends Task {
    private String date;
    public Deadline(String name, String date) {
        super(name);
        this.date = date;
    }

    @Override
    public String getTypeIcon() {
        return "D";
    }

    @Override
    public String getExtraInfo() {
        return (" (by: " + date + ")");
    }
}
