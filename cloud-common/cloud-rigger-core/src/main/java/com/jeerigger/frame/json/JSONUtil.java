package com.jeerigger.frame.json;


import com.jeerigger.frame.base.controller.FastJSON;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@Slf4j
public class JSONUtil {

    public static void writeJson(HttpServletResponse response, FastJSON json, int status) {
        PrintWriter writer;
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            writer = response.getWriter();
            writer.write(json.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static void writeJson(HttpServletResponse response, FastJSON json) {
        writeJson(response, json, SC_OK);
    }

}
