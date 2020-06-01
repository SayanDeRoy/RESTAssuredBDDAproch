package payLoadGeneration;

import pojo.googleMap.AddPlaceRequest;
import pojo.googleMap.DeletePlaceRequest;
import pojo.googleMap.LocationRequest;
import pojo.googleMap.UpdatePlaceRequest;

import java.util.Arrays;
import java.util.List;

public class PayLoadSetup {

    public static AddPlaceRequest addGoogleMapPayloadSetup(int accuracy, String name, String phone_number, String address, String website, String language, String types, String lat, String lng) {
        String[] splitTypes = types.split(",");
        List<String> list = Arrays.asList(splitTypes);

        LocationRequest location = new LocationRequest();
        location.setLat(lat);
        location.setLng(lng);

        AddPlaceRequest addPlaceRequest = new AddPlaceRequest();

        addPlaceRequest.setAccuracy(accuracy);
        addPlaceRequest.setName(name);
        addPlaceRequest.setPhone_number(phone_number);
        addPlaceRequest.setAddress(address);
        addPlaceRequest.setWebsite(website);
        addPlaceRequest.setLanguage(language);
        addPlaceRequest.setTypes(list);
        addPlaceRequest.setLocation(location);

        return addPlaceRequest;
    }

    public static UpdatePlaceRequest updateGoogleMapPayloadSetup(String address, String place_id) {

        UpdatePlaceRequest updatePlaceRequest = new UpdatePlaceRequest();

        updatePlaceRequest.setAddress(address);
        updatePlaceRequest.setPlace_id(place_id);
        updatePlaceRequest.setKey();

        return updatePlaceRequest;
    }

    public static DeletePlaceRequest deleteGoogleMapPayloadSetup(String place_id) {

        DeletePlaceRequest deletePlaceRequest = new DeletePlaceRequest();

        deletePlaceRequest.setPlace_id(place_id);

        return deletePlaceRequest;
    }
}
