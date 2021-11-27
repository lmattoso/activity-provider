package com.m2w.sispj.service;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.CommitInfo;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.GetTemporaryLinkResult;
import com.dropbox.core.v2.files.GetTemporaryUploadLinkResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
@Slf4j
public class FileUploadService {

    @Value("${sispj.file-upload-token}")
    private String token;

    public static String generateCustomerDirectoryName(long customerId) {
        return String.format("/customer/%d", customerId);
    }

    public static String generateCustomerDocumentPath(long customerId, long documentId) {
        return String.format("/customer/%d/%d", customerId, documentId);
    }

    public static String generateCustomerFileName(long customerId, long documentId, String fileName) {
        return String.format("/customer/%d/%d/%s", customerId, documentId, fileName);
    }

    public String upload(String fileName, byte[] file) {
        try {
            // Create Dropbox client
            DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
            DbxClientV2 client = new DbxClientV2(config, token);

            FileMetadata metadata = client.files().uploadBuilder(fileName).uploadAndFinish(new ByteArrayInputStream(file));

            return metadata.getPathDisplay();
        } catch (DbxException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void delete(String fileName) {
        try {
            // Create Dropbox client
            DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
            DbxClientV2 client = new DbxClientV2(config, token);

            client.files().deleteV2(fileName);
        } catch (DbxException e) {
            log.error("FileUploadService :: delete :: Error trying to remove {} from dropbox!", fileName, e);
        }
    }

    public String getTemporaryDownloadableLink(String fileName) {
        if(ObjectUtils.isEmpty(fileName)) {
            return null;
        }

        try {
            // Create Dropbox client
            DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
            DbxClientV2 client = new DbxClientV2(config, token);

            GetTemporaryLinkResult getTemporaryLinkResult = client.files().getTemporaryLink(fileName);

            return getTemporaryLinkResult.getLink();
        } catch (DbxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getTemporaryUploadLink(String path) {
        if(ObjectUtils.isEmpty(path)) {
            return null;
        }

        try {
            // Create Dropbox client
            DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
            DbxClientV2 client = new DbxClientV2(config, token);

            GetTemporaryUploadLinkResult getTemporaryLinkResult = client.files()
                .getTemporaryUploadLink(new CommitInfo(path), 60.0);

            return getTemporaryLinkResult.getLink();
        } catch (DbxException e) {
            e.printStackTrace();
        }

        return null;
    }
}