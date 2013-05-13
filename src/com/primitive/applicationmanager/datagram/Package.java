/**
 * Package
 * 
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT Licens (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.applicationmanager.datagram;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.primitive.applicationmanager.ApplicationManager;
import com.primitive.library.helper.Logger;

/**
 * Package
 */
public class Package extends ApplicationManagerDatagram {
	/** serialVersionUID */
	private static final long serialVersionUID = 1772622872971057448L;
	private static final String Name = "name";
	private static final String Application = "application";
	private static final String EnableDate = "enableDate";
	private static final String ExpirationDate = "expirationDate";
	private static final String Preference = "preference";
	private static final String Sale = "sale";
	private static final String Type = "type";
	private static final String Password = "password";
	private static final String CreateAt = "createAt";
	private static final String UpdateAt = "updateAt";
	private static final String Resources = "resources";
	private static final String Pay = "pay";
	private static final String Free = "free";
	private static final String[] TAGs = { 
		ApplicationManagerDatagram.Id, 
		Package.Name,
		Package.Application, 
		Package.EnableDate, 
		Package.ExpirationDate,
		Package.Preference, 
		Package.Sale, 
		Package.Type, 
		Package.CreateAt,
		Package.UpdateAt, 
		Package.Resources, 
		Package.Pay, 
		Package.Free,
	};
	private final ArrayList<Resource> resources = new ArrayList<Resource>();

	/**
	 * Package initialize
	 * @param json
	 */
	public Package(final JSONObject json) {
		super(json);
		try {
			final JSONArray resources = json.getJSONArray(Package.Resources);
			for (int i = 0; i < resources.length(); i++) {
				final JSONObject obj = resources.getJSONObject(i);
				this.resources.add(new Resource(obj));
			}
		} catch (final JSONException ex) {
			Logger.err(ex);
		}
	}

	@Override
	protected String[] getTags() {
		return Package.TAGs;
	}


	/**
	 * I will return the value that is set to Package.Application tag
	 * @return String
	 */
	public String getApplication() {
		Logger.start();
		return super.getString(Package.Application);
	}

	/**
	 * I will return the value that is set to Package.CreateAt tag
	 * @return Date
	 */
	public Date getCreateAt() {
		Logger.start();
		return this.getDate(Package.CreateAt);
	}

	/**
	 * I will return the value that is set to Package.EnableDate tag
	 * @return Date
	 */
	public Date getEnableDate() {
		Logger.start();
		Date date = this.getDate(Package.EnableDate);
		if (date != null) {
			date = new Date((date.getTime() + (1 * 24 * 60 * 60)) - 1);
		}
		return date;
	}

	/**
	 * I will return the value that is set to Package.EnableDate tag
	 * @return String
	 */
	public String getEnableDateString() {
		Logger.start();
		String date = this.getString(Package.EnableDate);
		if(date != null){
			StringTokenizer strt = new StringTokenizer(date,"T");
			if(strt.hasMoreTokens()){
				date = strt.nextToken();
			}
		}
		return date;
	}

	/**
	 * I will return the value that is set to Package.EnableDate tag
	 * @return Long
	 */
	public Long getEnableTime() {
		final Date date = this.getExpirationDate();
		return date != null ? date.getTime() : Long.MAX_VALUE;
	}

	/**
	 * I will return the value that is set to Package.ExpirationDate tag
	 * @return Date
	 */
	public Date getExpirationDate() {
		Logger.start();
		Date date = this.getDate(Package.ExpirationDate);
		if (date != null) {
			date = new Date((date.getTime() + (1 * 24 * 60 * 60)) - 1);
		}
		return date;
	}

	/**
	 * I will return the value that is set to Package.ExpirationDate tag
	 * @return String
	 */
	public String getExpirationDateString() {
		Logger.start();
		String date = this.getString(Package.ExpirationDate);
		if(date != null){
			StringTokenizer strt = new StringTokenizer(date,"T");
			if(strt.hasMoreTokens()){
				date = strt.nextToken();
			}
		}
		return date;
	}

	/**
	 * I will return the value that is set to Package.ExpirationDate tag
	 * @return Long
	 */
	public Long getExpirationTime() {
		final Date date = this.getExpirationDate();
		return date != null ? date.getTime() : Long.MAX_VALUE;
	}

	/**
	 * I will return the value that is set to Package.Name tag
	 * @return String
	 */
	public String getName() {
		Logger.start();
		return super.getString(Package.Name);
	}

	/**
	 * I will return the value that is set to Package.Password tag
	 * @return String
	 */
	public String getPassword() {
		Logger.start();
		return super.getString(Package.Password);
	}

	/**
	 * I will return the value that is set to Package.Preference tag
	 * @return int
	 */
	public int getPreference() {
		Logger.start();
		return super.getInt(Package.Preference);
	}

	/**
	 * I will return the resources to match the system language.
	 * @return Resource
	 */
	public Resource getResource() {
		final String language = Locale.getDefault().getLanguage();
		Logger.debug(language);
		Resource defaultResouce = null;
		for (final Resource res : this.resources) {
			Logger.debug(res);
			if (language.equals(res.getLocale())) {
				return res;
			} else if ("en".equals(res.getLocale())) {
				defaultResouce = res;
			}
		}
		return defaultResouce;
	}

	/**
	 * I will return the value that is set to Package.Resources tag
	 * @return Resource[]
	 */
	public Resource[] getResources() {
		return this.resources.toArray(new Resource[]{});
	}

	/**
	 * I will return the value that is set to Package.Sale tag
	 * @return String
	 */
	public String getSale() {
		Logger.start();
		return super.getString(Package.Sale);
	}

	/**
	 * I will return the value that is set to Package.Type tag
	 * @return String
	 */
	public String getType() {
		return super.getString(Package.Type);
	}

	/**
	 * I will return the value that is set to Package.UpdateAt tag
	 * @return String
	 */
	public Date getUpdateAt() {
		return this.getDate(Package.UpdateAt);
	}

	/**
	 * I will return packages have enabled.
	 * @return boolean
	 */
	public boolean isEnable() {
		boolean result = true;
		final Date enableDate = this.getEnableDate();
		if (enableDate != null) {
			final ApplicationManager am = ApplicationManager.getInstance();
			final Date timestamp = am.getTimestamp();
			final long enableTime = enableDate.getTime();
			final long timestampTime = timestamp != null ? timestamp.getTime() : 0;
			if (enableTime >= timestampTime) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * Package will be returned or expired.
	 * @return boolean
	 */
	public boolean isExpiration() {
		boolean result = true;
		final Date expirationDate = this.getExpirationDate();
		if(expirationDate != null) {
			final ApplicationManager am = ApplicationManager.getInstance();
			final Date timestamp = am.getTimestamp();
			final long expirationTime = expirationDate.getTime();
			final long timestampTime = timestamp != null ? timestamp.getTime() : 0;
			if (expirationTime <= timestampTime) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * I will return the free or package.
	 * @return boolean
	 */
	public boolean isFreePackage() {
		return Package.Free.equals(this.getSale());
	}

	/**
	 * I will return the package or for a fee.
	 * @return boolean
	 */
	public boolean isPayPackage() {
		return Package.Pay.equals(this.getSale());
	}
}
