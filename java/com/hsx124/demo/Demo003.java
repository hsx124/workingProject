package com.hsx124.demo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;

public class Demo003 {

	public static void main(String[] args) throws Exception {
		// 设置代理服务器地址和端口号
		HttpHost proxy = new HttpHost("111.225.152.139", 8089);

		// 构建 HTTP Client 对象
		HttpClient httpClient = HttpClientBuilder.create()
				.setProxy(proxy)
				.build();

		// 构建 HTTP 请求对象
		HttpUriRequest request = new HttpGet("https://www.ip138.com/");

		// 发送 HTTP 请求并获取响应结果
		HttpResponse execute = httpClient.execute(request);
		InputStream inputStream = execute.getEntity().getContent();
		Reader rr = new InputStreamReader(inputStream);
		BufferedReader br = new BufferedReader(rr);
		while (br.readLine() != "") {
			System.out.println(br.readLine());
		}

	}
}