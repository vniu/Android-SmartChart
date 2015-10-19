package com.lecast.smartchart.utils;

import java.lang.reflect.Field;

/**
 * @author vincent
 * 
 */
public class ReflectUtils
{

    public static Object getProperty(Object object, String name)
    {
        try
        {
            Class clazz = object.getClass();
            // Field[] fields = clazz.getDeclaredFields();
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        } catch (SecurityException e)
        {
            e.printStackTrace();
        } catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
