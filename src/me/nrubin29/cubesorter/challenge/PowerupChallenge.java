package me.nrubin29.cubesorter.challenge;

import me.nrubin29.cubesorter.CubeSorter;
import me.nrubin29.cubesorter.powerup.LifePowerup;
import me.nrubin29.cubesorter.powerup.MultiballPowerup;
import me.nrubin29.cubesorter.powerup.Powerup;
import me.nrubin29.cubesorter.powerup.SlowPowerup;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PowerupChallenge extends Challenge {

    private final Random r;
    private HashMap<Integer, Class<? extends Powerup>> powerups = new HashMap<Integer, Class<? extends Powerup>>();
    private Powerup currentPowerup;

    public PowerupChallenge() {
        super(null);

        this.r = new Random();
        this.powerups = new HashMap<Integer, Class<? extends Powerup>>();
        powerups.put(20, SlowPowerup.class);
        powerups.put(50, LifePowerup.class);
        powerups.put(80, MultiballPowerup.class);
    }

    @Override
    public void apply(final CubeSorter cubeSorter) {
        new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (r.nextInt(50) == 29 && currentPowerup == null) {
                    int x = r.nextInt(640), y = r.nextInt(480);
                    cubeSorter.addPowerup(getPowerup(cubeSorter.getRound().getScore(), x, y));
                }
            }
        }).start();

        new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPowerup != null) {
                    cubeSorter.removePowerup();
                    currentPowerup = null;
                }
            }
        }).start();
    }

    public void onLevelIncrease(int level, CubeSorter cubeSorter) {
        if (powerups.get(level) != null) {
            cubeSorter.addText("Unlocked " + powerups.get(level).getSimpleName() + ".");
        }
    }

    private Powerup getPowerup(int score, int x, int y) {
        try {
            ArrayList<Map.Entry<Integer, Class<? extends Powerup>>> options = new ArrayList<Map.Entry<Integer, Class<? extends Powerup>>>();

            for (Map.Entry<Integer, Class<? extends Powerup>> e : powerups.entrySet()) {
                if (e.getKey() <= score) {
                    options.add(e);
                }
            }

            return options.get(r.nextInt(options.size())).getValue().getConstructor(Integer.class, Integer.class).newInstance(x, y);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}