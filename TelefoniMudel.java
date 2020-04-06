import java.time.LocalDate;

public class TelefoniMudel {

    private String taisnimi;
    private String mark;
    private String mudel;
    private LocalDate valjatulekuKp;
    private int hind;

    public TelefoniMudel (String mark, String mudel, LocalDate valjatulekuKp, int hind) {
        this.taisnimi = mark + " " + mudel;
        this.mark = mark;
        this.mudel = mudel;
        this.valjatulekuKp = valjatulekuKp;
        this.hind = hind;
    }

    public String getMark() {
        return mark;
    }
    
    public String getMudel() {
        return mudel;
    }

    public String getTaisnimi() {
        return taisnimi;
    }

    public int getHind() {
        return hind;
    }
}