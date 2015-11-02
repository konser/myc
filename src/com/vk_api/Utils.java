package com.vk_api;

import java.io.IOException;
import java.util.Map;

public class Utils {
	private static HTTPResult getHTML(HTTPRequest req, String urlToRead)
			throws IOException {
		return req.Get(urlToRead);
	}

	private static final String base_url = "https://api.vk.com/method/";

	protected static HTTPResult Request(String method,
			Map<String, String> arguments) throws IOException {
		String t = base_url + method + "?";
		for (Map.Entry<String, String> entry : arguments.entrySet()) {
			t += entry.getKey() + "=" + entry.getValue() + "&";
		}
		HTTPRequest req = new HTTPRequest();
		return getHTML(req, t);
	}

	public static void main(String[] args) throws Exception{
		HTTPRequest req = new HTTPRequest();
		String appid = "wxb70b13f5f24c1e47";
		String secret = "af57701a9d5c92b2fe80d78e045ccf14";
		HTTPResult result = req.Get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret);
		System.out.println(result.Result());
		System.out.println("ghVO7yqmWNypn2nIydEx_UylkgpIOrxSoin7jSONVYjBlbppE-9qUJSgpxqjCQFedGL6rN4RTJlD5iukGqj-4jTLNgrfGSbOq7Xq-_sS91A"
						.length());
	}
}
