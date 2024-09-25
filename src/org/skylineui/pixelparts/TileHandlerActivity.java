/*
 * Copyright (C) 2023 iusmac <iusico.maxim@libero.it>
 * SPDX-License-Identifier: MIT
 */

package org.skylineui.pixelparts;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.service.quicksettings.TileService;
import android.util.Log;

import org.skylineui.pixelparts.autohbm.AutoHbmActivity;
import org.skylineui.pixelparts.autohbm.AutoHbmTileService;
import org.skylineui.pixelparts.pixeltorch.PixelTorchActivity;
import org.skylineui.pixelparts.pixeltorch.PixelTorchTileService;

public final class TileHandlerActivity extends Activity {
    private static final String TAG = "TileHandlerActivity";
    @Override
    protected void onCreate(final android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();
        try {
            if (android.service.quicksettings.TileService.ACTION_QS_TILE_PREFERENCES.equals(intent.getAction())) {
                final ComponentName qsTile =
                        intent.getParcelableExtra(Intent.EXTRA_COMPONENT_NAME);
                final String qsName = qsTile.getClassName();
                final Intent aIntent = new Intent();

                if (qsName.equals(AutoHbmTileService.class.getName())) {
                    aIntent.setClass(this, AutoHbmActivity.class);
                } else if (qsName.equals(PixelTorchTileService.class.getName())) {
                    aIntent.setClass(this, PixelTorchActivity.class);
                } else {
                    aIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    aIntent.setData(Uri.fromParts("package", qsTile.getPackageName(), null));
                }

                aIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(aIntent);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error handling intent: " + intent, e);
        } finally {
            finish();
        }
    }
}
