package com.microsoft.identity.common.integration.ClientCredentialsGrant.OAuth2;

import com.microsoft.identity.common.internal.providers.keys.CertificateCredential;
import com.microsoft.identity.common.internal.providers.keys.ClientCertificateMetadata;
import com.microsoft.identity.common.internal.providers.keys.KeyStoreConfiguration;
import com.microsoft.identity.common.internal.providers.microsoft.MicrosoftClientAssertion;
import com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsOAuth2Configuration;
import com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsOAuth2Strategy;
import com.microsoft.identity.common.internal.providers.oauth2.OAuth2Strategy;
import com.microsoft.identity.common.internal.providers.oauth2.TokenRequest;
import com.microsoft.identity.common.internal.providers.oauth2.TokenResult;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import static org.junit.Assert.assertEquals;



@RunWith(JUnit4.class)
public class MicrosoftSTSClientCredentialsGrantTest {

    private final static String CLIENT_ID = "4bc6e96f-bd23-408f-8ecb-a7a7145463f9";
    private final static String SCOPE = "https://management.core.windows.net/.default";
    private final static String GRANT_TYPE = "client_credentials";
    private final static String CERTIFICATE_ALIAS = "AutomationRunner";
    private final static String KEYSTORE_TYPE = "Windows-MY";
    private final static String KEYSTORE_PROVIDER = "SunMSCAPI";
    private final static String MSSTS_CLIENT_ASSERTION_AUDIENCE = "https://login.microsoftonline.com/microsoft.com/oauth2/v2.0/token";

    @Test
    public void test_ClientCredentials() throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException, IOException {

        CertificateCredential credential = new CertificateCredential.CertificateCredentialBuilder(CLIENT_ID)
                .clientCertificateMetadata(new ClientCertificateMetadata(CERTIFICATE_ALIAS, null))
                .keyStoreConfiguration(new KeyStoreConfiguration(KEYSTORE_TYPE, KEYSTORE_PROVIDER, null))
                .build();

        String audience = MSSTS_CLIENT_ASSERTION_AUDIENCE;

        MicrosoftClientAssertion assertion = new MicrosoftClientAssertion(audience, credential);

        TokenRequest tr = new TokenRequest();

        tr.setClientAssertionType(assertion.getClientAssertionType());
        tr.setClientAssertion(assertion.getClientAssertion());
        tr.setClientId(CLIENT_ID);
        tr.setScope(SCOPE);
        tr.setGrantType(GRANT_TYPE);

        OAuth2Strategy strategy = new MicrosoftStsOAuth2Strategy(new MicrosoftStsOAuth2Configuration());

        TokenResult tokenResult = strategy.requestToken(tr);

        assertEquals(true, tokenResult.getSuccess());

    }


}
