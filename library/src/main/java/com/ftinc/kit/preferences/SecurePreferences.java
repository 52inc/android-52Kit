package com.ftinc.kit.preferences;

/*
Copyright (C) 2012 Sveinung Kval Bakken, sveinung.bakken@gmail.com

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

Modified By: Drew Heavner
Last Modified Date: 3/5/2014

 */

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * The Secure Preference class.
 *
 * This class is a encryption wrapper for the {@link android.content.SharedPreferences} object
 * in android that encrypts the data and keys when saving content to the Preference store.
 *
 */
public class SecurePreferences {

    /**
     * Secure Preference Exception error for dealing with
     * errors in operation for this class
     */
	public static class SecurePreferencesException extends RuntimeException {
		public SecurePreferencesException(Throwable e) {
			super(e);
		}
	}

    /**********************************************************
     *
     * Constants
     *
     */

	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
	private static final String KEY_TRANSFORMATION = "AES/ECB/PKCS5Padding";
	private static final String SECRET_KEY_HASH_TRANSFORMATION = "SHA-256";
	private static final String CHARSET = "UTF-8";

    /**********************************************************
     *
     * Variables
     *
     */

	private final boolean encryptKeys;
	private final Cipher writer;
	private final Cipher reader;
	private final Cipher keyWriter;
	private final SharedPreferences preferences;

