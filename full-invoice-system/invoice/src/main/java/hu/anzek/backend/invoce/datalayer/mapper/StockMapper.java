/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hu.anzek.backend.invoce.datalayer.mapper;


import hu.anzek.backend.invoce.datalayer.dto.StockDto;
import hu.anzek.backend.invoce.datalayer.model.Stock;
import java.util.List;
import org.mapstruct.Mapper;


/**
 *
 * @author User
 */
@Mapper(componentModel = "spring")
public interface StockMapper {
    StockDto stockToDto(Stock stock);
    Stock dtoToStock(StockDto stockDto);   
    List<StockDto> stocksToDtos(List<Stock> stocks);
    List<Stock> dtosToStocks(List<StockDto> stockDtos);     
}
