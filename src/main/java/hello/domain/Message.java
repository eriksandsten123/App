package hello.domain;

public class Message {
    private OnlineUser from;
    private String text;

    public OnlineUser getFrom() {
        return from;
    }

    public void setFrom(final OnlineUser from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }
}