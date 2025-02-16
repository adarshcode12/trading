package com.trading.exper;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface StockFileRepository extends JpaRepository<StockFile, Long> {
    // Method to find stock files by symbol and date
    List<StockFile> findBySymbolAndDate(String symbol, LocalDate date);
}
