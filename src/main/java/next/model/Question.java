package next.model;

import java.util.Date;
import java.util.Objects;

public class Question {
    private long questionId;

    private String writer;

    private String title;

    private String contents;

    private Date createdDate;

    private int countOfComment;

    public Question(String writer, String title, String contents) {
        this(0, writer, title, contents, new Date(), 0);
    }

    public Question(long questionId, String writer, String title, String contents, Date createdDate,
            int countOfComment) {
        this.questionId = questionId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
        this.countOfComment = countOfComment;
    }

    public long getQuestionId() {
        return questionId;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public long getTimeFromCreateDate() {
        return this.createdDate.getTime();
    }

    public int getCountOfComment() {
        return countOfComment;
    }

    public void addCommentCount() {
        this.countOfComment++;
    }

    public boolean isWrittenBy(User user) {
        if (user == null) {
            return false;
        }

        return writer.equals(user.getName());
    }

    public boolean isNotWrittenBy(User user) {
        return !isWrittenBy(user);
    }

    @Override
    public String toString() {
        return "Question [questionId=" + questionId + ", writer=" + writer + ", title=" + title + ", contents="
                + contents + ", createdDate=" + createdDate + ", countOfComment=" + countOfComment + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (questionId ^ (questionId >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Question other = (Question) obj;
        if (questionId != other.questionId)
            return false;
        return true;
    }

    public void update(String title, String contents) {
        Objects.requireNonNull(title, "title");
        Objects.requireNonNull(contents, "contents");

        this.title = title;
        this.contents = contents;
    }
}
