package kh.edu.rupp.ckcc.myproject;

public class major
{
    private String description;
    private String major_name;
    private String picture;


    public major(String description, String major_name, String picture) {
        this.description = description;
        this.major_name = major_name;
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMajor_name() {
        return major_name;
    }

    public void setMajor_name(String major_name) {
        this.major_name = major_name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
