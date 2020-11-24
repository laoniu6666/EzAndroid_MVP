package com.laoniu.ezandroid.utils.http.httpsettings;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtils {

	public int timeout = 10;

	/**
	 * @param timeout second
	 */
	public OkHttpUtils setTimeOut(int timeout) {
		this.timeout=timeout;
		return this;
	}
	public OkHttpClient getClient() {
			X509TrustManager trustAllCert = new X509TrustManagerImpl();
			SSLSocketFactory sslSocketFactory = new SSLSocketFactoryImpl(trustAllCert);
			HostnameVerifier verifier = new HostnameVerifier() {
				@Override
				public boolean verify(String s, SSLSession sslSession) {
//					HostnameVerifier hv= HttpsURLConnection.getDefaultHostnameVerifier();
//					hv.verify("",sslSession);
					return true;
				}
			};

			OkHttpClient client = new OkHttpClient();
			OkHttpClient.Builder builder = client.newBuilder()
					.connectTimeout(timeout, TimeUnit.SECONDS)
					.readTimeout(timeout, TimeUnit.SECONDS)
					.writeTimeout(timeout, TimeUnit.SECONDS)
					.sslSocketFactory(sslSocketFactory,trustAllCert)
					.hostnameVerifier(verifier)
					.dns(new ApiDns());
			client = builder
					.addInterceptor(new LoggingInterceptor())
					.build();
		return client;
	}

}
