package uz.perevods.perevod.entitiy.authorization;

public class ActivUserCount {
    private String count;
    private String activ;

    public ActivUserCount() {
    }

    public ActivUserCount(String count, String activ) {
        this.count = count;
        this.activ = activ;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getActiv() {
        return activ;
    }

    public void setActiv(String activ) {
        this.activ = activ;
    }
}
