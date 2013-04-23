/**
 * Resource
 * 
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT Licens (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */

package com.primitive.applicationmanager.datagram;

import org.json.JSONObject;

import com.primitive.applicationmanager.helper.Logger;

/**
 * Resource
 */
public class Resource extends ApplicationManagerDatagram {
	/** serialVersionUID */
	private static final long serialVersionUID = 3771028588981515330L;
	private static final String Extra = "extra";
	private static final String Description = "description";
	private static final String Title = "title";
	private static final String Locale = "locale";
	private static final String[] TAGs = {
		ApplicationManagerDatagram.Id, 
		Resource.Extra,
		Resource.Description, 
		Resource.Title, 
		Resource.Locale, 
	};

	/**
	 * Resource initialize
	 * @param json
	 */
	public Resource(final JSONObject json) {
		super(json);
	}

	@Override
	protected String[] getTags() {
		return Resource.TAGs;
	}

	/**
	 * I will return the value that is set to Resource.Title tag
	 * @return String
	 */
	public String getTitle() {
		Logger.start();
		return super.getString(Resource.Title);
	}

	/**
	 * I will return the value that is set to Resource.Copyright tag
	 * @return String
	 */
	public String getExtra() {
		Logger.start();
		return super.getString(Resource.Extra);
	}

	/**
	 * I will return the value that is set to Resource.Description tag
	 * @return String
	 */
	public String getDescription() {
		Logger.start();
		return super.getString(Resource.Description);
	}

	/**
	 * I will return the value that is set to Resource.Locale tag
	 * @return String
	 */
	public String getLocale() {
		Logger.start();
		return super.getString(Resource.Locale);
	}
}
