package com.ciaj.comm.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class Safes {
    public static <T> Stream<T> of(List<T> source) {
        return Optional.ofNullable(source).orElse(Lists.newArrayListWithCapacity(0)).stream().filter(x -> x != null);
    }

    public static <T> Stream<T> of(Set<T> source) {
        return Optional.ofNullable(source).orElse(Sets.newHashSetWithExpectedSize(0)).stream().filter(x -> x != null);
    }

    public static <T> Stream<T> of(T... source) {
        return Arrays.stream(source).filter(x -> x != null);
    }

}