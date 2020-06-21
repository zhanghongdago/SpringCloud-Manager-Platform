/*
 *  Copyright (C) 2018  laogandie<463540703@qq.com>

 *  AG-Enterprise 企业版源码
 *  郑重声明:
 *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  老A将追究授予人和传播人的法律责任!

 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.github.wxiaoqi.cloud.auth.module.application.service;

import com.github.wxiaoqi.cloud.auth.module.application.entity.Client;
import com.github.wxiaoqi.cloud.auth.module.application.util.GeneratorUtils;
import com.github.wxiaoqi.cloud.common.context.BaseContextHandler;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.zip.ZipOutputStream;

/**
 * @author ace
 * @version 2018/1/17.
 */
@Component
public class GeneratorService {
    /**
     * 生成工程
     * @return
     * @param client
     * @param packageName
     */
    public byte[] buildProject(Client client, String packageName){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        HashMap<String, Object> map = new HashMap<>();
        map.put("package", packageName);
        map.put("author", BaseContextHandler.getUsername());
        map.put("description", client.getDescription());
        // 决定服务名\maven模块名\主文件夹名
        map.put("clientId", client.getCode());
        map.put("clientSecret", client.getSecret());
        GeneratorUtils.buildProject(map,zip);
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
}
