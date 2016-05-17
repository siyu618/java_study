package com.study.java8;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by tianyuzhi on 16/5/17.
 */
public class NashornJavaScriptTest {
    public static void main(String[] args) {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine nashon = scriptEngineManager.getEngineByName("nashorn");

        String name = "Mahesh";
        Integer result = null;
        try {
            nashon.eval("print('" + name + "')");
            result = (Integer)nashon.eval("10+2");
        } catch (ScriptException e) {
            System.out.println("Error executing script:" + e.getMessage());
        }
        System.out.println(result.toString());
    }
}
