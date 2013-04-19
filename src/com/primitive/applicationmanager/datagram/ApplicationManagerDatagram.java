/**
 * ApplicationManagerDatagram
 * 
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT Licens (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.applicationmanager.datagram;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.primitive.applicationmanager.helper.DateUtility;
import com.primitive.applicationmanager.helper.Logger;

/**
 * ApplicationManagerDatagram
 */
public abstract class ApplicationManagerDatagram implements Serializable {
	private static final long serialVersionUID = -4224625233854390371L;
	private final JSONObject json;
	protected static final String Id = "_id";

	/**
	 * ApplicationManagerDatagram initialize
	 * @param json
	 */
	public ApplicationManagerDatagram(final JSONObject json) {
		this.json = json;
	}

	/**
	 * パラメータ指定したタグの情報をDate型に変換して返却します。
	 * @param argTag
	 * @return Date
	 */
	public Date getDate(final String argTag) {
		final String dateString = this.getString(argTag);
		if (dateString == null) {
			return null;
		}
		Date date = null;
		try {
			date = DateUtility.getUTCDate(dateString);
		} catch (final ParseException ex) {
			Logger.warm(ex);
		}
		return date;
	}

	/**
	 * I will return the value that is set to ApplicationManagerDatagram.ID tag
	 * @return String
	 */
	public String getID() {
		Logger.start();
		return this.getString(ApplicationManagerDatagram.Id);
	}

	/**
	 * パラメータ指定したタグの情報をint型に変換して返却します。
	 * @param argTag
	 * @return int
	 */
	public int getInt(final String argTag) {
		try {
			return this.json.getInt(argTag);
		} catch (final JSONException ex) {
			Logger.warm(ex);
			return Integer.MIN_VALUE;
		}
	}

	/**
	 * パラメータ指定したタグの情報をString型に変換して返却します。
	 * @param argTag
	 * @return String
	 */
	public String getString(final String argTag) {
		try {
			return this.json.getString(argTag);
		} catch (final JSONException ex) {
			Logger.warm(ex);
			return null;
		}
	}

	/**
	 * タグ定義リストを返却する仮想関数定義
	 * @return String[]
	 */
	protected abstract String[] getTags();

	@Override
	/**
	 * 自身の内容をStringで表現し返却します。
	 * @retrun String
	 */
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		final String[] tags = this.getTags();
		for (final String tag : tags) {
			sb.append(tag + ":" + this.getString(tag) + "\n");
		}
		return sb.toString();
	}
}
