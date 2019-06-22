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
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OsR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

package com.microsoft.identity.client;

/**
 * Broker Account service APIs provided by the broker app. Those APIs will be responsible for interacting with the
 * account manager API. Calling app does not need to request for contacts permission if the broker installed on the
 * device has the support for the bound service.
 */
interface IMicrosoftAuthService {

    Bundle hello(in Bundle bundle);

    Bundle getAccounts(in Bundle bundle);

    Bundle acquireTokenSilently(in Bundle requestBundle);

    Intent getIntentForInteractiveRequest();

    Bundle removeAccount(in Bundle bundle);

    Bundle getDeviceMode();

    Bundle getCurrentAccount();

    Bundle removeAccountFromSharedDevice(in Bundle bundle);
}