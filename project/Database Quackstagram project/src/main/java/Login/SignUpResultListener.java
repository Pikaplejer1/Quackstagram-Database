package Login;

public interface SignUpResultListener {
    void onSuccess(String username, String password, String bio);
    void onFailure();
}
