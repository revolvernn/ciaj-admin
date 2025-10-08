package com.ciaj.boot.component.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: Ciaj.
 * @Date: 2018/12/6 14:43
 * @Description:
 */
public class ResubmitFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		ServletRequest requestWrapper = null;
		if (request instanceof HttpServletRequest) {
			requestWrapper = new ResubmitRequestWrapper((HttpServletRequest) request);
		}
		if (null == requestWrapper) {
			chain.doFilter(request, response);
		} else {
			chain.doFilter(requestWrapper, response);
		}
	}

	@Override
	public void destroy() {
	}
}