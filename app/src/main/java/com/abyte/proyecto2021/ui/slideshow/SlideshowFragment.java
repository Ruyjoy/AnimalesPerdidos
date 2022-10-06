package com.abyte.proyecto2021.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.abyte.proyecto2021.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SlideshowFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            int zoom = 6; //Si aumntamos el numero acerca màs el mapa
            LatLng ref1 = new LatLng(-30.93429184988971, -55.55311244938687);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-30.93429184988971, -55.55311244938687),zoom));
            googleMap.addMarker(new MarkerOptions()
                    .position(ref1)
                    .title("Refugio Mi Zoo")
                    .snippet("Rivera")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));


            LatLng ref2 = new LatLng(-34.42623323810264, -57.435177231480594);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-34.42623323810264, -57.435177231480594),zoom));
            googleMap.addMarker(new MarkerOptions()
                    .position(ref2)
                    .title("Refugio Canino Juan Lacaze")
                    .snippet("Colonia")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));


            LatLng ref3 = new LatLng(-34.839689741868966, -54.94414945733151);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-34.839689741868966, -54.94414945733151),zoom));
            googleMap.addMarker(new MarkerOptions()
                    .position(ref3)
                    .title("Refugio Municipal de Animales")
                    .snippet("Maldonado")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

            LatLng ref4 = new LatLng(-34.694580613683456, -55.96115696030681);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-34.694580613683456, -55.96115696030681),zoom));
            googleMap.addMarker(new MarkerOptions()
                    .position(ref4)
                    .title("Refugio Animal Help")
                    .snippet("Pando, Canelones")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

            LatLng ref5 = new LatLng(-34.906191104117525, -56.196579431464116);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-34.906191104117525, -56.196579431464116),zoom));
            googleMap.addMarker(new MarkerOptions()
                    .position(ref5)
                    .title("Refugio Espacio ASH")
                    .snippet("Montevideo")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));


            LatLng ref6 = new LatLng(-34.79756886567967, -55.9220671026324);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-34.79756886567967, -55.9220671026324),zoom));
            googleMap.addMarker(new MarkerOptions()
                    .position(ref6)
                    .title("Refugio La Costa Bichera")
                    .snippet("Maldonado")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        }
    };


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}