package Utils;

public enum PostDataType {
    USERNAME("username"),
    BIO("post_desc"),
    TIMESTAMP("post_timestamp"),
    LIKES("likes");

    private final String columnName;

    PostDataType(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
