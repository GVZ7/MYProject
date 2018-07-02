package kh.edu.rupp.ckcc.myproject;

public class SingleTon {
    private static SingleTon instance;
    private User user;

    public static SingleTon getInstance() {
        if(instance == null){
            instance = new SingleTon();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
