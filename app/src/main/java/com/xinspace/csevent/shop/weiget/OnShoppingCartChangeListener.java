package com.xinspace.csevent.shop.weiget;

/**
 * Created by Administrator on 2015/11/3.
 */
public interface OnShoppingCartChangeListener {
    void onDataChange(String selectCount, String selectMoney);
    void onSelectItem(boolean isSelectedAll);
}
