public class Event extends Task {
    private String start;
    private String end;
    public Event(String name, String start, String end) {
        super(name);
        this.start = start;
        this.end = end;
    }

    @Override
    public String getTypeIcon() {
        return "E";
    }

    @Override
    public String getExtraInfo() {
        return (" (from: " + start + " to: " + end + ")");
    }

    @Override //to append from and to
    public String toSaveFormat() {
        return super.toSaveFormat() + " | " + start + " | " + end;
    }
}
