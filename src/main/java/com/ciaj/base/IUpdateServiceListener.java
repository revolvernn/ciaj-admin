package com.ciaj.base;

/**
 * @author ciaj
 */
public interface IUpdateServiceListener<PO> {
    /**
     * 在更新前调用
     * @param po
     */
    default void preUpdate(PO po) {

    }

    /**
     * 在更新后调用
     * @param po
     */
    default void postUpdate(PO po) {

    }
}
