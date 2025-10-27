package com.distraction.minijam196.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

public class AudioHandler {

    private final Map<String, Music> music;
    private final Map<String, Sound> sounds;

    private MusicConfig currentlyPlaying;

    public AudioHandler() {
        music = new HashMap<>();
        addMusic("bg", "music/intothesnakepit.ogg");

        sounds = new HashMap<>();
        addSound("endturn", "sfx/enter.wav");
        addSound("click", "sfx/click.ogg");
        addSound("tick", "sfx/shift.wav");
        addSound("finish", "sfx/finish.wav");
        addSound("lose", "sfx/hit.wav");
        addSound("bombthrow", "sfx/collect.wav");

        addSound("explosion", "sfx/FreeSFX/Retro Explosion Swooshes 04.wav");
        addSound("bombland", "sfx/FreeSFX/Retro FootStep 03.wav");
    }

    private void addMusic(String key, String fileName) {
        music.put(key, Gdx.audio.newMusic(Gdx.files.internal(fileName)));
    }

    private void addSound(String key, String fileName) {
        sounds.put(key, Gdx.audio.newSound(Gdx.files.internal(fileName)));
    }

    public void playMusic(String key, float volume, boolean looping) {
        Music newMusic = music.get(key);
        if (newMusic == null) {
            System.out.println("unknown music: " + key);
            return;
        }
        currentlyPlaying = new MusicConfig(music.get(key), volume, looping);
        currentlyPlaying.play();
    }

    public void stopMusic() {
        if (currentlyPlaying != null) {
            currentlyPlaying.stop();
            currentlyPlaying = null;
        }
    }

    public void playSound(String key) {
        playSound(key, 1, false);
    }

    public void playSound(String key, float volume) {
        playSound(key, volume, false);
    }

    public void playSound(String key, float volume, boolean cut) {
        for (Map.Entry<String, Sound> entry : sounds.entrySet()) {
            if (entry.getKey().equals(key)) {
                if (cut) entry.getValue().stop();
                entry.getValue().play(volume);
            }
        }
    }

}
