package hu.anzek.backend.invoce.datalayer.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;

@Entity        
public class InvoiceItem implements Serializable{

    private static final long serialVersionUID = 11L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ez itt a "túloldal"
    // Alább egy szabályozott kapcsolatot látunk a számla főadatokkal:
    // a "JoinColumn" annotáció az adatbázisban lévő forgenKey (idegen kulcs) oszlopát jelöli a Hibernate számára
    // eddig láttuk, hogy ezt valójában magától is létrehozná az ORM eszköz, 
    // de így "magunk/másook/ és a Hibernate" számára is egyértelműen jelezzük a szándékunkat:
    // a "nullable" false jelentése, hogy minden itteni ("InvoiceItem") kapcsolódnia kell egy "invoice" entitáshoz.
    // bár ez explicit módon így van, kíván némi körültekintést!
    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;
    // Itt azért nem hozunk létre kapcsolatot, mert átemeljük a rendelésben megljelölt cikk törzsdatban lévő pillanatnyi állapotát!
    // hiszen most készül a számla, ebben a pillanatban (ez már nem változhat)
    private Long cikk_kod;
    private String megnevezes;
    private String mennyegys;
    private Double afa_kulcs;
    private Double egyseg_ar;
    private Integer mennyiseg;
    private String megjegyzes;

    public InvoiceItem() {
    }

    public InvoiceItem(Invoice invoice,
                       Long cikk_kod,
                       String megnevezes,
                       String mennyegys,
                       Double afa_kulcs,
                       Double egyseg_ar,
                       Integer mennyiseg,
                       String megjegyzes) {
        this.invoice = invoice;
        this.cikk_kod = cikk_kod;
        this.megnevezes = megnevezes;
        this.mennyegys = mennyegys;
        this.afa_kulcs = afa_kulcs;
        this.egyseg_ar = egyseg_ar;
        this.mennyiseg = mennyiseg;
        this.megjegyzes = megjegyzes;
    }

    @Override
    public String toString(){
        return "SzamlaTetel{ - cikk_kod = " + this.cikk_kod + "\n" 
                + "          - szamlaszam = " + this.invoice.getSzamlaszam() + "\n"
                + "          - megnevezes = " + this.megnevezes  + "\n"
                + "          - megjegyzes = " + this.megjegyzes  + "\n"
                + "          - afa_kulcs = " + this.afa_kulcs + " % " + "\n"
                + "          - egyseg_ar = " + this.egyseg_ar + "HUF " + "\n"
                + "          - mennyiseg = " + this.mennyiseg + " " + this.mennyegys + "\n"
                + "        }" + "\n";
    }
    
    public void toConsol(String s){
        System.out.println( s + " " + this.toString());
    }
    
    public Integer getMennyiseg() {
        return mennyiseg;
    }

    public void setMennyiseg(Integer mennyiseg) {
        this.mennyiseg = mennyiseg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Long getCikk_kod() {
        return cikk_kod;
    }

    public void setCikk_kod(Long cikk_kod) {
        this.cikk_kod = cikk_kod;
    }

    public String getMegnevezes() {
        return megnevezes;
    }

    public void setMegnevezes(String megnevezes) {
        this.megnevezes = megnevezes;
    }

    public String getMennyegys() {
        return mennyegys;
    }

    public void setMennyegys(String mennyegys) {
        this.mennyegys = mennyegys;
    }

    public Double getAfa_kulcs() {
        return afa_kulcs;
    }

    public void setAfa_kulcs(Double afa_kulcs) {
        this.afa_kulcs = afa_kulcs;
    }

    public Double getEgyseg_ar() {
        return egyseg_ar;
    }

    public void setEgyseg_ar(Double egyseg_ar) {
        this.egyseg_ar = egyseg_ar;
    }

    public String getMegjegyzes() {
        return megjegyzes;
    }

    public void setMegjegyzes(String megjegyzes) {
        this.megjegyzes = megjegyzes;
    }
        
}
