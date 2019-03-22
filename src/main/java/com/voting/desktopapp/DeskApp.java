package com.voting.desktopapp;

import com.voting.desktopapp.form.MainGUIForm;

import javax.net.ssl.*;
import javax.swing.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class DeskApp {

    public static void main(String[] args) throws Exception {
        fixSSL(); // or use JVM Args, but it doesn't work  : -Dcom.sun.net.ssl.checkRevocation=false
        //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        UIManager.getDefaults().put("TextArea.font", UIManager.getFont("TextField.font"));

        SwingUtilities.invokeLater(() -> {
            MainGUIForm guiForm = new MainGUIForm();
            guiForm.setVisible(true);
        });

    }


    private static void fixSSL() throws NoSuchAlgorithmException, KeyManagementException {
        /*
         *  fix for
         *    Exception in thread "main" javax.net.ssl.SSLHandshakeException:
         *       sun.security.validator.ValidatorException:
         *           PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException:
         *               unable to find valid certification path to requested target
         */
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }


                    public void checkServerTrusted(X509Certificate[] certs, String authType) {  }

                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        /*
         * end of the fix
         */
    }
}
