/**
 * ApplicationManager
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT Licens (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.applicationmanager;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.primitive.applicationmanager.datagram.ApplicationSummary;
import com.primitive.applicationmanager.datagram.Package;
import com.primitive.applicationmanager.exception.ApplicationManagerException;
import com.primitive.library.helper.cipher.CipherHelper.Mode;
import com.primitive.library.helper.cipher.CipherHelper.Padding;
import com.primitive.library.helper.cipher.HashHelper;
import com.primitive.library.helper.cipher.CipherHelper;
import com.primitive.library.helper.cipher.CipherHelper.Algorithm;
import com.primitive.library.helper.DateUtility;
import com.primitive.library.helper.Logger;

/**
 * ApplicationManager
 */
public class ApplicationManager extends BaseApplicationManager {
	private static final long serialVersionUID = -8615069519678495614L;
	private static ApplicationManager instance = null;
	private static String ApplicationURI = "api/application";
	private static String TimeStampURI = "api/timestamp";
	private static String PackagesURI = "api/packages";

	/**
	 * 静的インスタンスを生成し返却します。
	 * @param applicationID
	 * @param config
	 * @return ApplicationManager.instance
	 */
	public static ApplicationManager createInstance(final String applicationID, final ServerConfig config) {
		Logger.start();
		synchronized (ApplicationManager.class) {
			if (ApplicationManager.instance == null) {
				ApplicationManager.instance = new ApplicationManager(applicationID,config);
			}
		}
		return ApplicationManager.instance;
	}

	/**
	 * 静的インスタンスを返却します。
	 * @return ApplicationManager.instance
	 */
	public static ApplicationManager getInstance() {
		return ApplicationManager.instance;
	}

	private String applicationID = null;
	private ApplicationSummary beforeSummary = null;
	private Date timestamp = null;
	private Package[] beforePackages = null;

	/**
	 * ApplicationManager
	 * @param applicationID
	 * @param config
	 */
	protected ApplicationManager(final String applicationID, final ServerConfig config) {
		super(config);
		Logger.start();
		this.applicationID = applicationID;
	}

	/**
	 * ApplicationSummaryを返却します。
	 * ネットワーク接続を行うため必ず AnsyncdTaskの中で呼び出してください。
	 * @return ApplicationSummary
	 */
	public synchronized ApplicationSummary getApplicationSummary() {
		Logger.start();
		try {
			return this.requestApplicationSummary();
		} catch (final ApplicationManagerException ex) {
			Logger.warm(ex);
		}
		return this.beforeSummary;
	}

	/**
	 * Package Array を返却します。
	 * ネットワーク接続を行うため必ず AnsyncdTaskの中で呼び出してください。
	 * @return Package[]
	 */
	public synchronized Package[] getPackages() {
		Logger.start();
		try {
			return this.requestPackages();
		} catch (final ApplicationManagerException ex) {
			Logger.warm(ex);
		}
		return this.beforePackages;
	}

	/**
	 * Timestamp を返却します。
	 * ネットワーク接続を行うため必ず AnsyncdTaskの中で呼び出してください。
	 * @return Date
	 */
	public synchronized Date getTimestamp() {
		Logger.start();
		if(this.timestamp == null){
			try {
				return this.requestTimeStamp();
			} catch (final ApplicationManagerException e) {
				Logger.warm(e);
			}
		}
		return this.timestamp;
	}

	/**
	 * 提示したApplicationSummaryと保持しているApplicationSummaryを比較しパッケージ内容の更新が必要か返却します。
	 * @param afterSummary
	 * @return boolean
	 */
	public boolean isUpgrade(final ApplicationSummary afterSummary) {
		Logger.start();
		final ApplicationSummary beforeSummary = this.beforeSummary;
		if (BuildConfig.DEBUG) {
			return true;
		} else if (beforeSummary == null || afterSummary == null) {
			return true;
		} else {
			final String afterVersion = afterSummary.getVersion();
			final String beforeVersion = beforeSummary.getVersion();
			final boolean result = !afterVersion.equals(beforeVersion);
			return result;
		}
	}

