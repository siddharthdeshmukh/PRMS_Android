package sg.edu.nus.iss.phoenix.user.entity;

/**
 * Created by Shubhanshu Gautam (e0146956) on 9/28/2017.
 */

public class Presenter {

    private String id;
    private String name;

    public Presenter(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
