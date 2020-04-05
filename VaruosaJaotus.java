
import java.util.List;

public class VaruosaJaotus {
    private String tootemark;
    private String mudel;
    private String varuosaLiik;
    private int kogus;
    private int omaHind;
    private int myygiHind;
    private List<Integer> jaotus;

    public VaruosaJaotus(
            String tootemark,
            String mudel,
            String varuosaLiik,
            int kogus,
            int omaHind,
            int myygiHind,
            List<Integer> jaotus) {
        this.tootemark = tootemark;
        this.mudel = mudel;
        this.varuosaLiik = varuosaLiik;
        this.kogus = kogus;
        this.omaHind = omaHind;
        this.myygiHind = myygiHind;
        this.jaotus = jaotus;
    }

    public String getTootemark() {
        return tootemark;
    }

    public void setTootemark(String tootemark) {
        this.tootemark = tootemark;
    }

    public String getVaruosaLiik() {
        return varuosaLiik;
    }

    public void setVaruosaLiik(String varuosaLiik) {
        this.varuosaLiik = varuosaLiik;
    }

    public static String tabeliPealkiri() {
        return String.format("%-12s%-15s%-15s%-10s%-10s%-10s",
                "Mark", "Mudel", "Liik", "Kogus", "Omahind", "Müügihind");
    }

    public String tabeliRida() {
        return String.format("%-12s%-15s%-15s%-10s%-10s%-10s",
                this.tootemark, this.mudel, this.varuosaLiik, this.kogus, this.omaHind, this.myygiHind);
    }
}
