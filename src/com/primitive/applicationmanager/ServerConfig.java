/**
 * ServerConfig
 * 
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT Licens (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.applicationmanager;

import java.io.File;

import com.primitive.applicationmanager.helper.Logger;

/**
 * ServerConfig
 * 接続先設定をカプセル化したオブジェクト定義
 */
public class ServerConfig {
	/**
	 * Protocol
	 * @return Protocol
	 */
	public enum Protocol {
		HTTP, HTTPS
	};

	/**
	 * Version
	 * @returen Version
	 */
	public enum Version {
		v1
	};

	/** Domain */
	final String domain;
	/** PassPhrase */
	final String passPhrase;
	/** Protocol */
	final Protocol protocol;
	/** Version */
	final Version version;
	/** cacheDir */
	final File cacheDir;

	/**
	 * ServerConfig
	 * @param domain
	 * @param protocol
	 * @param version
	 * @param passPhrase
	 * @param cacheDir
	 */
	public ServerConfig(final String domain, final Protocol protocol,
			final Version version, final String passPhrase,
			final File cacheDir) {
		this.domain = domain;
		this.protocol = protocol;
		this.version = version;
		this.passPhrase = passPhrase;
		this.cacheDir = cacheDir;
	}

	/**
	 * buildServerURL
	 * 
	 * @param argUri
	 * @return String
	 */
	public String buildServerURL(final String argUri) {
		Logger.start();
		final StringBuilder sb = new StringBuilder();
		switch (this.protocol) {
		case HTTP:
			sb.append("http://");
			break;
		case HTTPS:
			sb.append("https://");
			break;
		}
		sb.append(this.domain);
		sb.append("/");
		sb.append(argUri);
		return sb.toString();
	}
}
