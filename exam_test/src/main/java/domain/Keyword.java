package domain;

public class Keyword {
    private String keyword;
    private String jName;

    public Keyword(String keyword, String jName) {
        this.keyword = keyword;
        this.jName = jName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getjName() {
        return jName;
    }

    public void setjName(String jName) {
        this.jName = jName;
    }

    @Override
    public String toString() {
        return "Keyword{" +
                "keyword='" + keyword + '\'' +
                ", jName='" + jName + '\'' +
                '}';
    }
}
