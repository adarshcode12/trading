package com.trading.exper;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class TickDataWebSocketHandler extends TextWebSocketHandler {

    private static final String CSV_FOLDER_PATH = "/Users/adarshkumar/Documents/PROJECTS/db_entry/symbol_data"; // Update with actual path
    private static final Logger LOGGER = Logger.getLogger(TickDataWebSocketHandler.class.getName());

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();  // Expected format: "SYMBOL_YYYYMMDD"
        LOGGER.info("Received WebSocket message: " + payload);

        String[] parts = payload.split("_");
        if (parts.length != 2) {
            LOGGER.warning("Invalid message format: " + payload);
            session.sendMessage(new TextMessage("Invalid format. Use SYMBOL_YYYYMMDD"));
            return;
        }

        String symbol = parts[0];
        String rawDate = parts[1];

        // Convert YYYYMMDD to YYYY-MM-DD
        if (rawDate.length() != 8) {
            LOGGER.warning("Invalid date format received: " + rawDate);
            session.sendMessage(new TextMessage("Invalid date format. Use SYMBOL_YYYYMMDD"));
            return;
        }

        try {
            LocalDate date = LocalDate.parse(rawDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
            String formattedDate = date.toString(); // Convert to "YYYY-MM-DD"
            LOGGER.info("Extracted Symbol: " + symbol + ", Date: " + formattedDate);

            String csvFilePath = CSV_FOLDER_PATH + "/" + symbol + "_" + formattedDate + ".csv";
            LOGGER.info("Looking for file: " + csvFilePath);

            File file = new File(csvFilePath);
            if (!file.exists()) {
                LOGGER.warning("File not found: " + csvFilePath);
                session.sendMessage(new TextMessage("No data found for " + symbol + " on " + formattedDate));
                return;
            }

            LOGGER.info("File found, starting streaming...");
            streamTickData(session, csvFilePath);
        } catch (Exception e) {
            LOGGER.severe("Error parsing date or processing request: " + e.getMessage());
            session.sendMessage(new TextMessage("Invalid date format. Use SYMBOL_YYYYMMDD"));
        }
    }

    private void streamTickData(WebSocketSession session, String csvFilePath) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try (BufferedReader br = Files.newBufferedReader(Paths.get(csvFilePath))) {
                String line;
                LOGGER.info("Streaming started for file: " + csvFilePath);

                while ((line = br.readLine()) != null) {
                    if (!session.isOpen()) break;
                    session.sendMessage(new TextMessage(line));
                    Thread.sleep(1000); // Simulating live tick streaming
                }

                LOGGER.info("Streaming completed for file: " + csvFilePath);
            } catch (Exception e) {
                LOGGER.severe("Error reading file: " + e.getMessage());
                try {
                    session.sendMessage(new TextMessage("Error reading file: " + e.getMessage()));
                } catch (IOException ignored) { }
            }
        });
        executorService.shutdown();
    }
}
