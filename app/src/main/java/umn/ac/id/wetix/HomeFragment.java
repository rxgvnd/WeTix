package umn.ac.id.wetix;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageSlider imageSlider = (ImageSlider) view.findViewById(R.id.slider);

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://i.pinimg.com/236x/62/c4/bf/62c4bf742aa0ad91c8541da187f20486.jpg"));
        slideModels.add(new SlideModel("https://cdn.tmpo.co/data/2018/09/23/id_735800/735800_720.jpg"));
        slideModels.add(new SlideModel("https://cdn1-production-images-kly.akamaized.net/hcPpUU1eH_KBhJirNmYGzHwGXMw=/640x853/smart/filters:quality(75):strip_icc():format(jpeg)/kly-media-production/medias/3301804/original/011403400_1605845389-Asih_2.jpg"));
        slideModels.add(new SlideModel("https://cinemags.co.id/wp-content/uploads/2019/08/Spidey-poster.jpg"));
        imageSlider.setImageList(slideModels, true);
        return view;
    }
}