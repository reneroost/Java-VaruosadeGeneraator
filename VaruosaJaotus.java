
import java.util.List;

public class VaruosaJaotus {
    private TelefoniMudel mudel;
    private String varuosaLiik;
    private double varuosaOmahind;
    private int teenuseHind;
    private int kogus;
    private List<Integer> jaotus;
    public VaruosaJaotus(
            TelefoniMudel mudel,
            String varuosaLiik,
            double varuosaOmahind,
            int teenuseHind,
            int kogus,
            List<Integer> jaotus) {
        this.mudel = mudel;
        this.varuosaLiik = varuosaLiik;
        this.varuosaOmahind = varuosaOmahind;
        this.teenuseHind = teenuseHind;
        this.kogus = kogus;
        this.jaotus = jaotus;
    }

    public String getVaruosaLiik() {
        return varuosaLiik;
    }

    public void setVaruosaLiik(String varuosaLiik) {
        this.varuosaLiik = varuosaLiik;
    }

    public String getMudeliTaisnimi() {
        return mudel.getTaisnimi();
    }

    public String csvRida() {    
        String rida = 
            this.mudel.getMark() + "," +
            this.mudel.getMudel() + "," +
            this.varuosaLiik + "," +
            this.varuosaOmahind + "," + 
            this.teenuseHind + "," +
            this.kogus;
        for (Integer arv: jaotus) {
            rida += "," + arv; 
        }
        rida += "\n";
        return rida;
    }

    public String tabeliPealkiri() {
        String pealkiri = String.format("%-12s%-20s%-15s%-16s%-15s%-10s",
        "Mark", "Mudel", "Varuosa liik", "Varuosa omahind", "Teenuse hind", "Kogus");
        for (int i = 0; i < jaotus.size(); i++) {
            pealkiri += String.format("%-10s", "Esindus " + (i + 1));
        }
        return pealkiri;
    }

    public String tabeliRida() {
        String rida = String.format("%-12s%-20s%-15s%-16s%-15s%-10s",
                this.mudel.getMark(), this.mudel.getMudel(), this.varuosaLiik, this.varuosaOmahind, this.teenuseHind, this.kogus);
        for (Integer arv: jaotus) {
            rida += String.format("%-10s", arv);
        }
        return rida;
    }
}
