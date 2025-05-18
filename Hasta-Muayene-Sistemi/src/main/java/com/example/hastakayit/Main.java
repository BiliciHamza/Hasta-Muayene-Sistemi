package com.example.hastakayit;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main extends Application {
    private double currentTime = 8.00;
    private double mesaiBaslangic = 9.00;
    private List<Hasta> bekleyenHastalar = new ArrayList<>();
    private Hasta suAnkiHasta = null;
    private double suAnkiHastaBitisSaati = 0.0;
    private MaxHeap heap = new MaxHeap(100);
    private HeapTreePane heapTreePane;
    private List<Hasta> tumHastalar;
    private int hastaNoCounter = 1;

    @FXML
    private TextArea Bilgiler;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Text siradakiHastaBilgisi;

    @FXML
    private Text bekleyenHastaSayisi;

    @FXML
    private Text durumBilgisi;

    @Override
    public void start(Stage primaryStage) throws IOException {
        tumHastalar = new ArrayList<>();
        heapTreePane = new HeapTreePane(heap);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hastakayit/Main.fxml"));
        fxmlLoader.setController(this);
        Scene scene = new Scene(fxmlLoader.load());

        scrollPane.setContent(heapTreePane);

        primaryStage.setTitle("Hastane Öncelik Sistemi");
        primaryStage.setScene(scene);
        primaryStage.show();

        hastaKayitlariniOku("Hasta.txt");
        heapGorselGuncelle();
        bilgileriGuncelle();
    }

    @FXML
    public void initialize() {
        if (scrollPane != null) {
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
        }
    }

    private void heapGorselGuncelle() {
        heapTreePane.drawHeap();
        bilgileriGuncelle();
    }

    private void hastaKayitlariniOku(String dosyaAdi) {
        try (BufferedReader br = new BufferedReader(new FileReader(dosyaAdi))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                line = line.replace("*", "").replace(",", " ").trim();

                String[] parts = line.split("\\s+");

                if (parts.length >= 7) {
                    try {
                        String hastaAdi = parts[0].trim();
                        int hastaYasi = Integer.parseInt(parts[1].trim());
                        String cinsiyet = parts[2].trim();
                        boolean mahkumluk = parts[3].trim().equalsIgnoreCase("true");
                        int engellilikOrani = Integer.parseInt(parts[4].trim());
                        String kanamaDurumu = parts[5].trim();

                        String[] saatDakika = parts[6].trim().split("\\.");
                        double kayitSaati = saatDakikaToDouble(
                                Integer.parseInt(saatDakika[0]),
                                Integer.parseInt(saatDakika[1])
                        );

                        Hasta hasta = new Hasta(
                                hastaAdi,
                                hastaYasi,
                                cinsiyet,
                                mahkumluk,
                                engellilikOrani,
                                kanamaDurumu,
                                kayitSaati
                        );

                        if (kayitSaati >= 8.00 && kayitSaati < 9.00) {
                            hastaKayitEkle(hasta);
                        } else {
                            bekleyenHastalar.add(hasta);
                        }

                    } catch (NumberFormatException e) {
                        System.err.println("Hatalı veri formatı: " + line);
                        e.printStackTrace();
                    }
                } else {
                    System.err.println("Yetersiz veri: " + line);
                }
            }
            bekleyenHastalar.sort((h1, h2) ->
                    Double.compare(h1.getHastaKayitSaati(), h2.getHastaKayitSaati()));
        } catch (IOException e) {
            System.err.println("Dosya okuma hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void hastaKayitEkle(Hasta hasta) {
        hasta.setHastaNo(hastaNoCounter++);
        hasta.setOncelikPuani(oncelikPuaniHesapla(hasta));
        hasta.setMuayeneSuresi(muayeneSuresiHesapla(hasta));
        tumHastalar.add(hasta);
        heap.insert(hasta);
    }

    private Integer oncelikPuaniHesapla(Hasta hasta) {
        int puan = 0;

        if (hasta.getHastaYasi() >= 65) puan += 25;
        else if (hasta.getHastaYasi() >= 45) puan += 15;
        else if (hasta.getHastaYasi() >= 20) puan += 5;

        if (hasta.getMahkumlukDurumBilgisi()) puan += 50;
        puan += hasta.getEngellilikOrani();

        switch (hasta.getKanamaliHastaDurumBilgisi()) {
            case "agirKanama": puan += 50; break;
            case "kanama": puan += 20; break;
        }

        return puan;
    }

    private Integer muayeneSuresiHesapla(Hasta hasta) {
        int sure = 10;

        if (hasta.getHastaYasi() >= 65) sure += 5;
        sure += hasta.getEngellilikOrani() / 10;

        switch (hasta.getKanamaliHastaDurumBilgisi()) {
            case "agirKanama": sure += 20; break;
            case "kanama": sure += 10; break;
        }

        return sure;
    }

    @FXML
    private void sonrakiHastaButonu() {
        if (suAnkiHasta != null) {
            currentTime = suAnkiHastaBitisSaati;
        }

        yeniKayitlariKontrolEt();

        if (!heap.isEmpty()) {
            suAnkiHasta = heap.remove();
            double muayeneBaslangic = Math.max(mesaiBaslangic, currentTime);
            suAnkiHasta.setMuayeneSaati(muayeneBaslangic);

            suAnkiHastaBitisSaati = muayeneBaslangic +
                    (suAnkiHasta.getMuayeneSuresi() / 60.0);

            String log = String.format("Muayeneye alınan hasta: %s%n" +
                            "Öncelik Puanı: %d%n" +
                            "Muayene Başlangıç: %s%n" +
                            "Tahmini Bitiş: %s%n",
                    suAnkiHasta.getHastaAdi(),
                    suAnkiHasta.getOncelikPuani(),
                    formatSaat(muayeneBaslangic),
                    formatSaat(suAnkiHastaBitisSaati));

            Bilgiler.appendText(log + "\n");

            heapGorselGuncelle();
        }
    }

    private String formatSaat(double saat) {
        int tamSaat = (int) saat;
        int dakika = (int) ((saat - tamSaat) * 60);
        return String.format("%02d:%02d", tamSaat, dakika);
    }

    private double saatDakikaToDouble(int saat, int dakika) {
        return saat + (dakika / 60.0);
    }
    private void yeniKayitlariKontrolEt() {
        Iterator<Hasta> iterator = bekleyenHastalar.iterator();
        while (iterator.hasNext()) {
            Hasta hasta = iterator.next();
            if (hasta.getHastaKayitSaati() <= currentTime) {
                hastaKayitEkle(hasta);
                iterator.remove();
            } else {
                break;
            }
        }
    }

    private void bilgileriGuncelle() {
        if (suAnkiHasta != null) {
            siradakiHastaBilgisi.setText(String.format(
                    "Mevcut Hasta: %s (Bitiş: %s)",
                    suAnkiHasta.getHastaAdi(),
                    formatSaat(suAnkiHastaBitisSaati)
            ));
        } else if (heap.isEmpty()) {
            Hasta siradaki = heap.peek();
            siradakiHastaBilgisi.setText(String.format(
                    "Sıradaki Hasta: %s (Öncelik: %d)",
                    siradaki.getHastaAdi(),
                    siradaki.getOncelikPuani()
            ));
        } else {
            siradakiHastaBilgisi.setText("Bekleyen Hasta Yok");
        }

        bekleyenHastaSayisi.setText(String.format(
                "Dosyada kalan hasta sayısı: %d | Sırada Bekleyen: %d | Saat: %s",
                bekleyenHastalar.size(),
                heap.getSize(),
                formatSaat(currentTime)
        ));

        if (currentTime < mesaiBaslangic) {
            durumBilgisi.setText("Mesai başlangıcı bekleniyor (09:00)");
        } else if (suAnkiHasta != null) {
            durumBilgisi.setText(String.format(
                    "Muayene devam ediyor - Bitiş: %s",
                    formatSaat(suAnkiHastaBitisSaati)
            ));
        } else {
            durumBilgisi.setText("Yeni hasta muayeneye alınabilir");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}