package hu.anzek.backend.invoce.datalayer.dto;

import hu.anzek.backend.invoce.datalayer.model.Invoice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItemDto {
    private Long id;
    private Invoice invoice;
    private Long cikk_kod;
    private String megnevezes;
    private String mennyegys;
    private Double afa_kulcs;
    private Double egyseg_ar;
    private Integer mennyiseg;
    private String megjegyzes;    
}
