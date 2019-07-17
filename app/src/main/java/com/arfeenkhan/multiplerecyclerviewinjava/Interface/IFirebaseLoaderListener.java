package com.arfeenkhan.multiplerecyclerviewinjava.Interface;

import com.arfeenkhan.multiplerecyclerviewinjava.model.ItemGroup;

import java.util.List;

public interface IFirebaseLoaderListener {
    void onFirebaseLoadSuccess(List<ItemGroup> itemGroupList);
    void onFirebaseFailed(String message);
}
