import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class VaruosadeGeneraator {

    private static String laoseisFail = "mudelid.csv";
    private static List<TelefoniMudel> mudelid = new ArrayList<>();
    private static List<VaruosaJaotus> varuosaJaotused = new ArrayList<>();
    private static String[][] erinevadVaruosad = {
        {"aku", "7"},
        {"akukaas", "5"},
        {"ekraan", "10"},
        {"esikaamera", "4"},
        {"kaamera", "6"},
        {"kaitseklaas", "1"},
        {"kuular", "4"},
        {"laadimispesa", "5"},
    };

    public static void main(String[] args) {
        loeMudelidFailist();
    }

    private static void jaotaVaruosad(int hulk) {
        Random suvaline = new Random();
        int mudel, varuosa;
        int mudeliteArv = mudelid.size();
        int erinevateVaruosadeArv = erinevadVaruosad.length;
        int[][] mudeliVaruosaPaarid = new int[hulk][2];
        for (int i = 0; i < hulk; i++) {
        }
    }

    private static void kirjutaJaotusFaili() {

    }

    private static void loeMudelidFailist() {
        String laoseisFailiTee = "C:\\Users\\roost\\Desktop\\Varuosade Generaator\\" + laoseisFail;
        BufferedReader br = null;
        String rida, csvEraldaja = ",";
        TelefoniMudel mudel;
        
        try {
            br = new BufferedReader((new FileReader(laoseisFailiTee)));
            rida = br.readLine();
            while ((rida = br.readLine()) != null) {
                String[] mudelRida = rida.split(csvEraldaja);
                LocalDate kuupaev = teisendaKuupaev(mudelRida[2]);

                mudel = new TelefoniMudel(
                    mudelRida[0], 
                    mudelRida[1], 
                    kuupaev, 
                    Integer.parseInt(mudelRida[3]));
                mudelid.add(mudel);
            }
        } catch (FileNotFoundException erind) {
            System.out.println("Faili ei leitud.");
        } catch (IOException erind) {
            System.out.println("Rida ei õnnestunud lugeda. Stack trace:");
            erind.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException erind) {
                    System.out.println("Faili ei õnnestunud sulgeda. Stack trace:");
                    erind.printStackTrace();
                }
            }
        }
    }

    public static LocalDate teisendaKuupaev(String kuupaevStringina) {
        DateTimeFormatter vormindaja = DateTimeFormatter.ofPattern("MM.yyyy");
        YearMonth aastaJaKuu = YearMonth.parse(kuupaevStringina, vormindaja);
        LocalDate kuupaev = aastaJaKuu.atDay(1);
        return kuupaev;
    }
}