package Utils;

public enum DataType {
    PASSWORD("password"),
    BIO("bio");

    private final String columnName;

    DataType(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
