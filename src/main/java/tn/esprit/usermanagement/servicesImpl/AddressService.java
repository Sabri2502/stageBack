package tn.esprit.usermanagement.servicesImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tn.esprit.usermanagement.dto.GeocodeResult;
import tn.esprit.usermanagement.entities.localisation;
import tn.esprit.usermanagement.entities.Address;
import tn.esprit.usermanagement.entities.User;
import tn.esprit.usermanagement.repositories.AddressRepo;
import tn.esprit.usermanagement.repositories.UserRepo;

import java.io.IOException;
import java.net.URLEncoder;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AddressService {
    private AddressRepo addressRepo;
    public UserRepo userRepo;
    public String apiKey = "3dg04s9Xw9lMtGI7QAXBoZoz40vfUWMa";


    public Address AddAddress(String address) throws IOException {

        OkHttpClient client = new OkHttpClient();
        String encodedAddress = URLEncoder.encode(address, "UTF-8");
        Request request = new Request.Builder()
                .url("https://google-maps-geocoding.p.rapidapi.com/geocode/json?address="+address)
                .get()
                .addHeader("X-RapidAPI-Key", "caf83ab54amsh7c09f9cfec15ca6p15df8djsnb130792f6d31")
                .addHeader("X-RapidAPI-Host", "google-maps-geocoding.p.rapidapi.com")
                .build();
        ResponseBody responseBody = client.newCall(request).execute().body();
        ObjectMapper objectMapper = new ObjectMapper();
        GeocodeResult result = objectMapper.readValue(responseBody.string(), GeocodeResult.class);
        Address a = new Address();
        a.setLongitude(result.getResults().get(0).getGeometry().getGeocodeLocation().getLongitude());
        a.setLatitude(result.getResults().get(0).getGeometry().getGeocodeLocation().getLatitude());
        a.setReal_Address(result.getResults().get(0).getFormattedAddress());
        return a;
    }
    public String retrouveradresseUSER(Integer idUser){
        User user =userRepo.findById2(idUser);

        String s=getAddress(user.getLatitude(),user.getLongitude());
        return  s;
    }

    public String getAddress(double latitude, double longitude) {
        try {
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey("caf83ab54amsh7c09f9cfec15ca6p15df8djsnb130792f6d31")
                    .build();

            GeocodingResult[] results = GeocodingApi.reverseGeocode(context, new LatLng(latitude, longitude)).await();
            if (results.length > 0) {
                return results[0].formattedAddress;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public localisation reverseGeoccode(float longitude, float latitude) {

        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "https://api.tomtom.com/search/2/reverseGeocode/";
        String position = latitude+","+longitude;
        String ext = "json";
        String apiKey = this.apiKey;
        String radius = "1000";
        String url = baseUrl + position + "." + ext + "?key=" + apiKey + "&" + "radius=" + radius + "&language=en-US";
        //send request and get response
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        //check if response is valid
        if (responseEntity.getStatusCode().isError()){
            return null;
        }
        //get response body
        String response = responseEntity.getBody();
        if (response == null){
            return  null;
        }
        // Parse the response JSON into a JsonObject
        com.google.gson.JsonObject responseObject = JsonParser.parseString(response).getAsJsonObject();
        JsonObject address = responseObject.getAsJsonArray("addresses").get(0).getAsJsonObject().getAsJsonObject("address");
        //fetch data
        String state = address.get("countrySubdivision").getAsString();
        String town = address.get("municipality").getAsString();
        //create location
        localisation ADRESSE = new localisation();
        ADRESSE.setState(state);
        ADRESSE.setCity(town);
        ADRESSE.setLongitude(longitude);
        ADRESSE.setLatitude(latitude);

        return ADRESSE;
    }




}