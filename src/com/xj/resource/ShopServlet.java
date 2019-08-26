package com.xj.resource;

import com.google.gson.Gson;
import com.xj.bean.ResultStatusBean;
import com.xj.bean.Shops;
import com.xj.dao.ShopsDao;
import com.xj.impl.ShopsDaoImpl;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by maxwell on 14/12/4.
 */
//@WebServlet("/shops")
@SuppressWarnings("serial")
public class ShopServlet extends HttpServlet{

    //logger
    private static Logger logger = Logger.getLogger(ShopServlet.class);
    //dao
    private ShopsDao shopDao = new ShopsDaoImpl();


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("doGet");
        //set encoding data
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        String communityId = request.getParameter("communityId");
        logger.info("community id is :"+communityId);
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        List<Shops> listShopsBeans = shopDao.findAllById(Integer.parseInt(communityId));
        logger.info("list shopbeans size is :"+ listShopsBeans.size());
        String resultJson = new Gson().toJson(listShopsBeans);
        out.print(resultJson);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //set encoding data
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        //init Gson
        Gson gson = new Gson();

        PrintWriter out = response.getWriter();

        BufferedReader reader = request.getReader();
        //parse request to bean
        Shops shopsBean = gson.fromJson(reader, Shops.class);
        //get shop name
        String shopName = shopsBean.getShopName();
        logger.info("shop name is :"+shopName);
        logger.info("record of shop id got is :"+shopDao.findIdByName(shopName));
        //result bean
        ResultStatusBean resultStatusBean = new ResultStatusBean();
        //if exist
        if(shopDao.findIdByName(shopName)>0){
            resultStatusBean.setStatus("no");
            resultStatusBean.setMessage("exist");
            String resultJson = new Gson().toJson(resultStatusBean);
            out.print(resultJson);
        }else{
            int id = shopDao.insert(shopsBean);
            logger.info("id of new shop is :"+id);
            if(id>0){
                resultStatusBean.setStatus("yes");
                out.print(gson.toJson(resultStatusBean));
            }
        }
    }

    public void doPut(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException
    {
        logger.info("do put");
    }

    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response, String method) throws IOException {

    }
}
