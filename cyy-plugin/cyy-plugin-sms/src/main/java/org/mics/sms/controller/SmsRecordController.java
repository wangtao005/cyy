package org.mics.sms.controller;

import org.mics.token.constant.TokenConstant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 普通短信发送
 * @author mics
 * @date 2020年5月21日
 * @version  1.0
 */
@RestController
@RequestMapping(value = "/sms", headers = {TokenConstant.API_VERSION_CODE_ONE})
public class SmsRecordController {

}
