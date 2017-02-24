package in.myinnos.gifimages.builder;

import java.net.URLEncoder;

public class GiphyQueryBuilder {

    private static final String BASE_URL = "http://api.giphy.com/v1/gifs/";
    private static final String PUBLIC_BETA_KEY = "dc6zaTOxFJmzC";

    public enum EndPoint {
        SEARCH("search"),
        TRENDS("trending"),
        RANDOM("random");

        private final String endpoint;

        EndPoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String endpointString() {
            return this.endpoint;
        }
    }

    public enum Rating {
        YOUTH("y"),
        GENERAL("g"),
        PARENTAL_GUIDANCE("pg"),
        THIRTEEN("pg-13"),
        RESTRICTED("r");

        private final String rating;

        Rating(String rating) {
            this.rating = rating;
        }

        public String ratingString() {
            return this.rating;
        }
    }

    private final String apiKey;
    private final EndPoint endPoint;

    private String query = null;
    private int limit = -1;
    private int offset = -1;
    private Rating rating = null;

    public GiphyQueryBuilder(EndPoint endPoint, String GIPHY_KEY) {
        this(GIPHY_KEY, endPoint);
    }

    public GiphyQueryBuilder(String apiKey, EndPoint endPoint) {
        if (apiKey.isEmpty()) {
            apiKey = PUBLIC_BETA_KEY;
        }
        this.apiKey = apiKey;
        this.endPoint = endPoint;
    }

    /**
     * Sets the query for the search api.
     *
     * @param query what you want to query on
     * @return this builder
     */
    public GiphyQueryBuilder setQuery(String query) {
        this.query = query;
        return this;
    }

    /**
     * Sets the number of results to get. Default is 25, max is 100.
     *
     * @param limit number of results to return
     * @return this builder.
     */
    public GiphyQueryBuilder setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    /**
     * Used for pagination, along with the limit. For example, limit of 20, if you want to get results 20-40,
     * you would set the offset to 20.
     *
     * @param offset offset for pagination
     * @return this builder
     */
    public GiphyQueryBuilder setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    /**
     * Set the rating of the results you want to return.
     *
     * @param rating
     * @return this builder
     */
    public GiphyQueryBuilder setRating(Rating rating) {
        this.rating = rating;
        return this;
    }

    /**
     * Get the URI to search GIPHY
     *
     * @return String of search uri
     */
    public String build() {

        if (endPoint == EndPoint.SEARCH && query == null) {
            throw new IllegalArgumentException("You must set a query to perform a search");
        }

        String uri = BASE_URL +
                endPoint.endpointString() + "?" +
                "api_key=" + apiKey;

        if (query != null) {
            uri += "&q=" + URLEncoder.encode(query);
        }

        if (limit != -1) {
            uri += "&limit=" + limit;
        }

        if (offset != -1) {
            uri += "&offset=" + offset;
        }

        if (rating != null) {
            uri += "&rating=" + rating.ratingString();
        }

        return uri;
    }
}
