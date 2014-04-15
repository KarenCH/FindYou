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
 * HttpClient工具类
 * 
 * @author 陈智磊
 * 
 */
public class HttpClientUtil {

	// 创建HttpParams对象
	private static HttpParams httpParams = new BasicHttpParams();
	// 创建HttpClient对象
	private static HttpClient httpClient = new DefaultHttpClient(httpParams);
//	private static HttpClient httpClient;
	// 服务端地址
	// private static String URL="http://chenfindyou.duapp.com/";

	private static String URL = "http://zlchen-pc.iflytek.com:8080/FindYou/";

	// 实际访问地址
	private static String requestUrl;
	

//	/**
//	 * 向服务端发起请求，讲结果以字符串形式返回
//	 * 
//	 * @param type
//	 *            请求类型
//	 * @param params
//	 *            请求参数
//	 * @return 服务器响应字符串
//	 */
//	public static String getRequest(String type, String params) {
//
//		requestUrl = URL + type + "?" + params;
//
//		FutureTask<String> task = new FutureTask<String>(
//				new Callable<String>() {
//					@Override
//					public String call() throws Exception {
//						// 设置连接超时
//						// HttpConnectionParams.setConnectionTimeout(httpParams,
//						// 5000);
//						// HttpConnectionParams.setSoTimeout(httpParams, 5000);
//						// 创建HttpGet对象
//
//						HttpGet get = new HttpGet(requestUrl);
//
//						// 发送GET请求
//						HttpResponse httpResponse = httpClient.execute(get);
//
//						// 如果服务器成功地返回响应
//						if (httpResponse.getStatusLine().getStatusCode() == 200) {
//							// 获取服务器响应字符串
//							HttpEntity entity = httpResponse.getEntity();
//							String result = EntityUtils.toString(entity,
//									"utf-8");
//							return result;
//						} else {
//							String result = "服务端异常";
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
//			String mes = "服务器端异常";
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
						//处理请求参数到请求对象
						post.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
						// 发送post请求
						HttpResponse httpResponse = httpClient.execute(post);

						// 如果服务器成功地返回响应
						if (httpResponse.getStatusLine().getStatusCode() == 200) {
							// 获取服务器响应字符串
							HttpEntity entitytttt = httpResponse.getEntity();
							String result = EntityUtils.toString(entitytttt,
									"utf-8");
							return result;
						} else {
							String result = "服务端异常";
							return result;
						}
					}

				});

		new Thread(task).start();

		try {
			return task.get();
		} catch (Exception e) {
			e.printStackTrace();
			String mes = "服务器端异常";
			return mes;
		}
	}
	
}
