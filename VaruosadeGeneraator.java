import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class VaruosadeGeneraator {

    private static String mudelidFail = "mudelid.csv";
    private static String laoseisValjundFail = "laoseis.csv";
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
        Scanner klaviatuur = new Scanner(System.in);
        System.out.println("Varuosade Generaator");
        System.out.print("Artikliridade hulk: ");
        int artikliRidu = klaviatuur.nextInt();
        System.out.print("Esinduste hulk: ");
        int esindusteHulk = klaviatuur.nextInt();
        klaviatuur.close();
        loeTelefoniMudelidFailist();
        looVaruosaJaotused(artikliRidu, esindusteHulk);
        sorteeriVaruosaJaotused();
        kuvaVaruosaJaotused();
        kirjutaVaruosaJaotusedCsvFaili();
    }

    private static void looVaruosaJaotused(int erinevaidVaruosi, int esindusteHulk) {
        Random suvaline = new Random();
        int[][] mudelVaruosaPaarid = genereeriMassiivAinulaadseteArvupaaridega(erinevaidVaruosi, mudelid.size(), erinevadVaruosad.length);
        for (int i = 0; i < mudelVaruosaPaarid.length; i++) {
            int mudelJrk = mudelVaruosaPaarid[i][0];
            int varuosaJrk = mudelVaruosaPaarid[i][1];
            TelefoniMudel mudel = mudelid.get(mudelJrk);
            String varuosaLiik = erinevadVaruosad[varuosaJrk][0];
            int varuosaJaTooteHinnasuhe = Integer.parseInt(erinevadVaruosad[varuosaJrk][1]);
            int teenuseHind = arvutaTeenusehind(mudel, varuosaJaTooteHinnasuhe);
            double omaHind = arvutaVaruosaOmahind(teenuseHind);
            int kogus = suvaline.nextInt(30);
            List<Integer> jaotus = jaotaKogusJagudeVahelJuhuslikult(kogus, esindusteHulk);
            VaruosaJaotus varuosaJaotus = new VaruosaJaotus(
                mudel, 
                varuosaLiik, 
                omaHind, 
                teenuseHind, 
                kogus, 
                jaotus);
            varuosaJaotused.add(varuosaJaotus);
        }
    }

    private static void sorteeriVaruosaJaotused() {
        Collections.sort(varuosaJaotused, new Comparator<VaruosaJaotus>() {
            @Override
            public int compare(final VaruosaJaotus artikkel1, final VaruosaJaotus artikkel2) {
                return artikkel1.getMudeliTaisnimi().compareTo(artikkel2.getMudeliTaisnimi());
            }
        });
    }

    private static void kuvaVaruosaJaotused() {
        System.out.println(varuosaJaotused.get(0).tabeliPealkiri());
        for (VaruosaJaotus varuosaJaotus: varuosaJaotused) {
            System.out.println(varuosaJaotus.tabeliRida());
        }
    }

    private static void kirjutaVaruosaJaotusedCsvFaili() {
        File csvValjundFail = new File(laoseisValjundFail);
        try (PrintWriter kirjuti = new PrintWriter(csvValjundFail)) {
            StringBuilder sb = new StringBuilder();
            // sb.append("SEP=,\n");     // et excel kuvaks korrekselt
            for (VaruosaJaotus varuosaJaotus: varuosaJaotused) {
                sb.append(varuosaJaotus.csvRida());
            }
            kirjuti.write(sb.toString());
        } catch (FileNotFoundException erind) {
            System.out.println("Väljundfaili ei leitud.");
        }

    }

    private static void loeTelefoniMudelidFailist() {
        String laoseisFailiTee = "C:\\Users\\roost\\Documents\\Java-VaruosadeGeneraator\\" + mudelidFail;
        BufferedReader br = null;
        String csvEraldaja = ",";
        
        try {
            br = new BufferedReader((new FileReader(laoseisFailiTee)));
            String rida = br.readLine();
            while ((rida = br.readLine()) != null) {
                String[] mudelRida = rida.split(csvEraldaja);
                LocalDate kuupaev = teisendaKuupaevStringtoLocalDate(mudelRida[2]);

                TelefoniMudel mudel = new TelefoniMudel(
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

    private static int arvutaTeenusehind(TelefoniMudel mudel, int hinnasuhe) {
        int teenuseHind = (int) Math.round(mudel.getHind() * hinnasuhe * 0.02);
        teenuseHind = teenuseHind + (10 - teenuseHind % 10) - 1;    // ümardab üles lähima 9-ni
        return teenuseHind;
    }

    private static double arvutaVaruosaOmahind(int teenuseHind) {
        Random suvaline = new Random();
        double minOsakaalTeenusehinnast = 0.4;
        double maxOsakaalTeenusehinnast = 0.7;
        double osakaalTeenusehinnast = minOsakaalTeenusehinnast + (suvaline.nextFloat() * (maxOsakaalTeenusehinnast - minOsakaalTeenusehinnast));
        double omahind = teenuseHind * osakaalTeenusehinnast;
        omahind = Math.round(omahind * 100.0) / 100.0;
        return omahind;
    }

    private static int[][] genereeriMassiivAinulaadseteArvupaaridega(int hulk, int vahemik1, int vahemik2) {
        Random suvaline = new Random();
        int[][] paarid = new int[hulk][2];
        int i = 0;
        while (i < hulk) {
            int suvalineArv1 = suvaline.nextInt(vahemik1);
            int suvalineArv2 = suvaline.nextInt(vahemik2);
            boolean paarOlemas = false;
            for (int j = 0; j < i; j++) {
                if (paarid[j][0] == suvalineArv1 && paarid[j][1] == suvalineArv2) {
                    paarOlemas = true;
                }
            }
            if (!paarOlemas) {
                paarid[i][0] = suvalineArv1;
                paarid[i][1] = suvalineArv2;
                i++;
            }
        }
        return paarid;
    }

    private static List<Integer> jaotaKogusJagudeVahelJuhuslikult(int kogus, int jagudeHulk) {
        Random suvaline = new Random();
        List<Integer> jaotus = new ArrayList<>();
        while (jaotus.size() < jagudeHulk) {
            jaotus.add(0);
        }
        for (int i = 0; i < kogus; i++) {
            int suvalineArv = suvaline.nextInt(jagudeHulk);
            jaotus.set(suvalineArv, jaotus.get(suvalineArv) + 1);
        }
        return jaotus;
    }

    public static LocalDate teisendaKuupaevStringtoLocalDate(String kuupaevStringina) {
        DateTimeFormatter vormindaja = DateTimeFormatter.ofPattern("MM.yyyy");
        YearMonth aastaJaKuu = YearMonth.parse(kuupaevStringina, vormindaja);
        LocalDate kuupaev = aastaJaKuu.atDay(1);
        return kuupaev;
    }
}