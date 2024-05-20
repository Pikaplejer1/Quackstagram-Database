package Utils;

public enum CredentialsDataType {
    PASSWORD("password"),
    BIO("bio"),
    USERNAME("username");


    private final String columnName;

    CredentialsDataType(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
