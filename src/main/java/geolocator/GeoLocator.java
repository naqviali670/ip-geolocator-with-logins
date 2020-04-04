
package geolocator;

import java.net.URL;

import java.io.IOException;

import com.google.gson.Gson;

import com.google.common.net.UrlEscapers;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoLocator {

    public static final String GEOLOCATOR_SERVICE_URI = "http://ip-api.com/json/";

    private static Logger logCreator = LoggerFactory.getLogger(GeoLocator.class);
    private static Gson GSON = new Gson();

    public GeoLocator() {}

    public GeoLocation getGeoLocation() {
        try {
            return getGeoLocation(null);
        } catch (IOException e) {
            logCreator.error("IOException", new RuntimeException(e.getMessage()));
        }
        return null;
    }

    public GeoLocation getGeoLocation(String ipAddrOrHost) throws IOException {

        URL url = null;
        String s = new String();
        if (ipAddrOrHost != null) {
            logCreator.info("Parsing url");
            ipAddrOrHost = UrlEscapers.urlPathSegmentEscaper().escape(ipAddrOrHost);
                logCreator.debug("Debug Message");
                url = new URL(GEOLOCATOR_SERVICE_URI + ipAddrOrHost);

        } else {

                url = new URL(GEOLOCATOR_SERVICE_URI);

        }

            s = IOUtils.toString(url, "UTF-8");
        logCreator.info("returning information");
        return GSON.fromJson(s, GeoLocation.class);
    }

    public static void main(String[] args)  {
        try {
        String arg = args.length > 0 ? args[0] : null;
        logCreator.info("Geolocation info...");

            System.out.println(new GeoLocator().getGeoLocation(arg));
        } catch (IOException e) {
            logCreator.error("IOException", new RuntimeException(e.getMessage()));
        }
    }

}