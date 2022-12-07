import java.util.ArrayList;

public class Fag {
    int fagnr;
    String fagnavn;
    public ArrayList<Studerende> studerendeListe=new ArrayList<Studerende>();

    Fag()
    {

    }
    Fag(int fagnr, String fagnavn)
    {
        this.fagnr = fagnr;
        this.fagnavn =fagnavn;
    }

    public int getFagnr() {
        return fagnr;
    }

    public void setFagnr(int fagnr) {
        this.fagnr = fagnr;
    }

    public String getFagnavn() {
        return fagnavn;
    }

    public void setFagnavn(String fagnavn) {
        this.fagnavn = fagnavn;
    }
    public void tilmeldStuderende(Studerende s)
    {
        studerendeListe.add(s);
    }

    @Override
    public String toString() {
        return "Fag{" +
                "fagnr=" + fagnr +
                ", fagnavn='" + fagnavn + '\'' +
                '}';
    }
}
