package com.example.project.CatchMonster.game;


import android.content.Context;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.util.Log;
import com.example.project.CatchMonster.R;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import com.example.project.CatchMonster.R;

public class Sound {
    private MediaPlayer mediaPlayer;
    private Equalizer equalizer;
    public void playSound(Context context, int resId, float volume) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(context, resId);
        if (mediaPlayer == null) {
            Log.e("SoundPlayer", "MediaPlayer creation failed");
            return;
        }

        setupEqualizer();

        mediaPlayer.setOnCompletionListener(mp -> mp.release());
        mediaPlayer.setVolume(volume, volume);
        mediaPlayer.start();
    }

    private void setupEqualizer() {
        equalizer = new Equalizer(0, mediaPlayer.getAudioSessionId());
        equalizer.setEnabled(true);

        // 주파수 대역의 볼륨을 증폭
        short numberOfBands = equalizer.getNumberOfBands();
        short lowerBandLevel = equalizer.getBandLevelRange()[0];
        short upperBandLevel = equalizer.getBandLevelRange()[1];

        for (short i = 0; i < numberOfBands; i++) {
            equalizer.setBandLevel(i, upperBandLevel); // 최대 볼륨으로 설정
        }
    }
}