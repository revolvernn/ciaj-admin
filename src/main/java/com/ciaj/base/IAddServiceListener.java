package com.ciaj.base;

/**
 * @author ciaj
 */
public interface IAddServiceListener <PO>{
    /**
     * 添加前调用
     * @param po
     */
    default void preAdd(PO po) {

    }

    /**
     * 添加后调用
     * @param po
     */
    default void postAdd(PO po) {

    }
}
