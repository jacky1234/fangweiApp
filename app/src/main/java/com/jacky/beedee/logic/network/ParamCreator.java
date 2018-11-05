package com.jacky.beedee.logic.network;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ParamCreator {
    public static final int PAGE_SIZE = 10;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType TYPE_FILE = MediaType.parse("application/octet-stream");
    private static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public static MultipartBody.Part prepareFilePart(String partName, @NotNull File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
}
