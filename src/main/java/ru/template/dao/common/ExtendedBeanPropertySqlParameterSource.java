package ru.template.dao.common;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import java.math.BigInteger;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ExtendedBeanPropertySqlParameterSource extends BeanPropertySqlParameterSource {

    private Map<String, Object> overriddenParams = new HashMap<>();
    protected final BeanWrapper beanWrapper;
    private final Set<String> fixedTypes = new HashSet<>();

    public ExtendedBeanPropertySqlParameterSource(Object object) {
        super(object);
        this.beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
    }

    private static Object getValue(Object value) {
        if (value == null) {
            return null;
        }
        if (value.getClass().isEnum()) {
            return ((Enum<?>) value).name();
        }
        if (value instanceof List<?>) {
            List<?> list = (List<?>) value;
            if (list.size() > 0 && list.get(0) != null && list.get(0).getClass().isEnum()) {
               return list.stream().map(o -> ((Enum<?>) o).name()).collect(Collectors.toList());
            } else {
                return value;
            }
        }
        if (value instanceof BigInteger) {
        	return value.toString();
        }
        return value;
    }


    @Override
	public int getSqlType(String paramName) {
    	int sqlType = super.getSqlType(paramName);
		if (sqlType == Types.BIGINT && !fixedTypes.contains(paramName)) {
			fixedTypes.add(paramName);
			Class<?> propType = beanWrapper.getPropertyType(paramName);
			if (propType.isAssignableFrom(BigInteger.class)) {
				registerSqlType(paramName, Types.VARCHAR);
				return Types.VARCHAR;
			}
		}
		return sqlType;
	}

    @Override
    public Object getValue(String paramName) throws IllegalArgumentException {
        return getValue(overriddenParams.containsKey(paramName) ?
                overriddenParams.get(paramName) : super.getValue(paramName));
    }

    public void overrideParam(String paramName, Object value) {
        overriddenParams.put(paramName, value);
    }

    @Override
    public String[] getParameterNames() {
    	if (overriddenParams.isEmpty()) {
    		return super.getParameterNames();
    	} else {
    		List<String> paramsToAdd = null;
    		String[] originalParams = super.getParameterNames();
    		for (String paramName : overriddenParams.keySet()) {
    			if (!ArrayUtils.contains(originalParams, paramName)) {
    				if (paramsToAdd == null) {
    					paramsToAdd = new ArrayList<>();
    				}
    				paramsToAdd.add(paramName);
    			}
    		}
    		if (paramsToAdd != null) {
    			String[] newParams = new String[originalParams.length + paramsToAdd.size()];
    			System.arraycopy(originalParams, 0, newParams, 0, originalParams.length);
    			for (int i=0; i<paramsToAdd.size(); i++) {
    				newParams[originalParams.length + i] = paramsToAdd.get(i);
    			}
    			return newParams;
    		} else {
    			return originalParams;
    		}
    	}
    }

    @Override
    public boolean hasValue(String paramName) {
    	return super.hasValue(paramName) || overriddenParams.containsKey(paramName);
    }
}
