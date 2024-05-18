package Utils;

public enum DataType {
    PASSWORD("password"),
    BIO("bio"),
    USERNAME("username");


    private final String columnName;

    DataType(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
