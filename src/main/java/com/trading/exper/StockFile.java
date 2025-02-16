package com.trading.exper;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "stock_files")
public class StockFile {

    @Id
    private Long id;

    private String exchange;

    private String symbol;

    private LocalDate date;

    // Store the file name or path to the CSV file
    private String csvPath;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // The CSV file path will not be included in API responses
    public String getCsvFilePath() {
        return csvPath;
    }

    public void setCsvFilePath(String csvFilePath) {
        this.csvPath = csvFilePath;
    }
}
