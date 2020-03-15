package jken.module.core.multipart;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UploadServiceImpl implements UploadService {

    @Override
    public String uploadPublic(MultipartFile multipartFile) {
        return null;
    }

    @Override
    public String uploadPrivate(MultipartFile multipartFile) {
        return null;
    }
}
