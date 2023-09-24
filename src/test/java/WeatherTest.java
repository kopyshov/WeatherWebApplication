import com.kopyshov.weatherwebapplication.auth.dao.UserDAO;
import com.kopyshov.weatherwebapplication.auth.entities.UserData;
import com.kopyshov.weatherwebapplication.buisness.Location;
import com.kopyshov.weatherwebapplication.openweathermap.api.OpenWeatherApiService;
import com.kopyshov.weatherwebapplication.openweathermap.api.geo.LocationGeoData;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import static com.kopyshov.weatherwebapplication.openweathermap.api.OpenWeatherApiService.buildUriRequestByCityName;

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

    @Test
    public void addLocationAndUsers() throws Exception {
        UserData user1 = new UserData("a", "a");
        UserDAO.INSTANCE.save(user1);

        LocationGeoData[] locationsByCoordinates = OpenWeatherApiService.getLocationsByCoordinates("55.7504461", "37.6174943");
        LocationGeoData foundedLocation = locationsByCoordinates[0];
        Optional<UserData> user = UserDAO.INSTANCE.find("a", "a");
        UserData _user1 = user.get();

        Location location = new Location();
        location.setName(foundedLocation.getName());
        location.setLatitude(foundedLocation.getLat().toString());
        location.setLongitude(foundedLocation.getLon().toString());

        UserDAO.INSTANCE.addLocationToUser(_user1, location);
    }
}
