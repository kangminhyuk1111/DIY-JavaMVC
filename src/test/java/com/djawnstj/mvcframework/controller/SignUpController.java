package com.djawnstj.mvcframework.controller;

import com.djawnstj.mvcframework.boot.web.servlet.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpController implements Controller {

    @Override
    public void handleRequest(HttpServletRequest req, HttpServletResponse res) {
        System.out.println("sign-up");

    }
}