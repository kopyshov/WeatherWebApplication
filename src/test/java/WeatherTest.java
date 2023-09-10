import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.kopyshov.weatherwebapplication.openweathermap.api.services.OpenWeatherApiService.buildUriRequestByCityName;

public class WeatherTest {

    @Test
    public void getLocations() throws Exception {
        final HttpClient client = HttpClient.newHttpClient();
        String location = "москва";
        URI uri = buildUriRequestByCityName(location);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