	/**
	 * ApplicationSummaryをサーバーへ要求します。
	 * @return ApplicationSummary
	 * @throws ApplicationManagerException
	 */
	private ApplicationSummary requestApplicationSummary()
			throws ApplicationManagerException {
		Logger.start();
		final String url = this.config
				.buildServerURL(ApplicationManager.ApplicationURI);
		final Map<String, String> params = new HashMap<String, String>();
		params.put("id", this.applicationID);
		final JSONObject json = this.requestToResponse(url, params);
		try {
			final boolean success = json.getBoolean("sucess");
			if (!success) {
				return this.beforeSummary;
			}
			final String hash = json.getString("hash");
			Logger.debug(hash);
			final String result = json.getString("result");
			Logger.debug(result);
			final String passphrase = HashHelper.getHMACBase64(
					com.primitive.library.helper.cipher.HashHelper.Algorithm.HmacSHA256,
					hash,
					this.config.passPhrase,
					"UTF-8");
			Logger.debug(passphrase);

			final byte[] decriptDataByte = CipherHelper.decrypt(
					Algorithm.AES,
					Mode.CBC,
					Padding.PKCS7Padding,
					result,
					hash,
					passphrase,
					256/8,
					"UTF-8"
				);
			final String decriptData = new String(decriptDataByte,"UTF-8");

			final JSONObject decript = new JSONObject(decriptData);
			final ApplicationSummary summary = new ApplicationSummary(decript);
			if (this.isUpgrade(summary)) {
				this.beforeSummary = summary;
			}
			this.beforeSummary = summary;
		} catch (final JSONException ex) {
			Logger.err(ex);
		} catch (final InvalidKeyException ex) {
			Logger.err(ex);
		} catch (final NoSuchAlgorithmException ex) {
			Logger.err(ex);
		} catch (final NoSuchPaddingException ex) {
			Logger.err(ex);
		} catch (final InvalidAlgorithmParameterException ex) {
			Logger.err(ex);
		} catch (final IllegalBlockSizeException ex) {
			Logger.err(ex);
		} catch (final BadPaddingException ex) {
			Logger.err(ex);
		} catch (final UnsupportedEncodingException ex) {
			Logger.err(ex);
		}
		return this.beforeSummary;
	}

	/**
	 * Packageをサーバーへ要求します。
	 * @return Package[]
	 * @throws ApplicationManagerException
	 */
	private Package[] requestPackages() throws ApplicationManagerException {
		Logger.start();
		final ApplicationSummary summary = this.requestApplicationSummary();
		if(this.beforePackages != null) {
			if (summary == null) {
				return null;
			} else {
				if (!this.isUpgrade(summary)) {
					return this.beforePackages;
				}
			}
		}
		final String applicationID = summary.getID();
		final String applicationName = summary.getName();
		final String url = this.config
				.buildServerURL(ApplicationManager.PackagesURI);

		final Map<String, String> params = new HashMap<String, String>();
		params.put("name", applicationName);
		params.put("application", applicationID);
		Logger.debug(Locale.getDefault().getCountry());
		Logger.debug(Locale.getDefault().getLanguage());
		params.put("region", Locale.getDefault().getCountry());
		final JSONObject json = super.requestToResponse(url, params);
		try {
			final boolean success = json.getBoolean("sucess");
			if (!success) {
				return this.beforePackages;
			}
			final String secret = summary.getSecret();
			final String hash = json.getString("hash");
			final String result = json.getString("result");
			final String passphrase = HashHelper.getHMACBase64(
					com.primitive.library.helper.cipher.HashHelper.Algorithm.HmacSHA256,
					hash,
					secret,
					"UTF-8");

			final byte[] decriptDataByte = CipherHelper.decrypt(
					Algorithm.AES,
					Mode.CBC,
					Padding.PKCS7Padding,
					result,
					hash,
					passphrase,
					256/8,
					"UTF-8"
				);
			final String decriptData = new String(decriptDataByte,"UTF-8");

			Logger.debug(decriptData);
			final JSONObject decript = new JSONObject(decriptData);
			final JSONArray packagesJSON = decript.getJSONArray("packages");
			final ArrayList<Package> packages = new ArrayList<Package>();
			for (int i = 0; i < packagesJSON.length(); i++) {
				final JSONObject object = packagesJSON.getJSONObject(i);
				final Package packageObject = new Package(object);
				packages.add(packageObject);
				Logger.debug(packageObject);
			}
			this.beforePackages = packages.toArray(new Package[]{});
		} catch (final JSONException ex) {
			Logger.err(ex);
		} catch (final InvalidKeyException ex) {
			Logger.err(ex);
		} catch (final NoSuchAlgorithmException ex) {
			Logger.err(ex);
		} catch (final NoSuchPaddingException ex) {
			Logger.err(ex);
		} catch (final InvalidAlgorithmParameterException ex) {
			Logger.err(ex);
		} catch (final IllegalBlockSizeException ex) {
			Logger.err(ex);
		} catch (final BadPaddingException ex) {
			Logger.err(ex);
		} catch (final UnsupportedEncodingException ex) {
			Logger.err(ex);
		}
		return this.beforePackages;
	}

	/**
	 * Timestampをサーバーへ要求します。
	 * @return Date
	 * @throws ApplicationManagerException
	 */
	private Date requestTimeStamp() throws ApplicationManagerException {
		Logger.start();
		final String url = this.config
				.buildServerURL(ApplicationManager.TimeStampURI);
		final Map<String, String> params = new HashMap<String, String>();
		final JSONObject json = super.requestToResponse(url, params);
		try {
			this.timestamp = DateUtility.getUTCDate(json.getString("result"));
		} catch (final ParseException e) {
			e.printStackTrace();
		} catch (final JSONException e) {
			e.printStackTrace();
		}
		return this.timestamp;
	}
}
