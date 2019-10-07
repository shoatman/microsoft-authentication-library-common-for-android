// Copyright (c) Microsoft Corporation.
// All rights reserved.
//
// This code is licensed under the MIT License.
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files(the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and / or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions :
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.
package com.microsoft.identity.common.internal.eststelemetry;

import androidx.annotation.NonNull;

import com.microsoft.identity.common.internal.cache.ISharedPreferencesFileManager;
import com.microsoft.identity.common.internal.logging.Logger;

import java.util.Map;

public abstract class SharedPreferencesRequestTelemetryCache implements IRequestTelemetryCache {

    private final static String TAG = SharedPreferencesRequestTelemetryCache.class.getSimpleName();


    // SharedPreferences used to store request telemetry data
    private final ISharedPreferencesFileManager mSharedPreferencesFileManager;

    /**
     * Constructor of SharedPreferencesRequestTelemetryCache.
     *
     * @param sharedPreferencesFileManager ISharedPreferencesFileManager
     */
    SharedPreferencesRequestTelemetryCache(
            @NonNull final ISharedPreferencesFileManager sharedPreferencesFileManager) {
        Logger.verbose(TAG, "Init: " + TAG);
        mSharedPreferencesFileManager = sharedPreferencesFileManager;
    }

    ISharedPreferencesFileManager getSharedPreferencesFileManager() {
        return mSharedPreferencesFileManager;
    }

    @Override
    public synchronized void saveRequestTelemetryToCache(@NonNull final RequestTelemetry requestTelemetry) {
        Logger.verbose(TAG, "Saving Request Telemetry to cache...");

        mSharedPreferencesFileManager.putString(Schema.SCHEMA_VERSION_KEY, Schema.CURRENT_SCHEMA_VERSION);
        saveTelemetryDataToCache(requestTelemetry.getCommonTelemetry());
        saveTelemetryDataToCache(requestTelemetry.getPlatformTelemetry());
    }

    private synchronized void saveTelemetryDataToCache(@NonNull final Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            final String cacheKey = entry.getKey();
            final String cacheValue = entry.getValue();
            mSharedPreferencesFileManager.putString(cacheKey, cacheValue);
        }
    }

    @Override
    public synchronized void clearAll() {
        Logger.info(TAG, "Clearing all SharedPreferences entries...");
        mSharedPreferencesFileManager.clear();
        Logger.info(TAG, "SharedPreferences cleared.");
    }


}