	/**
	 * This will initialize an instance of the SecurePreferences class
     *
	 * @param context           your current context.
	 * @param preferenceName    name of preferences file (preferenceName.xml)
	 * @param secureKey         the key used for encryption, finding a good key scheme is hard.
	 * Hardcoding your key in the application is bad, but better than plaintext preferences. Having the user enter the key upon application launch is a safe(r) alternative, but annoying to the user.
	 * @param encryptKeys       settings this to false will only encrypt the values,
	 * true will encrypt both values and keys. Keys can contain a lot of information about 
	 * the plaintext value of the value which can be used to decipher the value.
	 * @throws com.ftinc.kit.preferences.SecurePreferences.SecurePreferencesException
	 */
	public SecurePreferences(Context context, String preferenceName, String secureKey, String salt, boolean encryptKeys) throws SecurePreferencesException {
		try {
			this.writer = Cipher.getInstance(TRANSFORMATION);
			this.reader = Cipher.getInstance(TRANSFORMATION);
			this.keyWriter = Cipher.getInstance(KEY_TRANSFORMATION);

			initCiphers(secureKey, salt);

			this.preferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);

			this.encryptKeys = encryptKeys;
		}
		catch (GeneralSecurityException e) {
			throw new SecurePreferencesException(e);
		}
		catch (UnsupportedEncodingException e) {
			throw new SecurePreferencesException(e);
		}
	}

    /**********************************************************
     *
     * Initialization Methods
     *
     */

    /**
     * Initialize the Ciphers used in encryption/decryption
     *
     * @param secureKey     the encryption key
     *
     * @throws java.io.UnsupportedEncodingException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.InvalidKeyException
     * @throws java.security.InvalidAlgorithmParameterException
     */
	protected void initCiphers(String secureKey, String salt) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException,
			InvalidAlgorithmParameterException {
		IvParameterSpec ivSpec = getIv(salt);
		SecretKeySpec secretKey = getSecretKey(secureKey);

		writer.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
		reader.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
		keyWriter.init(Cipher.ENCRYPT_MODE, secretKey);
	}

    /**
     * Get the Iv Parameter Spec
     * @return      the delicious salt
     */
	protected IvParameterSpec getIv(String salt) {
		byte[] iv = new byte[writer.getBlockSize()];
		System.arraycopy(salt.getBytes(), 0, iv, 0, writer.getBlockSize());
		return new IvParameterSpec(iv);
	}

    /**
     * Create the Secret Key Spec
     *
     * @param key       the encryption key
     * @return          the secret key spec
     *
     * @throws java.io.UnsupportedEncodingException
     * @throws java.security.NoSuchAlgorithmException
     */
	protected SecretKeySpec getSecretKey(String key) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		byte[] keyBytes = createKeyBytes(key);
		return new SecretKeySpec(keyBytes, TRANSFORMATION);
	}

    /**
     * Create the key bytes
     *
     * @param key       the encryption key
     * @return          the byte array of the hashed key
     *
     * @throws java.io.UnsupportedEncodingException
     * @throws java.security.NoSuchAlgorithmException
     */
	protected byte[] createKeyBytes(String key) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(SECRET_KEY_HASH_TRANSFORMATION);
		md.reset();
		byte[] keyBytes = md.digest(key.getBytes(CHARSET));
		return keyBytes;
	}


    /**********************************************************
     *
     * Date storing/restoring methods
     *
     */

    /**
     * Put a string into the secure preference store
     *
     * @param key       the key to store the data at
     * @param value     the value to encrypt and store
     */
	public void put(String key, String value) {
        putValue(toKey(key), value, false);
	}

    /**
     * Put a string into the secure preference store using the
     * {@link android.content.SharedPreferences.Editor#apply()} method
     * instead of {@link android.content.SharedPreferences.Editor#commit()}
     *
     * @param key       the key of the value to sent
     * @param value     the value to encrypt and store
     */
    public void putApply(String key, String value){
        putValue(toKey(key), value, true);
    }

    /**
     * Put a boolean value into the secure preference store
     *
     * @param key       the key of the value to sent
     * @param value     the boolean value you want to encrypt and store
     */
    public void putBoolean(String key, boolean value){
        // Convert value to boolean
        String boolValue = String.valueOf(value);
        put(toKey(key), boolValue);
    }

    /**
     * Put a secure set of strings into the secure store
     *
     * @param key       the key value
     * @param value     the string set to encode
     */
    public void putStringSet(String key, Set<String> value){
        if(value == null){
            preferences.edit().remove(toKey(key)).commit();
        }else{
            putSetValue(toKey(key), value);
        }
    }

    /**
     * Determin if a key exists in the secure store
     *
     * @param key       the key to check for
     * @return          true if the key exists, false otherwise
     */
	public boolean containsKey(String key) {
		return preferences.contains(toKey(key));
	}

    /**
     * Remove a key/value pair from the secure store
     * @param key       the key of the value you want to remove
     */
	public void removeValue(String key) {
		preferences.edit().remove(toKey(key)).commit();
	}

    /**
     * Get a String value from the encrypted store.
     *
     * @param key       the key of the value you wish to receive
     * @return          the unencrypted string value at the provide key, or null
     *
     * @throws SecurePreferencesException
     */
	public String getString(String key) throws SecurePreferencesException {
		if (preferences.contains(toKey(key))) {
			String securedEncodedValue = preferences.getString(toKey(key), "");
			return decrypt(securedEncodedValue);
		}
		return null;
	}

    /**
     * Get a boolean value from the encrypted store
     *
     * @param key       the key of the value you wish to retrieve
     * @return          the boolean value if found, otherwise just false
     *
     * @throws SecurePreferencesException       Such error. Much headache. Wow.
     */
    public boolean getBoolean(String key) throws SecurePreferencesException{
        if(preferences.contains(toKey(key))){
            String securedEncodedValue = preferences.getString(toKey(key), "");
            String decryptedValue = decrypt(securedEncodedValue);
            return Boolean.parseBoolean(decryptedValue);
        }
        return false;
    }

    /**
     * Get a String set from the secured preferences
     *
     * @param key       the key of the set
     * @return          the Set of strings unencoded
     *
     * @throws SecurePreferencesException       Such error. Much headache. Wow.
     */
    public Set<String> getStringSet(String key) throws SecurePreferencesException{
        if(preferences.contains(toKey(key))){
            // Get encoded string set
            Set<String> unencodedSet = new HashSet<String>();
            Set<String> encodedSet = preferences.getStringSet(toKey(key), new HashSet<String>());
            for(String val: encodedSet){
                String unencodedValue = decrypt(val);
                unencodedSet.add(unencodedValue);
            }
            return unencodedSet;
        }
        return null;
    }

    /**
     * Clear out all the key/value pairs from the store
     */
	public void clear() {
		preferences.edit().clear().commit();
	}

    /**
     * Get the correctly hashed key (if encrypted) for a given key
     *
     * @param key   the key value you want
     * @return      the encrypted key value, or just the key value if encrypted keys is disabled
     */
	private String toKey(String key) {
		if (encryptKeys)
			return encrypt(key, keyWriter);
		else return key;
	}

    /**
     * Place a key/value pair into the preference store, first encrypting the data
     *
     * @param key       the transformed or raw key
     * @param value     the value you want to encrypt and store
     *
     * @throws SecurePreferencesException
     */
	private void putValue(String key, String value, boolean apply) throws SecurePreferencesException {
		String secureValueEncoded = encrypt(value, writer);

		SharedPreferences.Editor editor = preferences.edit().putString(key, secureValueEncoded);
        if(apply){
            editor.apply();
        }else{
            editor.commit();
        }
	}

    /**
     * Put a Secure Set into the secure preferences
     *
     * @param key       the key value
     * @param value     the unencoded value set
     * @throws SecurePreferencesException       oh shit, something done goofed.
     */
    private void putSetValue(String key, Set<String> value) throws SecurePreferencesException{
        // Generate Secure String set
        Set<String> secureSetValueEncoded = new HashSet<String>();
        for(String val: value){
            // Encrypt value and insert into encoded set
            String encodedValue = encrypt(val, writer);
            secureSetValueEncoded.add(encodedValue);
        }

        preferences.edit().putStringSet(key, secureSetValueEncoded).commit();
    }

    /**********************************************************
     *
     * The Encryption/Decryption Methods
     *
     */

    /**
     * Encrypt a given string value
     *
     * @param value     the value to encrypt
     * @param writer    the encryption cipher
     * @return          the encrypted value
     *
     * @throws SecurePreferencesException
     */
	protected String encrypt(String value, Cipher writer) throws SecurePreferencesException {
		byte[] secureValue;
		try {
			secureValue = convert(writer, value.getBytes(CHARSET));
		}
		catch (UnsupportedEncodingException e) {
			throw new SecurePreferencesException(e);
		}
		String secureValueEncoded = Base64.encodeToString(secureValue, Base64.NO_WRAP);
		return secureValueEncoded;
	}

    /**
     * Decrypt a given encrypted value
     *
     * @param securedEncodedValue   the encrypted value you wish to decrypt
     * @return      the unencrypted value
     */
	protected String decrypt(String securedEncodedValue) {
		byte[] securedValue = Base64.decode(securedEncodedValue, Base64.NO_WRAP);
		byte[] value = convert(reader, securedValue);
		try {
			return new String(value, CHARSET);
		}
		catch (UnsupportedEncodingException e) {
			throw new SecurePreferencesException(e);
		}
	}

    /**
     * Finialize an Encryption/Decryption event
     *
     * @param cipher        the cipher being used
     * @param bs            the byte array of encoded values
     * @return              the ciphered byte array
     *
     * @throws SecurePreferencesException
     */
	private static byte[] convert(Cipher cipher, byte[] bs) throws SecurePreferencesException {
		try {
			return cipher.doFinal(bs);
		}
		catch (Exception e) {
			throw new SecurePreferencesException(e);
		}
	}
}
