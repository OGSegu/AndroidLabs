package com.segu.lab_3;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

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

public class Fragment3 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        Button toFirstBtn = view.findViewById(R.id.to_first_btn);
        toFirstBtn.setOnClickListener((btn) -> {
            navController.navigate(R.id.action_fragment3_to_fragment1);
        });

        Button toSecondBtn = view.findViewById(R.id.to_second_btn);
        toSecondBtn.setOnClickListener((btn) -> {
            navController.navigate(R.id.action_fragment3_to_fragment2);
        });
    }
}

