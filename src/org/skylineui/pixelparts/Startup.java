/*
 * Copyright (C) 2023-2024 The Evolution X Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.skylineui.pixelparts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.skylineui.pixelparts.autohbm.AutoHbmActivity;
import org.skylineui.pixelparts.autohbm.AutoHbmFragment;
import org.skylineui.pixelparts.autohbm.AutoHbmTileService;
import org.skylineui.pixelparts.chargecontrol.ChargeControlFragment;
import org.skylineui.pixelparts.pixeltorch.PixelTorchActivity;
import org.skylineui.pixelparts.pixeltorch.PixelTorchFragment;
import org.skylineui.pixelparts.pixeltorch.PixelTorchButtonService;
import org.skylineui.pixelparts.pixeltorch.PixelTorchTileService;
import org.skylineui.pixelparts.saturation.SaturationFragment;
import org.skylineui.pixelparts.utils.ComponentUtils;
import org.skylineui.pixelparts.utils.FileUtils;

public class Startup extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        // Auto hbm
        AutoHbmFragment.toggleAutoHbmService(context);

        ComponentUtils.toggleComponent(
                context,
                AutoHbmActivity.class,
                AutoHbmFragment.isHbmSupported(context)
        );

        ComponentUtils.toggleComponent(
                context,
                AutoHbmTileService.class,
                AutoHbmFragment.isHbmSupported(context)
        );

        // Charge control
        ChargeControlFragment.restoreStartChargingSetting(context);
        ChargeControlFragment.restoreStopChargingSetting(context);

        // PixelTorch
        ComponentUtils.toggleComponent(
                context,
                PixelTorchActivity.class,
                PixelTorchFragment.hasTorch(context)
        );

        ComponentUtils.toggleComponent(
                context,
                PixelTorchButtonService.class,
                PixelTorchFragment.hasTorch(context)
        );

        ComponentUtils.toggleComponent(
                context,
                PixelTorchTileService.class,
                PixelTorchFragment.hasTorch(context)
        );

        // Saturation
        SaturationFragment saturationFragment = new SaturationFragment();
        saturationFragment.restoreSaturationSetting(context);
    }
}
