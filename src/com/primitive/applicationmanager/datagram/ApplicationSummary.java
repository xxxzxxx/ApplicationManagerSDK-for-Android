/**
 * ApplicationSummary
 * 
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT Licens (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.applicationmanager.datagram;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.primitive.library.helper.Logger;

/**
 * ApplicationSummary
 */
public class ApplicationSummary extends ApplicationManagerDatagram {
	/** serialVersionUID */
	private static final long serialVersionUID = -8087875781906435392L;
	/** version */
	private static final String Version = "version";
	/** name */
	private static final String Name = "name";
	/** secret */
	private static final String Secret = "secret";
	/** packageTypes */
	private static final String PackageTypes = "packageTypes";

	/**
	 * defined tag list
	 */
	private static final String[] TAGs = { 
		ApplicationManagerDatagram.Id,
		ApplicationSummary.Version, 
		ApplicationSummary.Name,
		ApplicationSummary.Secret, 
		ApplicationSummary.PackageTypes,
	};

	private final ArrayList<PackageType> packageTypes = new ArrayList<PackageType>();

	/**
	 * ApplicationManagerDatagram initialize
	 * @param json
	 */
	public ApplicationSummary(final JSONObject json) {
		super(json);
		Logger.start();
		try {
			final JSONArray packageTypes = json
					.getJSONArray(ApplicationSummary.PackageTypes);
			Logger.debug(packageTypes);

			for (int i = 0; i < packageTypes.length(); i++) {
				final JSONObject obj = packageTypes.getJSONObject(i);
				Logger.debug(obj);
				this.packageTypes.add(new PackageType(obj));
			}

		} catch (final JSONException ex) {
			Logger.err(ex);
		}
		Logger.end();
	}

	@Override
	protected String[] getTags() {
		return ApplicationSummary.TAGs;
	}

	/**
	 * I will return the value that is set to ApplicationSummary.Name tag
	 * @return String
	 */
	public String getName() {
		Logger.start();
		return super.getString(ApplicationSummary.Name);
	}

	/**
	 * I will return the value that is set to ApplicationSummary.PackageTypes tag
	 * @return PackageType[]
	 */
	public PackageType[] getPackageTypes() {
		Logger.start();
		return this.packageTypes.toArray(new PackageType[]{});
	}

	/**
	 * I will return the value that is set to ApplicationSummary.Secret tag
	 * @return String
	 */
	public String getSecret() {
		Logger.start();
		return super.getString(ApplicationSummary.Secret);
	}

	/**
	 * I will return the value that is set to ApplicationSummary.Version tag
	 * @return String
	 */
	public String getVersion() {
		Logger.start();
		return super.getString(ApplicationSummary.Version);
	}
}
