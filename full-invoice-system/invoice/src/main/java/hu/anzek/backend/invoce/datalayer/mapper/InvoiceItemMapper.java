/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.mapper;


import hu.anzek.backend.invoce.datalayer.dto.InvoiceItemDto;
import hu.anzek.backend.invoce.datalayer.model.InvoiceItem;
import java.util.List;
import org.mapstruct.Mapper;


/**
 *
 * @author User
 */
@Mapper(componentModel = "spring")
public interface InvoiceItemMapper  {
    InvoiceItemDto itemToDto(InvoiceItem invoiceItem);
    InvoiceItem dtoToItem(InvoiceItemDto invoiceItemDto);   
    List<InvoiceItemDto> itemsToDtos(List<InvoiceItem> item);
    List<InvoiceItem> dtosToItems(List<InvoiceItemDto> invoiceDtos);   
}
