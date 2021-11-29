package com.segu.lab_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class Fragment2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        Button toFirstBtn = view.findViewById(R.id.to_first_btn);
        toFirstBtn.setOnClickListener((btn) -> navController.navigate(R.id.action_fragment2_to_fragment1));

        Button toThirdBtn = view.findViewById(R.id.to_third_btn);
        toThirdBtn.setOnClickListener((btn) -> {
            navController.navigate(R.id.action_fragment2_to_fragment3);
        });
    }
}
