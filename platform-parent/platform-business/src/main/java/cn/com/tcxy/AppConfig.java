package cn.com.tcxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Value("${jdbc.url}")
	private String url;

	@Value("${jdbc.username}")
	private String username;

	@Value("${jdbc.password}")
	private String password;

	@Value("${mail.host}")
	private String mailHost;

	@Value("${mail.username}")
	private String mailUserName;

	@Value("${mail.password}")
	private String mailPassword;

	@Value("${mail.from}")
	private String mailFrom;

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getMailHost() {
		return mailHost;
	}

	public String getMailUserName() {
		return mailUserName;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public String getMailFrom() {
		return mailFrom;
	}
}