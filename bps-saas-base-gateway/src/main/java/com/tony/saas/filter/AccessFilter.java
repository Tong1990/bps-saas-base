package com.tony.saas.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.tony.saas.constant.ZuulConst;
import com.tony.saas.util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

/**
 * 前置过滤器
 * Created by Administrator on 2018-8-23.
 */
public class AccessFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    @Value("${zuul.prefix}")
    private String zuulPrefix;
    @Value("${gate.ignore.startWith}")
    private String startWith;

    /**
     * zuul 中定义了四种不同生命周期的过滤器类型：
     * 1、pre：可以在请求被路由之前调用；
     * 2、route：在路由请求时候被调用；
     * 3、post：在route和error过滤器之后被调用；
     * 4、error：处理请求时发生错误时被调用；
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤的优先级，数字越大，优先级越低。
     * @return
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 是否执行该过滤器。
     * true：说明需要过滤；
     * false：说明不要过滤；
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return false;
    }

    /**
     * 过滤器的具体逻辑。
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        final String requestUri = request.getRequestURI().substring(zuulPrefix.length());
        String token = request.getHeader(ZuulConst.AUTHORIZATION_KEY);
        //不进行拦截的地址
        if(isStartWith(requestUri)){
            return null;
        }
        if(token == null){
            ctx.setSendZuulResponse(false);// 过滤该请求，不对其进行路由
            ctx.setResponseStatusCode(40001);// 返回错误码
            ctx.setResponseBody("null token");// 返回错误内容
            ctx.set("isSuccess", false);
            return null;
        }
        try {
            boolean reAuth = false;
            if(JWTUtil.validateTokenEffective( token.toString() )) {//过期失效
                reAuth = true;
            }
            if(reAuth){
                setFailedRequest("token过期", 40001);
                return null;
            }else {
                ctx.addZuulRequestHeader("token",token);
            }
        } catch (Exception e) {
            setFailedRequest(e.getMessage(), 40001);
            return null;
        }
        return null;
    }

    /**
     * URI是否以什么打头
     * @param requestUri
     * @return
     */
    private boolean isStartWith(String requestUri) {
        boolean flag = false;
        for (String s : startWith.split(",")) {
            if (requestUri.startsWith(s)) {
                return true;
            }
        }
        return flag;
    }

    /**
     * 网关抛异常
     * @param body
     * @param code
     */
    private void setFailedRequest(String body, int code) {
        logger.debug("Reporting error ({}): {}", code, body);
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(code);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody(body);
            ctx.setSendZuulResponse(false);
        }
    }
}
