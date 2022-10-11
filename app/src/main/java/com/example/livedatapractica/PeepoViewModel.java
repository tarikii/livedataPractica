package com.example.livedatapractica;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class PeepoViewModel extends AndroidViewModel {
    Peepo peepo;

    LiveData<Integer> peepoLiveData;
    LiveData<String> repeticionLiveData;

    public PeepoViewModel(@NonNull Application application) {
        super(application);

        peepo = new Peepo();

        peepoLiveData = Transformations.switchMap(peepo.ordenLiveData, new Function<String, LiveData<Integer>>() {

            String peepoAnterior;

            @Override
            public LiveData<Integer> apply(String orden) {

                String peepo = orden.split(":")[0];

                if(!peepo.equals(peepoAnterior)){
                    peepoAnterior = peepo;
                    int imagen;
                    switch (peepo) {
                        case "PEEPO1":
                        default:
                            imagen = R.drawable.peepohappy;
                            break;
                        case "PEEPO2":
                            imagen = R.drawable.peepopokerface;
                            break;
                        case "PEEPO3":
                            imagen = R.drawable.peepofeelsbadman;
                            break;
                        case "PEEPO4":
                            imagen = R.drawable.peepocrying;
                            break;
                        case "PEEPO5":
                            imagen = R.drawable.peeposuicide;
                            break;
                        case "PEEPO6":
                            imagen = R.drawable.peepodead;
                            break;
                        case "PEEPO7":
                            imagen = R.drawable.peepograve;
                            break;
                    }

                    return new MutableLiveData<>(imagen);
                }
                return null;
            }
        });

        repeticionLiveData = Transformations.switchMap(peepo.ordenLiveData, new Function<String, LiveData<String>>() {
            @Override
            public LiveData<String> apply(String orden) {
                return new MutableLiveData<>(orden.split(":")[1]);
            }
        });
    }

    LiveData<Integer> obtenerPeepo(){
        return peepoLiveData;
    }

    LiveData<String> obtenerRepeticion(){
        return repeticionLiveData;
    }
}
