package app.service;

import app.model.Mood;
import app.model.Quote;
import app.util.HttpUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class QuoteService {
    private static final Logger LOGGER = Logger.getLogger(QuoteService.class.getName());
    private static final String QUOTES_FILE = "/data/quotes.json";
    private static final String QUOTABLE_API_BASE = "https://api.quotable.io";
    
    private final ObjectMapper objectMapper;
    private final HttpUtil httpUtil;
    private final Map<String, List<Quote>> localQuotes;

    public QuoteService() {
        this.objectMapper = new ObjectMapper();
        this.httpUtil = new HttpUtil();
        this.localQuotes = loadLocalQuotes();
    }

    public List<Quote> getQuotesForMood(Mood mood, boolean onlineMode) {
        List<Quote> quotes = new ArrayList<>();
        
        // Always include local quotes
        quotes.addAll(getLocalQuotesForMood(mood));
        
        // Add online quotes if enabled
        if (onlineMode) {
            try {
                List<Quote> onlineQuotes = fetchOnlineQuotes(mood).get();
                quotes.addAll(onlineQuotes);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Failed to fetch online quotes for mood: " + mood, e);
            }
        }
        
        // Shuffle and limit results
        Collections.shuffle(quotes);
        return quotes.stream().limit(5).collect(Collectors.toList());
    }

    public CompletableFuture<List<Quote>> fetchOnlineQuotes(Mood mood) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String tag = getMoodTag(mood);
                String url = QUOTABLE_API_BASE + "/quotes?tags=" + tag + "&limit=3";
                
                String response = httpUtil.get(url);
                JsonNode jsonNode = objectMapper.readTree(response);
                JsonNode results = jsonNode.get("results");
                
                List<Quote> quotes = new ArrayList<>();
                if (results != null && results.isArray()) {
                    for (JsonNode quoteNode : results) {
                        String text = quoteNode.get("content").asText();
                        String author = quoteNode.get("author").asText();
                        quotes.add(new Quote(text, author, mood.name().toLowerCase(), "api"));
                    }
                }
                
                LOGGER.info("Fetched " + quotes.size() + " online quotes for mood: " + mood);
                return quotes;
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Error fetching online quotes", e);
                return new ArrayList<>();
            }
        });
    }

    private List<Quote> getLocalQuotesForMood(Mood mood) {
        String moodKey = mood.name().toLowerCase();
        return localQuotes.getOrDefault(moodKey, new ArrayList<>());
    }

    private Map<String, List<Quote>> loadLocalQuotes() {
        try (InputStream inputStream = getClass().getResourceAsStream(QUOTES_FILE)) {
            if (inputStream != null) {
                TypeReference<Map<String, List<Quote>>> typeRef = new TypeReference<Map<String, List<Quote>>>() {};
                Map<String, List<Quote>> quotes = objectMapper.readValue(inputStream, typeRef);
                LOGGER.info("Loaded local quotes from " + QUOTES_FILE);
                return quotes;
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to load local quotes", e);
        }
        
        // Return default quotes if file not found
        return createDefaultQuotes();
    }

    private Map<String, List<Quote>> createDefaultQuotes() {
        Map<String, List<Quote>> defaultQuotes = new HashMap<>();
        
        // Happy quotes
        List<Quote> happyQuotes = Arrays.asList(
            new Quote("Happiness is a direction, not a place.", "Sydney J. Harris", Mood.HAPPY),
            new Quote("The purpose of our lives is to be happy.", "Dalai Lama", Mood.HAPPY),
            new Quote("Happiness is not something ready made. It comes from your own actions.", "Dalai Lama", Mood.HAPPY)
        );
        defaultQuotes.put("happy", happyQuotes);
        
        // Sad quotes
        List<Quote> sadQuotes = Arrays.asList(
            new Quote("Tears come from the heart and not from the brain.", "Leonardo da Vinci", Mood.SAD),
            new Quote("The way sadness works is one of the strange riddles of the world.", "Lemony Snicket", Mood.SAD),
            new Quote("Every man has his secret sorrows.", "Henry Wadsworth Longfellow", Mood.SAD)
        );
        defaultQuotes.put("sad", sadQuotes);
        
        // Stressed quotes
        List<Quote> stressedQuotes = Arrays.asList(
            new Quote("Don't stress. Do your best. Forget the rest.", "Unknown", Mood.STRESSED),
            new Quote("Stress is caused by being 'here' but wanting to be 'there'.", "Eckhart Tolle", Mood.STRESSED),
            new Quote("Take time to make your soul happy.", "Unknown", Mood.STRESSED)
        );
        defaultQuotes.put("stressed", stressedQuotes);
        
        // Calm quotes
        List<Quote> calmQuotes = Arrays.asList(
            new Quote("Peace comes from within. Do not seek it without.", "Buddha", Mood.CALM),
            new Quote("Calm mind brings inner strength and self-confidence.", "Dalai Lama", Mood.CALM),
            new Quote("In the midst of movement and chaos, keep stillness inside of you.", "Deepak Chopra", Mood.CALM)
        );
        defaultQuotes.put("calm", calmQuotes);
        
        LOGGER.info("Created default quotes");
        return defaultQuotes;
    }

    private String getMoodTag(Mood mood) {
        switch (mood) {
            case HAPPY: return "happiness";
            case SAD: return "sadness";
            case STRESSED: return "stress";
            case EXCITED: return "motivational";
            case CALM: return "wisdom";
            case ANXIOUS: return "courage";
            case ANGRY: return "patience";
            case ROMANTIC: return "love";
            case ENERGETIC: return "motivational";
            case MELANCHOLIC: return "wisdom";
            default: return "inspirational";
        }
    }

    public List<Quote> searchQuotes(String searchTerm) {
        return localQuotes.values().stream()
                .flatMap(List::stream)
                .filter(quote -> quote.getText().toLowerCase().contains(searchTerm.toLowerCase()) ||
                               quote.getAuthor().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }
}
