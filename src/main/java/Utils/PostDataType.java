package Utils;

public enum PostDataType {
    USERNAME("username"),
    BIO("bio"),
    TIMESTAMP("timestamp"),
    LIKES("likes");

    private final String columnName;

    PostDataType(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
