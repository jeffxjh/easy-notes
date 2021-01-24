package cn.zealon.notes.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.zealon.notes.common.config.FileConfig;
import cn.zealon.notes.common.result.HttpCodeEnum;
import cn.zealon.notes.common.result.Result;
import cn.zealon.notes.common.result.ResultUtil;
import cn.zealon.notes.controller.dto.EmailEnableParam;
import cn.zealon.notes.controller.dto.RegisterBO;
import cn.zealon.notes.controller.dto.UserBO;
import cn.zealon.notes.controller.dto.UserPwdBO;
import cn.zealon.notes.security.domain.LoginUserBean;
import cn.zealon.notes.service.UserService;
import cn.zealon.notes.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: zealon
 * @since: 2020/11/20
 */
@Slf4j
@RestController
public class UserController {
    @Resource
    private FileConfig fileConfig;
    @Resource
    private UserService userService;

    @GetMapping("/user-info")
    public Result getCurrentUser(Authentication authentication) {
        LoginUserBean loginUser = (LoginUserBean) authentication.getPrincipal();
        UserInfoVO vo = new UserInfoVO();
        vo.setUserId(loginUser.getUser().getUserId());
        vo.setUserName(loginUser.getUser().getUserName());
        vo.setClients(loginUser.getUser().getClients());
        return ResultUtil.success(vo);
    }

    /**
     * 注册用户
     *
     * @param registerBO
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody RegisterBO registerBO) {
        return this.userService.register(registerBO);
    }

    /**
     * 去验证邮箱
     *
     * @param emailEnableParam
     * @return
     */
    @PostMapping("/enableUserByEmail")
    public Result<?> enableUserByEmail(@RequestBody EmailEnableParam emailEnableParam) {
        return this.userService.enableUserByEmail(emailEnableParam);
    }

    /**
     * 通过邮箱激活账号
     *
     * @param emailToken
     * @return
     */
    @GetMapping("/activateMail")
    public Result<?> activateMail(@RequestParam String emailToken) {
        return this.userService.activateMail(emailToken);
    }

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    @PostMapping("/user/update")
    public Result update(@RequestBody UserBO user) {
        return this.userService.update(user);
    }

    /**
     * 更新用户密码
     *
     * @param user
     * @return
     */
    @PostMapping("/user/update-pwd")
    public Result updatePwd(@RequestBody UserPwdBO user) {
        return this.userService.updatePwd(user);
    }

    /**
     * 更新绑定信息
     *
     * @param user
     * @return
     */
    @PostMapping("/user/remove-bind")
    public Result removeBind(@RequestBody UserBO user) {
        return this.userService.removeBind(user);
    }

    /**
     * 获取用户社交绑定信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/account-bind-list")
    public Result getAccountBindList(String userId) {
        return this.userService.getAccountBindList(userId);
    }


    /**
     * 更新用户头像
     *
     * @param imgFile
     * @return
     */
    @PostMapping("/user/upAvatar")
    public Result<?> upAvatar(@RequestParam("imgFile") MultipartFile[] imgFile, HttpServletRequest request, @RequestParam String userId) throws IOException {
        if (imgFile.length == 1) {
            try {
                MultipartFile file = imgFile[0];
                InputStream inputStream = file.getInputStream();
                String newMd5 = DigestUtil.md5Hex(inputStream);
                String filePath = fileConfig.getPath();
                String originalFilename = file.getOriginalFilename();
                String ext = FileUtil.extName(originalFilename);
                String newFileName = newMd5 + "." + ext;
                String newFilePath= filePath+newMd5 + "." + ext;
                if (!FileUtil.exist(newFilePath)) {
                    imgFile[0].transferTo(new File(newFilePath));
                }
                String finalPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/uploadFile/" + newFileName;
                log.info(finalPath);
                return this.userService.upAvatar(finalPath, userId);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.fail();
            }
        } else {
            return ResultUtil.custom(HttpCodeEnum.FILE_ERROR);
        }
    }

}
