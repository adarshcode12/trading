package com.trading.exper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.List;

@RestController
public class StockFileController {

    @Autowired
    private StockFileService stockFileService;

    // API endpoint to fetch stock data by symbol and date (without CSV file data)
    @GetMapping("/api/stock-files")
    public List<StockFile> getStockFilesBySymbolAndDate(
            @RequestParam String symbol,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return stockFileService.getStockFilesBySymbolAndDate(symbol, date);
    }
}
