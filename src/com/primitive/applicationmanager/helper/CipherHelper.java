/**
 * CipherHelper
 * 
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT Licens (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */
package com.primitive.applicationmanager.helper;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

/**
 * CipherHelper
 */
public class CipherHelper {
	public static String decryptAES256(
			final String encryptedData,
			final String passphrase, 
			final String iv)
		throws NoSuchAlgorithmException, 
			NoSuchPaddingException,
			InvalidKeyException, 
			InvalidAlgorithmParameterException,
			IllegalBlockSizeException, 
			BadPaddingException,
			UnsupportedEncodingException {
		Logger.start();
		final String alg = "AES/CBC/PKCS7Padding";
		Logger.debug(alg);
		final Cipher cipher = Cipher.getInstance(alg);
		final byte[] Iv = iv.getBytes("UTF-8");
		final byte[] Key = passphrase.getBytes("UTF-8");
		final IvParameterSpec ivSpec = 
				new IvParameterSpec(
						Iv,
						0,
						16
					);
		final SecretKeySpec keySpec = 
				new SecretKeySpec(
						Key,
						0,
						32,
						alg
					);
		cipher.init(
				Cipher.DECRYPT_MODE, 
				keySpec, 
				ivSpec
			);
		byte[] decode = Base64.decode(encryptedData.getBytes(), Base64.DEFAULT);
		byte[] decript = cipher.doFinal(decode);
		String decryptData = new String(decript,"UTF-8");
		return decryptData;
	}

	public static String requestSha256HMAC(
			final String key,
			final String content) {
		Logger.start();
		String signature = null;
		try {
			final byte[] secretyKeyBytes = key.getBytes();
			final SecretKeySpec secretKeySpec = new SecretKeySpec(
					secretyKeyBytes, "HmacSHA256");
			final Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(secretKeySpec);
			final byte[] data = content.getBytes("UTF-8");
			final byte[] rawHmac = mac.doFinal(data);
			signature = Base64.encodeToString(rawHmac, 0, rawHmac.length,Base64.DEFAULT);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		return signature;
	}
}
