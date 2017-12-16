package com.henallux.namikot;


import android.os.Bundle;
import android.support.v4.app.*;
import android.view.*;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment {

    private SupportMapFragment mSupportMapFragment;

    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapwhere);
        if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mSupportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.mapwhere, mSupportMapFragment).commit();
        }

        if (mSupportMapFragment != null) {
            mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    if (googleMap != null) {

                        googleMap.getUiSettings().setAllGesturesEnabled(true);

                         LatLng marker_latlng = new LatLng(50.469335, 4.862534);// MAKE THIS WHATEVER YOU WANT

                        CameraPosition cameraPosition = new CameraPosition.Builder().target(marker_latlng).zoom(15.0f).build();
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                        googleMap.moveCamera(cameraUpdate);

                    }

                }
            });
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

}
