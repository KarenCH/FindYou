package com.example.findyouclient.util;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

/**
 * HttpClient������
 * 
 * @author ������
 * 
 */
public class HttpClientUtil {

	// ����HttpParams����
	private static HttpParams httpParams = new BasicHttpParams();
	// ����HttpClient����
	private static HttpClient httpClient = new DefaultHttpClient(httpParams);
//	private static HttpClient httpClient;
	// ����˵�ַ
	// private static String URL="http://chenfindyou.duapp.com/";

	private static String URL = "http://zlchen-pc.iflytek.com:8080/FindYou/";

	// ʵ�ʷ��ʵ�ַ
	private static String requestUrl;
	

//	/**
//	 * �����˷������󣬽�������ַ�����ʽ����
//	 * 
//	 * @param type
//	 *            ��������
//	 * @param params
//	 *            �������
//	 * @return ��������Ӧ�ַ���
//	 */
//	public static String getRequest(String type, String params) {
//
//		requestUrl = URL + type + "?" + params;
//
//		FutureTask<String> task = new FutureTask<String>(
//				new Callable<String>() {
//					@Override
//					public String call() throws Exception {
//						// �������ӳ�ʱ
//						// HttpConnectionParams.setConnectionTimeout(httpParams,
//						// 5000);
//						// HttpConnectionParams.setSoTimeout(httpParams, 5000);
//						// ����HttpGet����
//
//						HttpGet get = new HttpGet(requestUrl);
//
//						// ����GET����
//						HttpResponse httpResponse = httpClient.execute(get);
//
//						// ����������ɹ��ط�����Ӧ
//						if (httpResponse.getStatusLine().getStatusCode() == 200) {
//							// ��ȡ��������Ӧ�ַ���
//							HttpEntity entity = httpResponse.getEntity();
//							String result = EntityUtils.toString(entity,
//									"utf-8");
//							return result;
//						} else {
//							String result = "������쳣";
//							return result;
//						}
//					}
//
//				});
//
//		new Thread(task).start();
//
//		try {
//			return task.get();
//		} catch (Exception e) {
//			e.printStackTrace();
//			String mes = "���������쳣";
//			return mes;
//		}
//	}

	public static String postRequest(String type,
			final List<NameValuePair> params) {
		requestUrl=URL+type;
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						HttpPost post = new HttpPost(requestUrl);
						//��������������������
						post.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
						// ����post����
						HttpResponse httpResponse = httpClient.execute(post);

						// ����������ɹ��ط�����Ӧ
						if (httpResponse.getStatusLine().getStatusCode() == 200) {
							// ��ȡ��������Ӧ�ַ���
							HttpEntity entitytttt = httpResponse.getEntity();
							String result = EntityUtils.toString(entitytttt,
									"utf-8");
							return result;
						} else {
							String result = "������쳣";
							return result;
						}
					}

				});

		new Thread(task).start();

		try {
			return task.get();
		} catch (Exception e) {
			e.printStackTrace();
			String mes = "���������쳣";
			return mes;
		}
	}
	
}
