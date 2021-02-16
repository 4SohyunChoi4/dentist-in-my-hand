package com.toothfairy.dentist.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.toothfairy.dentist.MainActivity;
import com.toothfairy.dentist.R;
import com.toothfairy.dentist.ui.intro.IntroFragment;
import com.toothfairy.dentist.ui.subject.SubjectFragment;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;
import net.daum.android.map.MapViewEventListener;

public class MapFragment extends Fragment {

    private MapViewModel mapViewModel;
    public static MapFragment newInstance() {
        return new MapFragment();
    }

    public MapViewModel getMapViewModel() {
        return mapViewModel;
    }

    public void setMapViewModel(MapViewModel mapViewModel) {
        this.mapViewModel = mapViewModel;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapViewModel =
                ViewModelProviders.of(this).get(MapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        final TextView textView = root.findViewById(R.id.mapTitle);
        mapViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        root.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(IntroFragment.newInstance());
            }
        });

        MapView mapView = new MapView(getActivity());
        ViewGroup mapViewContainer = root.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        //mapView.setMapViewEventListener((MapViewEventListener) getActivity()); // this에 MapView.MapViewEventListener 구현.

        // 중심점 변경
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.546456944360514, 126.9648174435511), true);

        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(37.546456944360514, 126.9648174435511);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("숙명치과");
        marker.setTag(0);
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.RedPin); // 기본으로 제공하는 BluePin 마커 모양.
        mapView.addPOIItem(marker);



// 줌 레벨 변경
        //mapView.setZoomLevel(7, true);

// 중심점 변경 + 줌 레벨 변경
        //mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(33.41, 126.52), 9, true);

// 줌 인
        //mapView.zoomIn(true);

// 줌 아웃
        //mapView.zoomOut(true);


        return root;
    }
}