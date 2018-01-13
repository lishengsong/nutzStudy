package com.gree.mvc;

import org.nutz.mvc.NutFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @user: 180296-Web寻梦狮
 * @date: 2018-01-12 11:00
 * @description: 重写
 */
public class NutzStudyNutFilter extends NutFilter {


    protected Set<String> prefixs = new HashSet<String>();


    @Override
    public void init(FilterConfig conf) throws ServletException {
        super.init(conf);
        prefixs.add(conf.getServletContext().getContextPath() + "/druid/");
        prefixs.add(conf.getServletContext().getContextPath() + "/rs/");
        /*可以省去
        <init-param>
          <param-name>exclusions</param-name>
          <param-value>/rs/*,/druid/*</param-value>
        </init-param>
        */

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if (req instanceof HttpServletRequest) {
            String uri = ((HttpServletRequest) req).getRequestURI();
            for (String prefix : prefixs) {
                if (uri.startsWith(prefix)) {
                    chain.doFilter(req, resp);
                    return;
                }
            }
        }
        super.doFilter(req, resp, chain);
    }
}
