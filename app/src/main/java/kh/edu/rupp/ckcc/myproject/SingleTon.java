package kh.edu.rupp.ckcc.myproject;

public class SingleTon {
    private static SingleTon instance;
    private User user;
    private String img;

    public static void setInstance(SingleTon instance) {
        SingleTon.instance = instance;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

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
