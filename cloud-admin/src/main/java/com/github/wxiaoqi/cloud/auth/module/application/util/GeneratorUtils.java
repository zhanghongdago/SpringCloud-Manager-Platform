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

package com.github.wxiaoqi.cloud.auth.module.application.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.joda.time.DateTime;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 *
 * @author chenshun
 * @version 2016年12月19日 下午11:40:24
 * @email sunlightcs@gmail.com
 */
public class GeneratorUtils {

    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<String>();
        // maven依赖
        templates.add("template/pom.xml.vm");

        templates.add("template/application.yml.vm");
        templates.add("template/bootstrap.yml.vm");
        // docker相关
        templates.add("template/Dockerfile.vm");
        templates.add("template/wait-for-it.sh.vm");

        templates.add("template/Bootstrap.java.vm");

        templates.add("template/FeignConfiguration.java.vm");
        templates.add("template/MybatisDataConfiguration.java.vm");
        templates.add("template/ResourceServerConfiguration.java.vm");
        templates.add("template/WebConfiguration.java.vm");
        templates.add("template/SecurityConfiguration.java.vm");

        return templates;
    }

    /**
     * 生成代码
     */
    public static void buildProject(Map<String, Object> config, ZipOutputStream zip) {

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //封装模板数据
        config.put("datetime", DateTime.now().toString());
        // 决定服务名\maven模块名\主文件夹名
        VelocityContext context = new VelocityContext(config);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(getFileName(template, config)));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException("工程渲染失败，服务名：" + config.get("clientId"), e);
            }
        }
    }


    /**
     * 获取文件名
     */
    public static String getFileName(String template, Map<String, Object> config) {
        String moduleName = config.get("clientId").toString();
        String packageName = config.get("package").toString();
        String mainPath = moduleName + File.separator;
        String packagePath = mainPath + "src" + File.separator + "main" + File.separator + "java" + File.separator;
        String ymlPath = mainPath + "src" + File.separator + "main" + File.separator + "resources" + File.separator;
        String dockerPath = mainPath + "src" + File.separator + "main" + File.separator + "docker" + File.separator;

        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }
        if (template.contains("application") || template.contains("bootstrap")) {
            return ymlPath + getFileName(template);
        }
        if (template.contains("Dockerfile") || template.contains("wait-for")) {
            return dockerPath + getFileName(template);
        }
        if (template.contains("pom")) {
            return mainPath + getFileName(template);
        }
        if (template.contains("WebConfiguration")
                || template.contains("MybatisDataConfiguration")
                || template.contains("FeignConfiguration")
                || template.contains("ResourceServerConfiguration")
                || template.contains("SecurityConfiguration")) {
            return packagePath + "config" + File.separator + getFileName(template);
        }
        if (template.contains("Bootstrap")) {
            return packagePath + getFileName(template);
        }
        return null;
    }

    private static String getFileName(String template) {
        return template.substring("template".length() + 1, template.length() - ".vm".length());
    }
}
