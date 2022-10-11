package com.example.livedatapractica;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.livedatapractica.databinding.FragmentPeepoBinding;


public class PeepoFragment extends Fragment {

    private FragmentPeepoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentPeepoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PeepoViewModel entrenadorViewModel = new ViewModelProvider(this).get(PeepoViewModel.class);

        entrenadorViewModel.obtenerPeepo().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer peepo) {
                Glide.with(PeepoFragment.this).load(peepo).into(binding.peepo);
            }
        });

        entrenadorViewModel.obtenerRepeticion().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String repeticion) {
                if(repeticion.equals("CAMBIO")){
                    binding.cambio.setVisibility(View.VISIBLE);
                } else {
                    binding.cambio.setVisibility(View.GONE);
                }
                binding.repeticion.setText(repeticion);
            }
        });
    }
}