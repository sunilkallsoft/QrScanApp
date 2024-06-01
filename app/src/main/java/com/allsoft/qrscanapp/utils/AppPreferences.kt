package com.allsoft.qrscanapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class AppPreferences(context: Context) {
    private lateinit var mPreferences: SharedPreferences

    init {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    /**
     * Sets the default values from an XML preference file
     *
     * @param context
     * The context of the shared preferences.
     * @param resId
     * The resource ID of the preference XML file.
     * @param readAgain
     * Whether to re-read the default values.
     * @throws Exception
     * if context is null or unable to set default values.
     */
    fun setDefaultValues(context: Context?, resId: Int, readAgain: Boolean) {
        PreferenceManager.setDefaultValues(context, resId, readAgain)
    }

    /**
     * Set a String value in the preferences.
     *
     * @param key
     * The name of the preference to modify.
     * @param value
     * The new value for the preference.
     * @throws Exception
     * if key or value is null.
     */
    fun putString(key: String, value: String) {
        try {
            val prefEdit = mPreferences.edit()
            prefEdit.putString(key, value)
            prefEdit.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Retrieve a String value from the preferences.
     *
     * @param key
     * The name of the preference to retrieve.
     * @return Returns the preference value if it exists, or defValue.
     * @throws ClassCastException
     * if there is a preference with this name that is not a String.
     */
    @Throws(ClassCastException::class)
    fun getString(key: String, defValue: String): String {
        return mPreferences.getString(key, defValue)?: ""
    }

    /**
     * Set a Integer value in the preferences.
     *
     * @param key
     * The name of the preference to modify.
     * @param value
     * The new value for the preference.
     * @throws Exception
     * if key or value is null.
     */
    fun putInt(key: String?, value: Int) {
        try {
            val prefEdit = mPreferences.edit()
            prefEdit.putInt(key, value)
            prefEdit.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Retrieve a Integer value from the preferences.
     *
     * @param key
     * The name of the preference to retrieve.
     * @return Returns the preference value if it exists, or defValue.
     * @throws ClassCastException
     * if there is a preference with this name that is not a int.
     */
    @Throws(ClassCastException::class)
    fun getInt(key: String?, defValue: Int): Int {
        return mPreferences.getInt(key, defValue)
    }

    /**
     * Set a Long value in the preferences.
     *
     * @param key
     * The name of the preference to modify.
     * @param value
     * The new value for the preference.
     * @throws Exception
     * if key or value is null.
     */
    fun putLong(key: String?, value: Long) {
        try {
            val prefEdit = mPreferences.edit()
            prefEdit.putLong(key, value)
            prefEdit.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Retrieve a Long value from the preferences.
     *
     * @param key
     * The name of the preference to retrieve.
     * @return Returns the preference value if it exists, or defValue.
     * @throws ClassCastException
     * if there is a preference with this name that is not a long.
     */
    @Throws(ClassCastException::class)
    fun getLong(key: String?, defValue: Long): Long {
        return mPreferences.getLong(key, defValue)
    }

    /**
     * Set a Boolean value in the preferences.
     *
     * @param key
     * The name of the preference to modify.
     * @param value
     * The new value for the preference.
     * @throws Exception
     * if key or value is null.
     */
    fun putBoolean(key: String?, value: Boolean?) {
        try {
            val prefEdit = mPreferences.edit()
            prefEdit.putBoolean(key, value!!)
            prefEdit.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Retrieve a Boolean value from the preferences.
     *
     * @param key
     * The name of the preference to retrieve.
     * @return Returns the preference value if it exists, or defValue.
     * @throws ClassCastException
     * if there is a preference with this name that is not a
     * boolean.
     */
    @Throws(ClassCastException::class)
    fun getBoolean(key: String?, defValue: Boolean): Boolean {
        return mPreferences.getBoolean(key, defValue)
    }

    /**
     * Set a Float value in the preferences.
     *
     * @param key
     * The name of the preference to modify.
     * @param value
     * The new value for the preference.
     * @throws Exception
     * if key or value is null.
     */
    fun putFloat(key: String?, value: Float) {
        try {
            val prefEdit = mPreferences.edit()
            prefEdit.putFloat(key, value)
            prefEdit.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Retrieve a Float value from the preferences.
     *
     * @param key
     * The name of the preference to retrieve.
     * @return Returns the preference value if it exists, or defValue.
     * @throws ClassCastException
     * if there is a preference with this name that is not a float.
     */
    @Throws(ClassCastException::class)
    fun getFloat(key: String?, defValue: Float): Float {
        return mPreferences.getFloat(key, defValue)
    }

    /**
     * Retrieve all values from the preferences.
     *
     * @return Map<String></String>,?> containing all values from Preferences.
     * @throws NullPointerException
     * if no data in preferences.
     */
    @Throws(NullPointerException::class)
    fun getAll(): Map<String?, *>? {
        return mPreferences.all
    }

    /**
     * Checks whether the preferences contains a preference.
     *
     * @param key
     * The name of the preference to check.
     * @return true if the preference exists in the preferences, otherwise.
     * false.
     * @throws Exception
     * if key is null.
     */
    fun contains(key: String?): Boolean {
        return mPreferences.contains(key)
    }

    /**
     * Method to remove all values from preferences.
     *
     * @throws Exception
     * if `mPreferences` is null.
     */
    fun clearPreferences() {
        try {
            val prefEdit = mPreferences.edit()
            prefEdit.clear()
            prefEdit.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Method to remove key
     *
     * @param key
     * The name of the preference to remove.
     * @throws Exception
     * if key is null.
     */
    fun removeKey(key: String?) {
        try {
            val prefEdit = mPreferences.edit()
            prefEdit.remove(key)
            prefEdit.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Set a `Set<String>` in the preferences.
     *
     * @param key
     * The name of the preference to modify.
     * @param set
     * The new value for the preference.
     * @throws Exception
     * if key or value is null.
     */
    fun putStringSet(key: String?, set: Set<String?>?) {
        try {
            val prefEdit = mPreferences.edit()
            prefEdit.putStringSet(key, set)
            prefEdit.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Retrieve a `Set<String>` from the preferences.
     *
     * @param key
     * the name of the preference to retrieve.
     * @param set
     * the default value for the preference.
     * @return Returns the preference value if it exists, or defValue.
     */
    fun getStringSet(key: String?, set: Set<String?>?): Set<String?>? {
        return mPreferences.getStringSet(key, set)
    }
}