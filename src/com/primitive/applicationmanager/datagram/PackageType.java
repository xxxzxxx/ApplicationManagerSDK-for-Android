/**
 * PackageType
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT Licens (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.applicationmanager.datagram;

import org.json.JSONObject;

import com.primitive.library.common.log.Logger;

/**
 * PackageType
 */
public class PackageType extends ApplicationManagerDatagram {
	/** serialVersionUID */
	private static final long serialVersionUID = -2753755409836691022L;
	private static final String Value = "value";
	private static final String[] TAGs = {
		ApplicationManagerDatagram.Id,
		PackageType.Value,
	};

	/**
	 * PackageType initialize
	 * @param json
	 */
	public PackageType(final JSONObject json) {
		super(json);
	}

	@Override
	protected String[] getTags() {
		return PackageType.TAGs;
	}

	/**
	 * I will return the value that is set to PackageType.Value tag
	 * @return String
	 */
	public String getValue() {
		Logger.start();
		return super.getString(PackageType.Value);
	}
}
