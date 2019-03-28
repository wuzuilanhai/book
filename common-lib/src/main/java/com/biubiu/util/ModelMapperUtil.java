package com.biubiu.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Haibiao.Zhang on 2019-03-28 10:03
 */
public class ModelMapperUtil {

    private static ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * bean转换
     *
     * @param source 源bean
     * @param clazz  目标class对象
     * @return 目标bean
     */
    public static <T> T convert(Object source, Class<T> clazz) {
        return modelMapper.map(source, clazz);
    }

    /**
     * List<bean>转换
     *
     * @param source 源List<bean>
     * @param clazz  目标class对象
     * @return 目标List<bean>
     */
    public static <T> List<T> convertList(List<?> source, Class<T> clazz) {
        List<T> result = new ArrayList<>(source.size());
        source.forEach(s -> result.add(convert(s, clazz)));
        return result;
    }

}
