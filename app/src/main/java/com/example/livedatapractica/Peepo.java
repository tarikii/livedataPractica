package com.example.livedatapractica;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

import androidx.lifecycle.LiveData;

public class Peepo {

    interface EntrenadorListener {
        void cuandoDeLaOrden(String orden);
    }

    Random random = new Random();
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> peepoSeEncuentra;

    LiveData<String> ordenLiveData = new LiveData<String>() {
        @Override
        protected void onActive() {
            super.onActive();

            iniciarEstadoPeepo(new EntrenadorListener() {
                @Override
                public void cuandoDeLaOrden(String orden) {
                    postValue(orden);
                }
            });
        }

        @Override
        protected void onInactive() {
            super.onInactive();

            pararPeepo();
        }
    };

    void iniciarEstadoPeepo(EntrenadorListener entrenadorListener) {
        if (peepoSeEncuentra == null || peepoSeEncuentra.isCancelled()) {
            peepoSeEncuentra = scheduler.scheduleAtFixedRate(new Runnable() {
                int estado = 0;
                int repeticiones = -1;

                @Override
                public void run() {
                    if (repeticiones < 0) {
                        repeticiones = 3;
                        estado++;
                    }
                    if(estado == 8){
                        estado = 1;
                    }
                    entrenadorListener.cuandoDeLaOrden("PEEPO" + estado + ":" + (repeticiones == 0 ? "CAMBIO" : repeticiones));
                    repeticiones--;
                }
            }, 0, 1, SECONDS);
        }
    }

    void pararPeepo() {
        if (peepoSeEncuentra != null) {
            peepoSeEncuentra.cancel(true);
        }
    }
}
