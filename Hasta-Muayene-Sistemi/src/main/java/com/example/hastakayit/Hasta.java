package com.example.hastakayit;

public class Hasta {
    private double visualX;
    private double visualY;
    private Integer hastaNo;
    private String hastaAdi;
    private Integer hastaYasi;
    private String cinsiyet;
    private Boolean mahkumlukDurumBilgisi;
    private Integer engellilikOrani;
    private String kanamaliHastaDurumBilgisi;
    private Double hastaKayitSaati;
    private Double muayeneSaati;
    private Integer muayeneSuresi;
    private Integer oncelikPuani;

    public Hasta(String hastaAdi, Integer hastaYasi, String cinsiyet, Boolean mahkumlukDurumBilgisi,
                 Integer engellilikOrani, String kanamaliHastaDurumBilgisi, Double hastaKayitSaati) {
        this.hastaAdi = hastaAdi;
        this.hastaYasi = hastaYasi;
        this.cinsiyet = cinsiyet;
        this.mahkumlukDurumBilgisi = mahkumlukDurumBilgisi;
        this.engellilikOrani = engellilikOrani;
        this.kanamaliHastaDurumBilgisi = kanamaliHastaDurumBilgisi;
        this.hastaKayitSaati = hastaKayitSaati;
    }


    public Integer getHastaNo() { return hastaNo; }
    public void setHastaNo(Integer hastaNo) { this.hastaNo = hastaNo; }
    public double getVisualX() { return visualX; }
    public void setVisualX(double visualX) { this.visualX = visualX; }
    public double getVisualY() { return visualY; }
    public void setVisualY(double visualY) { this.visualY = visualY; }
    public String getHastaAdi() { return hastaAdi; }
    public void setHastaAdi(String hastaAdi) { this.hastaAdi = hastaAdi; }

    public Integer getHastaYasi() { return hastaYasi; }
    public void setHastaYasi(Integer hastaYasi) { this.hastaYasi = hastaYasi; }

    public String getCinsiyet() { return cinsiyet; }
    public void setCinsiyet(String cinsiyet) { this.cinsiyet = cinsiyet; }

    public Boolean getMahkumlukDurumBilgisi() { return mahkumlukDurumBilgisi; }
    public void setMahkumlukDurumBilgisi(Boolean mahkumlukDurumBilgisi) { this.mahkumlukDurumBilgisi = mahkumlukDurumBilgisi; }

    public Integer getEngellilikOrani() { return engellilikOrani; }
    public void setEngellilikOrani(Integer engellilikOrani) { this.engellilikOrani = engellilikOrani; }

    public String getKanamaliHastaDurumBilgisi() { return kanamaliHastaDurumBilgisi; }
    public void setKanamaliHastaDurumBilgisi(String kanamaliHastaDurumBilgisi) { this.kanamaliHastaDurumBilgisi = kanamaliHastaDurumBilgisi; }

    public Double getHastaKayitSaati() { return hastaKayitSaati; }
    public void setHastaKayitSaati(Double hastaKayitSaati) { this.hastaKayitSaati = hastaKayitSaati; }

    public Double getMuayeneSaati() { return muayeneSaati; }
    public void setMuayeneSaati(Double muayeneSaati) { this.muayeneSaati = muayeneSaati; }

    public Integer getMuayeneSuresi() { return muayeneSuresi; }
    public void setMuayeneSuresi(Integer muayeneSuresi) { this.muayeneSuresi = muayeneSuresi; }

    public Integer getOncelikPuani() { return oncelikPuani; }
    public void setOncelikPuani(Integer oncelikPuani) { this.oncelikPuani = oncelikPuani; }
    @Override
    public String toString() {
        return String.format("Hasta{Ad='%s', Kayıt=%.2f, Öncelik=%d, Muayene=%.2f}",
                hastaAdi, hastaKayitSaati, oncelikPuani, muayeneSaati != null ? muayeneSaati : 0.0);
    }
}