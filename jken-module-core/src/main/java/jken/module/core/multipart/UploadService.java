package jken.module.core.multipart;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    /**
     * @param multipartFile
     * @return
     */
    String uploadPublic(MultipartFile multipartFile);

    /**
     * @param multipartFile
     * @return
     */
    String uploadPrivate(MultipartFile multipartFile);
}
