package org.mendybot.android.aucalc.model;

import org.mendybot.android.aucalc.MainActivity;

import java.util.ArrayList;

public final class CommoditiesModel {
    private static final double G_OZT_FACTOR = 31.103715338797;
    private static CommoditiesModel singleton;
    private ArrayList<CommoditiesChangeListener> changeListeners = new ArrayList<>();
    private long auStrikeOzt;
    private long auStrikeG;

    private CommoditiesModel() {
    }

    public void setAuStrikeOzt(double goldStrikeOzt) {
        this.auStrikeOzt = Math.round(goldStrikeOzt*100d);
        updateStrikeValues();
        notifyChangeListenersAU();
    }

    private void updateStrikeValues() {
        auStrikeG = Math.round(auStrikeOzt / G_OZT_FACTOR);

    }

    public double getAuStrikeOzt() {
        return auStrikeOzt / 100d;
    }

    public double getAuStrikeG() {
        return auStrikeG / 100d;
    }

    public void addCommoditiesChangeListener(CommoditiesChangeListener lis) {
        changeListeners.add(lis);
    }

    public void removeCommoditiesChangeListener(CommoditiesChangeListener lis) {
        changeListeners.remove(lis);
    }

    private void notifyChangeListenersAU() {
        for (CommoditiesChangeListener lis : changeListeners) {
            lis.auCommodityChanged();
        }
    }

    public synchronized static CommoditiesModel getInstance() {
        if (singleton == null) {
            singleton = new CommoditiesModel();
        }
        return singleton;
    }

}
