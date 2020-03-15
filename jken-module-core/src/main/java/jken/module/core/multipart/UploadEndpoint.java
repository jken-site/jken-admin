package jken.module.core.multipart;

import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/upload")
public class UploadEndpoint {

    @Autowired
    private UploadService uploadService;

    @RequestMapping(value = "/public", method = RequestMethod.POST)
    @ResponseBody
    public String uploadPublic(@RequestPart("file") MultipartFile[] parts) {
        return Joiner.on(",").join(Arrays.stream(parts).map(uploadService::uploadPublic).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/private", method = RequestMethod.POST)
    @ResponseBody
    public String uploadPrivate(@RequestPart("file") MultipartFile[] parts) {
        return Joiner.on(",").join(Arrays.stream(parts).map(uploadService::uploadPrivate).collect(Collectors.toList()));
    }

}
