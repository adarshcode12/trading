package com.trading.exper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class StockFileService {

    @Autowired
    private StockFileRepository stockFileRepository;

    // Method to get stock files by symbol and date
    public List<StockFile> getStockFilesBySymbolAndDate(String symbol, LocalDate date) {
        return stockFileRepository.findBySymbolAndDate(symbol, date);
    }

    // Fetch the CSV file path for a specific stock
    public String getCsvFilePathBySymbolAndDate(String symbol, LocalDate date) {
        StockFile stockFile = stockFileRepository.findBySymbolAndDate(symbol, date).stream().findFirst().orElse(null);
        return (stockFile != null) ? stockFile.getCsvFilePath() : null;
    }
}
