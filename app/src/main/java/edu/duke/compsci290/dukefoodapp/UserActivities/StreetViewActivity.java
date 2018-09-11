
package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

import edu.duke.compsci290.dukefoodapp.R;

public class StreetViewActivity extends FragmentActivity
        implements OnStreetViewPanoramaReadyCallback {

    private String mAddress;
    private  LatLng mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_view);

        Intent receivedIntent = this.getIntent();
        mAddress = receivedIntent.getStringExtra("address");
        Geocoder gc = new Geocoder(this);
        double lat = 0;
        double lng = 0;
        if(gc.isPresent()){
            List<Address> list = null;
            try {
                list = gc.getFromLocationName(mAddress, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = list.get(0);
            lat = address.getLatitude();
            lng = address.getLongitude();
        }
        this.mLocation = new LatLng(lat, lng);
        StreetViewPanoramaFragment streetViewPanoramaFragment =
                (StreetViewPanoramaFragment) getFragmentManager()
                        .findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);


    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        streetViewPanorama.setPosition(mLocation,20);
        streetViewPanorama.setPanningGesturesEnabled(true);
        streetViewPanorama.setUserNavigationEnabled(true);
        streetViewPanorama.setStreetNamesEnabled(false);
        streetViewPanorama.setZoomGesturesEnabled(true);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
